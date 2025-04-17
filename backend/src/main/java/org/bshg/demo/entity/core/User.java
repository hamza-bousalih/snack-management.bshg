package org.bshg.demo.entity.core;
import org.bshg.demo.zsecurity.core.entities.AppUser;
import jakarta.persistence.*;
import java.time.*;
import java.util.*;
import java.math.BigDecimal;
@Entity
@jakarta.persistence.Table(name="snack_user")
public class User extends AppUser {
private String fullname;
public User() {
super();
}
public String getFullname() {
return fullname;
}
public void setFullname(String value) {
this.fullname = value;
}
@Override
public boolean equals(Object object) {
if (object instanceof User user)
return user.getId().equals(this.getId());
return false;
}
@Override
public int hashCode() {return (this.getId() == null) ? 0 : this.getId().hashCode();}
}