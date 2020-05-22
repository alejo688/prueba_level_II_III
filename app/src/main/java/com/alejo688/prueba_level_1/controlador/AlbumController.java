package com.alejo688.prueba_level_1.controlador;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.room.Room;

import com.alejo688.prueba_level_1.database.AlbumDao;
import com.alejo688.prueba_level_1.database.AppDatabase;
import com.alejo688.prueba_level_1.modelo.Album;

import java.util.List;

public class AlbumController {
    @SuppressLint("StaticFieldLeak")
    private static AlbumController sAlbum;

    private AlbumDao mAlbumDao;

    private AlbumController(Context context) {
        Context appContext = context.getApplicationContext();
        AppDatabase database = Room.databaseBuilder(appContext, AppDatabase.class, "Album")
                .allowMainThreadQueries().build();
        mAlbumDao = database.getAlbumDao();
    }

    public static AlbumController get(Context context) {
        if (sAlbum == null) {
            sAlbum = new AlbumController(context);
        }
        return sAlbum;
    }

    public List<Album> getAlbums() {
        return mAlbumDao.getAlbums();
    }

    public Album getArtist(String id) {
        return mAlbumDao.getAlbum(id);
    }

    public void addArtist(Album album) {
        mAlbumDao.addAlbum(album);
    }

    public void updateArtist(Album album) {
        mAlbumDao.updateAlbum(album);
    }

    public void deleteArtist(Album album) {
        mAlbumDao.deleteAlbum(album);
    }

    public void truncateTable() {
        mAlbumDao.truncateTable();
    }
}
