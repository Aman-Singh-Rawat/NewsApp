package com.newsapp.presenter.screen.onboading

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.view.marginTop
import androidx.viewpager2.widget.ViewPager2
import com.newsapp.R
import com.newsapp.databinding.ActivityOnboardingBinding
import com.newsapp.databinding.LayoutTwoButtonBinding
import com.newsapp.presenter.screen.auth.SignUp

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private var btnGetStarted: Button? = null
    private var btnOnboardingSkip: TextView? = null
    private var btnOnboardingContinue: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnOnboardingSkip = binding.twoButton.root.findViewById(R.id.btnOnboardingSkip)
        btnOnboardingContinue = binding.twoButton.root.findViewById(R.id.btnOnboardingContinue)
        btnGetStarted = binding.btnGetStarted.root.findViewById(R.id.btnAllInOne)

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

        btnOnboardingSkip?.setOnClickListener {
            openNewActivity()
        }
    }


    private fun onPageChangeCallback() {
        binding.viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when(position) {
                    0 -> {
                        btnOnboardingContinue?.setOnClickListener {
                            binding.viewPager2.setCurrentItem(1, true)
                        }
                        btnGetStartedInvisible()
                    }
                    1 -> {
                        btnOnboardingContinue?.setOnClickListener {
                            binding.viewPager2.setCurrentItem(2, true)
                        }
                        btnGetStartedInvisible()
                    }
                    2 -> {
                        btnGetStarted?.setOnClickListener {
                            openNewActivity()
                        }
                        btnGetStartedVisible()
                    }
                }
            }
        })
    }
    private fun btnGetStartedVisible() {
        binding.btnGetStarted.root.visibility = View.VISIBLE
        btnOnboardingSkip?.visibility = View.GONE
        btnOnboardingContinue?.visibility = View.GONE
        btnGetStarted?.text = "Get Started"
    }
    private fun btnGetStartedInvisible() {
        btnOnboardingContinue?.visibility = View.VISIBLE
        btnOnboardingSkip?.visibility = View.VISIBLE
        binding.btnGetStarted.root.visibility = View.GONE
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