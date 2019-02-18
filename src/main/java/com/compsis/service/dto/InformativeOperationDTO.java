package com.compsis.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the InformativeOperation entity.
 */
@ApiModel(description = "Comentários do usuário")
public class InformativeOperationDTO implements Serializable {

    private Long id;

    private String comments;


    private Long accountOperationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

        InformativeOperationDTO informativeOperationDTO = (InformativeOperationDTO) o;
        if (informativeOperationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), informativeOperationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InformativeOperationDTO{" +
            "id=" + getId() +
            ", comments='" + getComments() + "'" +
            ", accountOperation=" + getAccountOperationId() +
            "}";
    }
}
