package org.bshg.demo.zsecurity.config.jwt.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String generate(UserDetails userDetails);

    String generate(Map< String, Object> claims, UserDetails userDetails);

    boolean validate(String jwt, UserDetails userDetails);

    boolean isExpired(String jwt);

    Date extractExpiration(String jwt);

    String extractUsername(String jwt);

    < T> T extractClaim(String jwt, Function< Claims, T> claimsResolver);
}
