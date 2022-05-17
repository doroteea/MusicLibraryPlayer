package com.lab4.demo.track;

import com.lab4.demo.playlist.PlaylistService;
import com.lab4.demo.playlist.model.dto.PlaylistDTO;
import com.lab4.demo.track.model.Track;
import com.lab4.demo.track.model.TrackDTO;
import com.lab4.demo.user.UserNotFoundException;
import com.lab4.demo.user.UserService;
import com.lab4.demo.user.dto.UserListDTO;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@Validated
@RestController
@RequestMapping(API_TRACKS)
@RequiredArgsConstructor
public class TrackApiController {

    private final TrackService trackService;
    private final PlaylistService playlistService;
    private final UserService userService;

    public List<TrackDTO> searchTracks(String query){
        // query="ariana";
        HttpResponse<String> response = Unirest.get("https://deezerdevs-deezer.p.rapidapi.com/playlist/3155776842")
                .header("X-RapidAPI-Host", "deezerdevs-deezer.p.rapidapi.com")
                .header("X-RapidAPI-Key", "b0a6df27edmsha84d7ac00c2bd55p1f21bfjsn857a47e91b7e")
                .asString();
        List<TrackDTO> tracks = new ArrayList<>();
        final JSONObject obj = new JSONObject(response.getBody());
        JSONObject data1 = obj.getJSONObject("tracks");
        JSONArray data = data1.getJSONArray("data");
        int n = data.length();

        for (int i = 0; i < n; ++i) {
            JSONObject song = data.getJSONObject(i);
            JSONObject artistJson = song.getJSONObject("artist");
            JSONObject albumJson = song.getJSONObject("album");
            TrackDTO track = TrackDTO.builder()
                    .id((long) song.getInt("id"))
                    .title(song.getString("title"))
                    .link(song.getString("link"))
                    .preview(song.getString("preview"))
                    .duration(song.getInt("duration"))
                    .explicit_lyrics(song.getBoolean("explicit_lyrics"))
                    .artist(artistJson.getString("name"))
                    .album(albumJson.getString("title"))
                    .build();
            tracks.add(track);
        }
        return tracks;
    }

    @GetMapping
    public List<TrackDTO> allTracks() {
        return searchTracks("ariana");
    }

    @GetMapping(FIND_SEARCH_BAR)
    public List<TrackDTO> findAllByFilter(@PathVariable String filter) {
        return searchTracks(filter);
    }

    @PutMapping(PLAYLIST_ID_PART)
    public PlaylistDTO addToPlaylist(@PathVariable Long id, @RequestBody TrackDTO track){
        return playlistService.addTrackInPlaylist(id,track);
    }

    @PutMapping("/purchase/{id}")
    public UserListDTO purchase(@PathVariable Long id, @RequestBody TrackDTO track) throws UserNotFoundException {
        return userService.buyTrack(id,track);
    }

}
