package com.music.musicwiki.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.music.musicwiki.R
import com.music.musicwiki.activities.AlbumDetailActivity
import com.music.musicwiki.activities.ArtistDetailActivity
import com.music.musicwiki.models.GenreDetailModel
import com.music.musicwiki.utilities.StaticClass
import com.squareup.picasso.Picasso
import java.util.*


class GenreDetailsAdapter(
    private val context: Activity,
    list: ArrayList<GenreDetailModel>
) : androidx.recyclerview.widget.RecyclerView.Adapter<GenreDetailsAdapter.MyViewHolder>() {

    private var modelList = ArrayList<GenreDetailModel>()

    inner class MyViewHolder(view: View, viewType: Int) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

        //listing widgets
        var albumName: TextView? = null
        var artistName: TextView? = null
        var imageView: ImageView? = null
        var mainLayout: RelativeLayout? = null

        init {

            if (viewType == 0)//grid item
            {
                albumName = itemView.findViewById(R.id.albumName)
                artistName = itemView.findViewById(R.id.artistName)
                imageView = itemView.findViewById(R.id.imageView)
                mainLayout = itemView.findViewById(R.id.mainLayout)
            }
        }
    }

    init {
        this.modelList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        lateinit var itemView: View
        if (viewType == 0)
        //grid item
        {
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.genres_details_gridview_item, parent, false)
        }

        val holder = MyViewHolder(itemView, viewType)
        return holder
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return modelList[position].viewType
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        context.runOnUiThread {

            if (modelList[position].viewType == 0) {
                holder.albumName!!.text = modelList[position].albumName
                holder.artistName!!.text = modelList[position].artistName

                Picasso.with(context)
                    .load(modelList[position].imageUrl)
                    .into(holder.imageView, object : com.squareup.picasso.Callback {
                        override fun onSuccess() {
                            //do nothing
                        }

                        override fun onError() {
                            //display default image
                        }
                    })


                holder.mainLayout!!.setOnClickListener {
                    if (StaticClass.getGenreDetailsActivity() != null) {
                        if (StaticClass.getGenreDetailsActivity().returnCurrentIndex() == 0) {
                            val intent = Intent(context, AlbumDetailActivity::class.java)
                            intent.putExtra("mbid", modelList[position].mbid)
                            context.startActivity(intent)
                        }
                        else if (StaticClass.getGenreDetailsActivity().returnCurrentIndex() == 1) {
                            val intent = Intent(context, ArtistDetailActivity::class.java)
                            intent.putExtra("mbid", modelList[position].mbid)
                            context.startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    //function called from the parent activity or fragment to notify the adapter change
    fun setListing(list: ArrayList<GenreDetailModel>) {

        this.modelList = ArrayList()
        this.modelList.addAll(list)
        notifyDataSetChanged()
    }
}