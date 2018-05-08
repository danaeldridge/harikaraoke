package com.danaeldridge.recyclerviewclickplayground;

public class Song {
    private String title;
    private String artist;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setTitleAndArtist (String title, String artist) {
        this.artist = artist;
        this.title = title;
    }
}
