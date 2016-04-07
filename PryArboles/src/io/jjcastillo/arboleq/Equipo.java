/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.jjcastillo.arboleq;

import java.util.Objects;
import java.util.Random;

/**
 *
 * @author cisco
 */
public class Equipo {

    private String nombre;
    private int goles;

    public Equipo(String nombre) {
        this.nombre = nombre;
        goles = generarGolesAleatorios();
    }

    public Equipo(String nombre, int goles) {
        this.nombre = nombre;
        this.goles = goles;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getGoles() {
        return goles;
    }

    public void setGoles(int goles) {
        this.goles = goles;
    }

    @Override
    public String toString() {
        return "Nombre del Equipo: " + nombre + ", goles: " + goles;
    }

    final int generarGolesAleatorios() {
        Random ra = new Random();
        int gol = ra.nextInt();
        gol = gol < 0 ? gol * (-1) : gol;
        gol = gol % 8;
        goles = gol;
        return goles;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Equipo) {
            Equipo o = (Equipo) obj;
            return getNombre().equalsIgnoreCase(o.getNombre());
        } else {
            return false;
        }

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

}
