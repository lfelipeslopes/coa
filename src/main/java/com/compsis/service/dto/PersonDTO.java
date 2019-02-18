package com.compsis.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;
import com.compsis.domain.enumeration.TypePerson;

/**
 * A DTO for the Person entity.
 */
@ApiModel(description = "Pessoa")
public class PersonDTO implements Serializable {

    private Long id;

    private String document;

    private String name;

    private String email;

    private TypePerson typePerson;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TypePerson getTypePerson() {
        return typePerson;
    }

    public void setTypePerson(TypePerson typePerson) {
        this.typePerson = typePerson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonDTO personDTO = (PersonDTO) o;
        if (personDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
            "id=" + getId() +
            ", document='" + getDocument() + "'" +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", typePerson='" + getTypePerson() + "'" +
            "}";
    }
}
