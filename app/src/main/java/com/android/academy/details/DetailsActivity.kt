package com.android.academy.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.android.academy.R
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private val fragments :MutableList<DetailsFragment> = mutableListOf()
    private lateinit var mPager :ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        loadFragments()
        mPager = activity_details_pager
        val pagerAdapter= DetailsFragmentPagerAdapter (supportFragmentManager,fragments)
        mPager.adapter = pagerAdapter
        //setContentView(R.layout.activity_main)
        /*movie_trailer_btn.setOnClickListener { val webpage: Uri = Uri.parse(getString(
            R.string.infinity_war_url
        ))
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } }*/
    }

    private fun loadFragments() {
        fragments.add(DetailsFragment.newInstance(getString( R.string.guardians_of_the_galaxy_name)))
        fragments.add(DetailsFragment.newInstance(getString( R.string.the_meg_name)))
        fragments.add(DetailsFragment.newInstance(getString( R.string.infinity_war_name)))
    }
}
