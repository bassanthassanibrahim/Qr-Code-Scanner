package com.example.bassanthassan.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.bassanthassan.R;
import com.example.bassanthassan.data.AppDatabase;
import com.example.bassanthassan.data.ScannedItemEntity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scanButton = findViewById(R.id.scan_button);
        Button viewHistoryButton = findViewById(R.id.history_button);
        Button viewFavoritesButton = findViewById(R.id.favorites_button);


        scanButton.setOnClickListener(v -> startScan());
        viewHistoryButton.setOnClickListener(v -> startActivity(new Intent(this, HistoryActivity.class)));
        viewFavoritesButton.setOnClickListener(v -> startActivity(new Intent(this, FavouritesActivity.class)));

    }

    private void startScan() {
        new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null) {
            saveScannedItem(result.getContents());
        } else {
            Toast.makeText(this, "No result scanned to show", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void saveScannedItem(String content) {
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());

        ScannedItemEntity entity = new ScannedItemEntity();
        entity.content = content;
        entity.timestamp = System.currentTimeMillis();
        entity.isFavorite = false;

        Executors.newSingleThreadExecutor().execute(() -> db.scannedItemDao().insert(entity));
    }
}