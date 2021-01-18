package com.music.musicwiki.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.music.musicwiki.R
import com.music.musicwiki.adapters.ArtistDetailsAlbums
import com.music.musicwiki.adapters.ArtistDetailsTracks
import com.music.musicwiki.adapters.GenreAdapter
import com.music.musicwiki.models.GenreDetailModel
import com.music.musicwiki.models.GenreModel
import com.music.musicwiki.utilities.Constants
import com.music.musicwiki.utilities.JSONParser
import com.music.musicwiki.utilities.StaticClass
import com.squareup.picasso.Picasso
import org.json.JSONObject

class ArtistDetailActivity : AppCompatActivity() {

    lateinit var progressBar: ProgressBar
    lateinit var imageView: ImageView
    lateinit var playcount: TextView
    lateinit var followerCount: TextView
    lateinit var artistName: TextView
    lateinit var genreRecycler: RecyclerView
    lateinit var topTracksRecycler: RecyclerView
    lateinit var topAlbumsRecycler: RecyclerView
    lateinit var genreAdapter: GenreAdapter
    lateinit var genreDetailsAdapterAlbums: ArtistDetailsAlbums
    lateinit var genreDetailsAdapterTracks: ArtistDetailsTracks
    lateinit var descriptionTextView: TextView
    var modelList = ArrayList<GenreModel>()
    var topAlbumsList = ArrayList<GenreDetailModel>()
    var topTracksList = ArrayList<GenreDetailModel>()
    val url =
        Constants.rootURL + "?method=" + Constants.artistInfoSuffix + "&api_key=" + Constants.apiKey + "&format=json"
    var mbid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.artist_detail)

        mbid = intent.getStringExtra("mbid")
        progressBar = findViewById(R.id.progressBar)
        imageView = findViewById(R.id.imageView)
        playcount = findViewById(R.id.playcount)
        followerCount = findViewById(R.id.followerCount)
        topTracksRecycler = findViewById(R.id.topTracksRecycler)
        topAlbumsRecycler = findViewById(R.id.topAlbumsRecycler)
        artistName = findViewById(R.id.artistName)
        genreRecycler = findViewById(R.id.genreRecycler)
        descriptionTextView = findViewById(R.id.descriptionTextView)

        genreAdapter = GenreAdapter(
            this, modelList
        )

        genreDetailsAdapterAlbums = ArtistDetailsAlbums(
            this, topAlbumsList
        )

        genreDetailsAdapterTracks = ArtistDetailsTracks(
            this, topTracksList
        )

        val mLayoutManagerSeasons =
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        genreRecycler.layoutManager = mLayoutManagerSeasons
        genreRecycler.adapter = genreAdapter

        val layoutManagerTwo =
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        topAlbumsRecycler.layoutManager = layoutManagerTwo
        topAlbumsRecycler.adapter = genreDetailsAdapterAlbums

        val layoutManagerThree =
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        topTracksRecycler.layoutManager = layoutManagerThree
        topTracksRecycler.adapter = genreDetailsAdapterTracks

        parseUrl("$url&mbid=$mbid")
    }

    override fun onResume() {
        super.onResume()
        StaticClass.setArtistDetailActivity(this)
    }

    fun albumResponse(result: String) {

        if (result == null) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
        } else {
            try {
                val albumObject = JSONObject(result)

                //loading data
                artistName.text = albumObject.getJSONObject("artist").getString("name")
                playcount.text = albumObject.getJSONObject("artist").getJSONObject("stats")
                    .getString("playcount")
                followerCount.text = albumObject.getJSONObject("artist").getJSONObject("stats")
                    .getString("listeners")

                Picasso.with(this)
                    .load(
                        albumObject.getJSONObject("artist").getJSONArray("image").getJSONObject(3)
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
                    albumObject.getJSONObject("artist").getJSONObject("tags").getJSONArray("tag")
                for (i in 0 until genreData.length()) {
                    val genreModel = GenreModel()
                    genreModel.setData(genreData.getJSONObject(i).getString("name"))
                    modelList.add(genreModel)
                }

                genreAdapter.setListing(modelList)

                descriptionTextView.text =
                    albumObject.getJSONObject("artist").getJSONObject("bio").getString("summary")
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Artist not found", Toast.LENGTH_SHORT).show()
            }
        }

        progressBar.visibility = View.GONE

        parseTopAlbumsUrl()
        parseTopTracksUrl()
    }

    private fun parseUrl(url: String) {
        JSONParser().parseJSON(url, "6")
    }

    fun parseTopAlbumsUrl()
    {
        var url =
            Constants.rootURL + "?method=" + Constants.artistTopAlbums + "&api_key=" + Constants.apiKey + "&format=json"
        url = "$url&mbid=$mbid"
        JSONParser().parseJSON(url, "7")
    }

    fun parseTopTracksUrl()
    {
        var url =
            Constants.rootURL + "?method=" + Constants.artistTopTracks + "&api_key=" + Constants.apiKey + "&format=json"
        url = "$url&mbid=$mbid"
        JSONParser().parseJSON(url, "8")
    }

    fun topAlbumsResponse(result: String) {

        if (result == null) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
        } else {
            val genreObject = JSONObject(result)
            val genreData = genreObject.getJSONObject("topalbums").getJSONArray("album")
            for (i in 0 until genreData.length()) {
                var mbid = ""
                if (genreData.getJSONObject(i).has("mbid")) {
                    mbid = genreData.getJSONObject(i).getString("mbid")
                }
                val genreModel = GenreDetailModel()
                genreModel.setData(
                    genreData.getJSONObject(i).getString("name"),
                    genreData.getJSONObject(i).getJSONObject("artist").getString("name"),
                    genreData.getJSONObject(i).getJSONArray("image").getJSONObject(3)
                        .getString("#text"),
                    mbid
                )
                topAlbumsList.add(genreModel)
            }
        }

        genreDetailsAdapterAlbums.setListing(topAlbumsList)
    }

    fun topTracksResponse(result: String) {

        if (result == null) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
        } else {
            val genreObject = JSONObject(result)
            val genreData = genreObject.getJSONObject("toptracks").getJSONArray("track")
            for (i in 0 until genreData.length()) {
                var mbid = ""
                if (genreData.getJSONObject(i).has("mbid")) {
                    mbid = genreData.getJSONObject(i).getString("mbid")
                }
                val genreModel = GenreDetailModel()
                genreModel.setData(
                    genreData.getJSONObject(i).getString("name"),
                    genreData.getJSONObject(i).getJSONObject("artist").getString("name"),
                    genreData.getJSONObject(i).getJSONArray("image").getJSONObject(3)
                        .getString("#text"),
                    mbid
                )
                topTracksList.add(genreModel)
            }
        }
        genreDetailsAdapterTracks.setListing(topTracksList)
    }
}