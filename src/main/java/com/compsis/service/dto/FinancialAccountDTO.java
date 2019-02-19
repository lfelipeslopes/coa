package com.compsis.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the FinancialAccount entity.
 */
@ApiModel(description = "Conta")
public class FinancialAccountDTO implements Serializable {

    private Long id;

    private String alias;

    private BigDecimal balance;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FinancialAccountDTO financialAccountDTO = (FinancialAccountDTO) o;
        if (financialAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), financialAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FinancialAccountDTO{" +
            "id=" + getId() +
            ", alias='" + getAlias() + "'" +
            ", balance=" + getBalance() +
            "}";
    }
}
