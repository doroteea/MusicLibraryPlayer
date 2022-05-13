package com.lab4.demo.playlist.model.dto;

import com.lab4.demo.track.model.Track;
import com.lab4.demo.track.model.TrackDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistDTO {

    private Long id;

    private String title;

    private List<TrackDTO> tracks = new ArrayList<>();

    private Integer duration;

    public void setDuration(){
        for(TrackDTO track: this.tracks) {
            this.duration += track.getDuration();
        }
    }
}
