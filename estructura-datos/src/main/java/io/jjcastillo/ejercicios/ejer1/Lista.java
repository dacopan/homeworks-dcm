/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.jjcastillo.ejercicios.ejer1;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author dacopan
 */
public class Lista implements Iterable<VideoJuego> {

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
    public void addFirst(VideoJuego item) {
        head = new Nodo(item, head);
    }

    /**
     * Retorna el primer elemento en la lista.
     *
     * @return
     */
    public VideoJuego getFirst() {
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
    public VideoJuego removeFirst() {
        VideoJuego tmp = getFirst();
        head = head.next;
        return tmp;
    }

    /**
     * Inserta un nuevo nodo al final de la lista.
     *
     * @param item
     */
    public void addLast(VideoJuego item) {
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
    public VideoJuego getLast() {
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
    public boolean contains(VideoJuego x) {
        for (VideoJuego tmp : this) {
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
        for (VideoJuego tmp : this) {
            if (tmp.getTitulo().equals(titulo)) {
                return true;
            }
        }

        return false;
    }

    public VideoJuego get(VideoJuego x) {
        for (VideoJuego tmp : this) {
            if (tmp.equals(x)) {
                return tmp;
            }
        }

        return null;
    }

    /**
     * Inserta un nuevo nodo después de un nodo que contiene la clave.
     *
     * @param key
     * @param toInsert
     */
    public void insertAfter(VideoJuego key, VideoJuego toInsert) {
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
    public void insertBefore(VideoJuego key, VideoJuego toInsert) {
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
    public void remove(VideoJuego key) {
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

    void printVideojuegos() {
        System.out.printf("%20s %10s %10s %10s%n", "titulo", "plataforma", "cantidad", "valor");
        for (VideoJuego tmp : this) {

            System.out.printf("%20s %10s %10d %10.2f%n", tmp.getTitulo(), tmp.getPlataforma(), tmp.getCantidad(), tmp.getValor());

        }
    }

    int eliminarDuplicados() {
        if (head == null) {
            return 0;
        }
        int removes = 0;
        Nodo currentNode = head;
        while (currentNode != null) {
            Nodo runner = currentNode;

            while (runner.next != null) {
                if (runner.next.data.equals(currentNode.data)) {
                    currentNode.data.setCantidad(currentNode.data.getCantidad() + runner.next.data.getCantidad());
                    runner.next = runner.next.next;
                    removes++;
                } else {
                    runner = runner.next;
                }
            }
            currentNode = currentNode.next;
        }
        return removes;
    }

    /**
     * *****************************************************
     *
     * Nodo
     *
     *******************************************************
     */
    private static class Nodo {

        private VideoJuego data;
        private Nodo next;

        public Nodo(VideoJuego data, Nodo next) {
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
    public Iterator<VideoJuego> iterator() {
        return new ListaIterator();
    }

    private class ListaIterator implements Iterator<VideoJuego> {

        private Nodo nextNodo;

        public ListaIterator() {
            nextNodo = head;
        }

        @Override
        public boolean hasNext() {
            return nextNodo != null;
        }

        @Override
        public VideoJuego next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            VideoJuego res = nextNodo.data;
            nextNodo = nextNodo.next;
            return res;
        }
    }
}
