package com.hipster.myapp.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Documento.
 */
@Entity
@Table(name = "documento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "documento")
public class Documento implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "baid")
    private Integer baid;

    @Column(name = "cuil")
    private String cuil;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @JsonRawValue
    @Column(name = "tipodocumento")    
    private String tipodocumento;
    
    final static ObjectMapper mapper = new ObjectMapper();
    

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBaid() {
        return baid;
    }

    public Documento baid(Integer baid) {
        this.baid = baid;
        return this;
    }

    public void setBaid(Integer baid) {
        this.baid = baid;
    }

    public String getCuil() {
        return cuil;
    }

    public Documento cuil(String cuil) {
        this.cuil = cuil;
        return this;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public String getNombre() {
        return nombre;
    }

    public Documento nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Documento apellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @JsonGetter("tipodocumento")
    public String getTipodocumento() {
        return tipodocumento;
    }
    
    @JsonSetter("tipodocumento")
    public Documento tipodocumento(Object tipodocumento) throws JsonProcessingException {
        this.tipodocumento = mapper.writeValueAsString(tipodocumento);
        return this;
    }
    @JsonSetter("tipodocumento")
    public void setTipodocumento(Object tipodocumento) throws JsonProcessingException {
        this.tipodocumento = mapper.writeValueAsString(tipodocumento);
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
        Documento documento = (Documento) o;
        if (documento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), documento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Documento{" +
            "id=" + getId() +
            ", baid=" + getBaid() +
            ", cuil='" + getCuil() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", tipodocumento='" + getTipodocumento() + "'" +
            "}";
    }
}
