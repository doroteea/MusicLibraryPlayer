package com.lab4.demo.playlist;

import com.lab4.demo.playlist.model.dto.PlaylistDTO;
import com.lab4.demo.track.model.TrackDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.lab4.demo.UrlMapping.*;
import static com.lab4.demo.UrlMapping.TRACK_ID_PART;

@Validated
@RestController
@RequestMapping(PLAYLIST)
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping
    public List<PlaylistDTO> getAllPlaylists(){
        return playlistService.findAll();
    }

    @PostMapping
    public PlaylistDTO create(@Valid @RequestBody PlaylistDTO playlistDTO) {
        return playlistService.create(playlistDTO);
    }

    @PutMapping(PLAYLIST_ID_PART)
    public PlaylistDTO update(@PathVariable Long id, @Valid @RequestBody PlaylistDTO playlistDTO) {
        return playlistService.update(id, playlistDTO);
    }

    @DeleteMapping(PLAYLIST_ID_PART)
    public void delete(@PathVariable long id){
        playlistService.delete(id);
    }

//    @GetMapping("/tracks" + PLAYLIST_ID_PART)
//    public PlaylistDTO getplaylist(@PathVariable long id){
//        return playlistService.getPlaylist(id);
//    }

    @PutMapping(PLAYLIST_ID_PART + "/tracks")
    public PlaylistDTO createTrack(@PathVariable Long id, @RequestBody TrackDTO track) {
        return playlistService.addTrackInPlaylist(id,track);
    }

    @PutMapping(PLAYLIST_ID_PART + "/tracks" + TRACK_ID_PART)
    public PlaylistDTO updateTrack(@PathVariable Long id, @RequestBody TrackDTO track) {
        return playlistService.addTrackInPlaylist(id,track);
    }

    @PutMapping(PLAYLIST_ID_PART + "/tracks1")
    public void deleteTrack(@PathVariable Long id,@RequestBody TrackDTO track) {
        playlistService.deleteTrackFromPlaylist(id,track.getId());
    }

}
