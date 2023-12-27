package com.example.koplakmungkin.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardingAdapter(activity : FragmentActivity, private val pagerlist : ArrayList<Page>) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return pagerlist.size
    }

    override fun createFragment(position: Int): Fragment {
        return OnBoardingFragment(pagerlist[position])
    }
}