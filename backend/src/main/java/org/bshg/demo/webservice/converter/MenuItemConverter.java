package org.bshg.demo.webservice.converter;

import org.bshg.demo.entity.core.MenuItem;
import org.bshg.demo.webservice.dto.MenuItemDto;
import org.bshg.demo.zutils.converter.BaseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuItemConverter extends BaseConverter<MenuItem, MenuItemDto> {
    @Autowired private OrderItemConverter orderItemConverter;
    private boolean orderItem = true;

    protected void configure(boolean value) {
        this.orderItemConverter.setMenuItem(value);
    }

    protected MenuItem convertToItem(MenuItemDto dto) {
        var item = new MenuItem();
        item.setId(dto.getId());
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        item.setCatrgory(dto.getCatrgory());
        item.setOrderItem(orderItemConverter.toItem(dto.getOrderItem()));
        return item;
    }

    protected MenuItemDto convertToDto(MenuItem item) {
        var dto = new MenuItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setPrice(item.getPrice());
        dto.setCatrgory(item.getCatrgory());
        dto.setOrderItem(orderItem? orderItemConverter.toDto(item.getOrderItem()): null);
        return dto;
    }

    public void setOrderItem(boolean value) {
        this.orderItem = value;
    }
}
