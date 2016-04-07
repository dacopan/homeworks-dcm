/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.jjcastillo.bconferencia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author anunaki
 */
@JsonIgnoreProperties({"emitting", "fullName"})
public class Asistente implements Comparable<Asistente> {

    private String nombre;
    private String apellido;
    private String identificacion;

    public Asistente() {
    }

    
    public Asistente(String nombre, String apellido, String identificacion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    @Override
    public int compareTo(Asistente o) {
        return this.identificacion.compareTo(o.identificacion);
    }

    public String getFullName() {
        return this.nombre + " " + this.apellido;
    }

}
