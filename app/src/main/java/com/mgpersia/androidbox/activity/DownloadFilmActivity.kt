package com.mgpersia.androidbox.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.adapter.FilmItemAllFilmsAdapter
import com.mgpersia.androidbox.adapter.LayoutDownloadItemSettingAdapter
import com.mgpersia.androidbox.data.FilmId
import com.mgpersia.androidbox.data.model.Image
import com.mgpersia.androidbox.data.model.LocalFilmDownload
import com.mgpersia.androidbox.databinding.ActivityAllFilmCategoryBinding
import com.mgpersia.androidbox.player.PlayerActivity
import com.mgpersia.androidbox.viewModel.LocalShareViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.floor
import kotlin.streams.toList

class DownloadFilmActivity : AppCompatActivity() {
    lateinit var binding: ActivityAllFilmCategoryBinding
    private val localShareViewModel: LocalShareViewModel by viewModel()
    private val itemFilm = ArrayList<LocalFilmDownload>()
    private val itemSeries = ArrayList<LocalFilmDownload>()
    private val items = ArrayList<LocalFilmDownload>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_film_category)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        this.binding.activityAllFilmCategoryTitletv.text = "دانلود ها"
        localShareViewModel.getAllFilm()
        setListener()
        setObserver()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setObserver() {
        localShareViewModel.getFilmsLiveData.observe(this) {
            itemFilm.clear()
            itemSeries.clear()
            items.clear()
            it?.forEach { item ->
                if (item.isMovie)
                    itemFilm.add(item)
                else
                    itemSeries.add(item)

                items.add(item)

            }

            if (localShareViewModel.downloadFilmsLiveData.value != null)
                localShareViewModel.downloadFilmsLiveData.value!!.forEach { item ->
                    if (item.isMovie)
                        itemFilm.add(item)
                    else
                        itemSeries.add(item)

                    items.add(item)
                }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                setInformation()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setInformation() {
        binding.activityAllFilmCategoryProgressbar.visibility = View.GONE

        val list = ArrayList<LocalFilmDownload>()

        if (this.binding.activityAllFilmCategoryAllItemRB.isChecked) {
            list.clear()
            list.addAll(items.stream().toList())
        } else if (this.binding.activityAllFilmCategoryFilmItemRB.isChecked) {
            list.clear()
            list.addAll(itemFilm.stream().toList())

        } else if (this.binding.activityAllFilmCategorySerialItemRB.isChecked) {
            list.clear()
            list.addAll(itemSeries.stream().toList())

        }

        if (list.isNotEmpty()) {
            this.binding.activityAllFilmCategoryItemRvShimmer.stopShimmer()
            this.binding.activityAllFilmCategoryItemRvShimmer.visibility = View.GONE
            this.binding.activityAllFilmCategoryItemGv.visibility = View.VISIBLE
            val displayMetrics = this.resources.displayMetrics
            val dpWidth = displayMetrics.widthPixels / displayMetrics.density
            val size = floor(dpWidth / 150).toInt()
            this.binding.activityAllFilmCategoryItemGv.numColumns = size

            if (list.size == 0)
                this.binding.activityAllFilmCategoryItemGv.visibility = View.GONE

            var adapter = LayoutDownloadItemSettingAdapter(
                this,
                list.toList(),
                object :
                    LayoutDownloadItemSettingAdapter.EventListener {
                    override fun play(item: LocalFilmDownload) {
                        pLayVideo(item)
                    }

                })
            binding.activityAllFilmCategoryItemGv.adapter = adapter
        } else {
            this.binding.activityAllFilmCategoryItemRvShimmer.stopShimmer()
            this.binding.activityAllFilmCategoryItemRvShimmer.visibility = View.GONE
            this.binding.activityAllFilmCategoryItemGv.visibility = View.GONE
        }

    }

    private fun pLayVideo(localFilmDownload: LocalFilmDownload) {
        startActivity(Intent(this, PlayerActivity::class.java).apply {
            putExtra("name", localFilmDownload.name_en)
            putExtra(
                "url",
                localFilmDownload.filmAddress
            )
        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setListener() {
        this.binding.activityAllFilmCategoryBackBtn.setOnClickListener {
            this.onBackPressed()
        }
        this.binding.activityAllFilmCategoryItemRG.setOnCheckedChangeListener { _, _ ->
            setInformation()
        }

    }
}