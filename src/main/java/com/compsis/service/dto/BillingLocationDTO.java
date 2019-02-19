package com.compsis.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the BillingLocation entity.
 */
@ApiModel(description = "Localização (praça)")
public class BillingLocationDTO implements Serializable {

    private Long id;

    private String description;

    private String code;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BillingLocationDTO billingLocationDTO = (BillingLocationDTO) o;
        if (billingLocationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), billingLocationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BillingLocationDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
