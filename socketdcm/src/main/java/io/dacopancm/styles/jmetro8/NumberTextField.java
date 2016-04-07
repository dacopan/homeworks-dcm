/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.styles.jmetro8;

import javafx.scene.control.TextField;

/**
 *
 * @author dtic
 */
public class NumberTextField extends TextField {

    @Override
    public void replaceText(int start, int end, String text) {
        if (validate(text)) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (validate(text)) {
            super.replaceSelection(text);
        }
    }

    private boolean validate(String value) {
        if ("".equals(value)) {
            return true;
        }

        String regex = "^[0-9]+$";

        return value.matches(regex);
    }
}
