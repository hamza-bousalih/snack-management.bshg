package org.bshg.demo.zsecurity.core.ws.dtos;

public record LoginResponseDto(
    String token,
    Long id,
    String username,
    String fullname,
    String email
    // String roles
) {
}