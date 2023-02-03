package com.mgpersia.androidbox.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.adapter.LiveItemChannelAdapter
import com.mgpersia.androidbox.common.RoundedTransformation
import com.mgpersia.androidbox.data.model.Live
import com.mgpersia.androidbox.databinding.ActivityLiveChannelBinding
import com.mgpersia.androidbox.player.PlayerActivity
import com.squareup.picasso.Picasso

class LiveChannelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLiveChannelBinding
    private var live: Live? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_live_channel)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        setListener()
        setInformation()
    }

    private fun onClickLiveChannle(live: Live, lives: ArrayList<Live>) {
        startActivity(
            Intent(
                this,
                LiveChannelActivity::class.java
            ).apply {
                putExtra(
                    "live",
                    live
                )
                putExtra(
                    "lives",
                    lives

                )
            })
    }

    private fun setInformation() {

        live = intent.getParcelableExtra("live")!!
        val lives: java.util.ArrayList<Live> = intent.getParcelableArrayListExtra("lives")!!

        val displayMetrics = this.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        val layoutParams = this.binding.ActivityLiveChannelChannelBannerIv.layoutParams
        layoutParams.height =
            if (dpWidth > 1000) (dpWidth * 0.4).toInt() else (dpWidth*1.3).toInt()
        this.binding.ActivityLiveChannelChannelBannerIv.layoutParams = layoutParams
        Picasso.with(this).load(live!!.baner)
            .transform(RoundedTransformation(15, 0))
            .fit().centerCrop()
            .into(this.binding.ActivityLiveChannelChannelBannerIv)

        Picasso.with(this).load(live!!.icon)
            .transform(RoundedTransformation(15, 0))
            .fit().centerCrop()
            .into(this.binding.ActivityLiveChannelChannelIconIv)

        this.binding.ActivityLiveChannelChannelEFilmNameTv.text = live!!.en_name

        this.binding.ActivityLiveChannelChannelItemDescriptionTv.text = live!!.description

        this.binding.ActivityLiveChannelChannelItemRv.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false)

        val items = ArrayList<Live>()

        for (item in lives)
            if (item.id != live!!.id)
                items.add(item)

        var adapter = LiveItemChannelAdapter(items as List<Live>, false, object :
            LiveItemChannelAdapter.EventListener {
            override fun click(live: Live) {
                onClickLiveChannle(live, lives)
            }
        }, this)
        binding.ActivityLiveChannelChannelItemRv.adapter = adapter


    }


    private fun setListener() {
        this.binding.ActivityLiveChannelBackBtn.setOnClickListener {
            this.onBackPressed()
        }

        this.binding.ActivityLiveChannelChannelBannerIv.setOnClickListener {
            startActivity(Intent(this, PlayerActivity::class.java).apply {
                putExtra("name", live!!.en_name)
                putExtra(
                    "url",
                    "https://cph-p2p-msl.akamaized.net/hls/live/2000341/test/master.m3u8"
                )
                //args.live.url.toString())
            })
        }
    }

}