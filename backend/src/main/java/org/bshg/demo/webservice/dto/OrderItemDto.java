package org.bshg.demo.webservice.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.*;
import java.util.List;
import java.math.BigDecimal;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemDto {
private Long id;
private int quantity;
private OrderDto order;
private MenuItemDto menuItem;
public Long getId() {
return id;
}
public void setId(Long id) {
this.id = id;
}
public int getQuantity() {
return quantity;
}
public void setQuantity(int value) {
this.quantity = value;
}
public OrderDto getOrder() {
return order;
}
public void setOrder(OrderDto value) {
this.order = value;
}
public MenuItemDto getMenuItem() {
return menuItem;
}
public void setMenuItem(MenuItemDto value) {
this.menuItem = value;
}
}