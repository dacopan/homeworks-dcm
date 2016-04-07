/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.jjcastillo.arboleq;

/**
 *
 * @author cisco
 */
public class NodoCola {
    
    private NodoArbol datos;
    private NodoCola siguiente;
    
    public NodoCola(NodoArbol datos, NodoCola siguiente){
           this.datos=datos;
           this.siguiente=siguiente;
    }

    public NodoArbol getDatos() {
        return datos;
    }

    public void setDatos(NodoArbol datos) {
        this.datos = datos;
    }

    public NodoCola getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoCola siguiente) {
        this.siguiente = siguiente;
    }
    
}
