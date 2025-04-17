package org.bshg.demo.zsecurity.core.ws.converter;

import org.bshg.demo.zsecurity.core.entities.AppUser;
import org.bshg.demo.zsecurity.core.ws.dtos.AppUserDto;
import org.bshg.demo.zutils.converter.BaseConverter;
import org.springframework.stereotype.Component;

@Component
public class AppUserConverter extends BaseConverter< AppUser, AppUserDto> {

    public void configure(boolean value) {
    }

    protected AppUser convertToItem(AppUserDto dto) {
        var item = new AppUser();
        item.setId(dto.getId());
        item.setEnabled(dto.isEnabled());
        item.setEmail(dto.getEmail());
        item.setAccountLocked(dto.isAccountLocked());
        item.setLastname(dto.getLastname());
        item.setFirstname(dto.getFirstname());
        item.setRoles(dto.getRoles());
        return item;
    }

    protected AppUserDto convertToDto(AppUser item) {
        var dto = new AppUserDto();
        dto.setId(item.getId());
        dto.setEnabled(item.isEnabled());
        dto.setEmail(item.getEmail());
        dto.setAccountLocked(item.isAccountLocked());
        dto.setLastname(item.getLastname());
        dto.setFirstname(item.getFirstname());
        dto.setRoles(item.getRoles());
        return dto;
    }

    public < T extends AppUser, DTO extends AppUserDto> void convertToItem(DTO dto, T item) {
        var converted = convertToItem(dto);
        item.setId(converted.getId());
        item.setEnabled(converted.isEnabled());
        item.setEmail(converted.getEmail());
        item.setAccountLocked(converted.isAccountLocked());
        item.setLastname(converted.getLastname());
        item.setFirstname(converted.getFirstname());
        item.setRoles(converted.getRoles());
    }

    public < T extends AppUser, DTO extends AppUserDto> void convertToDto(T item, DTO dto) {
        var converted = convertToDto(item);
        dto.setId(converted.getId());
        dto.setEnabled(converted.isEnabled());
        dto.setEmail(converted.getEmail());
        dto.setAccountLocked(converted.isAccountLocked());
        dto.setLastname(converted.getLastname());
        dto.setFirstname(converted.getFirstname());
        dto.setRoles(converted.getRoles());
    }
}