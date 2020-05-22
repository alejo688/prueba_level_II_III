package com.alejo688.prueba_level_1.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.alejo688.prueba_level_1.modelo.Album;
import com.alejo688.prueba_level_1.modelo.Artist;

@Database(entities = {Album.class, Artist.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AlbumDao getAlbumDao();
    public abstract ArtistDao getArtistDao();
}
