package com.example.bassanthassan.domain;

public class ScannedItem {
    public int id;
    public String content;
    public long timestamp;
    public boolean isFavorite;

    public ScannedItem(int id, String content, long timestamp, boolean isFavorite) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.isFavorite = isFavorite;
    }
}

