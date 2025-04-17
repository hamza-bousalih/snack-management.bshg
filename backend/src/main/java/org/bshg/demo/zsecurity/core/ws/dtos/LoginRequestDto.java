package org.bshg.demo.zsecurity.core.ws.dtos;

public record LoginRequestDto(
        String email,
        String password
) {
}
