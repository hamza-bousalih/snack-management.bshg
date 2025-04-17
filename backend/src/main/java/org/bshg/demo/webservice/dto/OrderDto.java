package org.bshg.demo.webservice.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.bshg.demo.entity.enums.OrderStatusEnum;
import java.time.*;
import java.util.List;
import java.math.BigDecimal;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {
private Long id;
private OrderStatusEnum status;
@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
private LocalDateTime createdAt;
private UserDto creator;
private TableDto table;
private List<OrderItemDto> orderItem;
public Long getId() {
return id;
}
public void setId(Long id) {
this.id = id;
}
public OrderStatusEnum getStatus() {
return status;
}
public void setStatus(OrderStatusEnum value) {
this.status = value;
}
public LocalDateTime getCreatedAt() {
return createdAt;
}
public void setCreatedAt(LocalDateTime value) {
this.createdAt = value;
}
public UserDto getCreator() {
return creator;
}
public void setCreator(UserDto value) {
this.creator = value;
}
public TableDto getTable() {
return table;
}
public void setTable(TableDto value) {
this.table = value;
}
public List<OrderItemDto> getOrderItem() {
return orderItem;
}
public void setOrderItem(List<OrderItemDto> value) {
this.orderItem = value;
}
}