package com.mgpersia.androidbox.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.mgpersia.androidbox.Fragment.bottomSheet.FilterOptionBottomSheetFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.adapter.FilmItemAllFilmsAdapter
import com.mgpersia.androidbox.common.MainContainer
import com.mgpersia.androidbox.data.model.FilterBottomSheet
import com.mgpersia.androidbox.data.model.FilterOptionBottomSheet
import com.mgpersia.androidbox.data.model.Image
import com.mgpersia.androidbox.databinding.ActivitySearchBinding
import com.mgpersia.androidbox.viewModel.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.floor
import kotlin.streams.toList

class SearchActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySearchBinding

    private var pageFilm = 1
    private var pageSeries = 1
    private val sharedViewModel: SharedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        if (MainContainer.setFilter) {
            MainContainer.setFilter = false
            this.binding.activitySearchFilmItemRvShimmer.startShimmer()
            this.binding.activitySearchFilmItemRvShimmer.visibility = View.VISIBLE
            this.binding.activitySearchFilmItemRv.visibility = View.GONE
            searchWithName()
        }else
            setInformation()

        setListener()
        setObserver()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setObserver() {
        this.sharedViewModel.getFilterFilmInformationLiveData.observe(this) {
            if (it.next != null) pageFilm++
            else pageFilm = -1
            if (!it.results!!.isNullOrEmpty()) {

                MainContainer.filmSearchList.addAll(
                    it.results!!.stream().map { film ->
                        Image(
                            film.id!!,
                            film.image,
                            film.cover,
                            film.name_en,
                            null,
                            film.is_dubbed,
                            film.has_subtitle,
                            film.imdb_id
                        )
                    }
                        .toList())
            }
            if (pageSeries != 1)
                setInformation()
        }

        this.sharedViewModel.getFilterSerialInformationLiveData.observe(this) {
            if (it.next != null) pageSeries++
            else pageSeries = -1
            if (!it.results!!.isNullOrEmpty()) {

                MainContainer.serialSearchList.addAll(
                    it.results!!.stream().map { film ->
                        Image(
                            film.id!!,
                            film.image,
                            film.cover,
                            film.name_en,
                            null,
                            film.is_dubbed,
                            film.has_subtitle,
                            film.imdb_id
                        )
                    }
                        .toList())
            }
            if (pageFilm != 1)
                setInformation()

        }
    }

    private fun clickItem(item: Image) {
        startActivity(
            Intent(
                this,
                FilmDetailActivity::class.java
            ).apply {
                putExtra("id_film", item.id.toString())
            })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setInformation() {
        binding.activitySearchProgressbar.visibility = View.GONE
        val list = ArrayList<Image>()
        list.clear()
        if (this.binding.activitySearchAllItemRB.isChecked) {
            if (MainContainer.filmSearchList.isNotEmpty())
                for (item in MainContainer.filmSearchList)
                    if (!list.contains(item))
                        list.add(item)
            if (MainContainer.serialSearchList.isNotEmpty())
                for (item in MainContainer.serialSearchList)
                    if (!list.contains(item))
                        list.add(item)
        } else if (this.binding.activitySearchFilmItemRB.isChecked) {
            if (MainContainer.filmSearchList.isNotEmpty())
                for (item in MainContainer.filmSearchList)
                    if (!list.contains(item))
                        list.add(item)
        } else if (this.binding.activitySearchSerialItemRB.isChecked) {
            if (MainContainer.serialSearchList.isNotEmpty())
                for (item in MainContainer.serialSearchList)
                    if (!list.contains(item))
                        list.add(item)
        }


        this.binding.activitySearchFilmItemRvShimmer.stopShimmer()
        this.binding.activitySearchFilmItemRvShimmer.visibility = View.GONE
        this.binding.activitySearchFilmItemRv.visibility = View.VISIBLE
        if (list.isNotEmpty()) {
            this.binding.activitySearchEmpty.root.visibility = View.GONE
            val displayMetrics = this.resources.displayMetrics
            val dpWidth = displayMetrics.widthPixels / displayMetrics.density
            val size = floor(dpWidth / 150).toInt()
            this.binding.activitySearchFilmItemRv.numColumns=size

            val adapter = FilmItemAllFilmsAdapter(
                this,
                list.toList(),
                object :
                    FilmItemAllFilmsAdapter.EventListener {
                    override fun click(item: Image) {
                        clickItem(item)
                    }

                    override fun reload() {
                        var dubbed = ""
                        var subtitle = ""
                        var country = ""
                        var genre = ""
                        var age = ""
                        for (item in MainContainer.filterItems) {
                            if (item.title == "زیرنویس" && item.itemSelect.isNotEmpty()) {
                                if (item.itemSelect.contains(0))
                                    dubbed = "1"
                                if (item.itemSelect.contains(1) || item.itemSelect.contains(2))
                                    subtitle = "1"
                                if (item.itemSelect.contains(3))
                                    dubbed = "0"
                            } else if (item.title == "کشور" && item.itemSelect.isNotEmpty()) {
                                for (item2 in item.itemSelect)
                                    country += ("$item2,")
                                if (country.isNotEmpty())
                                    country = "[" + country.substring(0, country.length - 1) + "]"
                            } else if (item.title == "ژانر" && item.itemSelect.isNotEmpty()) {
                                for (item2 in item.itemSelect)
                                    genre += ("${item2},")
                                if (genre.isNotEmpty())
                                    genre = "[" + genre.substring(0, genre.length - 1) + "]"
                            } else if (item.title == "رده سنی" && item.itemSelect.isNotEmpty()) {
                                for (item2 in item.itemSelect)
                                    age += ("$item2,")
                                if (age.isNotEmpty())
                                    age = "[" + age.substring(0, age.length - 1) + "]"

                            }
                        }
                        if (binding.activitySearchAllItemRB.isChecked) {
                            if (pageFilm != -1) {
                                binding.activitySearchProgressbar.visibility = View.VISIBLE
                                sharedViewModel.getFilterFilmInformation(
                                    dubbed,
                                    "film",
                                    subtitle,
                                    age,
                                    if (MainContainer.endYear == null) ""
                                    else MainContainer.endYear!!,
                                    if (MainContainer.startYear == null) ""
                                    else MainContainer.startYear!!,
                                    "",
                                    genre,
                                    country,
                                    pageFilm.toString(),
                                    "",
                                    binding.ActivitySearchSearchTI.text.toString()
                                )
                            }
                            if (pageSeries != -1) {
                                binding.activitySearchProgressbar.visibility = View.VISIBLE
                                sharedViewModel.getFilterSerialInformation(
                                    dubbed,
                                    "series",
                                    subtitle,
                                    age,
                                    if (MainContainer.endYear == null) ""
                                    else MainContainer.endYear!!,
                                    if (MainContainer.startYear == null) ""
                                    else MainContainer.startYear!!,
                                    "",
                                    genre,
                                    country,
                                    pageSeries.toString(),
                                    "",
                                    binding.ActivitySearchSearchTI.text.toString()
                                )
                            }
                            if (pageSeries == -1 && pageFilm == -1)
                                binding.activitySearchProgressbar.visibility = View.GONE


                        } else if (binding.activitySearchFilmItemRB.isChecked) {
                            if (pageFilm != -1) {
                                binding.activitySearchProgressbar.visibility = View.VISIBLE
                                sharedViewModel.getFilterFilmInformation(
                                    dubbed,
                                    "film",
                                    subtitle,
                                    age,
                                    if (MainContainer.endYear == null) ""
                                    else MainContainer.endYear!!,
                                    if (MainContainer.startYear == null) ""
                                    else MainContainer.startYear!!,
                                    "",
                                    genre,
                                    country,
                                    pageFilm.toString(),
                                    "",
                                    binding.ActivitySearchSearchTI.text.toString()
                                )
                            } else
                                binding.activitySearchProgressbar.visibility = View.GONE

                        } else if (binding.activitySearchSerialItemRB.isChecked) {
                            if (pageSeries != -1) {
                                binding.activitySearchProgressbar.visibility = View.VISIBLE
                                sharedViewModel.getFilterSerialInformation(
                                    dubbed,
                                    "series",
                                    subtitle,
                                    age,
                                    if (MainContainer.endYear == null) ""
                                    else MainContainer.endYear!!,
                                    if (MainContainer.startYear == null) ""
                                    else MainContainer.startYear!!,
                                    "",
                                    genre,
                                    country,
                                    pageSeries.toString(),
                                    "",
                                    binding.ActivitySearchSearchTI.text.toString()
                                )
                            } else
                                binding.activitySearchProgressbar.visibility = View.GONE


                        }
                    }


                })
            binding.activitySearchFilmItemRv.adapter = adapter
        } else {
            this.binding.activitySearchFilmItemRv.visibility = View.GONE
            this.binding.activitySearchEmpty.root.visibility = View.VISIBLE
        }


    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun setListener() {

        this.binding.ActivitySearchSearchTI.setOnEditorActionListener { _, keyCode, event ->
            if (((event?.action ?: -1) == KeyEvent.ACTION_DOWN)
                || keyCode == EditorInfo.IME_ACTION_SEARCH
            ) {
                this.binding.activitySearchFilmItemRvShimmer.startShimmer()
                this.binding.activitySearchFilmItemRvShimmer.visibility = View.VISIBLE
                this.binding.activitySearchFilmItemRv.visibility = View.GONE
                searchWithName()

                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        this.binding.activitySearchBackBtn.setOnClickListener {
            this.onBackPressed()
        }
        this.binding.ActivitySearchFilterBtn.setOnClickListener {
            this.onBackPressed()
            startActivity(
                Intent(
                    this,
                    FilterActivity::class.java
                ).apply {
                    putExtra(
                        "generalId",
                        -1
                    )
                })

        }

        this.binding.activitySearchItemRG.setOnCheckedChangeListener { _, _ ->
            setInformation()
        }

        this.binding.ActivitySearchSearchBtn.setOnClickListener {
            this.binding.activitySearchFilmItemRvShimmer.startShimmer()
            this.binding.activitySearchFilmItemRvShimmer.visibility = View.VISIBLE
            this.binding.activitySearchFilmItemRv.visibility = View.GONE
            searchWithName()
        }


        this.binding.activitySearchSortBtn.setOnClickListener {
            val list = ArrayList<FilterOptionBottomSheet>()
            list.add(FilterOptionBottomSheet(1, "امتیاز Rotten Tomatos"))
            list.add(FilterOptionBottomSheet(2, "امتیاز IMDb"))
            list.add(FilterOptionBottomSheet(3, "قدیمی ترین ها"))
            list.add(FilterOptionBottomSheet(4, "جدیدترین ها"))
            list.add(FilterOptionBottomSheet(5, "پیشفرض ها"))
            val bottomSheetDialog =
                FilterOptionBottomSheetFragment(
                    listOf(
                        FilterBottomSheet(
                            "مرتب سازی",
                            list,
                            ArrayList()
                        )
                    ), true, 0, object : FilterOptionBottomSheetFragment.EventListener {
                        override fun update() {

                        }

                    })
            bottomSheetDialog.show(supportFragmentManager, "bottomSheetDialog")
        }
    }

    private fun searchWithName() {
        pageFilm = 1
        pageSeries = 1
        MainContainer.filmSearchList.clear()
        MainContainer.serialSearchList.clear()
        var dubbed = ""
        var subtitle = ""
        var country = ""
        var genre = ""
        var age = ""

        for (item in MainContainer.filterItems) {
            if (item.title == "زیرنویس" && item.itemSelect.isNotEmpty()) {
                if (item.itemSelect.contains(0))
                    dubbed = "1"
                if (item.itemSelect.contains(1) || item.itemSelect.contains(2))
                    subtitle = "1"
                if (item.itemSelect.contains(3))
                    dubbed = "0"
            } else if (item.title == "کشور" && item.itemSelect.isNotEmpty()) {
                for (item2 in item.itemSelect)
                    country += ("$item2,")
                if (country.isNotEmpty())
                    country = "[" + country.substring(0, country.length - 1) + "]"
            } else if (item.title == "ژانر" && item.itemSelect.isNotEmpty()) {
                for (item2 in item.itemSelect)
                    genre += ("${item2},")
                if (genre.isNotEmpty())
                    genre = "[" + genre.substring(0, genre.length - 1) + "]"
            } else if (item.title == "رده سنی" && item.itemSelect.isNotEmpty()) {
                for (item2 in item.itemSelect)
                    age += ("$item2,")
                if (age.isNotEmpty())
                    age = "[" + age.substring(0, age.length - 1) + "]"

            }
        }
        sharedViewModel.getFilterFilmInformation(
            dubbed,
            "film",
            subtitle,
            age,
            if (MainContainer.endYear == null) ""
            else MainContainer.endYear!!,
            if (MainContainer.startYear == null) ""
            else MainContainer.startYear!!,
            "",
            genre,
            country,
            pageFilm.toString(),
            "",
            this.binding.ActivitySearchSearchTI.text.toString()
        )
        sharedViewModel.getFilterSerialInformation(
            dubbed,
            "series",
            subtitle,
            age,
            if (MainContainer.endYear == null) ""
            else MainContainer.endYear!!,
            if (MainContainer.startYear == null) ""
            else MainContainer.startYear!!,
            "",
            genre,
            country,
            pageSeries.toString(),
            "",
            this.binding.ActivitySearchSearchTI.text.toString()
        )
    }
}