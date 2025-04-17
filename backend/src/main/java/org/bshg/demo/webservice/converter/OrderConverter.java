package org.bshg.demo.webservice.converter;

import org.bshg.demo.entity.core.Order;
import org.bshg.demo.webservice.dto.OrderDto;
import org.bshg.demo.zutils.converter.BaseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderConverter extends BaseConverter<Order, OrderDto> {
    @Autowired private UserConverter userConverter;
    @Autowired private TableConverter tableConverter;
    @Autowired private OrderItemConverter orderItemConverter;
    private boolean creator = true;
    private boolean table = true;
    private boolean orderItem = true;

    protected void configure(boolean value) {
        this.orderItemConverter.setOrder(value);
    }

    protected Order convertToItem(OrderDto dto) {
        var item = new Order();
        item.setId(dto.getId());
        item.setStatus(dto.getStatus());
        item.setCreatedAt(dto.getCreatedAt());
        item.setCreator(userConverter.toItem(dto.getCreator()));
        item.setTable(tableConverter.toItem(dto.getTable()));
        item.setOrderItem(orderItemConverter.toItem(dto.getOrderItem()));
        return item;
    }

    protected OrderDto convertToDto(Order item) {
        var dto = new OrderDto();
        dto.setId(item.getId());
        dto.setStatus(item.getStatus());
        dto.setCreatedAt(item.getCreatedAt());
        dto.setCreator(creator? userConverter.toDto(item.getCreator()): null);
        dto.setTable(table? tableConverter.toDto(item.getTable()): null);
        dto.setOrderItem(orderItem? orderItemConverter.toDto(item.getOrderItem()): null);
        return dto;
    }

    public void setCreator(boolean value) {
        this.creator = value;
    }
    public void setTable(boolean value) {
        this.table = value;
    }
    public void setOrderItem(boolean value) {
        this.orderItem = value;
    }
}
