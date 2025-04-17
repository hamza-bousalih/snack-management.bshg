package org.bshg.demo.entity.core;
import org.bshg.demo.entity.enums.TableStatusEnum;
import jakarta.persistence.*;
import java.time.*;
import java.util.*;
import java.math.BigDecimal;
@Entity
@jakarta.persistence.Table(name="snack_table")
public class Table {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private int tableNumber;
@Enumerated(EnumType.STRING)
private TableStatusEnum status;
public Table() {
}
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
@Override
public boolean equals(Object object) {
if (object instanceof Table table)
return table.getId().equals(this.getId());
return false;
}
@Override
public int hashCode() {return (this.getId() == null) ? 0 : this.getId().hashCode();}
}