package com.lab4.demo;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String TRACKS = API_PATH+"/tracks";
    public static final String API_TRACKS = API_PATH+"/tracksAPI";
    public static final String TRACK_ID_PART = "/{id}";

    public static final String PLAYLIST = API_PATH + "/playlists";
    public static final String PLAYLIST_ID_PART = "/{id}" ;

    public static final String BOOKS = API_PATH + "/items";

    public static final String BOOKS_ID_PART = "/{id}";
    public static final String EXPORT_REPORT = "/export/{type}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USER = API_PATH + "/user";
    public static final String USER_ID_PART = "/{id}";

    public static final String SELL ="/sell";

    public static final String FIND_SEARCH_BAR ="/filter/{filter}";

}