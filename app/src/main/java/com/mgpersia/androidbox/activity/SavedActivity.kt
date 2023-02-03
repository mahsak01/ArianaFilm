package com.mgpersia.androidbox.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.mgpersia.androidbox.Fragment.LoadingFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.adapter.SavedGroupItemSettingAdapter
import com.mgpersia.androidbox.data.model.FavoriteFilmList
import com.mgpersia.androidbox.data.model.Image
import com.mgpersia.androidbox.databinding.ActivitySavedBinding
import com.mgpersia.androidbox.viewModel.UserSettingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.streams.toList

class SavedActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySavedBinding
    private val userSettingViewModel: UserSettingViewModel by viewModel()
    private var loadingFragment: LoadingFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_saved)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
    }

    override fun onResume() {
        super.onResume()
        loadingFragment = LoadingFragment()
        loadingFragment?.show(supportFragmentManager, null)
        Handler(mainLooper)
            .postDelayed({
                if (loadingFragment != null && loadingFragment!!.showsDialog)
                    loadingFragment!!.dismiss()
            }, (30000))
        userSettingViewModel.getWatchlistFavorite()
        setObserver()
        setListener()

    }

    private fun setListener() {
        this.binding.ActivitySavedBackBtn.setOnClickListener {
            this.onBackPressed()
        }
    }

    private fun setObserver() {

        userSettingViewModel.getWatchlistFavoriteLiveData.observe(this) {
            userSettingViewModel.getWatchListBookmark()
        }
        userSettingViewModel.getWatchListBookmarkLiveData.observe(this) {
            this.binding.ActivitySavedSavedRv.layoutManager =
                GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)

            var list = ArrayList<FavoriteFilmList?>()
            if (userSettingViewModel.getWatchlistFavoriteLiveData.value!!.results.isNullOrEmpty())
                list.add(null)
            else
                list.add(userSettingViewModel.getWatchlistFavoriteLiveData.value!!.results!![0])

            if (it.results.isNullOrEmpty())
                list.add(null)
            else
                list.add(it.results[0])

            val adapter = SavedGroupItemSettingAdapter(
                list,
                this,
                object : SavedGroupItemSettingAdapter.EventListener {
                    @RequiresApi(Build.VERSION_CODES.N)
                    override fun click(favoriteFilmList: FavoriteFilmList, title: String) {
                        startActivity(
                            Intent(
                                this@SavedActivity,
                                AllFilmActivity::class.java
                            ).apply {
                                putExtra(
                                    "title",
                                    title
                                )
                                putExtra(
                                    "films",
                                    favoriteFilmList.films!!.stream()
                                        .map { it1 ->
                                            Image(
                                                it1!!.id!!,
                                                it1.image,
                                                it1.cover,
                                                it1.name_en
                                            )
                                        }
                                        .toList() as ArrayList<Image>
                                )
                            })
                    }

                })
            binding.ActivitySavedSavedRv.adapter = adapter
            if (loadingFragment != null && loadingFragment!!.showsDialog)
                loadingFragment!!.dismiss()
        }

    }

}