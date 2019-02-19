package com.compsis.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the UserAccount entity.
 */
@ApiModel(description = "Conta Usuário")
public class UserAccountDTO implements Serializable {

    private Long id;


    private Long accountableId;

    private Long contactsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountableId() {
        return accountableId;
    }

    public void setAccountableId(Long personId) {
        this.accountableId = personId;
    }

    public Long getContactsId() {
        return contactsId;
    }

    public void setContactsId(Long personId) {
        this.contactsId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserAccountDTO userAccountDTO = (UserAccountDTO) o;
        if (userAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserAccountDTO{" +
            "id=" + getId() +
            ", accountable=" + getAccountableId() +
            ", contacts=" + getContactsId() +
            "}";
    }
}
