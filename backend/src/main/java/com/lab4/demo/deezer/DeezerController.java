package com.lab4.demo.deezer;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Controller;

@Controller
public class DeezerController {

    public void getApi(){
        RequestConfig customizedRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();
        HttpClientBuilder customizedClientBuilder = HttpClients.custom().setDefaultRequestConfig(customizedRequestConfig);
        CloseableHttpClient client = customizedClientBuilder.build(); // customized client,

        HttpResponse<String> response = Unirest.get("https://deezerdevs-deezer.p.rapidapi.com/search?q=ariana")
                .header("X-RapidAPI-Host", "deezerdevs-deezer.p.rapidapi.com")
                .header("X-RapidAPI-Key", "b0a6df27edmsha84d7ac00c2bd55p1f21bfjsn857a47e91b7e")
                .asString();

        final JSONObject obj = new JSONObject(response.getBody());

        JSONArray data = obj.getJSONArray("data");
         int n = data.length();
        for (int i = 0; i < n; ++i) {
            JSONObject song = data.getJSONObject(i);
            System.out.println(song.getInt("id"));

            System.out.println(song.getString("title"));
            System.out.println(song.getString("link"));
            System.out.println(song.getString("preview"));
            System.out.println(song.getInt("duration"));
            System.out.println(song.getBoolean("explicit_lyrics"));
            JSONObject artistJson = song.getJSONObject("artist");
            System.out.println(artistJson.getString("name"));
            JSONObject albumJson = song.getJSONObject("album");
            System.out.println(albumJson.getString("title"));

        }

        System.out.println(response.getBody());
    }
    public void getTracks(){
        RequestConfig customizedRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();
        HttpClientBuilder customizedClientBuilder = HttpClients.custom().setDefaultRequestConfig(customizedRequestConfig);
        CloseableHttpClient client = customizedClientBuilder.build(); // customized client,
        HttpResponse<String> response = Unirest.get("https://deezerdevs-deezer.p.rapidapi.com/track/629899842")
                .header("X-RapidAPI-Host", "deezerdevs-deezer.p.rapidapi.com")
                .header("X-RapidAPI-Key", "b0a6df27edmsha84d7ac00c2bd55p1f21bfjsn857a47e91b7e")
                .asString();
        System.out.println(response.getBody());
    }
}
