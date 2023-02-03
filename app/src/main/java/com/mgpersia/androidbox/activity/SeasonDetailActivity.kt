package com.mgpersia.androidbox.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.adapter.SeasonsItemAdapter
import com.mgpersia.androidbox.data.model.*
import com.mgpersia.androidbox.databinding.ActivitySeasonsDetailBinding
import com.mgpersia.androidbox.player.PlayerActivity

class SeasonDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivitySeasonsDetailBinding
    lateinit var season: Season
    var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_seasons_detail)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
    }

    override fun onResume() {
        super.onResume()
        season= intent.getParcelableExtra("season")!!
        setInformation()
        setListener()
    }
    private fun setListener(){
        this.binding.activitySeasonsDetailBackBtn.setOnClickListener {
            this.onBackPressed()
        }
    }


    private fun showVideo(item: Episode){
        startActivity(Intent(this, PlayerActivity::class.java).apply {
            putExtra("name", item.name)
            putExtra(
                "url",
                "http://dl.digbangone.com/Film1/52/No.Country.for.Old.Men.2007.720p.BluRay.DUBLE.arianafilm.mkv"
            )
            //ToDO
            //args.live.url.toString())
        })
    }


    private fun setInformation() {

        this.binding.activitySeasonsDetailSeasonsTv.text =season.name
        this.binding.activitySeasonsDetailSeasonsNumberTv.text= season.episodes!!.size.toString()+" قسمت"


            this.binding.activitySeasonsDetailItemRv.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)

        var adapter = SeasonsItemAdapter(this, season.episodes!! as List<Episode>, object :
            SeasonsItemAdapter.EventListener {
            override fun click(item: Episode) {
                showVideo(item)
            }


        })
        binding.activitySeasonsDetailItemRv.adapter = adapter

    }



}