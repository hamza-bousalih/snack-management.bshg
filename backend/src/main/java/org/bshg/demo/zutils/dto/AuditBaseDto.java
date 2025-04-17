package org.bshg.demo.zutils.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuditBaseDto extends BaseDto {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LocalDateTime createdOn;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LocalDateTime updatedOn;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LocalDateTime createdBy;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LocalDateTime updatedBy;

    public LocalDateTime getCreatedOn() {
        return this.createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public LocalDateTime getCreatedBy() {
        return this.createdBy;
    }

    public LocalDateTime getUpdatedBy() {
        return this.updatedBy;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public void setCreatedBy(LocalDateTime createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdatedBy(LocalDateTime updatedBy) {
        this.updatedBy = updatedBy;
    }
}
