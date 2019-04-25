package io.github.jhipster.application.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A InternalCode.
 */
@Entity
@Table(name = "internal_code")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InternalCode implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "internal_code_external",
               joinColumns = @JoinColumn(name = "internal_code_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "external_id", referencedColumnName = "id"))
    private Set<ExternalCode> externals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public InternalCode code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public InternalCode description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ExternalCode> getExternals() {
        return externals;
    }

    public InternalCode externals(Set<ExternalCode> externalCodes) {
        this.externals = externalCodes;
        return this;
    }

    public InternalCode addExternal(ExternalCode externalCode) {
        this.externals.add(externalCode);
        externalCode.getInternals().add(this);
        return this;
    }

    public InternalCode removeExternal(ExternalCode externalCode) {
        this.externals.remove(externalCode);
        externalCode.getInternals().remove(this);
        return this;
    }

    public void setExternals(Set<ExternalCode> externalCodes) {
        this.externals = externalCodes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InternalCode internalCode = (InternalCode) o;
        if (internalCode.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), internalCode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InternalCode{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
