package org.bshg.demo.zsecurity.core.ws.dtos;

public record RegisterDto(
        String firstname,
        String lastname,
        String username,
        String email,
        String phone,
        String password
) {
}
