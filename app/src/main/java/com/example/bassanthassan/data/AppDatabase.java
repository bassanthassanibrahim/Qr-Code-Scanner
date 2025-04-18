package com.example.bassanthassan.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ScannedItemEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ScannedItemDao scannedItemDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, "qr_database")
                        .build();
            }
        }
        return INSTANCE;
    }
}
