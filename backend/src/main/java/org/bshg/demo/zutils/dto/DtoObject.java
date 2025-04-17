package org.bshg.demo.zutils.dto;

public record DtoObject< T>(T data) {
    public static < T> DtoObject< T> data(T data) {
        return new DtoObject<>(data);
    }
}
