package org.bshg.demo.entity.core;
import jakarta.persistence.*;
import java.time.*;
import java.util.*;
import java.math.BigDecimal;
@Entity
@Table(name="menu_item")
public class MenuItem {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
@Lob
private String description;
private float price;
private String catrgory;
@OneToMany(mappedBy = "menuItem")
private List<OrderItem> orderItem;
public MenuItem() {
}
public MenuItem(Long id, String label) {
// constructor to get optimized fields
this.id = id;
this.name = label;
}
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
public List<OrderItem> getOrderItem() {
return orderItem;
}
public void setOrderItem(List<OrderItem> value) {
this.orderItem = value;
}
@Override
public boolean equals(Object object) {
if (object instanceof MenuItem menuItem)
return menuItem.getId().equals(this.getId());
return false;
}
@Override
public int hashCode() {return (this.getId() == null) ? 0 : this.getId().hashCode();}
}