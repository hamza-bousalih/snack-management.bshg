package org.bshg.demo.zsecurity.core.services.auth;

import jakarta.mail.MessagingException;
import org.bshg.demo.zsecurity.core.entities.AppUser;
import org.bshg.demo.zsecurity.core.ws.dtos.LoginRequestDto;
import org.bshg.demo.zsecurity.core.ws.dtos.LoginResponseDto;
import org.bshg.demo.zsecurity.core.ws.dtos.RegisterDto;
import org.bshg.demo.zsecurity.core.ws.dtos.ResetPasswordDto;

public interface AuthService {
    AppUser register(RegisterDto dto) throws MessagingException;

    LoginResponseDto login(LoginRequestDto dto);

    LoginResponseDto validateJwtToken(String jwtToken);

    void activateAccount(String token) throws MessagingException;

    void sendForgetPasswordToken(String email) throws MessagingException;

    String validateForgetPasswordToken(String token) throws MessagingException;

    void resetPassword(ResetPasswordDto dto) throws MessagingException;
}
