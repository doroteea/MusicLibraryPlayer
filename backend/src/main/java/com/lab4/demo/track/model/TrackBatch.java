package com.lab4.demo.track.model;

import javax.persistence.*;

@Entity
@Table(name="tracks")
public class TrackBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    private String title;

    @Column(length = 512, nullable = false)
    private String link;

    @Column(length = 512, nullable = false)
    private int duration;

    @Column(length = 512, nullable = false)
    private Boolean explicit_lyrics;

    @Column(length = 512, nullable = false)
    private String preview;

    @Column(length = 512, nullable = false)
    private String artist;

    @Column(length = 512, nullable = false)
    private String album;

    public TrackBatch(Long id, String title, String link, int duration, Boolean explicit_lyrics, String preview, String artist, String album) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.duration = duration;
        this.explicit_lyrics = explicit_lyrics;
        this.preview = preview;
        this.artist = artist;
        this.album = album;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Boolean getExplicit_lyrics() {
        return explicit_lyrics;
    }

    public void setExplicit_lyrics(Boolean explicit_lyrics) {
        this.explicit_lyrics = explicit_lyrics;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public TrackBatch() {
    }

    @Override
    public String toString() {
        return "TrackBatch{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", duration=" + duration +
                ", explicit_lyrics=" + explicit_lyrics +
                ", preview='" + preview + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                '}';
    }
}
