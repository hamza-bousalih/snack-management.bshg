package org.bshg.demo.webservice.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.bshg.demo.entity.enums.TableStatusEnum;
import java.time.*;
import java.util.List;
import java.math.BigDecimal;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableDto {
private Long id;
private int tableNumber;
private TableStatusEnum status;
public Long getId() {
return id;
}
public void setId(Long id) {
this.id = id;
}
public int getTableNumber() {
return tableNumber;
}
public void setTableNumber(int value) {
this.tableNumber = value;
}
public TableStatusEnum getStatus() {
return status;
}
public void setStatus(TableStatusEnum value) {
this.status = value;
}
}