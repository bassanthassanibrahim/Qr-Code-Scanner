package com.example.bassanthassan.presentation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bassanthassan.R;
import com.example.bassanthassan.data.AppDatabase;
import com.example.bassanthassan.data.ScannedItemEntity;

import java.util.List;
import java.util.concurrent.Executors;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ScannedItemAdapter adapter;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recycler_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = AppDatabase.getInstance(this);

        loadHistory();
    }

    private void loadHistory() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<ScannedItemEntity> itemList = database.scannedItemDao().getAll();

            runOnUiThread(() -> {
                adapter = new ScannedItemAdapter(itemList, database);
                recyclerView.setAdapter(adapter);
            });
        });
    }
}