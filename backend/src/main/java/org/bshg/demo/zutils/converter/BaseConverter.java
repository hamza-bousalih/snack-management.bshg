package org.bshg.demo.zutils.converter;

import java.util.List;

public abstract class BaseConverter< T, DTO> {
    abstract protected void configure(boolean value);

    abstract protected T convertToItem(DTO dto);

    abstract protected DTO convertToDto(T item);

    public final DTO toDto(T item) {
        this.configure(false);
        var dto = item != null ? convertToDto(item) : null;
        this.configure(true);
        return dto;
    }

    public final T toItem(DTO dto) {
        return dto != null ? convertToItem(dto) : null;
    }

    public final List< T> toItem(List< DTO> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(this::toItem).toList();
    }

    public final List< DTO> toDto(List< T> items) {
        if (items == null) return null;
        return items.stream().map(this::toDto).toList();
    }
}