/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.jjcastillo.arboleq;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author cisco
 */
public class NodoArbol {

    private Equipo datos;
    private NodoArbol izquierdo;
    private NodoArbol derecho;

    public NodoArbol(Equipo datos) {
        this.datos = datos;
        izquierdo = derecho = null;
    }

    public NodoArbol(Equipo datos, NodoArbol iz, NodoArbol der) {
        this.datos = datos;
        izquierdo = iz;
        derecho = der;
    }

    public Equipo getDatos() {
        return datos;
    }

    public void setDatos(Equipo datos) {
        this.datos = datos;
    }

    public NodoArbol getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoArbol izquierdo) {
        this.izquierdo = izquierdo;
    }

    public NodoArbol getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoArbol derecho) {
        this.derecho = derecho;
    }

    /**
     * Recorrido pre orden RID
     */
    public void preOrden() {
        //System.out.print(valor + " ");
        if (izquierdo != null) {
            izquierdo.preOrden();
        }
        if (derecho != null) {
            derecho.preOrden();
        }
    }

    public void preOrdenGoles(AtomicInteger goles, String nombre) {
        //System.out.print(valor + " ");
        if (getDatos().getNombre().equalsIgnoreCase(nombre)) {
            goles.addAndGet(getDatos().getGoles());
        }
        if (izquierdo != null) {
            izquierdo.preOrdenGoles(goles, nombre);
        }
        if (derecho != null) {
            derecho.preOrdenGoles(goles, nombre);
        }
    }

}
