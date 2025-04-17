package org.bshg.demo.webservice.converter;

import org.bshg.demo.entity.core.Bill;
import org.bshg.demo.webservice.dto.BillDto;
import org.bshg.demo.zutils.converter.BaseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BillConverter extends BaseConverter<Bill, BillDto> {
    @Autowired private OrderConverter orderConverter;
    @Autowired private UserConverter userConverter;
    private boolean order = true;
    private boolean issuer = true;

    protected void configure(boolean value) {
    }

    protected Bill convertToItem(BillDto dto) {
        var item = new Bill();
        item.setId(dto.getId());
        item.setTotalAmount(dto.getTotalAmount());
        item.setDiscount(dto.getDiscount());
        item.setTax(dto.getTax());
        item.setFinalAmmount(dto.getFinalAmmount());
        item.setCreatedAt(dto.getCreatedAt());
        item.setOrder(orderConverter.toItem(dto.getOrder()));
        item.setIssuer(userConverter.toItem(dto.getIssuer()));
        return item;
    }

    protected BillDto convertToDto(Bill item) {
        var dto = new BillDto();
        dto.setId(item.getId());
        dto.setTotalAmount(item.getTotalAmount());
        dto.setDiscount(item.getDiscount());
        dto.setTax(item.getTax());
        dto.setFinalAmmount(item.getFinalAmmount());
        dto.setCreatedAt(item.getCreatedAt());
        dto.setOrder(order? orderConverter.toDto(item.getOrder()): null);
        dto.setIssuer(issuer? userConverter.toDto(item.getIssuer()): null);
        return dto;
    }

    public void setOrder(boolean value) {
        this.order = value;
    }
    public void setIssuer(boolean value) {
        this.issuer = value;
    }
}
