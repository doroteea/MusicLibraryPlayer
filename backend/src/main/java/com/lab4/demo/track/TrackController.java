package com.lab4.demo.track;

import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.track.model.Track;
import com.lab4.demo.track.model.TrackDTO;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@Validated
@RestController
@RequestMapping(TRACKS)
@RequiredArgsConstructor
public class TrackController {

    private final RequestConfig customizedRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();
    private final HttpClientBuilder customizedClientBuilder = HttpClients.custom().setDefaultRequestConfig(customizedRequestConfig);
    private final CloseableHttpClient client = customizedClientBuilder.build(); // customized client,

    private final TrackService trackService;

//    public List<TrackDTO> searchTracks(String query){
//       // query="ariana";
//        HttpResponse<String> response = Unirest.get("https://deezerdevs-deezer.p.rapidapi.com/search?q=" + query)
//                .header("X-RapidAPI-Host", "deezerdevs-deezer.p.rapidapi.com")
//                .header("X-RapidAPI-Key", "b0a6df27edmsha84d7ac00c2bd55p1f21bfjsn857a47e91b7e")
//                .asString();
//        List<TrackDTO> tracks = new ArrayList<>();
//        final JSONObject obj = new JSONObject(response.getBody());
//        JSONArray data = obj.getJSONArray("data");
//        int n = data.length();
//
//        for (int i = 0; i < n; ++i) {
//            JSONObject song = data.getJSONObject(i);
//            JSONObject artistJson = song.getJSONObject("artist");
//            JSONObject albumJson = song.getJSONObject("album");
//            TrackDTO track = TrackDTO.builder()
//                    .id((long) song.getInt("id"))
//                    .title(song.getString("title"))
//                    .link(song.getString("link"))
//                    .preview(song.getString("preview"))
//                    .duration(song.getInt("duration"))
//                    .explicit_lyrics(song.getBoolean("explicit_lyrics"))
//                    .artist(artistJson.getString("name"))
//                    .album(albumJson.getString("title"))
//                    .build();
//            tracks.add(track);
//        }
//        return tracks;
//    }
public List<TrackDTO> searchTracks(){
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
        return searchTracks();
    }

    @GetMapping(FIND_SEARCH_BAR)
    public List<TrackDTO> findAllByFilter(@PathVariable String filter) {
        return searchTracks();
    }



}
