package com.example.rithvik.top10downloader;

/**
 * Created by rithvik on 18/07/16.
 */
public class Song {
    private String title;
    private String artist;
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Title : " + getTitle() + "\n" +
                "Artist : " + getArtist() + "\n" +
                "Url : " + getUrl() + "\n";
    }
}

