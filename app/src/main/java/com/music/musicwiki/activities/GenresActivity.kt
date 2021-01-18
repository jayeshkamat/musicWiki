package com.music.musicwiki.activities

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.music.musicwiki.R
import com.music.musicwiki.adapters.GenreAdapter
import com.music.musicwiki.models.GenreModel
import com.music.musicwiki.utilities.Constants
import com.music.musicwiki.utilities.JSONParser
import com.music.musicwiki.utilities.StaticClass
import org.json.JSONObject

class GenresActivity : AppCompatActivity() {

    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var genreAdapter: GenreAdapter
    var modelList = ArrayList<GenreModel>()
    val url =
        Constants.rootURL + "?method=" + Constants.genreSuffix + "&api_key=" + Constants.apiKey + "&format=json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genres)

        recyclerView = findViewById(R.id.genreRecyclerView)
        progressBar = findViewById(R.id.progressBar)
        genreAdapter = GenreAdapter(
            this, modelList
        )

        val mLayoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = genreAdapter

        parseUrl(url)
    }

    override fun onResume() {
        super.onResume()
        StaticClass.setGenresActivity(this)
    }

    fun genreResponse(result: String) {

        if (result == null) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
        } else {
            val genreData = JSONObject(result).getJSONObject("tags").getJSONArray("tag")
            for (i in 0 until genreData.length()) {
                val genreModel = GenreModel()
                genreModel.setData(genreData.getJSONObject(i).getString("name"))
                modelList.add(genreModel)
            }
        }

        progressBar.visibility = View.GONE
        genreAdapter.setListing(modelList)
    }

    fun fetchAlbums(chosenTag: String) {
        //open genre details activity.
    }

    private fun parseUrl(url: String) {
        JSONParser().parseJSON(url,"1")
    }
}