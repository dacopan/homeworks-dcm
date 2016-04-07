/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.jjcastillo.bconferencia.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author anunaki
 */
public class Conferencia implements Comparable<Conferencia> {

    private String nombre;
    private Date fechaInicio;
    private Date fechaFin;
    private ArrayList<Asistente> asistentes;
    private String idConferencia;

    public Conferencia() {
        asistentes = new ArrayList();
        idConferencia = UUID.randomUUID().toString();
    }

    public Conferencia(String nombre, Date fechaInicio, Date fechaFin) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        asistentes = new ArrayList();
        idConferencia = UUID.randomUUID().toString();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public ArrayList<Asistente> getAsistentes() {
        return asistentes;
    }

    public void setAsistentes(ArrayList<Asistente> asistentes) {
        this.asistentes = asistentes;
    }

    public void addAsistente(Asistente asistente) {
        this.asistentes.add(asistente);
    }

    @Override
    public boolean equals(Object obj) {

        return obj instanceof Conferencia && ((Conferencia) obj).getNombre().equalsIgnoreCase(getNombre());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.nombre);
        hash = 53 * hash + Objects.hashCode(this.fechaInicio);
        hash = 53 * hash + Objects.hashCode(this.fechaFin);
        return hash;
    }

    @Override
    public int compareTo(Conferencia o) {
        return o.getNombre().compareTo(getNombre());
    }

    @Override
    public String toString() {
        return "Conferencia{" + "nombre=" + nombre + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", asistentes=" + asistentes + ", idConferencia=" + idConferencia + '}';
    }

}
