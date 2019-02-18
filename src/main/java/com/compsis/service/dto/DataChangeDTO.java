package com.compsis.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DataChange entity.
 */
@ApiModel(description = "Data alteração")
public class DataChangeDTO implements Serializable {

    private Long id;

    private String changeDetail;


    private Long accountOperationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChangeDetail() {
        return changeDetail;
    }

    public void setChangeDetail(String changeDetail) {
        this.changeDetail = changeDetail;
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

        DataChangeDTO dataChangeDTO = (DataChangeDTO) o;
        if (dataChangeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataChangeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataChangeDTO{" +
            "id=" + getId() +
            ", changeDetail='" + getChangeDetail() + "'" +
            ", accountOperation=" + getAccountOperationId() +
            "}";
    }
}
