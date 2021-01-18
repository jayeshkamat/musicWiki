package com.music.musicwiki.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.music.musicwiki.R
import com.music.musicwiki.fragments.GenreAlbumsFragment
import com.music.musicwiki.fragments.GenreArtistsFragment
import com.music.musicwiki.fragments.GenreTracksFragment
import com.music.musicwiki.utilities.StaticClass


class GenreDetailsActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    var genre = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre_details)

        StaticClass.setGenreDetailsActivity(this)

//        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
//        val viewPager: ViewPager = findViewById(R.id.view_pager)
//        viewPager.adapter = sectionsPagerAdapter
//        val tabs: TabLayout = findViewById(R.id.tabs)
//        tabs.setupWithViewPager(viewPager)

        genre = intent.getStringExtra("genre")
        viewPager = findViewById(R.id.view_pager)
        addTabs(viewPager);
    }

    private fun addTabs(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFrag(GenreAlbumsFragment(), "ALBUMS")
        adapter.addFrag(GenreArtistsFragment(), "ARTISTS")
        adapter.addFrag(GenreTracksFragment(), "TRACKS")
        viewPager.adapter = adapter
    }

    internal class ViewPagerAdapter(manager: FragmentManager?) :
        FragmentPagerAdapter(manager!!) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFrag(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }

    fun returnCurrentIndex(): Int {
        return viewPager.currentItem
    }
}