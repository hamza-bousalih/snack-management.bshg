package org.bshg.demo.zsecurity.core.ws.apis;

import jakarta.mail.MessagingException;
import org.bshg.demo.zsecurity.core.entities.AppUser;
import org.bshg.demo.zsecurity.core.services.auth.AuthService;
import org.bshg.demo.zsecurity.core.ws.converter.AppUserConverter;
import org.bshg.demo.zsecurity.core.ws.dtos.*;
import org.bshg.demo.zutils.dto.DtoObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthApi {
    private final AuthService service;
    private final AppUserConverter appUserConverter;

    public AuthApi(AuthService service, AppUserConverter appUserConverter) {
        this.service = service;
        this.appUserConverter = appUserConverter;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity< AppUserDto> register(@RequestBody RegisterDto dto) throws MessagingException {
        AppUser registered = service.register(dto);
        return ResponseEntity
                .accepted()
                .body(appUserConverter.toDto(registered));
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity< LoginResponseDto> register(@RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok(service.login(dto));
    }

    @PostMapping("/validate-token")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity< LoginResponseDto> register(@RequestBody LoginResponseDto token) {
        return ResponseEntity.ok(service.validateJwtToken(token.token()));
    }

    @PostMapping("/activate-account")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity< DtoObject<?>> activateAccount(@RequestParam(name = "token") String token) throws MessagingException {
        service.activateAccount(token);
        return ResponseEntity.ok(DtoObject.data(null));
    }

    @PostMapping("/forget-password/send-token")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity< DtoObject<?>> sendForgetPasswordToken(@RequestParam(name = "email") String email) throws MessagingException {
        service.sendForgetPasswordToken(email);
        return ResponseEntity.ok(DtoObject.data(null));
    }

    @PostMapping("/forget-password/validate-token")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity< DtoObject< String>> validateForgetPasswordToken(@RequestParam(name = "token") String token) throws MessagingException {
        String resetPasswordToken = service.validateForgetPasswordToken(token);
        return ResponseEntity.ok(DtoObject.data(resetPasswordToken));
    }

    @PostMapping("/forget-password/reset")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity< DtoObject<?>> resetPassword(@RequestBody ResetPasswordDto dto) throws MessagingException {
        service.resetPassword(dto);
        return ResponseEntity.ok(DtoObject.data(null));
    }
}
