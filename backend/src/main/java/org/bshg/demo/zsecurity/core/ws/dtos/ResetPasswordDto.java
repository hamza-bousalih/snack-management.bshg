package org.bshg.demo.zsecurity.core.ws.dtos;

public record ResetPasswordDto(
    String token,
    String password
) {
}
