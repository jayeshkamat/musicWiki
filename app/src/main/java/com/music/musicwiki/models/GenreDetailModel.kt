package com.music.musicwiki.models

class GenreDetailModel {

    var albumName: String? = null
    var artistName: String? = null
    var imageUrl: String? = null
    var mbid: String? = null
    var viewType = 0

    fun setData(album: String, artist: String, imageLink: String, genreId: String) {

        this.albumName = album.trim()
        this.artistName = artist.trim()
        this.imageUrl = imageLink.trim()
        this.mbid = genreId.trim()
    }
}