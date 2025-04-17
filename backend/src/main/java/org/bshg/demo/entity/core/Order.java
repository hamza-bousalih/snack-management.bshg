package org.bshg.demo.entity.core;
import org.bshg.demo.entity.enums.OrderStatusEnum;
import jakarta.persistence.*;
import java.time.*;
import java.util.*;
import java.math.BigDecimal;
@Entity
@Table(name="order")
public class Order {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@Enumerated(EnumType.STRING)
private OrderStatusEnum status;
private LocalDateTime createdAt;
@OneToOne()
private User creator;
@ManyToOne(fetch = FetchType.LAZY)
private Table table;
@OneToMany(mappedBy = "order")
private List<OrderItem> orderItem;
public Order() {
}
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
public User getCreator() {
return creator;
}
public void setCreator(User value) {
this.creator = value;
}
public Table getTable() {
return table;
}
public void setTable(Table value) {
this.table = value;
}
public List<OrderItem> getOrderItem() {
return orderItem;
}
public void setOrderItem(List<OrderItem> value) {
this.orderItem = value;
}
@Override
public boolean equals(Object object) {
if (object instanceof Order order)
return order.getId().equals(this.getId());
return false;
}
@Override
public int hashCode() {return (this.getId() == null) ? 0 : this.getId().hashCode();}
}