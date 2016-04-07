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
public enum Genero {

    MASCULINO("MASCULINO"),
    FEMENINO("FEMENINO");
    String text;

    /**
     * @param text
     */
    private Genero(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
