package com.compsis.service.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import com.compsis.domain.enumeration.AutomaticOperationStatus;
import com.compsis.domain.enumeration.AutomaticEvent;
import com.compsis.domain.enumeration.TransactionType;

/**
 * A DTO for the AutomaticOperation entity.
 */
public class AutomaticOperationDTO implements Serializable {

    private Long id;

    private BigDecimal amount;

    private Boolean recurrent;

    private AutomaticOperationStatus automatictOperationStatus;

    private AutomaticEvent automatictEvent;

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

    public Boolean isRecurrent() {
        return recurrent;
    }

    public void setRecurrent(Boolean recurrent) {
        this.recurrent = recurrent;
    }

    public AutomaticOperationStatus getAutomatictOperationStatus() {
        return automatictOperationStatus;
    }

    public void setAutomatictOperationStatus(AutomaticOperationStatus automatictOperationStatus) {
        this.automatictOperationStatus = automatictOperationStatus;
    }

    public AutomaticEvent getAutomatictEvent() {
        return automatictEvent;
    }

    public void setAutomatictEvent(AutomaticEvent automatictEvent) {
        this.automatictEvent = automatictEvent;
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

        AutomaticOperationDTO automaticOperationDTO = (AutomaticOperationDTO) o;
        if (automaticOperationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), automaticOperationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AutomaticOperationDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", recurrent='" + isRecurrent() + "'" +
            ", automatictOperationStatus='" + getAutomatictOperationStatus() + "'" +
            ", automatictEvent='" + getAutomatictEvent() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            ", accountOperation=" + getAccountOperationId() +
            "}";
    }
}
