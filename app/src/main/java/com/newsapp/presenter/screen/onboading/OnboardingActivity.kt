package com.newsapp.presenter.screen.onboading

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.newsapp.R
import com.newsapp.databinding.ActivityOnboardingBinding
import com.newsapp.presenter.screen.auth.SignUp

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPagerFunctionality()

    }

    private fun viewPagerFunctionality() {
        val VPAdapter = VPAdapter(addValuesOnArrayList())
        binding.viewPager2.adapter = VPAdapter
        binding.viewPager2.clipToPadding = false
        binding.viewPager2.clipChildren = false
        binding.viewPager2.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER

        binding.dotsIndicator.attachTo(binding.viewPager2)

        onPageChangeCallback()

        binding.btnOnboardingSkip.setOnClickListener {
            openNewActivity()
        }
    }


    private fun onPageChangeCallback() {
        binding.viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when(position) {
                    0 -> {
                        binding.btnOnboardingContinue.setOnClickListener {
                            binding.viewPager2.setCurrentItem(1, true)
                        }
                    }
                    1 -> {
                        binding.btnOnboardingContinue.setOnClickListener {
                            binding.viewPager2.setCurrentItem(2, true)
                        }
                    }
                    2 -> {
                        binding.btnOnboardingContinue.setOnClickListener {
                            openNewActivity()
                        }
                    }
                }
            }
        })
    }
    private fun openNewActivity() {
        startActivity(Intent(
            this@OnboardingActivity,
            SignUp::class.java
        ))
    }
    private fun addValuesOnArrayList(): ArrayList<ViewPagerItem> {
        val arrayList = ArrayList<ViewPagerItem>()
        arrayList.add(ViewPagerItem(R.drawable.img_trending_demo,
            "Stay Informed, Anytime, Anywhere",
            "Welcome to our news app, your go-to source" +
                    " for breaking news, exclusive"
        ))

        arrayList.add(ViewPagerItem(R.drawable.img_profile_demo,
            "Be a knowledgeable Global Citizen",
            "Unlock a personalized news experience that matches " +
                    "your interest and preferences. Your news, your way!"
        ))

        arrayList.add(ViewPagerItem(R.drawable.img_elevate_demo,
            "Elevate Your News Experience Now!",
            "Join our vibrant community of news enthusiasts. " +
                    "Share your thoughts, and engage in meaningful discussions."
        ))
        return arrayList
    }
}