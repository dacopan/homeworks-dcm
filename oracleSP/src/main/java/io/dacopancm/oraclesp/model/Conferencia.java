/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.oraclesp.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dacopan
 */
@Entity
@Table(name = "CONFERENCIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Conferencia.findAll", query = "SELECT c FROM Conferencia c"),
    @NamedQuery(name = "Conferencia.findByCfrId", query = "SELECT c FROM Conferencia c WHERE c.cfrId = :cfrId"),
    @NamedQuery(name = "Conferencia.findByCfrInicio", query = "SELECT c FROM Conferencia c WHERE c.cfrInicio = :cfrInicio"),
    @NamedQuery(name = "Conferencia.findByCfrFin", query = "SELECT c FROM Conferencia c WHERE c.cfrFin = :cfrFin"),
    @NamedQuery(name = "Conferencia.findByCfrNombre", query = "SELECT c FROM Conferencia c WHERE c.cfrNombre = :cfrNombre")})
public class Conferencia implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CFR_ID")
    private BigDecimal cfrId;
    @Column(name = "CFR_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cfrInicio;
    @Column(name = "CFR_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cfrFin;
    @Column(name = "CFR_NOMBRE")
    private String cfrNombre;

    public Conferencia() {
    }

    public Conferencia(BigDecimal cfrId) {
        this.cfrId = cfrId;
    }

    public BigDecimal getCfrId() {
        return cfrId;
    }

    public void setCfrId(BigDecimal cfrId) {
        this.cfrId = cfrId;
    }

    public Date getCfrInicio() {
        return cfrInicio;
    }

    public void setCfrInicio(Date cfrInicio) {
        this.cfrInicio = cfrInicio;
    }

    public Date getCfrFin() {
        return cfrFin;
    }

    public void setCfrFin(Date cfrFin) {
        this.cfrFin = cfrFin;
    }

    public String getCfrNombre() {
        return cfrNombre;
    }

    public void setCfrNombre(String cfrNombre) {
        this.cfrNombre = cfrNombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cfrId != null ? cfrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conferencia)) {
            return false;
        }
        Conferencia other = (Conferencia) object;
        if ((this.cfrId == null && other.cfrId != null) || (this.cfrId != null && !this.cfrId.equals(other.cfrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException ex) {
            return "[]";
        }
    }

}
