package com.lab4.demo.playlist;

import com.lab4.demo.playlist.model.dto.PlaylistDTO;
import com.lab4.demo.track.model.TrackDTO;
import com.lab4.demo.user.UserNotFoundException;
import com.lab4.demo.user.UserService;
import com.lab4.demo.user.dto.UserListDTO;
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

    private final UserService userService;
    private final PlaylistService playlistService;

    @GetMapping
    public List<PlaylistDTO> getAllPlaylists(){
        return playlistService.findAll();
    }

    @PutMapping("/create/{id}")
    public UserListDTO create(@PathVariable Long id, @Valid @RequestBody PlaylistDTO playlistDTO) throws UserNotFoundException {
        return userService.createPlaylist(id,playlistDTO);
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

}