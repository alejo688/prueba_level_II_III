package com.alejo688.prueba_level_1.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alejo688.prueba_level_1.modelo.Album;

import java.util.List;

@Dao
public interface AlbumDao {
    @Query("SELECT * FROM Album")
    List<Album> getAlbums();

    @Query("SELECT * FROM Album WHERE mbid LIKE :uuid")
    Album getAlbum(String uuid);

    @Insert
    void addAlbum(Album book);

    @Delete
    void deleteAlbum(Album book);

    @Update
    void updateAlbum(Album book);

    @Query("DELETE FROM Album")
    public void truncateTable();
}
