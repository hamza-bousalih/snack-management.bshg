package org.bshg.demo.webservice.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.*;
import java.util.List;
import java.math.BigDecimal;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillDto {
private Long id;
private float totalAmount;
private float discount;
private float tax;
private float finalAmmount;
@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
private LocalDateTime createdAt;
private OrderDto order;
private UserDto issuer;
public Long getId() {
return id;
}
public void setId(Long id) {
this.id = id;
}
public float getTotalAmount() {
return totalAmount;
}
public void setTotalAmount(float value) {
this.totalAmount = value;
}
public float getDiscount() {
return discount;
}
public void setDiscount(float value) {
this.discount = value;
}
public float getTax() {
return tax;
}
public void setTax(float value) {
this.tax = value;
}
public float getFinalAmmount() {
return finalAmmount;
}
public void setFinalAmmount(float value) {
this.finalAmmount = value;
}
public LocalDateTime getCreatedAt() {
return createdAt;
}
public void setCreatedAt(LocalDateTime value) {
this.createdAt = value;
}
public OrderDto getOrder() {
return order;
}
public void setOrder(OrderDto value) {
this.order = value;
}
public UserDto getIssuer() {
return issuer;
}
public void setIssuer(UserDto value) {
this.issuer = value;
}
}