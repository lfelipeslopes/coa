package com.compsis.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import com.compsis.domain.enumeration.TransactionType;

/**
 * A DTO for the AccountTransction entity.
 */
@ApiModel(description = "Transações da conta")
public class AccountTransctionDTO implements Serializable {

    private Long id;

    private BigDecimal amount;

    private String origin;

    private TransactionType transactionType;


    private Long accountOperationId;

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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
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

        AccountTransctionDTO accountTransctionDTO = (AccountTransctionDTO) o;
        if (accountTransctionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accountTransctionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccountTransctionDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", origin='" + getOrigin() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            ", accountOperation=" + getAccountOperationId() +
            "}";
    }
}
