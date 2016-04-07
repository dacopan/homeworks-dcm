/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.jjcastillo.ejercicios.ejer1;

/**
 *
 * @author dacopan
 */
public enum Plataforma {

    XBOX("XBOX"),
    PS4("PS4");
    String text;

    /**
     * @param text
     */
    private Plataforma(final String text) {
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
