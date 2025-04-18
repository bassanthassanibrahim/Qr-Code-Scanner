package com.example.bassanthassan.presentation;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bassanthassan.R;
import com.example.bassanthassan.data.AppDatabase;
import com.example.bassanthassan.data.ScannedItemEntity;

import java.util.List;
import java.util.concurrent.Executors;

public class FavouritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ScannedItemAdapter adapter;
    private AppDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        recyclerView = findViewById(R.id.recycler_favourites);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = AppDatabase.getInstance(this);
        loadFavorites();

    }

    private void loadFavorites() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<ScannedItemEntity> itemList = database.scannedItemDao().getFavorites();

            runOnUiThread(() -> {
                adapter = new ScannedItemAdapter(itemList, database);
                recyclerView.setAdapter(adapter);
            });
        });
    }
}