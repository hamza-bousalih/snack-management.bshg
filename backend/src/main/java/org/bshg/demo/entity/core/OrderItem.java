package org.bshg.demo.entity.core;
import jakarta.persistence.*;
import java.time.*;
import java.util.*;
import java.math.BigDecimal;
@Entity
@Table(name="order_item")
public class OrderItem {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private int quantity;
@ManyToOne(fetch = FetchType.LAZY)
private Order order;
@ManyToOne(fetch = FetchType.LAZY)
private MenuItem menuItem;
public OrderItem() {
}
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
public Order getOrder() {
return order;
}
public void setOrder(Order value) {
this.order = value;
}
public MenuItem getMenuItem() {
return menuItem;
}
public void setMenuItem(MenuItem value) {
this.menuItem = value;
}
@Override
public boolean equals(Object object) {
if (object instanceof OrderItem orderItem)
return orderItem.getId().equals(this.getId());
return false;
}
@Override
public int hashCode() {return (this.getId() == null) ? 0 : this.getId().hashCode();}
}