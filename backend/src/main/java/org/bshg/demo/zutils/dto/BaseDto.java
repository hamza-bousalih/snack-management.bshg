package org.bshg.demo.zutils.dto;

import java.io.Serializable;

public class BaseDto implements Serializable {
    protected Long id;

    @Override
    public boolean equals(Object object) {
        if (this.id != null && object instanceof BaseDto dto)
            return this.id.equals(dto.getId());
        return false;
    }


    public int hashCode() {
        Serializable pk = id;
        return pk == null ? 0 : pk.toString().hashCode();
    }

    public String toString() {
        return this.getId() != null ? this.getId().toString() : null;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
