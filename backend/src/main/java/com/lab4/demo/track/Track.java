package com.lab4.demo.track;

import com.lab4.demo.album.Album;
import com.lab4.demo.artist.Artist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Track {
    private Long id;
    private String title;
    private String link;
    private int duration;
    private Boolean explicit_lyrics;
    private String preview;
    //private Artist artist;
    //private Album album;
    private String artist;
    private String album;

}
