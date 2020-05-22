package com.alejo688.prueba_level_1.modelo;

public class Item {

    public String name;
    public String listeners;
    public String mbid;

    public Item(String name, String listeners) {
        this.mbid = mbid;
        this.name = name;
        this.listeners = listeners;
    }

    public String getName() {
        return name;
    }

    public String getListeners() {
        return listeners;
    }
}
