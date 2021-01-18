package com.music.musicwiki.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.music.musicwiki.R
import com.music.musicwiki.adapters.GenreAdapter
import com.music.musicwiki.models.GenreModel
import com.music.musicwiki.utilities.Constants
import com.music.musicwiki.utilities.JSONParser
import com.music.musicwiki.utilities.StaticClass
import com.squareup.picasso.Picasso
import org.json.JSONObject

class AlbumDetailActivity : AppCompatActivity() {

    lateinit var progressBar: ProgressBar
    lateinit var imageView: ImageView
    lateinit var albumName: TextView
    lateinit var artistName: TextView
    lateinit var genreRecycler: RecyclerView
    lateinit var genreAdapter: GenreAdapter
    lateinit var descriptionTextView: TextView
    var modelList = ArrayList<GenreModel>()
    val url =
        Constants.rootURL + "?method=" + Constants.albumDetailsSuffix + "&api_key=" + Constants.apiKey + "&format=json"
    var mbid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.album_detail)

        mbid = intent.getStringExtra("mbid")
        progressBar = findViewById(R.id.progressBar)
        imageView = findViewById(R.id.imageView)
        albumName = findViewById(R.id.albumName)
        artistName = findViewById(R.id.artistName)
        genreRecycler = findViewById(R.id.genreRecycler)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        genreAdapter = GenreAdapter(
            this, modelList
        )

        val mLayoutManagerSeasons =
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        genreRecycler.layoutManager = mLayoutManagerSeasons
        genreRecycler.adapter = genreAdapter

        parseUrl("$url&mbid=$mbid")
    }

    override fun onResume() {
        super.onResume()
        StaticClass.setAlbumDetailActivity(this)
    }

    fun albumResponse(result: String) {

        if (result == null) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
        } else {
            try {
                val albumObject = JSONObject(result)

                Log.v(" test "," result is "+result)
                //loading data
                albumName.text = albumObject.getJSONObject("album").getString("name")
                artistName.text = albumObject.getJSONObject("album").getString("artist")

                Picasso.with(this)
                    .load(
                        albumObject.getJSONObject("album").getJSONArray("image").getJSONObject(3)
                            .getString("#text")
                    )
                    .into(imageView, object : com.squareup.picasso.Callback {
                        override fun onSuccess() {
                            //do nothing
                        }

                        override fun onError() {
                            //display default image
                        }
                    })

                val genreData =
                    albumObject.getJSONObject("album").getJSONObject("tags").getJSONArray("tag")
                for (i in 0 until genreData.length()) {
                    val genreModel = GenreModel()
                    genreModel.setData(genreData.getJSONObject(i).getString("name"))
                    modelList.add(genreModel)
                }

                genreAdapter.setListing(modelList)

                descriptionTextView.text =
                    albumObject.getJSONObject("album").getJSONObject("wiki").getString("summary")
            }
            catch (e:Exception)
            {
                e.printStackTrace()
                Toast.makeText(this, "Album not found", Toast.LENGTH_SHORT).show()
            }
        }

        progressBar.visibility = View.GONE
    }

    private fun parseUrl(url: String) {
        JSONParser().parseJSON(url, "5")
    }
}