package com.alejo688.prueba_level_1.modelo;

public class Artist {

    public String name;
    public String listeners;
    public String mbid;
    public String url;
    public String streamable;

    public Artist (String name, String listeners, String mbid, String url, String streamable) {
        this.name = name;
        this.listeners = listeners;
        this.mbid = mbid;
        this.url = url;
        this.streamable = streamable;
    }
}
