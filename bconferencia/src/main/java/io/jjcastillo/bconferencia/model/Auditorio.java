/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.jjcastillo.bconferencia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.jjcastillo.bconferencia.helper.HelperUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author anunaki
 */
@JsonIgnoreProperties({"emitting", "conferenciasVector"})
public class Auditorio {

    private String nombre;
    private int asientos;

    private List<Conferencia> conferencias;

    public Auditorio() {
    }

    public Auditorio(String nombre, int asientos) {
        this.nombre = nombre;
        this.asientos = asientos;
        conferencias = new ArrayList();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAsientos() {
        return asientos;
    }

    public void setAsientos(int asientos) {
        this.asientos = asientos;
    }

    public List<Conferencia> getConferencias() {
        return conferencias;
    }

    public void setConferencias(List<Conferencia> conferencias) {
        this.conferencias = conferencias;
    }

    public boolean addConferencia(Conferencia c) {
        this.conferencias.add(c);
        return true;
    }

    public boolean removeConferencia(Conferencia c) {
        return this.conferencias.remove(c);
    }
    //public Conferencia getConferencia

    public Vector getConferenciasVector() {
        Vector v = new Vector();
        for (Conferencia c : conferencias) {
            Vector v1 = new Vector();
            v1.add(c.getNombre());
            v1.add(HelperUtil.formatDate(c.getFechaInicio()));
            v1.add(HelperUtil.formatDate(c.getFechaFin()));
            v1.add(HelperUtil.PrettifyDateDiff(c.getFechaFin().getTime() - c.getFechaInicio().getTime()));
            v.add(v1);
        }
        return v;
    }

    @Override
    public String toString() {
        return "Auditorio{" + "nombre=" + nombre + ", asientos=" + asientos + ", conferencias=" + conferencias + '}';
    }

}
