package com.example.bassanthassan.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScannedItemDao {
    @Insert
    void insert(ScannedItemEntity item);

    @Query("SELECT * FROM scanned_items ORDER BY timestamp DESC")
    List<ScannedItemEntity> getAll();

    @Query("SELECT * FROM scanned_items WHERE isFavorite = 1")
    List<ScannedItemEntity> getFavorites();

    @Query("UPDATE scanned_items SET isFavorite = :fav WHERE id = :id")
    void setFavorite(int id, boolean fav);
}
