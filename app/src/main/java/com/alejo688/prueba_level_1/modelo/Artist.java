package com.alejo688.prueba_level_1.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Artist")
public class Artist {

    @PrimaryKey
    @NonNull
    public String mbid;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "listeners")
    public String listeners;
    @ColumnInfo(name = "url")
    public String url;
    @ColumnInfo(name = "image")
    public String image;

    public Artist () {
    }

    @NonNull
    public String getId() {
        return mbid;
    }

    public void setId(@NonNull String id) {
        mbid = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String mName) {
        name = mName;
    }

    public String getListeners() {
        return listeners;
    }

    public void setListeners(String mListeners) {
        listeners = mListeners;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String mUrl) {
        url = mUrl;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String mImage) {
        image = mImage;
    }

}
