package org.bshg.demo.webservice.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.*;
import java.util.List;
import java.math.BigDecimal;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuItemDto {
private Long id;
private String name;
private String description;
private float price;
private String catrgory;
private List<OrderItemDto> orderItem;
public Long getId() {
return id;
}
public void setId(Long id) {
this.id = id;
}
public String getName() {
return name;
}
public void setName(String value) {
this.name = value;
}
public String getDescription() {
return description;
}
public void setDescription(String value) {
this.description = value;
}
public float getPrice() {
return price;
}
public void setPrice(float value) {
this.price = value;
}
public String getCatrgory() {
return catrgory;
}
public void setCatrgory(String value) {
this.catrgory = value;
}
public List<OrderItemDto> getOrderItem() {
return orderItem;
}
public void setOrderItem(List<OrderItemDto> value) {
this.orderItem = value;
}
}