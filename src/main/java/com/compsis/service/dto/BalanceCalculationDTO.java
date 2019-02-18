package com.compsis.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the BalanceCalculation entity.
 */
@ApiModel(description = "Balanço")
public class BalanceCalculationDTO implements Serializable {

    private Long id;

    private BigDecimal balance;


    private Long accountOperationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getAccountOperationId() {
        return accountOperationId;
    }

    public void setAccountOperationId(Long accountOperationId) {
        this.accountOperationId = accountOperationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BalanceCalculationDTO balanceCalculationDTO = (BalanceCalculationDTO) o;
        if (balanceCalculationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), balanceCalculationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BalanceCalculationDTO{" +
            "id=" + getId() +
            ", balance=" + getBalance() +
            ", accountOperation=" + getAccountOperationId() +
            "}";
    }
}
