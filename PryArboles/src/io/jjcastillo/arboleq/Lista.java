/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.jjcastillo.arboleq;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author dacopan
 */
public class Lista implements Iterable<Equipo> {

    private Nodo head;

    /**
     * Construye una lista vacia.
     */
    public Lista() {
        head = null;
    }

    /**
     * Retorna true si la lista esta vacia.
     *
     * @return
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Inserta un nuevo nodo al inicio de esta lista.
     *
     * @param item
     */
    public void addFirst(Equipo item) {
        head = new Nodo(item, head);
    }

    /**
     * Retorna el primer elemento en la lista.
     *
     * @return
     */
    public Equipo getFirst() {
        if (head == null) {
            return null;
        }

        return head.data;
    }

    /**
     * Elimina el primer elemento en la lista.
     *
     * @return
     */
    public Equipo removeFirst() {
        Equipo tmp = getFirst();
        head = head.next;
        return tmp;
    }

    /**
     * Inserta un nuevo nodo al final de la lista.
     *
     * @param item
     */
    public void addLast(Equipo item) {
        if (head == null) {
            addFirst(item);
        } else {
            Nodo tmp = head;
            while (tmp.next != null) {
                tmp = tmp.next;
            }

            tmp.next = new Nodo(item, null);
        }
    }

    /**
     * Retorna el ultimo elemento de la lista.
     *
     * @return
     */
    public Equipo getLast() {
        if (head == null) {
            return null;
        }

        Nodo tmp = head;
        while (tmp.next != null) {
            tmp = tmp.next;
        }

        return tmp.data;
    }

    /**
     * Elimina todos los nodos de la lista.
     *
     */
    public void clear() {
        head = null;
    }

    /**
     * Devuelve true si esta lista contiene el videojuego especificado.
     *
     * @param x
     * @return
     */
    public boolean contains(Equipo x) {
        for (Equipo tmp : this) {
            if (tmp.equals(x)) {
                return true;
            }
        }

        return false;
    }

    /**
     * retorna verdadero si el elemento por titulo de videojuego existe en la
     * lista
     *
     * @param titulo videojuego a buscar
     * @return true si existe en ela lista
     */
    public boolean contains(String titulo) {
        for (Equipo tmp : this) {
            if (tmp.getNombre().equals(titulo)) {
                return true;
            }
        }

        return false;
    }

    public Equipo get(Equipo x) {
        for (Equipo tmp : this) {
            if (tmp.equals(x)) {
                return tmp;
            }
        }

        return null;
    }

    /**
     * Devuelve los datos en la posición especificada en la lista.
     *
     * @param pos
     */
    public Equipo get(int pos) {
        if (head == null) {
            throw new IndexOutOfBoundsException();
        }

        Nodo tmp = head;
        for (int k = 0; k < pos; k++) {
            tmp = tmp.next;
        }

        if (tmp == null) {
            throw new IndexOutOfBoundsException();
        }

        return tmp.data;
    }

    /**
     * Inserta un nuevo nodo después de un nodo que contiene la clave.
     *
     * @param key
     * @param toInsert
     */
    public void insertAfter(Equipo key, Equipo toInsert) {
        Nodo tmp = head;

        while (tmp != null && !tmp.data.equals(key)) {
            tmp = tmp.next;
        }

        if (tmp != null) {
            tmp.next = new Nodo(toInsert, tmp.next);
        }
    }

    /**
     * Inserta un nuevo nodo antes de un nodo que contiene la clave.
     *
     * @param key
     * @param toInsert
     */
    public void insertBefore(Equipo key, Equipo toInsert) {
        if (head == null) {
            return;
        }

        if (head.data.equals(key)) {
            addFirst(toInsert);
            return;
        }

        Nodo prev = null;
        Nodo cur = head;

        while (cur != null && !cur.data.equals(key)) {
            prev = cur;
            cur = cur.next;
        }
        //insert between cur and prev
        if (cur != null) {
            prev.next = new Nodo(toInsert, cur);
        }
    }

    /**
     * Elimina la primera ocurrencia del elemento especificado en la lista.
     *
     * @param key
     */
    public void remove(Equipo key) {
        if (head == null) {
            throw new RuntimeException("cannot delete");
        }

        if (head.data.equals(key)) {
            head = head.next;
            return;
        }

        Nodo cur = head;
        Nodo prev = null;

        while (cur != null && !cur.data.equals(key)) {
            prev = cur;
            cur = cur.next;
        }

        if (cur == null) {
            throw new RuntimeException("cannot delete");
        }

        //delete cur node
        prev.next = cur.next;
    }

    void print() {
        System.out.printf("%20s %n", "Nombre equipo");
        for (Equipo tmp : this) {

            System.out.printf("%20s %n", tmp.getNombre());

        }
    }

    /**
     * *****************************************************
     *
     * Nodo
     *
     *******************************************************
     */
    private static class Nodo {

        private Equipo data;
        private Nodo next;

        public Nodo(Equipo data, Nodo next) {
            this.data = data;
            this.next = next;
        }
    }

    /**
     * *****************************************************
     *
     * Iterator
     *
     *******************************************************
     */
    @Override
    public Iterator<Equipo> iterator() {
        return new ListaIterator();
    }

    private class ListaIterator implements Iterator<Equipo> {

        private Nodo nextNodo;

        public ListaIterator() {
            nextNodo = head;
        }

        @Override
        public boolean hasNext() {
            return nextNodo != null;
        }

        @Override
        public Equipo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Equipo res = nextNodo.data;
            nextNodo = nextNodo.next;
            return res;
        }
    }
}
