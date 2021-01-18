package com.music.musicwiki.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.music.musicwiki.R
import com.music.musicwiki.activities.GenreDetailsActivity
import com.music.musicwiki.models.GenreModel
import java.util.*


class GenreAdapter(
    private val context: Activity,
    list: ArrayList<GenreModel>
) : androidx.recyclerview.widget.RecyclerView.Adapter<GenreAdapter.MyViewHolder>() {

    private var modelList = ArrayList<GenreModel>()

    inner class MyViewHolder(view: View, viewType: Int) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

        //listing widgets
        var genreTag: TextView? = null

        init {

            if (viewType == 0)//grid item
            {
                genreTag = itemView.findViewById(R.id.genreTag)
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
                .inflate(R.layout.genres_gridview_item, parent, false)
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
                holder.genreTag!!.text = modelList[position].tag

                holder.genreTag!!.setOnClickListener {
                    val intent = Intent(context, GenreDetailsActivity::class.java)
                    intent.putExtra("genre", modelList[position].tag)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    //function called from the parent activity or fragment to notify the adapter change
    fun setListing(list: ArrayList<GenreModel>) {

        this.modelList = ArrayList()
        this.modelList.addAll(list)
        notifyDataSetChanged()
    }
}