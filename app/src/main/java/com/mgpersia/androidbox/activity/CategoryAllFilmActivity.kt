package com.mgpersia.androidbox.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.adapter.FilmItemAllFilmsAdapter
import com.mgpersia.androidbox.data.model.Image
import com.mgpersia.androidbox.databinding.ActivityAllFilmCategoryBinding
import com.mgpersia.androidbox.viewModel.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.floor
import kotlin.streams.toList

class CategoryAllFilmActivity : AppCompatActivity() {
    lateinit var binding: ActivityAllFilmCategoryBinding
    private val sharedViewModel: SharedViewModel by viewModel()
    private val itemFilm = ArrayList<Image>()
    private val itemSeries = ArrayList<Image>()
    private val items = ArrayList<Image>()
    private val filmsList = ArrayList<Image>()
    private var pageFilm = 1
    private var pageSeries = 1


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
        sharedViewModel.getFilterFilmInformation(
            "",
            "film",
            "",
            "",
            "",
            "",
            if (intent.getStringExtra("tag").toString() != "") "[" + intent.getStringExtra("tag")
                .toString() + "]" else "",
            if (intent.getStringExtra("genre")
                    .toString() != ""
            ) "[" + intent.getStringExtra("genre")
                .toString() + "]" else "",
            "",
            pageFilm.toString(),
            "",
            ""
        )
        sharedViewModel.getFilterSerialInformation(
            "",
            "series",
            "",
            "",
            "",
            "",
            if (intent.getStringExtra("tag")
                    .toString() != ""
            ) "[" + intent.getStringExtra("tag")
                .toString() + "]" else "",
            if (intent.getStringExtra("genre")
                    .toString() != ""
            ) "[" + intent.getStringExtra("genre")
                .toString() + "]" else "",
            "",
            pageSeries.toString(),
            "",
            ""
        )
        this.binding.activityAllFilmCategoryTitletv.text = intent.getStringExtra("title").toString()
        setListener()
        setObserver()
    }

    private fun onClickMovie(item: Image) {
        startActivity(
            Intent(
                this,
                FilmDetailActivity::class.java
            ).apply {
                putExtra("id_film", item.id.toString())
            })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setObserver() {
        this.sharedViewModel.getFilterFilmInformationLiveData.observe(this) {
            if (!it.results.isNullOrEmpty()) {
                if (it.next != null) pageFilm++
                else pageFilm = -1
                itemFilm.addAll(
                    it.results!!.stream()
                        .map { film -> Image(film.id!!, film.image, film.cover, film.name_en) }
                        .toList())
                items.addAll(
                    it.results!!.stream()
                        .map { film -> Image(film.id!!, film.image, film.cover, film.name_en) }
                        .toList())
                if (pageSeries != 1)
                    setInformation()

            }
        }

        this.sharedViewModel.getFilterSerialInformationLiveData.observe(this) {
            if (!it.results.isNullOrEmpty()) {
                if (it.next != null) pageSeries++
                else pageSeries = -1
                itemSeries.addAll(
                    it.results!!.stream()
                        .map { film -> Image(film.id!!, film.image, film.cover, film.name_en) }
                        .toList())
                items.addAll(
                    it.results!!.stream()
                        .map { film -> Image(film.id!!, film.image, film.cover, film.name_en) }
                        .toList())
                if (pageFilm != 1)
                    setInformation()
            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setInformation() {
        binding.activityAllFilmCategoryProgressbar.visibility = View.GONE

        val list = ArrayList<Image>()

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
            this.binding.activityAllFilmCategoryItemGv.numColumns=size

            var adapter = FilmItemAllFilmsAdapter(
                this,
                list.stream().map { film ->
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
                    .toList(),
                object :
                    FilmItemAllFilmsAdapter.EventListener {
                    override fun click(item: Image) {
                        onClickMovie(item)
                    }

                    override fun reload() {

                        if (binding.activityAllFilmCategoryAllItemRB.isChecked) {
                            if (pageFilm != -1) {
                                binding.activityAllFilmCategoryProgressbar.visibility = View.VISIBLE
                                sharedViewModel.getFilterFilmInformation(
                                    "",
                                    "film",
                                    "",
                                    "",
                                    "",
                                    "",
                                    if (intent.getStringExtra("tag")
                                            .toString() != ""
                                    ) "[" + intent.getStringExtra("tag")
                                        .toString() + "]" else "",
                                    if (intent.getStringExtra("genre")
                                            .toString() != ""
                                    ) "[" + intent.getStringExtra("genre")
                                        .toString() + "]" else "",
                                    "",
                                    pageFilm.toString(),
                                    "",
                                    ""
                                )
                            }
                            if (pageSeries != -1) {
                                binding.activityAllFilmCategoryProgressbar.visibility = View.VISIBLE
                                sharedViewModel.getFilterSerialInformation(
                                    "",
                                    "series",
                                    "",
                                    "",
                                    "",
                                    "",
                                    if (intent.getStringExtra("tag")
                                            .toString() != ""
                                    ) "[" + intent.getStringExtra("tag")
                                        .toString() + "]" else "",
                                    if (intent.getStringExtra("genre")
                                            .toString() != ""
                                    ) "[" + intent.getStringExtra("genre")
                                        .toString() + "]" else "",
                                    "",
                                    pageSeries.toString(),
                                    "",
                                    ""
                                )
                            }
                            if (pageSeries == -1 && pageFilm == -1)
                                binding.activityAllFilmCategoryProgressbar.visibility = View.GONE


                        } else if (binding.activityAllFilmCategoryFilmItemRB.isChecked) {
                            if (pageFilm != -1) {
                                binding.activityAllFilmCategoryProgressbar.visibility = View.VISIBLE
                                sharedViewModel.getFilterFilmInformation(
                                    "",
                                    "film",
                                    "",
                                    "",
                                    "",
                                    "",
                                    if (intent.getStringExtra("tag")
                                            .toString() != ""
                                    ) "[" + intent.getStringExtra("tag")
                                        .toString() + "]" else "",
                                    if (intent.getStringExtra("genre")
                                            .toString() != ""
                                    ) "[" + intent.getStringExtra("genre")
                                        .toString() + "]" else "",
                                    "",
                                    pageFilm.toString(),
                                    "",
                                    ""
                                )
                            } else
                                binding.activityAllFilmCategoryProgressbar.visibility = View.GONE

                        } else if (binding.activityAllFilmCategorySerialItemRB.isChecked) {
                            if (pageSeries != -1) {
                                binding.activityAllFilmCategoryProgressbar.visibility = View.VISIBLE
                                sharedViewModel.getFilterSerialInformation(
                                    "",
                                    "series",
                                    "",
                                    "",
                                    "",
                                    "",
                                    if (intent.getStringExtra("tag")
                                            .toString() != ""
                                    ) "[" + intent.getStringExtra("tag")
                                        .toString() + "]" else "",
                                    if (intent.getStringExtra("genre")
                                            .toString() != ""
                                    ) "[" + intent.getStringExtra("genre")
                                        .toString() + "]" else "",
                                    "",
                                    pageSeries.toString(),
                                    "",
                                    ""
                                )
                            } else
                                binding.activityAllFilmCategoryProgressbar.visibility = View.GONE


                        }
                    }


                })
            binding.activityAllFilmCategoryItemGv.adapter = adapter
        }

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