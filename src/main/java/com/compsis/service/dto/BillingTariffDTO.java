package com.compsis.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the BillingTariff entity.
 */
@ApiModel(description = "Tarifa")
public class BillingTariffDTO implements Serializable {

    private Long id;

    private Integer dayOfWeek;

    private ZonedDateTime startedIn;

    private BigDecimal value;


    private Long idBillingTariffId;

    private Long billingLocationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public ZonedDateTime getStartedIn() {
        return startedIn;
    }

    public void setStartedIn(ZonedDateTime startedIn) {
        this.startedIn = startedIn;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Long getIdBillingTariffId() {
        return idBillingTariffId;
    }

    public void setIdBillingTariffId(Long vehicleClassId) {
        this.idBillingTariffId = vehicleClassId;
    }

    public Long getBillingLocationId() {
        return billingLocationId;
    }

    public void setBillingLocationId(Long billingLocationId) {
        this.billingLocationId = billingLocationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BillingTariffDTO billingTariffDTO = (BillingTariffDTO) o;
        if (billingTariffDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), billingTariffDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BillingTariffDTO{" +
            "id=" + getId() +
            ", dayOfWeek=" + getDayOfWeek() +
            ", startedIn='" + getStartedIn() + "'" +
            ", value=" + getValue() +
            ", idBillingTariff=" + getIdBillingTariffId() +
            ", billingLocation=" + getBillingLocationId() +
            "}";
    }
}
