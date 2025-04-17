package org.bshg.demo.entity.enums;
public enum OrderStatusEnum {
    PENDING("PENDING"),
    PREPARING("PREPARING"),
    READY("READY"),
    SERVED("SERVED"),
    ;

    private final String value;
    
    OrderStatusEnum(String value) {
        this.value = value;
    }
    
    public String value() {
        return value;
    }
}