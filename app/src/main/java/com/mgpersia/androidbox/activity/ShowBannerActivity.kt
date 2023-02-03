package com.mgpersia.androidbox.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.adapter.BannerSliderActivityAdapter
import com.mgpersia.androidbox.data.model.Image
import com.mgpersia.androidbox.databinding.ActivityShowBannerBinding
import convertDpToPixel

class ShowBannerActivity : AppCompatActivity() {

    lateinit var binding: ActivityShowBannerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_banner)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
    }

    override fun onResume() {
        super.onResume()
        setInformation()
        setListener()
    }

    private fun setInformation() {
        val getBundle = this.intent.extras
        val list: List<Image> = getBundle!!.getParcelableArrayList("banner")!!
        val bannerSliderAdapter =
            BannerSliderActivityAdapter(
                this,
                list
            )
        this.binding.activityShowBannerBannerSliderVp.adapter = bannerSliderAdapter
        this.binding.activityShowBannerBannerSliderVp.post {
            val height =
                (((this.binding.activityShowBannerBannerSliderVp.width - convertDpToPixel(
                    32f,
                    this
                )) * 173) / 328).toInt()
            val layoutParams = this.binding.activityShowBannerBannerSliderVp.layoutParams
            layoutParams.height = height
            this.binding.activityShowBannerBannerSliderVp.layoutParams = layoutParams
            this.binding.activityShowBannerBannerSliderDi.setViewPager2(this.binding.activityShowBannerBannerSliderVp)

        }

        if (list.size == 1)
            this.binding.activityShowBannerBannerSliderDi.visibility = View.GONE
    }

    private fun setListener() {
        this.binding.activityShowBannerCloseBtn.setOnClickListener {
            this.onBackPressed()
        }
    }
}