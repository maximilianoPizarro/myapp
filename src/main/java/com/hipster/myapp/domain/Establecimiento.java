package com.hipster.myapp.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Establecimiento.
 */
@Entity
@Table(name = "establecimiento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "establecimiento")
public class Establecimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nro_cue")
    private Long nroCue;

    @Column(name = "gestion")
    private String gestion;

    @Column(name = "modalidad")
    private String modalidad;

    @Column(name = "nivel")
    private String nivel;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNroCue() {
        return nroCue;
    }

    public Establecimiento nroCue(Long nroCue) {
        this.nroCue = nroCue;
        return this;
    }

    public void setNroCue(Long nroCue) {
        this.nroCue = nroCue;
    }

    public String getGestion() {
        return gestion;
    }

    public Establecimiento gestion(String gestion) {
        this.gestion = gestion;
        return this;
    }

    public void setGestion(String gestion) {
        this.gestion = gestion;
    }

    public String getModalidad() {
        return modalidad;
    }

    public Establecimiento modalidad(String modalidad) {
        this.modalidad = modalidad;
        return this;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getNivel() {
        return nivel;
    }

    public Establecimiento nivel(String nivel) {
        this.nivel = nivel;
        return this;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
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
        Establecimiento establecimiento = (Establecimiento) o;
        if (establecimiento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), establecimiento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Establecimiento{" +
            "id=" + getId() +
            ", nroCue=" + getNroCue() +
            ", gestion='" + getGestion() + "'" +
            ", modalidad='" + getModalidad() + "'" +
            ", nivel='" + getNivel() + "'" +
            "}";
    }
}
