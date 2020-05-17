package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * A Lapin.
 */
@Entity
@Table(name = "lapin")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Lapin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created")
    private LocalDate created;

    @Column(name = "modify")
    private Instant modify;

    @Column(name = "deleted")
    private ZonedDateTime deleted;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreated() {
        return created;
    }

    public Lapin created(LocalDate created) {
        this.created = created;
        return this;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public Instant getModify() {
        return modify;
    }

    public Lapin modify(Instant modify) {
        this.modify = modify;
        return this;
    }

    public void setModify(Instant modify) {
        this.modify = modify;
    }

    public ZonedDateTime getDeleted() {
        return deleted;
    }

    public Lapin deleted(ZonedDateTime deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(ZonedDateTime deleted) {
        this.deleted = deleted;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lapin)) {
            return false;
        }
        return id != null && id.equals(((Lapin) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lapin{" +
            "id=" + getId() +
            ", created='" + getCreated() + "'" +
            ", modify='" + getModify() + "'" +
            ", deleted='" + getDeleted() + "'" +
            "}";
    }
}
