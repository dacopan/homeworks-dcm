/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.socketdcm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.dacopancm.socketdcm.player.PlayerThread;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author dtic
 */
@JsonIgnoreProperties({"emitting", "listPlayerThread"})
public class StreamFile {

    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty idx = new SimpleStringProperty();
    private final StringProperty path = new SimpleStringProperty();
    private String mime;
    private int views;
    private int emisiones;
    private List<PlayerThread> listPlayerThread = new ArrayList();

    public int getEmisiones() {
        return emisiones;
    }

    public void setEmisiones(int emisiones) {
        this.emisiones = emisiones;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void stepUpView() {
        this.views += 1;
    }

    public void stepUpEmisiones() {
        this.emisiones += 1;
    }

    public void stepDownEmisiones() {
        this.emisiones -= 1;
    }

    public boolean isEmitting() {
        return emisiones != 0;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getPath() {
        return path.get();
    }

    public void setPath(String value) {
        path.set(value);
    }

    public StringProperty pathProperty() {
        return path;
    }

    public String getIdx() {
        return idx.get();
    }

    public void setIdx(String value) {
        idx.set(value);
    }

    public StringProperty idxProperty() {
        return idx;
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String value) {
        nombre.set(value);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void addPlayerThread(PlayerThread player) {
        this.listPlayerThread.add(player);
    }

    public void removePlayerThread(PlayerThread player) {
        this.listPlayerThread.remove(player);
    }

}
