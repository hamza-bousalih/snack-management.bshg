package org.bshg.demo.zsecurity.config.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.bshg.demo.zsecurity.exceptions.ExpiredJwtTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${app.auth.jwt.expiration}")
    private long jetExpiration;
    @Value("${app.auth.jwt.secret-key}")
    private String secretKey;

    @Override
    public String generate(UserDetails userDetails) {
        return generate(new HashMap<>(), userDetails);
    }

    @Override
    public String generate(Map< String, Object> claims, UserDetails userDetails) {
        return buildToken(claims, userDetails, jetExpiration);
    }

    @Override
    public boolean validate(String jwt, UserDetails userDetails) {
        var username = extractUsername(jwt);
        return username.equals(userDetails.getUsername())
                && !isExpired(jwt);
    }

    @Override
    public boolean isExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

    @Override
    public Date extractExpiration(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    @Override
    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    @Override
    public < T> T extractClaim(String jwt, Function< Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwt) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtTokenException();
        }
    }

    private String buildToken(Map< String, Object> extraClaims, UserDetails userDetails, long jwtExpiration) {
        var authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .claim("authorities", authorities)
                .signWith(getSignInKey())
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
