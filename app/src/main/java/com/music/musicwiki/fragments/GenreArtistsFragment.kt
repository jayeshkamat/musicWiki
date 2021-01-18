package com.music.musicwiki.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.music.musicwiki.R
import com.music.musicwiki.adapters.GenreDetailsAdapter
import com.music.musicwiki.models.GenreDetailModel
import com.music.musicwiki.utilities.Constants
import com.music.musicwiki.utilities.JSONParser
import com.music.musicwiki.utilities.StaticClass
import org.json.JSONObject

class GenreArtistsFragment : androidx.fragment.app.Fragment() {

    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var genreDetailsAdapter: GenreDetailsAdapter
    var modelList = ArrayList<GenreDetailModel>()
    var url =
        Constants.rootURL + "?method=" + Constants.artistsSuffix + "&api_key=" + Constants.apiKey + "&format=json"

    lateinit var fragment: GenreArtistsFragment
    private var fragmentView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (fragmentView != null) {
            return fragmentView
        }

        fragmentView = inflater.inflate(
            R.layout.genre_details,
            container, false
        )

        recyclerView = fragmentView!!.findViewById(R.id.recyclerView)
        progressBar = fragmentView!!.findViewById(R.id.progressBar)

        //initialise adapters
        genreDetailsAdapter = GenreDetailsAdapter(
            activity!!, modelList
        )

        val mLayoutManager = GridLayoutManager(activity, 2)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = genreDetailsAdapter

        parseUrl("$url&tag=${StaticClass.getGenreDetailsActivity().genre}")

        return fragmentView
    }

    private fun parseUrl(url: String) {
        JSONParser().parseJSON(url, "3")
    }

    fun albumsResponse(result: String) {

        if (result == null) {
            Toast.makeText(activity, "No data", Toast.LENGTH_SHORT).show()
        } else {
            Log.v(" test ", " result is " + result)
            val genreObject = JSONObject(result)
            val genreData = genreObject.getJSONObject("topartists").getJSONArray("artist")
            for (i in 0 until genreData.length()) {
                var mbid = ""
                if (genreData.getJSONObject(i).has("mbid")) {
                    mbid = genreData.getJSONObject(i).getString("mbid")
                }
                val genreModel = GenreDetailModel()
                genreModel.setData(
                    genreData.getJSONObject(i).getString("name"),
                    "",
                    genreData.getJSONObject(i).getJSONArray("image").getJSONObject(3)
                        .getString("#text"),
                    mbid
                )
                modelList.add(genreModel)
            }
        }

        progressBar.visibility = View.GONE
        genreDetailsAdapter.setListing(modelList)
    }

    override fun onResume() {
        super.onResume()
        StaticClass.setGenreArtistsFragment(this)
    }
}