package com.music.musicwiki.utilities;

public interface Constants {

    //defining the constants
    String rootURL = "https://ws.audioscrobbler.com/2.0/";
    String apiKey = "32fa124edb31be9275a9a2f0a6d87d51";
    String genreSuffix = "chart.getTopTags";
    String albumsSuffix = "tag.getTopAlbums";
    String artistsSuffix = "tag.getTopArtists";
    String tracksSuffix = "tag.getTopTracks";
    String albumDetailsSuffix = "album.getInfo";
    String artistInfoSuffix = "artist.getInfo";
    String artistTopAlbums = "artist.getTopAlbums";
    String artistTopTracks = "artist.getTopTracks";
}
