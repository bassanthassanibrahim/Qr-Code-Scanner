package com.example.bassanthassan.presentation;

import android.content.Intent;
import android.net.Uri;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bassanthassan.R;
import com.example.bassanthassan.data.AppDatabase;
import com.example.bassanthassan.data.ScannedItemEntity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class ScannedItemAdapter extends RecyclerView.Adapter<ScannedItemAdapter.ViewHolder> {

    private final List<ScannedItemEntity> items;
    private final AppDatabase database;

    public ScannedItemAdapter(List<ScannedItemEntity> items, AppDatabase db) {
        this.items = items;
        this.database = db;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView contentText, timestampText;
        ImageView favoriteIcon;

        public ViewHolder(View view) {
            super(view);
            contentText = view.findViewById(R.id.text_content);
            timestampText = view.findViewById(R.id.text_timestamp);
            favoriteIcon = view.findViewById(R.id.icon_favorite);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_scanned, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ScannedItemEntity item = items.get(position);
        holder.contentText.setText(item.content);
        holder.timestampText.setText(formatTimestamp(item.timestamp));
        holder.favoriteIcon.setImageResource(
                item.isFavorite ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off
        );

        holder.favoriteIcon.setOnClickListener(v -> {
            item.isFavorite = !item.isFavorite;
            holder.favoriteIcon.setImageResource(
                    item.isFavorite ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off
            );

            Executors.newSingleThreadExecutor().execute(() ->
                    database.scannedItemDao().setFavorite(item.id, item.isFavorite)
            );
        });

        // Open link if it's a valid URL
        holder.contentText.setOnClickListener(v -> {
            if (Patterns.WEB_URL.matcher(item.content).matches()) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.content));
                v.getContext().startActivity(browserIntent);
            } else {
                Toast.makeText(v.getContext(), "Not a valid URL", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String formatTimestamp(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
