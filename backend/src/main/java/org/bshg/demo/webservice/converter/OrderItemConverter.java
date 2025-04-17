package org.bshg.demo.webservice.converter;

import org.bshg.demo.entity.core.OrderItem;
import org.bshg.demo.webservice.dto.OrderItemDto;
import org.bshg.demo.zutils.converter.BaseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderItemConverter extends BaseConverter<OrderItem, OrderItemDto> {
    @Autowired private OrderConverter orderConverter;
    @Autowired private MenuItemConverter menuItemConverter;
    private boolean order = true;
    private boolean menuItem = true;

    protected void configure(boolean value) {
        this.orderConverter.setOrderItem(value);
        this.menuItemConverter.setOrderItem(value);
    }

    protected OrderItem convertToItem(OrderItemDto dto) {
        var item = new OrderItem();
        item.setId(dto.getId());
        item.setQuantity(dto.getQuantity());
        item.setOrder(orderConverter.toItem(dto.getOrder()));
        item.setMenuItem(menuItemConverter.toItem(dto.getMenuItem()));
        return item;
    }

    protected OrderItemDto convertToDto(OrderItem item) {
        var dto = new OrderItemDto();
        dto.setId(item.getId());
        dto.setQuantity(item.getQuantity());
        dto.setOrder(order? orderConverter.toDto(item.getOrder()): null);
        dto.setMenuItem(menuItem? menuItemConverter.toDto(item.getMenuItem()): null);
        return dto;
    }

    public void setOrder(boolean value) {
        this.order = value;
    }
    public void setMenuItem(boolean value) {
        this.menuItem = value;
    }
}
