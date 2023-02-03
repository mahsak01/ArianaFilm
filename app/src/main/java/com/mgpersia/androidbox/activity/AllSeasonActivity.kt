package com.mgpersia.androidbox.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.adapter.SeasonItemAllSeasonAdapter
import com.mgpersia.androidbox.data.model.Season
import com.mgpersia.androidbox.databinding.ActivityAllSeasonBinding
import com.mgpersia.androidbox.viewModel.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.floor

class AllSeasonActivity : AppCompatActivity() {
    lateinit var binding: ActivityAllSeasonBinding
    private val sharedViewModel: SharedViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_season)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        sharedViewModel.getLive()
        setInformation()
        setListener()
        setFocus()
        this.binding.activityAllSeasonBackBtn.requestFocus(1)
    }

    private fun setFocus() {
        this.binding.activityAllSeasonBackBtn.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.activityAllSeasonBackBtn.background =
                    resources.getDrawable(
                        R.drawable.background_select_icon_android_tv
                    )
            } else {
                this.binding.activityAllSeasonBackBtn.background =
                    resources.getDrawable(
                        R.drawable.background_button_setting
                    )
//                this.binding.layoutFilmItemHomePageItemCl.setPadding(0,10,0,0)
            }
        }
    }

    private fun onClickSeason(season: Season) {
        startActivity(
            Intent(
                this,
                SeasonDetailActivity::class.java
            ).apply {
                putExtra("season", season)
            })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setInformation() {
        val list: java.util.ArrayList<Season> = intent.getParcelableArrayListExtra("seasons")!!
        this.binding.activityAllSeasonTitletv.text = intent.getStringExtra("title").toString()
        val displayMetrics = this.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        val size = floor(dpWidth / 150).toInt()
        this.binding.activityAllSeasonItemRv.numColumns = size

        var adapter = SeasonItemAllSeasonAdapter(this, list, object :
            SeasonItemAllSeasonAdapter.EventListener {
            override fun click(season: Season) {
                onClickSeason(season)
            }
        })
        binding.activityAllSeasonItemRv.adapter = adapter
    }

    private fun setListener() {
        this.binding.activityAllSeasonBackBtn.setOnClickListener {
            this.onBackPressed()
        }
    }
}