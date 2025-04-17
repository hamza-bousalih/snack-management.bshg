package org.bshg.demo.webservice.converter;

import org.bshg.demo.entity.core.Table;
import org.bshg.demo.webservice.dto.TableDto;
import org.bshg.demo.zutils.converter.BaseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TableConverter extends BaseConverter<Table, TableDto> {

    protected void configure(boolean value) {
    }

    protected Table convertToItem(TableDto dto) {
        var item = new Table();
        item.setId(dto.getId());
        item.setTableNumber(dto.getTableNumber());
        item.setStatus(dto.getStatus());
        return item;
    }

    protected TableDto convertToDto(Table item) {
        var dto = new TableDto();
        dto.setId(item.getId());
        dto.setTableNumber(item.getTableNumber());
        dto.setStatus(item.getStatus());
        return dto;
    }

}
