package org.bshg.demo.entity.enums;
public enum TableStatusEnum {
    AVAILABLE("AVAILABLE"),
    OCCUPIED("OCCUPIED"),
    ;

    private final String value;
    
    TableStatusEnum(String value) {
        this.value = value;
    }
    
    public String value() {
        return value;
    }
}