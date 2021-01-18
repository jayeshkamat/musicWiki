package com.music.musicwiki.models

class GenreModel {

    var tag: String? = null
    var viewType = 0

    fun setData(tagString: String) {

        this.tag = tagString.trim()
    }
}