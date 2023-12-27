package com.example.koplakmungkin.ui.onboarding


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.koplakmungkin.ui.locationpermission.LocationPermissionActivity
import com.example.koplakmungkin.R
import com.example.koplakmungkin.utils.SharedPreferenceManager
import com.example.koplakmungkin.databinding.ActivityOnBoardingBinding
import com.example.koplakmungkin.utils.gone
import com.example.koplakmungkin.utils.visible
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    private val onBoardingPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            when (position) {
                1 -> {
                    binding.nextBtn.text = getString(R.string.started)
                    binding.nextBtn.visible()
                    binding.skipBtn.gone()
                }
                else -> {
                    binding.nextBtn.text = getString(R.string.next)
                    binding.nextBtn.visible()
                    binding.skipBtn.visible()
                }
            }
        }
    }

    private val pagerList = arrayListOf(
        Page(R.string.onboarding1, R.string.descboarding1),
        Page(R.string.onboarding2, R.string.descboarding2)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.onBoardingViewPager2.apply {
            adapter = OnBoardingAdapter(this@OnBoardingActivity, pagerList)
            registerOnPageChangeCallback(onBoardingPageChangeCallback)
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, binding.onBoardingViewPager2) { _, _ -> }.attach()

        binding.nextBtn.setOnClickListener {
            if (binding.onBoardingViewPager2.currentItem < binding.onBoardingViewPager2.adapter!!.itemCount - 1) {
                binding.onBoardingViewPager2.currentItem += 1
            } else {
                homeScreenIntent()
            }
        }

        binding.skipBtn.setOnClickListener {
            homeScreenIntent()
        }
    }

    override fun onDestroy() {
        binding.onBoardingViewPager2.unregisterOnPageChangeCallback(onBoardingPageChangeCallback)
        super.onDestroy()
    }

    private fun homeScreenIntent() {
        val sharedPreferenceManager = SharedPreferenceManager(this)
        sharedPreferenceManager.isFirstTime = false
        val intent = Intent(this, LocationPermissionActivity::class.java)
        startActivity(intent)
    }
}
