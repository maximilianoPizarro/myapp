package com.hipster.myapp.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hipster.myapp.domain.enumeration.TipoDni;
import com.hipster.myapp.domain.enumeration.TipoEjemplar;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


/**
 * A TituloSecundario.
 */
@Entity
@Table(name = "titulo_secundario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "titulosecundario")
public class TituloSecundario implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nro_titulo", nullable = false)
    private Long nroTitulo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_ejemplar", nullable = false)
    private TipoEjemplar tipoEjemplar;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "dni", nullable = false)
    private TipoDni dni;
    
    
    @JsonDeserialize(using = LocalDateDeserializer.class)  
    @NotNull
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @NotNull
    @Column(name = "titulo_otorgado", nullable = false)
    private String tituloOtorgado;

    @NotNull
    @Column(name = "promedio", nullable = false)
    private Double promedio;

    @NotNull
    @Column(name = "mes_annio_egreso", nullable = false)
    private String mesAnnioEgreso;

    @NotNull
    @Column(name = "validez_nacional", nullable = false)
    private Long validezNacional;

    @Column(name = "dictamen")
    private String dictamen;

    @NotNull
    @Column(name = "revisado", nullable = false)
    private String revisado;

    @JsonDeserialize(using = LocalDateDeserializer.class)  
    @NotNull
    @Column(name = "ingreso", nullable = false)
    private LocalDate ingreso;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NotNull
    @Column(name = "egreso", nullable = false)
    private LocalDate egreso;

    @ManyToOne
    @JsonIgnoreProperties("tituloSecundarios")
    private Establecimiento nroCue;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNroTitulo() {
        return nroTitulo;
    }

    public TituloSecundario nroTitulo(Long nroTitulo) {
        this.nroTitulo = nroTitulo;
        return this;
    }

    public void setNroTitulo(Long nroTitulo) {
        this.nroTitulo = nroTitulo;
    }

    public TipoEjemplar getTipoEjemplar() {
        return tipoEjemplar;
    }

    public TituloSecundario tipoEjemplar(TipoEjemplar tipoEjemplar) {
        this.tipoEjemplar = tipoEjemplar;
        return this;
    }

    public void setTipoEjemplar(TipoEjemplar tipoEjemplar) {
        this.tipoEjemplar = tipoEjemplar;
    }

    public String getNombre() {
        return nombre;
    }

    public TituloSecundario nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public TituloSecundario apellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public TipoDni getDni() {
        return dni;
    }

    public TituloSecundario dni(TipoDni dni) {
        this.dni = dni;
        return this;
    }

    public void setDni(TipoDni dni) {
        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public TituloSecundario fechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTituloOtorgado() {
        return tituloOtorgado;
    }

    public TituloSecundario tituloOtorgado(String tituloOtorgado) {
        this.tituloOtorgado = tituloOtorgado;
        return this;
    }

    public void setTituloOtorgado(String tituloOtorgado) {
        this.tituloOtorgado = tituloOtorgado;
    }

    public Double getPromedio() {
        return promedio;
    }

    public TituloSecundario promedio(Double promedio) {
        this.promedio = promedio;
        return this;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }

    public String getMesAnnioEgreso() {
        return mesAnnioEgreso;
    }

    public TituloSecundario mesAnnioEgreso(String mesAnnioEgreso) {
        this.mesAnnioEgreso = mesAnnioEgreso;
        return this;
    }

    public void setMesAnnioEgreso(String mesAnnioEgreso) {
        this.mesAnnioEgreso = mesAnnioEgreso;
    }

    public Long getValidezNacional() {
        return validezNacional;
    }

    public TituloSecundario validezNacional(Long validezNacional) {
        this.validezNacional = validezNacional;
        return this;
    }

    public void setValidezNacional(Long validezNacional) {
        this.validezNacional = validezNacional;
    }

    public String getDictamen() {
        return dictamen;
    }

    public TituloSecundario dictamen(String dictamen) {
        this.dictamen = dictamen;
        return this;
    }

    public void setDictamen(String dictamen) {
        this.dictamen = dictamen;
    }

    public String getRevisado() {
        return revisado;
    }

    public TituloSecundario revisado(String revisado) {
        this.revisado = revisado;
        return this;
    }

    public void setRevisado(String revisado) {
        this.revisado = revisado;
    }

    public LocalDate getIngreso() {
        return ingreso;
    }

    public TituloSecundario ingreso(LocalDate ingreso) {
        this.ingreso = ingreso;
        return this;
    }

    public void setIngreso(LocalDate ingreso) {
        this.ingreso = ingreso;
    }

    public LocalDate getEgreso() {
        return egreso;
    }

    public TituloSecundario egreso(LocalDate egreso) {
        this.egreso = egreso;
        return this;
    }

    public void setEgreso(LocalDate egreso) {
        this.egreso = egreso;
    }

    public Establecimiento getNroCue() {
        return nroCue;
    }

    public TituloSecundario nroCue(Establecimiento establecimiento) {
        this.nroCue = establecimiento;
        return this;
    }

    public void setNroCue(Establecimiento establecimiento) {
        this.nroCue = establecimiento;
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
        TituloSecundario tituloSecundario = (TituloSecundario) o;
        if (tituloSecundario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tituloSecundario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "TituloSecundario [id=" + id + ", nroTitulo=" + nroTitulo + ", tipoEjemplar=" + tipoEjemplar
				+ ", nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + ", fechaNacimiento="
				+ fechaNacimiento + ", tituloOtorgado=" + tituloOtorgado + ", promedio=" + promedio
				+ ", mesAnnioEgreso=" + mesAnnioEgreso + ", validezNacional=" + validezNacional + ", dictamen="
				+ dictamen + ", revisado=" + revisado + ", ingreso=" + ingreso + ", egreso=" + egreso + ", nroCue="
				+ nroCue + "]";
	}


}
