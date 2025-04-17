package org.bshg.demo.webservice.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.bshg.demo.zsecurity.core.ws.dtos.AppUserDto;
import java.time.*;
import java.util.List;
import java.math.BigDecimal;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto extends AppUserDto {
private Long id;
private String fullname;
public Long getId() {
return id;
}
public void setId(Long id) {
this.id = id;
}
public String getFullname() {
return fullname;
}
public void setFullname(String value) {
this.fullname = value;
}
}