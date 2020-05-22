package com.alejo688.prueba_level_1.controlador;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.room.Room;

import com.alejo688.prueba_level_1.database.ArtistDao;
import com.alejo688.prueba_level_1.database.AppDatabase;
import com.alejo688.prueba_level_1.modelo.Artist;

import java.util.List;

public class ArtistController {
    @SuppressLint("StaticFieldLeak")
    private static ArtistController sArtist;

    private ArtistDao mArtistDao;

    private ArtistController(Context context) {
        Context appContext = context.getApplicationContext();
        AppDatabase database =  Room.databaseBuilder(appContext, AppDatabase.class, "Artist")
                .allowMainThreadQueries().build();
        mArtistDao = database.getArtistDao();
    }

    public static ArtistController get(Context context) {
        if (sArtist == null) {
            sArtist = new ArtistController(context);
        }
        return sArtist;
    }

    public List<Artist> getArtists() {
        return mArtistDao.getArtists();
    }

    public Artist getArtist(String id) {
        return mArtistDao.getArtist(id);
    }

    public void addArtist(Artist artist) {
        mArtistDao.addArtist(artist);
    }

    public void updateArtist(Artist artist) {
        mArtistDao.updateArtist(artist);
    }

    public void deleteArtist(Artist artist) {
        mArtistDao.deleteArtist(artist);
    }

    public void truncateArtist() {
        mArtistDao.truncateArtist();
    }
}
