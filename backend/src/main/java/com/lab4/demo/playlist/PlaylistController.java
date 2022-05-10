package com.lab4.demo.playlist;

import com.lab4.demo.playlist.model.dto.PlaylistDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.lab4.demo.UrlMapping.PLAYLIST;
import static com.lab4.demo.UrlMapping.PLAYLIST_ID_PART;

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

    @DeleteMapping
    public void delete(@PathVariable long id){
        playlistService.delete(id);
    }

}
