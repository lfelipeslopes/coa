package com.compsis.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the AccountOperation entity.
 */
@ApiModel(description = "Operação da conta")
public class AccountOperationDTO implements Serializable {

    private Long id;

    private ZonedDateTime occurrenceDate;


    private Long financialAccountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getOccurrenceDate() {
        return occurrenceDate;
    }

    public void setOccurrenceDate(ZonedDateTime occurrenceDate) {
        this.occurrenceDate = occurrenceDate;
    }

    public Long getFinancialAccountId() {
        return financialAccountId;
    }

    public void setFinancialAccountId(Long financialAccountId) {
        this.financialAccountId = financialAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AccountOperationDTO accountOperationDTO = (AccountOperationDTO) o;
        if (accountOperationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accountOperationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccountOperationDTO{" +
            "id=" + getId() +
            ", occurrenceDate='" + getOccurrenceDate() + "'" +
            ", financialAccount=" + getFinancialAccountId() +
            "}";
    }
}
