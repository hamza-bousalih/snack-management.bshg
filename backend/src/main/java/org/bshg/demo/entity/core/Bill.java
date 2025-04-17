package org.bshg.demo.entity.core;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@jakarta.persistence.Table(name="bill")
public class Bill {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private float totalAmount;
private float discount;
private float tax;
private float finalAmmount;
private LocalDateTime createdAt;
@OneToOne() @JoinColumn(name = "snack_order")
private Order order;
@OneToOne()
private User issuer;
public Bill() {
}
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
public Order getOrder() {
return order;
}
public void setOrder(Order value) {
this.order = value;
}
public User getIssuer() {
return issuer;
}
public void setIssuer(User value) {
this.issuer = value;
}
@Override
public boolean equals(Object object) {
if (object instanceof Bill bill)
return bill.getId().equals(this.getId());
return false;
}
@Override
public int hashCode() {return (this.getId() == null) ? 0 : this.getId().hashCode();}
}