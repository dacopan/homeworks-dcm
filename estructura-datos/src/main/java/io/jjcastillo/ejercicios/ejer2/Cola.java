/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.jjcastillo.ejercicios.ejer2;

/**
 *
 * @author dacopan
 */
public class Cola {

    private class Nodo {

        Cliente data;
        Nodo sig;
    }

    private Nodo raiz, fondo;
    private int tamano = 0;

    Cola() {
        raiz = null;
        fondo = null;
    }

    public boolean vacia() {
        if (raiz == null) {
            return true;
        } else {
            return false;
        }
    }

    public void insertar(Cliente data) {
        Nodo nuevo;
        nuevo = new Nodo();
        nuevo.data = data;
        nuevo.sig = null;
        if (vacia()) {
            raiz = nuevo;
            fondo = nuevo;
        } else {
            fondo.sig = nuevo;
            fondo = nuevo;
        }
        tamano++;
    }

    public Cliente extraer() {
        if (!vacia()) {
            Cliente datarmacion = raiz.data;
            if (raiz == fondo) {
                raiz = null;
                fondo = null;
            } else {
                raiz = raiz.sig;
            }
            tamano--;
            return datarmacion;
        } else {
            return null;
        }
    }

    public void printClientes() {
        Nodo reco = raiz;
        System.out.printf("%20s %10s %10s %10s %10s %10s%n", "Nombre", "Edad", "Género", "Ingresos", "Egresos", "Impuestos");
        while (reco != null) {
            System.out.printf("%20s %10d %10s %10.2f %10.2f %10.2f%n", reco.data.getNombre(), reco.data.getEdad(), reco.data.getGenero(), reco.data.getTotal_ingresos_anuales(), reco.data.getTotal_egresos_anuales(), reco.data.getImpuestos());
            reco = reco.sig;
        }
        System.out.println();
    }

    public boolean contiene(Cliente clt) {
        Nodo reco = raiz;
        while (reco != null) {
            if (reco.data.equals(clt)) {
                return true;
            }
            reco = reco.sig;
        }
        return false;
    }

    public Cola getClientesTerceraEdad() {
        Cola colaB = new Cola();
        Nodo reco = raiz;
        Nodo anterior = null;
        while (reco != null) {
            if (reco.data.getEdad() >= 65) {//elimino d esta cola y pongo en la otra
                colaB.insertar(reco.data);
                if (reco == raiz) {
                    raiz = reco.sig;
                }
                if (reco == fondo) {

                    fondo = anterior;
                }
                if (anterior != null) {
                    anterior.sig = reco.sig;
                }
                tamano--;
            }
            anterior = reco;
            reco = reco.sig;
        }
        return colaB;
    }

    public Cola getClientesMujeres() {
        Cola colaB = new Cola();
        Nodo reco = raiz;
        Nodo anterior = null;
        while (reco != null) {
            if (reco.data.getGenero().toString().equalsIgnoreCase(Genero.FEMENINO.toString())) {//elimino d esta cola y pongo en la otra
                colaB.insertar(reco.data);
                if (reco == raiz) {
                    raiz = reco.sig;
                }
                if (reco == fondo) {

                    fondo = anterior;
                }
                if (anterior != null) {
                    anterior.sig = reco.sig;
                }
                tamano--;
            }
            anterior = reco;
            reco = reco.sig;
        }
        return colaB;
    }

    public int getTamano() {
        return tamano;
    }

}
