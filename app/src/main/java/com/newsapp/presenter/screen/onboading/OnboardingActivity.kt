package com.newsapp.presenter.screen.onboading

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.newsapp.R
import com.newsapp.databinding.ActivityOnboardingBinding
import com.newsapp.presenter.screen.auth.register.SignUpActivity

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding

//    private var btnGetStarted: Button? = null
//    private var btnOnboardingSkip: TextView? = null
//    private var btnOnboardingContinue: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        btnOnboardingSkip = binding.twoButton.root.findViewById(R.id.btnOnboardingSkip)
//        btnOnboardingContinue = binding.twoButton.root.findViewById(R.id.btnOnboardingContinue)
//        btnGetStarted = binding.btnGetStarted.root.findViewById(R.id.btnAllInOne)

        viewPagerFunctionality()

    }

    private fun viewPagerFunctionality() {
        binding.viewPager2.adapter = OnboardingAdapter(addValuesOnArrayList())
        binding.viewPager2.clipToPadding = false
        binding.viewPager2.clipChildren = false
        binding.viewPager2.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
        binding.dotsIndicator.attachTo(binding.viewPager2)

        onPageChangeCallback()

        binding.twoButton.btnOnboardingSkip.setOnClickListener {
            openNewActivity()
        }
    }

    private fun onPageChangeCallback() {
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when (position) {
                    0 -> {
                        binding.twoButton.btnOnboardingContinue.setOnClickListener {
                            binding.viewPager2.setCurrentItem(1, true)
                        }
                        btnGetStartedInvisible()
                    }
                    1 -> {
                        binding.twoButton.btnOnboardingContinue.setOnClickListener {
                            binding.viewPager2.setCurrentItem(2, true)
                        }
                        btnGetStartedInvisible()
                    }
                    2 -> {
                        binding.btnGetStarted.btnAllInOne.setOnClickListener {
                            openNewActivity()
                        }
                        btnGetStartedVisible()
                    }
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun btnGetStartedVisible() {
        binding.btnGetStarted.root.visibility = View.VISIBLE
        binding.twoButton.btnOnboardingSkip.visibility = View.GONE
        binding.twoButton.btnOnboardingContinue.visibility = View.GONE
        binding.btnGetStarted.btnAllInOne.text = "Get Started"
    }

    private fun btnGetStartedInvisible() {
        binding.twoButton.btnOnboardingContinue.visibility = View.VISIBLE
        binding.twoButton.btnOnboardingSkip.visibility = View.VISIBLE
        binding.btnGetStarted.root.visibility = View.GONE
    }

    private fun openNewActivity() {
        startActivity(
            Intent(
                this@OnboardingActivity,
                SignUpActivity::class.java
            )
        )
    }

    private fun addValuesOnArrayList() = listOf(
        OnboardingItem(
            R.drawable.img_trending_demo,
            "Stay Informed, Anytime, Anywhere",
            "Welcome to our news app, your go-to source" +
                    " for breaking news, exclusive"
        ),
        OnboardingItem(
            R.drawable.img_profile_demo,
            "Be a knowledgeable Global Citizen",
            "Unlock a personalized news experience that matches " +
                    "your interest and preferences. Your news, your way!"
        ),
        OnboardingItem(
            R.drawable.img_elevate_demo,
            "Elevate Your News Experience Now!",
            "Join our vibrant community of news enthusiasts. " +
                    "Share your thoughts, and engage in meaningful discussions."
        )
    )
}