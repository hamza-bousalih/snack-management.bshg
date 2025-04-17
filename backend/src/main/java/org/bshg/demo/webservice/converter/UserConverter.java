package org.bshg.demo.webservice.converter;

import org.bshg.demo.entity.core.User;
import org.bshg.demo.webservice.dto.UserDto;
import org.bshg.demo.zsecurity.core.ws.converter.AppUserConverter;
import org.bshg.demo.zutils.converter.BaseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter extends BaseConverter<User, UserDto> {
    @Autowired private AppUserConverter appUserConverter;

    protected void configure(boolean value) {
    }

    protected User convertToItem(UserDto dto) {
        var item = new User();
        appUserConverter.convertToItem(dto, item);
        item.setFullname(dto.getFullname());
        return item;
    }

    protected UserDto convertToDto(User item) {
        var dto = new UserDto();
        appUserConverter.convertToDto(item, dto);
        dto.setFullname(item.getFullname());
        return dto;
    }

}
