package org.bshg.demo.zsecurity.core.services.auth;

import jakarta.mail.MessagingException;
import org.bshg.demo.exceptions.NotFoundException;
import org.bshg.demo.zmail.services.EmailService;
import org.bshg.demo.zsecurity.config.jwt.service.JwtService;
import org.bshg.demo.zsecurity.core.entities.AppUser;
import org.bshg.demo.zsecurity.core.entities.Token;
import org.bshg.demo.zsecurity.core.entities.TokenType;
import org.bshg.demo.zsecurity.core.repositories.AppUserRepository;
import org.bshg.demo.zsecurity.core.repositories.RoleRepository;
import org.bshg.demo.zsecurity.core.repositories.TokenRepository;
import org.bshg.demo.zsecurity.core.ws.dtos.LoginRequestDto;
import org.bshg.demo.zsecurity.core.ws.dtos.LoginResponseDto;
import org.bshg.demo.zsecurity.core.ws.dtos.RegisterDto;
import org.bshg.demo.zsecurity.core.ws.dtos.ResetPasswordDto;
import org.bshg.demo.zsecurity.exceptions.InvalidJwtTokenException;
import org.bshg.demo.zsecurity.exceptions.TokenExpiredException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bshg.demo.zsecurity.core.entities.TokenType.*;

@Service
public class AuthServiceImpl implements AuthService {
    @Value("${app.auth.activation.token.duration}")
    private int activateTokenDuration;
    @Value("${app.auth.activation.token.length}")
    private int tokenLength;
    @Value("${app.auth.activation.token.characters}")
    private String tokenCharacters;

    private final RoleRepository roleRepository;
    private final AppUserRepository appUserRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(
            RoleRepository roleRepository,
            AppUserRepository appUserRepository,
            TokenRepository tokenRepository,
            PasswordEncoder passwordEncoder,
            EmailService emailService,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {
        this.roleRepository = roleRepository;
        this.appUserRepository = appUserRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public AppUser register(RegisterDto dto) throws MessagingException {
        var role = roleRepository.findByName("USER")
                .orElseThrow(() -> new NotFoundException("Role not found"));

        var appUser = new AppUser();
        appUser.setFirstname(dto.firstname());
        appUser.setLastname(dto.lastname());
        appUser.setEmail(dto.email());
        appUser.setPhone(dto.phone());
        appUser.setPassword(passwordEncoder.encode(dto.password()));
        appUser.setRoles(List.of(role));

        appUserRepository.save(appUser);
        sendValidationEmail(appUser);
        return appUser;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
        );
        Map< String, Object> claims = new HashMap<>();
        var appUser = (AppUser) auth.getPrincipal();
        claims.put("fullname", appUser.getfullname());
        var jwt = jwtService.generate(claims, appUser);
        return new LoginResponseDto(
            jwt,
            appUser.getId(),
            appUser.getUsername(),
            appUser.getfullname(),
            appUser.getEmail()
        );
    }

    @Override
    public LoginResponseDto validateJwtToken(String jwtToken) {
        var username = jwtService.extractUsername(jwtToken);
        var appUser = appUserRepository.findByEmail(username).orElseThrow(InvalidJwtTokenException::new);
        return new LoginResponseDto(jwtToken, appUser.getId(), appUser.getUsername(), appUser.getfullname(), appUser.getEmail());
    }

    @Override
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = validateToken(token, ACTIVATE_ACCOUNT);
        var appUser = savedToken.getAppUser();
        appUser.setEnabled(true);
        appUserRepository.save(appUser);
    }

    @Override
    public void sendForgetPasswordToken(String email) throws MessagingException {
        var appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("There is no account with email: " + email));

        var token = generateAndSaveToken(appUser, FORGET_PASSWORD);
        emailService.sendForgetPasswordEmail(appUser.getEmail(), appUser.getfullname(), token);
    }

    @Override
    public String validateForgetPasswordToken(String token) throws MessagingException {
        Token savedToken = validateToken(token, FORGET_PASSWORD);
        return generateAndSaveToken(savedToken.getAppUser(), RESET_PASSWORD);
    }

    @Override
    public void resetPassword(ResetPasswordDto dto) throws MessagingException {
        // TODO: validate dto: be sure that the token and new password are passed
        Token savedToken = validateToken(dto.token(), RESET_PASSWORD);
        AppUser appUser = savedToken.getAppUser();
        appUser.setPassword(passwordEncoder.encode(dto.password()));
        appUserRepository.save(appUser);
    }

    private Token validateToken(String token, TokenType type) throws MessagingException {
        Token savedToken = tokenRepository.findByTokenAndType(token, type)
                .orElseThrow(() -> new NotFoundException("Token Not Found"));

        var appUser = appUserRepository.findById(savedToken.getAppUser().getId())
                .orElseThrow(() -> new NotFoundException("There is no account linked with the token!"));

        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            switch (type) {
                case ACTIVATE_ACCOUNT -> sendValidationEmail(appUser);
                case FORGET_PASSWORD -> sendForgetPasswordToken(appUser.getEmail());
            }
            throw new TokenExpiredException();
        }

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);

        savedToken.setAppUser(appUser);
        return savedToken;
    }

    public void sendValidationEmail(AppUser appUser) throws MessagingException {
        var token = generateAndSaveToken(appUser, ACTIVATE_ACCOUNT);
        emailService.sendActivationEmail(appUser.getEmail(), appUser.getfullname(), token);
    }

    private String generateAndSaveToken(AppUser appUser, TokenType type) {
        String generatedToken = generateActivationCode();

        var token = new Token();
        token.setToken(generatedToken);
        token.setAppUser(appUser);
        token.setType(type);
        token.setCreatedAt(LocalDateTime.now());
        token.setExpiresAt(LocalDateTime.now().plusMinutes(activateTokenDuration));

        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode() {
        var builder = new StringBuilder();
        var secureRan = new SecureRandom();
        for (int i = 0; i < tokenLength; i++) {
            var ran = secureRan.nextInt(tokenCharacters.length());
            builder.append(tokenCharacters.charAt(ran));
        }
        return builder.toString();
    }
}
