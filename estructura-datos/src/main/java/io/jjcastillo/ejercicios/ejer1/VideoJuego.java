/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.jjcastillo.ejercicios.ejer1;

import java.util.Objects;

/**
 *
 * @author dacopan
 */
public class VideoJuego {

    private String titulo;
    private Plataforma plataforma;
    private int cantidad;
    private double valor;

    public VideoJuego() {
    }

    public VideoJuego(String titulo, Plataforma plataforma, int cantidad, double valor) {
        this.titulo = titulo;
        this.plataforma = plataforma;
        this.cantidad = cantidad;
        this.valor = valor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Plataforma getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(Plataforma plataforma) {
        this.plataforma = plataforma;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VideoJuego) {
            return titulo.equalsIgnoreCase(((VideoJuego) obj).titulo);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.titulo);
        hash = 13 * hash + Objects.hashCode(this.plataforma);
        return hash;
    }

}
