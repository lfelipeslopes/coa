package com.compsis.service.dto;
import java.io.Serializable;
import java.util.Objects;
import com.compsis.domain.enumeration.OperatorStatus;

/**
 * A DTO for the Operator entity.
 */
public class OperatorDTO implements Serializable {

    private Long id;

    private String email;

    private String name;

    private String password;

    private OperatorStatus operatorStatus;


    private Long accountOperationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public OperatorStatus getOperatorStatus() {
        return operatorStatus;
    }

    public void setOperatorStatus(OperatorStatus operatorStatus) {
        this.operatorStatus = operatorStatus;
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

        OperatorDTO operatorDTO = (OperatorDTO) o;
        if (operatorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operatorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OperatorDTO{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", password='" + getPassword() + "'" +
            ", operatorStatus='" + getOperatorStatus() + "'" +
            ", accountOperation=" + getAccountOperationId() +
            "}";
    }
}
