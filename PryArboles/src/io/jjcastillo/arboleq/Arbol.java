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
public class Arbol {

    public NodoArbol raiz;

    // Tipos de recorrido
    public enum recorrido {

        PREORDEN, ENORDEN, POSTORDEN
    }

    public Arbol() {
        raiz = null;
    }

    public Arbol(Equipo datos) {
        raiz = new NodoArbol(datos);
    }

    public void unirArboles(Equipo datos, Arbol a1, Arbol a2) {
        if (a1.raiz == a2.raiz && a1.raiz != null) {
            System.out.println("No puedo unir al mismo nodo");
            return;
        }
        Equipo nuevo = new Equipo(datos.getNombre(), 0);
        raiz = new NodoArbol(nuevo, a1.raiz, a2.raiz);
//        a1.raiz=null;
//        a2.raiz=null;
    }

    public void recorridoNivel(NodoArbol actual) {
        Cola cola, colaAux;
        NodoArbol aux;

        if (actual != null) {
            cola = new Cola();
            cola.encolar(actual);
            colaAux = new Cola();
            while (!cola.esVacia()) {
                aux = cola.desencolar();
                colaAux.encolar(aux);
                if (aux.getIzquierdo() != null) {
                    cola.encolar(aux.getIzquierdo());
                }
                if (aux.getDerecho() != null) {
                    cola.encolar(aux.getDerecho());
                }

            }
            //colaAux.imprimir();
        }

    }

    public void preOrden(NodoArbol actual) {
        if (actual != null) {
            System.out.println("" + actual.getDatos());
            preOrden(actual.getIzquierdo());
            preOrden(actual.getDerecho());
        }

    }

    public void preOrden() {
        preOrden(raiz);
    }

    public void recorridoNivel() {
        recorridoNivel(raiz);
    }

    //http://www.juliocesar.in/2013/09/arbol-binario-y-recorridos-preorden.html
    public void recorrer(recorrido tipo) {
        switch (tipo) {
            case PREORDEN:
                System.out.println("Recorrido pre orden");
                raiz.preOrden();
                break;
            case ENORDEN:
                System.out.println("Recorrido en orden");
                //raiz.enOrden();
                break;
            case POSTORDEN:
                System.out.println("Recorrido post orden");
                //raiz.postOrden();
                break;
        }
        System.out.println();
    }

    public void goles(String equipo, recorrido tipo, AtomicInteger goles) {
        switch (tipo) {
            case PREORDEN:
                System.out.println("Recorrido pre orden");
                raiz.preOrdenGoles(goles, equipo);
                break;
            case ENORDEN:
                System.out.println("Recorrido en orden");
                //raiz.enOrden();
                break;
            case POSTORDEN:
                System.out.println("Recorrido post orden");
                //raiz.postOrden();
                break;
        }
        System.out.println();
    }

}
