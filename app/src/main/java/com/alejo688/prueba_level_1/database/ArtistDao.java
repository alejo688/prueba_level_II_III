package com.alejo688.prueba_level_1.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alejo688.prueba_level_1.modelo.Artist;

import java.util.List;

@Dao
public interface ArtistDao {
    @Query("SELECT * FROM Artist")
    List<Artist> getArtists();

    @Query("SELECT * FROM Artist WHERE mbid LIKE :uuid")
    Artist getArtist(String uuid);

    @Insert
    void addArtist(Artist book);

    @Delete
    void deleteArtist(Artist book);

    @Update
    void updateArtist(Artist book);

    @Query("DELETE FROM Artist")
    void truncateArtist();
}
