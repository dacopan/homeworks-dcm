/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.jjcastillo.ejercicios.ejer2;

import java.util.Objects;

/**
 *
 * @author dacopan
 */
public class Cliente {

    private String nombre;
    private Integer edad;
    private Genero genero;
    private Double total_ingresos_anuales;
    private Double total_egresos_anuales;
    private Double impuestos;

    public Cliente() {
    }

    public Cliente(String nombre, Integer edad, Genero genero, Double total_ingresos_anuales, Double total_egresos_anuales) {
        this.nombre = nombre;
        this.edad = edad;
        this.genero = genero;
        this.total_ingresos_anuales = total_ingresos_anuales;
        this.total_egresos_anuales = total_egresos_anuales;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Double getTotal_ingresos_anuales() {
        return total_ingresos_anuales;
    }

    public void setTotal_ingresos_anuales(Double total_ingresos_anuales) {
        this.total_ingresos_anuales = total_ingresos_anuales;
    }

    public Double getTotal_egresos_anuales() {
        return total_egresos_anuales;
    }

    public void setTotal_egresos_anuales(Double total_egresos_anuales) {
        this.total_egresos_anuales = total_egresos_anuales;
    }

    public Double getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(Double impuestos) {
        this.impuestos = impuestos;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cliente) {
            Cliente c = (Cliente) obj;
            return getNombre().equalsIgnoreCase(c.getNombre()) && c.getEdad().intValue() == getEdad();
        }
        return false;

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.nombre);
        hash = 23 * hash + Objects.hashCode(this.edad);
        return hash;
    }

}
