package com.compsis.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the Passage entity.
 */
@ApiModel(description = "Passagem")
public class PassageDTO implements Serializable {

    private Long id;

    private BigDecimal amount;

    private Boolean automaticPassage;

    private Integer lane;

    private ZonedDateTime occurrenceDate;

    private Long passageIdentification;

    private String plate;

    private ZonedDateTime processedAt;


    private Long vehicleId;

    private Long classifiedClassId;

    private Long detectClassId;

    private Long chargedClassId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean isAutomaticPassage() {
        return automaticPassage;
    }

    public void setAutomaticPassage(Boolean automaticPassage) {
        this.automaticPassage = automaticPassage;
    }

    public Integer getLane() {
        return lane;
    }

    public void setLane(Integer lane) {
        this.lane = lane;
    }

    public ZonedDateTime getOccurrenceDate() {
        return occurrenceDate;
    }

    public void setOccurrenceDate(ZonedDateTime occurrenceDate) {
        this.occurrenceDate = occurrenceDate;
    }

    public Long getPassageIdentification() {
        return passageIdentification;
    }

    public void setPassageIdentification(Long passageIdentification) {
        this.passageIdentification = passageIdentification;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public ZonedDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(ZonedDateTime processedAt) {
        this.processedAt = processedAt;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getClassifiedClassId() {
        return classifiedClassId;
    }

    public void setClassifiedClassId(Long vehicleClassId) {
        this.classifiedClassId = vehicleClassId;
    }

    public Long getDetectClassId() {
        return detectClassId;
    }

    public void setDetectClassId(Long vehicleClassId) {
        this.detectClassId = vehicleClassId;
    }

    public Long getChargedClassId() {
        return chargedClassId;
    }

    public void setChargedClassId(Long vehicleClassId) {
        this.chargedClassId = vehicleClassId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PassageDTO passageDTO = (PassageDTO) o;
        if (passageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), passageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PassageDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", automaticPassage='" + isAutomaticPassage() + "'" +
            ", lane=" + getLane() +
            ", occurrenceDate='" + getOccurrenceDate() + "'" +
            ", passageIdentification=" + getPassageIdentification() +
            ", plate='" + getPlate() + "'" +
            ", processedAt='" + getProcessedAt() + "'" +
            ", vehicle=" + getVehicleId() +
            ", classifiedClass=" + getClassifiedClassId() +
            ", detectClass=" + getDetectClassId() +
            ", chargedClass=" + getChargedClassId() +
            "}";
    }
}
