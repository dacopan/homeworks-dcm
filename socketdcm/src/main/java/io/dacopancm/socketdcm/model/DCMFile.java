/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.socketdcm.model;

import java.io.File;
import java.time.LocalDateTime;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author dtic
 */
public class DCMFile {

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty folder = new SimpleStringProperty();
    private final StringProperty format = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> date = new SimpleObjectProperty();
    private final StringProperty state = new SimpleStringProperty();

    public String getState() {
        return state.get();
    }

    public void setState(String value) {
        state.set(value);
    }

    public StringProperty stateProperty() {
        return state;
    }

    public DCMFile() {
    }

    public DCMFile(String name, String folder, String format) {
        this.name.set(name);
        this.folder.set(folder);
        this.format.set(format);
    }

    public LocalDateTime getDate() {
        return date.get();
    }

    public void setDate(LocalDateTime value) {
        date.set(value);
    }

    public ObjectProperty dateProperty() {
        return date;
    }

    public String getFormat() {
        return format.get();
    }

    public void setFormat(String value) {
        format.set(value);
    }

    public StringProperty formatProperty() {
        return format;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getFolder() {
        return folder.get();
    }

    public void setFolder(String value) {
        folder.set(value);
    }

    public StringProperty folderProperty() {
        return folder;
    }

    public String getPath() {
        return getFolder() + File.separator + getName() + "." + getFormat();
    }
}
