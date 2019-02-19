package com.compsis.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Media entity.
 */
@ApiModel(description = "Midia")
public class MediaDTO implements Serializable {

    private Long id;

    private String identification;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MediaDTO mediaDTO = (MediaDTO) o;
        if (mediaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mediaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MediaDTO{" +
            "id=" + getId() +
            ", identification='" + getIdentification() + "'" +
            "}";
    }
}
