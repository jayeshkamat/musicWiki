package com.music.musicwiki.utilities;

import com.music.musicwiki.activities.AlbumDetailActivity;
import com.music.musicwiki.activities.ArtistDetailActivity;
import com.music.musicwiki.activities.GenreDetailsActivity;
import com.music.musicwiki.activities.GenresActivity;
import com.music.musicwiki.fragments.GenreAlbumsFragment;
import com.music.musicwiki.fragments.GenreArtistsFragment;
import com.music.musicwiki.fragments.GenreTracksFragment;

public class StaticClass {

    //activity instances
    static GenresActivity genresActivity;
    static GenreDetailsActivity genreDetailsActivity;
    static AlbumDetailActivity albumDetailActivity;
    static ArtistDetailActivity artistDetailActivity;

    //fragment instances
    static GenreAlbumsFragment genreAlbumsFragment;
    static GenreArtistsFragment genreArtistsFragment;
    static GenreTracksFragment genreTracksFragment;

    public static GenresActivity getGenresActivity() {
        return genresActivity;
    }

    public static void setGenresActivity(GenresActivity genresActivity) {
        StaticClass.genresActivity = genresActivity;
    }

    public static GenreAlbumsFragment getGenreAlbumsFragment() {
        return genreAlbumsFragment;
    }

    public static void setGenreAlbumsFragment(GenreAlbumsFragment genreAlbumsFragment) {
        StaticClass.genreAlbumsFragment = genreAlbumsFragment;
    }

    public static GenreArtistsFragment getGenreArtistsFragment() {
        return genreArtistsFragment;
    }

    public static void setGenreArtistsFragment(GenreArtistsFragment genreArtistsFragment) {
        StaticClass.genreArtistsFragment = genreArtistsFragment;
    }

    public static GenreTracksFragment getGenreTracksFragment() {
        return genreTracksFragment;
    }

    public static void setGenreTracksFragment(GenreTracksFragment genreTracksFragment) {
        StaticClass.genreTracksFragment = genreTracksFragment;
    }

    public static GenreDetailsActivity getGenreDetailsActivity() {
        return genreDetailsActivity;
    }

    public static void setGenreDetailsActivity(GenreDetailsActivity genreDetailsActivity) {
        StaticClass.genreDetailsActivity = genreDetailsActivity;
    }

    public static AlbumDetailActivity getAlbumDetailActivity() {
        return albumDetailActivity;
    }

    public static void setAlbumDetailActivity(AlbumDetailActivity albumDetailActivity) {
        StaticClass.albumDetailActivity = albumDetailActivity;
    }

    public static ArtistDetailActivity getArtistDetailActivity() {
        return artistDetailActivity;
    }

    public static void setArtistDetailActivity(ArtistDetailActivity artistDetailActivity) {
        StaticClass.artistDetailActivity = artistDetailActivity;
    }
}
