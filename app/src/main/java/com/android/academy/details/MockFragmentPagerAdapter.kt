package com.android.academy.details

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MockFragmentPagerAdapter (
    manager:FragmentManager,
    val fragments : List<DetailsFragment>
    ):FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getItem(position: Int): Fragment =fragments[position]

    override fun getCount(): Int = fragments.size

}