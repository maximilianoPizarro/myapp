package com.hipster.myapp.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Prueba.
 */
@Entity
@Table(name = "prueba")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prueba")
public class Prueba implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "campo")
    private String campo;
    
    @JsonRawValue
    @Column(name = "valor")
    private String valor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampo() {
        return campo;
    }

    public Prueba campo(String campo) {
        this.campo = campo;
        return this;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }
    
    @JsonGetter("valor")
    public String getValor() {
        return valor;
    }
    @JsonSetter("valor")
    public Prueba valor(Object valor) throws JsonProcessingException {
    	ObjectMapper mapper = new ObjectMapper();
        this.valor=mapper.writeValueAsString(valor);
        return this;
    }
    @JsonSetter("valor")
    public void setValor(Object valor) throws JsonProcessingException {
    	ObjectMapper mapper = new ObjectMapper();
        this.valor =mapper.writeValueAsString(valor);
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
        Prueba prueba = (Prueba) o;
        if (prueba.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prueba.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Prueba{" +
            "id=" + getId() +
            ", campo='" + getCampo() + "'" +
            ", valor='" + getValor() + "'" +
            "}";
    }
}
