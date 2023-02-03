package com.mgpersia.androidbox.Fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.mgpersia.androidbox.Fragment.dialog.LogInPhoneDialogFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.activity.*
import com.mgpersia.androidbox.adapter.ActorItemHomePageAdapter
import com.mgpersia.androidbox.adapter.BannerSliderAdapter
import com.mgpersia.androidbox.adapter.FilmItemHomePageAdapter
import com.mgpersia.androidbox.common.TokenContainer
import com.mgpersia.androidbox.common.UserContainer
import com.mgpersia.androidbox.data.model.ActorFilmDetailInformation
import com.mgpersia.androidbox.data.model.Image
import com.mgpersia.androidbox.data.model.enum.ToastStatus
import com.mgpersia.androidbox.databinding.FragmentHomeBinding
import com.mgpersia.androidbox.viewModel.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import showCustomToast
import kotlin.math.ceil

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val sharedViewModel: SharedViewModel by viewModel()
    private var isLogOut = 0

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        this.view?.isFocusableInTouchMode = true
        this.view?.requestFocus()
        lifecycleScope.launch(Dispatchers.Main) {
            sharedViewModel.getBannerHome()
        }
        setObserver()
        setInformation()
        setListener()
        setFocus()
        this.binding.fragmentHomeSearchIv.requestFocus()

    }

    private fun setFocus() {
        this.binding.fragmentHomeSearchIv.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.fragmentHomeSearchIv.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_icon_android_tv
                    )
            } else {
                this.binding.fragmentHomeSearchIv.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_text_transparent_android_tv
                    )
            }
        }
        this.binding.fragmentHomeProfileTv.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.fragmentHomeProfileTv.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_icon_android_tv
                    )
            } else {
                this.binding.fragmentHomeProfileTv.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_text_transparent_android_tv
                    )
            }
        }
        this.binding.fragmentHomeAllPopulerFilmRv.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.fragmentHomeAllPopulerFilmRv.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_text_android_tv
                    )
            } else {
                this.binding.fragmentHomeAllPopulerFilmRv.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_text_transparent_android_tv
                    )
            }
        }
        this.binding.fragmentHomeAllPopulerSerialRv.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.fragmentHomeAllPopulerSerialRv.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_text_android_tv
                    )
            } else {
                this.binding.fragmentHomeAllPopulerSerialRv.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_text_transparent_android_tv
                    )
            }
        }
        this.binding.fragmentHomeAllUpdatedSeriesRv.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.fragmentHomeAllUpdatedSeriesRv.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_text_android_tv
                    )
            } else {
                this.binding.fragmentHomeAllUpdatedSeriesRv.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_text_transparent_android_tv
                    )
            }
        }
        this.binding.fragmentHomeAllUnreleasedMoviesRv.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.fragmentHomeAllUnreleasedMoviesRv.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_text_android_tv
                    )
            } else {
                this.binding.fragmentHomeAllUnreleasedMoviesRv.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_text_transparent_android_tv
                    )
            }
        }
        this.binding.fragmentHomeAllActorRv.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.fragmentHomeAllActorRv.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_text_android_tv
                    )
            } else {
                this.binding.fragmentHomeAllActorRv.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_text_transparent_android_tv
                    )
            }
        }
        this.binding.fragmentHomeAllDirectorRv.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.fragmentHomeAllDirectorRv.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_text_android_tv
                    )
            } else {
                this.binding.fragmentHomeAllDirectorRv.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_text_transparent_android_tv
                    )
            }
        }
        this.binding.fragmentHomeAllNewFilmTv.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.fragmentHomeAllNewFilmTv.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_text_android_tv
                    )
            } else {
                this.binding.fragmentHomeAllNewFilmTv.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_text_transparent_android_tv
                    )
            }
        }
        this.binding.fragmentHomeAllNewSeriesRv.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.fragmentHomeAllNewSeriesRv.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_text_android_tv
                    )
            } else {
                this.binding.fragmentHomeAllNewSeriesRv.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_text_transparent_android_tv
                    )
//                this.binding.layoutFilmItemHomePageItemCl.setPadding(0,10,0,0)
            }
        }

    }

    private fun setListener() {

//        this.binding.root.setOnKeyListener { _, p1, _ ->
//            if (p1 != KeyEvent.ACTION_DOWN) {
//                if (isLogOut == 0) {
//                    val bottomSheetDialog =
//                        LogoutBottomSheetFragment(object : LogoutBottomSheetFragment.EventListener {
//                            override fun close() {
//                                // on below line we are finishing activity.
//                                activity!!.finish()
//
//                                // on below line we are exiting our activity
//                                exitProcess(0)
//                            }
//
//                        })
//                    bottomSheetDialog.show(childFragmentManager, null)
//                    isLogOut++
//                } else {
//                    isLogOut = 0
//                }
//
//            }
//            true
//        }
        this.binding.fragmentHomeRefresh.setOnRefreshListener {
            lifecycleScope.launch(Dispatchers.Main) {
                sharedViewModel.getBannerHome()
            }
        }
        this.binding.fragmentHomeProfileTv.setOnClickListener {
            if (UserContainer.user == null) {
                val logInPhoneDialogFragment = LogInPhoneDialogFragment()
                logInPhoneDialogFragment.show(requireActivity().supportFragmentManager, null)
            } else
                startActivity(
                    Intent(
                        requireActivity(),
                        ProfileActivity::class.java
                    ).apply {})
        }
        this.binding.fragmentHomeSearchIv.setOnClickListener {
            startActivity(
                Intent(
                    requireActivity(),
                    SearchActivity::class.java
                ).apply {})
        }
        this.binding.fragmentHomeAllNewFilmTv.setOnClickListener {
            startActivity(
                Intent(
                    requireActivity(),
                    AllFilmActivity::class.java
                ).apply {
                    putExtra(
                        "title",
                        "جدیدترین فیلم ها"
                    )
                    putExtra(
                        "films",
                        sharedViewModel.getNewFilmLiveData.value!!.results!! as ArrayList<Image>
                    )
                })
        }
        this.binding.fragmentHomeAllNewSeriesRv.setOnClickListener {
            startActivity(
                Intent(
                    requireActivity(),
                    AllFilmActivity::class.java
                ).apply {
                    putExtra(
                        "title",
                        "جدیدترین سریال ها"
                    )
                    putExtra(
                        "films",
                        sharedViewModel.getNewSeriesLiveData.value!!.results!! as ArrayList<Image>
                    )
                })

        }
        this.binding.fragmentHomeAllUpdatedSeriesRv.setOnClickListener {

            startActivity(
                Intent(
                    requireActivity(),
                    AllFilmActivity::class.java
                ).apply {
                    putExtra(
                        "title",
                        "سریال های بروز شده"
                    )
                    putExtra(
                        "films",
                        sharedViewModel.getNewlySeriesLiveData.value!!.results!! as ArrayList<Image>
                    )
                })
        }
        this.binding.fragmentHomeAllUnreleasedMoviesRv.setOnClickListener {
            startActivity(
                Intent(
                    requireActivity(),
                    AllFilmActivity::class.java
                ).apply {
                    putExtra(
                        "title",
                        "فیلم های اکران نشده"
                    )
                    putExtra(
                        "films",
                        sharedViewModel.getCommingSoonLiveData.value!!.results!! as ArrayList<Image>
                    )
                })
        }

        this.binding.fragmentHomeAllPopulerFilmRv.setOnClickListener {
            startActivity(
                Intent(
                    requireActivity(),
                    AllFilmActivity::class.java
                ).apply {
                    putExtra(
                        "title",
                        "فیلم های محبوب"
                    )
                    putExtra(
                        "films",
                        sharedViewModel.getPopularFilmLiveData.value!!.results!! as ArrayList<Image>
                    )
                })
        }

        this.binding.fragmentHomeAllPopulerSerialRv.setOnClickListener {
            startActivity(
                Intent(
                    requireActivity(),
                    AllFilmActivity::class.java
                ).apply {
                    putExtra(
                        "title",
                        "سریال های محبوب"
                    )
                    putExtra(
                        "films",
                        sharedViewModel.getPopularMovieLiveData.value!!.results!! as ArrayList<Image>
                    )
                })
        }
        this.binding.fragmentHomeAllActorRv.setOnClickListener {
            startActivity(
                Intent(
                    requireActivity(),
                    AllActorActivity::class.java
                ).apply {
                    putExtra(
                        "title",
                        "بازیگران"
                    )
                    putExtra(
                        "isActor",
                        true
                    )
                })
        }
        this.binding.fragmentHomeAllDirectorRv.setOnClickListener {
            startActivity(
                Intent(
                    requireActivity(),
                    AllActorActivity::class.java
                ).apply {
                    putExtra(
                        "title",
                        "کارگردانان"
                    )
                    putExtra(
                        "isActor",
                        false
                    )
                })
        }

        this.binding.fragmentHomeBookmarkTv.setOnClickListener {
            startActivity(
                Intent(
                    requireActivity(),
                    SavedActivity::class.java
                ).apply {})
        }

    }

    private fun setInformation() {

        if (TokenContainer.token != null) {
            this.binding.fragmentHomeBookmarkTv.visibility = View.VISIBLE
        }
//        this.binding.fragmentHomeItemsNsv.visibility = View.GONE
        val handler = Handler()
        this.binding.fragmentHomeBannerSliderVp.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                handler.removeMessages(0)

                val runnable = Runnable {
                    binding.fragmentHomeBannerSliderVp.currentItem =
                        (position + 1) % binding.fragmentHomeBannerSliderVp.adapter!!.itemCount

                }
                handler.postDelayed(runnable, 10000)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setObserver() {
        sharedViewModel.errorHandlingLiveData.observe(this) {
            Toast.makeText(
                requireContext(),
                "خطا در دریافت اطلاعات از }سرور",
                Toast.LENGTH_LONG
            )
                .showCustomToast(
                    "خطا در دریافت اطلاعات از سرور",
                    requireActivity(),
                    ToastStatus.ERROR
                )
        }
        sharedViewModel.getBannerHomeLiveData.observe(this) {
            val displayMetrics = requireContext().resources.displayMetrics
            val dpWidth = displayMetrics.widthPixels / displayMetrics.density
            this.binding.fragmentHomeBannerSliderShimmer.stopShimmer()
            this.binding.fragmentHomeBannerSliderShimmer.visibility = View.GONE
            this.binding.fragmentHomeBannerSlider.visibility = View.VISIBLE
            if (!it.results.isNullOrEmpty()) {
                var images = ArrayList<Image>()
                it.results.forEach { image ->
                    if (image!!.cover != null) images.add(
                        Image(image!!.id, image.cover!!, null, null, image!!.film)
                    )
                }

                val imageSliderAdapter =
                    BannerSliderAdapter(this, images.toList(), true)
                this.binding.fragmentHomeBannerSliderVp.adapter = imageSliderAdapter
                this.binding.fragmentHomeBannerSliderVp.post {
                    val layoutParams = this.binding.fragmentHomeBannerSliderVp.layoutParams
                    layoutParams.height =
                        if (dpWidth > 1000) (dpWidth * 0.4).toInt() else (dpWidth * 1.3).toInt()
                    this.binding.fragmentHomeBannerSliderVp.layoutParams = layoutParams
                    this.binding.fragmentHomeBannerSliderDi.setViewPager2(this.binding.fragmentHomeBannerSliderVp)
                }

            } else {
                this.binding.fragmentHomeBannerSliderVp.visibility = View.GONE
                this.binding.fragmentHomeBannerSliderDi.visibility = View.GONE
            }

            lifecycleScope.launch(Dispatchers.Main) { sharedViewModel.getNewFilm() }


        }
        sharedViewModel.getNewFilmLiveData.observe(this) {
            val displayMetrics = requireContext().resources.displayMetrics
            val dpWidth = displayMetrics.widthPixels / displayMetrics.density
            val size = if (ceil(dpWidth / 150) < 5) 5 else ceil(dpWidth / 150).toInt()

            this.binding.fragmentHomeNewFilmRvShimmer.stopShimmer()
            this.binding.fragmentHomeNewFilmRvShimmer.visibility = View.GONE
            this.binding.fragmentHomeNewFilmRv.visibility = View.VISIBLE
            if (!it.results.isNullOrEmpty()) {

                this.binding.fragmentHomeNewFilmRv.layoutManager =
                    GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)


                var adapter = FilmItemHomePageAdapter(
                    requireContext(),
                    (if (it.count!!.toInt() > size) it.results.subList(
                        0,
                        size
                    ) else it.results) as List<Image>,
                    object :
                        FilmItemHomePageAdapter.EventListener {
                        override fun click(item: Image) {
                            startActivity(
                                Intent(
                                    requireActivity(),
                                    FilmDetailActivity::class.java
                                ).apply {
                                    putExtra("id_film", item.id.toString())
                                })
                        }

                    },
                    this.binding.fragmentHomeAllNewSeriesRv.id,
                    this.binding.fragmentHomeAllNewFilmTv.id,
                    this.binding.fragmentHomeAllNewFilmTv.id,
                    this.binding.fragmentHomeAllNewSeriesRv.id
                )
                binding.fragmentHomeNewFilmRv.adapter = adapter

            } else
                this.binding.fragmentHomeAllNewFilmLl.visibility = View.GONE

            lifecycleScope.launch(Dispatchers.Main) { sharedViewModel.getNewSeries() }

        }
        sharedViewModel.getNewSeriesLiveData.observe(this) {
            val displayMetrics = requireContext().resources.displayMetrics
            val dpWidth = displayMetrics.widthPixels / displayMetrics.density
            val size = if (ceil(dpWidth / 150) < 5) 5 else ceil(dpWidth / 150).toInt()
            this.binding.fragmentHomeNewSeriesRvShimmer.stopShimmer()
            this.binding.fragmentHomeNewSeriesRvShimmer.visibility = View.GONE
            this.binding.fragmentHomeNewSeriesRv.visibility = View.VISIBLE
            if (!it.results.isNullOrEmpty()) {
                this.binding.fragmentHomeNewSeriesRv.layoutManager =
                    GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)


                var adapter = FilmItemHomePageAdapter(
                    requireContext(),
                    (if (it.count!!.toInt() > size) it.results.subList(
                        0,
                        size
                    ) else it.results) as List<Image>,
                    object :
                        FilmItemHomePageAdapter.EventListener {
                        override fun click(item: Image) {
                            startActivity(
                                Intent(
                                    requireActivity(),
                                    FilmDetailActivity::class.java
                                ).apply {
                                    putExtra("id_film", item.id.toString())
                                })
                        }

                    }, this.binding.fragmentHomeAllPopulerFilmRv.id,
                    this.binding.fragmentHomeAllNewSeriesRv.id,
                    this.binding.fragmentHomeAllNewSeriesRv.id,
                    this.binding.fragmentHomeAllPopulerFilmRv.id
                )
                binding.fragmentHomeNewSeriesRv.adapter = adapter

            } else
                this.binding.fragmentHomeAllNewSeriesLl.visibility = View.GONE

            lifecycleScope.launch(Dispatchers.Main) { sharedViewModel.getNewlySeries() }

        }
        sharedViewModel.getNewlySeriesLiveData.observe(this) {
            val displayMetrics = requireContext().resources.displayMetrics
            val dpWidth = displayMetrics.widthPixels / displayMetrics.density
            val size = if (ceil(dpWidth / 150) < 5) 5 else ceil(dpWidth / 150).toInt()
            this.binding.fragmentHomeUpdatedSeriesRvShimmer.stopShimmer()
            this.binding.fragmentHomeUpdatedSeriesRvShimmer.visibility = View.GONE
            this.binding.fragmentHomeUpdatedSeriesRv.visibility = View.VISIBLE
            if (!it.results.isNullOrEmpty()) {

                this.binding.fragmentHomeUpdatedSeriesRv.layoutManager =
                    GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)


                var adapter = FilmItemHomePageAdapter(
                    requireContext(),
                    (if (it.count!!.toInt() > size) it.results.subList(
                        0,
                        size
                    ) else it.results) as List<Image>,
                    object :
                        FilmItemHomePageAdapter.EventListener {
                        override fun click(item: Image) {
                            startActivity(
                                Intent(
                                    requireActivity(),
                                    FilmDetailActivity::class.java
                                ).apply {
                                    putExtra("id_film", item.id.toString())
                                })
                        }

                    },
                    this.binding.fragmentHomeAllUnreleasedMoviesRv.id,
                    this.binding.fragmentHomeAllUpdatedSeriesRv.id,
                    this.binding.fragmentHomeAllUpdatedSeriesRv.id,
                    this.binding.fragmentHomeAllUnreleasedMoviesRv.id
                )
                binding.fragmentHomeUpdatedSeriesRv.adapter = adapter

            } else
                this.binding.fragmentHomeAllUpdatedSeriesLl.visibility = View.GONE


            lifecycleScope.launch(Dispatchers.Main) { sharedViewModel.getCommingSoon() }

        }

        sharedViewModel.getCommingSoonLiveData.observe(this)
        {
            val displayMetrics = requireContext().resources.displayMetrics
            val dpWidth = displayMetrics.widthPixels / displayMetrics.density
            val size = if (ceil(dpWidth / 150) < 5) 5 else ceil(dpWidth / 150).toInt()
            this.binding.fragmentHomeUnreleasedMoviesRvShimmer.stopShimmer()
            this.binding.fragmentHomeUnreleasedMoviesRvShimmer.visibility = View.GONE
            this.binding.fragmentHomeUnreleasedMoviesRv.visibility = View.VISIBLE
            if (!it.results.isNullOrEmpty()) {

                this.binding.fragmentHomeUnreleasedMoviesRv.layoutManager =
                    GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)


                var adapter = FilmItemHomePageAdapter(
                    requireContext(),
                    (if (it.count!!.toInt() > size) it.results.subList(
                        0,
                        size
                    ) else it.results) as List<Image>,
                    object :
                        FilmItemHomePageAdapter.EventListener {
                        override fun click(item: Image) {
                            startActivity(
                                Intent(
                                    requireActivity(),
                                    FilmDetailActivity::class.java
                                ).apply {
                                    putExtra("id_film", item.id.toString())
                                })
                        }

                    },
                    this.binding.fragmentHomeAllActorRv.id,
                    this.binding.fragmentHomeAllUnreleasedMoviesRv.id,
                    this.binding.fragmentHomeAllUnreleasedMoviesRv.id,
                    this.binding.fragmentHomeAllActorRv.id
                )
                binding.fragmentHomeUnreleasedMoviesRv.adapter = adapter

            } else
                this.binding.fragmentHomeAllUnreleasedMoviesLl.visibility = View.GONE


            lifecycleScope.launch(Dispatchers.Main) { sharedViewModel.getPopularActor() }

        }
        sharedViewModel.getPopularActorLiveData.observe(this) {
            val displayMetrics = requireContext().resources.displayMetrics
            val dpWidth = displayMetrics.widthPixels / displayMetrics.density
            val size = if (ceil(dpWidth / 120) < 5) 5 else ceil(dpWidth / 120).toInt()
            this.binding.fragmentHomeActorRvShimmer.stopShimmer()
            this.binding.fragmentHomeActorRvShimmer.visibility = View.GONE
            this.binding.fragmentHomeActorRv.visibility = View.VISIBLE
            if (!it.isNullOrEmpty()) {

                this.binding.fragmentHomeActorRv.layoutManager =
                    GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)


                var adapter = ActorItemHomePageAdapter(
                    requireContext(),
                    (if (it.size > size) it.subList(
                        0,
                        size
                    ) else it),
                    object :
                        ActorItemHomePageAdapter.EventListener {
                        override fun click(item: ActorFilmDetailInformation) {
                            startActivity(
                                Intent(
                                    requireActivity(),
                                    ActorDetailActivity::class.java
                                ).apply {
                                    putExtra("id_actor", item.id.toString())
                                })
                        }

                    },
                    this.binding.fragmentHomeAllDirectorRv.id,
                    this.binding.fragmentHomeAllActorRv.id,
                    this.binding.fragmentHomeAllActorRv.id,
                    this.binding.fragmentHomeAllDirectorRv.id
                )
                binding.fragmentHomeActorRv.adapter = adapter

            } else
                this.binding.fragmentHomeAllActorLl.visibility = View.GONE


            lifecycleScope.launch(Dispatchers.Main) { sharedViewModel.getPopularDirector() }

        }

        sharedViewModel.getPopularDirectorLiveData.observe(this)
        {
            val displayMetrics = requireContext().resources.displayMetrics
            val dpWidth = displayMetrics.widthPixels / displayMetrics.density
            val size = if (ceil(dpWidth / 120) < 5) 5 else ceil(dpWidth / 120).toInt()
            this.binding.fragmentHomeDirectorRvShimmer.stopShimmer()
            this.binding.fragmentHomeDirectorRvShimmer.visibility = View.GONE
            this.binding.fragmentHomeDirectorRv.visibility = View.VISIBLE
            if (!it.isNullOrEmpty()) {

                this.binding.fragmentHomeDirectorRv.layoutManager =
                    GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)


                var adapter = ActorItemHomePageAdapter(
                    requireContext(),
                    (if (it.size > size) it.subList(
                        0,
                        size
                    ) else it),
                    object :
                        ActorItemHomePageAdapter.EventListener {
                        override fun click(item: ActorFilmDetailInformation) {
                            startActivity(
                                Intent(
                                    requireActivity(),
                                    ActorDetailActivity::class.java
                                ).apply {
                                    putExtra("id_actor", item.id.toString())
                                })
                        }

                    },
                    -1,
                    this.binding.fragmentHomeAllDirectorRv.id,
                    this.binding.fragmentHomeAllDirectorRv.id,
                    -1
                )
                binding.fragmentHomeDirectorRv.adapter = adapter


            } else {
                this.binding.fragmentHomeAllDirectorLl.visibility = View.GONE
            }

            lifecycleScope.launch(Dispatchers.Main) { sharedViewModel.getPopularFilm() }

        }
        sharedViewModel.getPopularFilmLiveData.observe(this) {
            val displayMetrics = requireContext().resources.displayMetrics
            val dpWidth = displayMetrics.widthPixels / displayMetrics.density
            val size = if (ceil(dpWidth / 150) < 5) 5 else ceil(dpWidth / 150).toInt()
            this.binding.fragmentHomePopulerFilmRvShimmer.stopShimmer()
            this.binding.fragmentHomePopulerFilmRvShimmer.visibility = View.GONE
            this.binding.fragmentHomePopulerFilmRv.visibility = View.VISIBLE
            if (!it.results.isNullOrEmpty()) {

                this.binding.fragmentHomePopulerFilmRv.layoutManager =
                    GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)


                var adapter = FilmItemHomePageAdapter(
                    requireContext(),
                    (if (it.count!!.toInt() > size) it.results.subList(
                        0,
                        size
                    ) else it.results) as List<Image>,
                    object :
                        FilmItemHomePageAdapter.EventListener {
                        override fun click(item: Image) {
                            startActivity(
                                Intent(
                                    requireActivity(),
                                    FilmDetailActivity::class.java
                                ).apply {
                                    putExtra("id_film", item.id.toString())
                                })
                        }

                    },
                    this.binding.fragmentHomeAllPopulerSerialRv.id,
                    this.binding.fragmentHomeAllPopulerFilmRv.id,
                    this.binding.fragmentHomeAllPopulerFilmRv.id,
                    this.binding.fragmentHomeAllPopulerSerialRv.id
                )
                binding.fragmentHomePopulerFilmRv.adapter = adapter

            } else
                this.binding.fragmentHomeAllPopulerFilmLl.visibility = View.GONE

            lifecycleScope.launch(Dispatchers.Main) {
                sharedViewModel.getPopularMovie()
            }

            sharedViewModel.getPopularMovieLiveData.observe(this) {
                val displayMetrics = requireContext().resources.displayMetrics
                val dpWidth = displayMetrics.widthPixels / displayMetrics.density
                val size = if (ceil(dpWidth / 150) < 5) 5 else ceil(dpWidth / 150).toInt()
                this.binding.fragmentHomePopulerSerialRvShimmer.stopShimmer()
                this.binding.fragmentHomePopulerSerialRvShimmer.visibility = View.GONE
                this.binding.fragmentHomePopulerSerialRv.visibility = View.VISIBLE
                if (!it.results.isNullOrEmpty()) {

                    this.binding.fragmentHomePopulerSerialRv.layoutManager =
                        GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)


                    var adapter = FilmItemHomePageAdapter(
                        requireContext(),
                        (if (it.count!!.toInt() > size) it.results.subList(
                            0,
                            size
                        ) else it.results) as List<Image>,
                        object :
                            FilmItemHomePageAdapter.EventListener {
                            override fun click(item: Image) {
                                startActivity(
                                    Intent(
                                        requireActivity(),
                                        FilmDetailActivity::class.java
                                    ).apply {
                                        putExtra("id_film", item.id.toString())
                                    })
                            }

                        },
                        this.binding.fragmentHomeAllUpdatedSeriesRv.id,
                        this.binding.fragmentHomeAllPopulerSerialRv.id,
                        this.binding.fragmentHomeAllPopulerSerialRv.id,
                        this.binding.fragmentHomeAllUpdatedSeriesRv.id
                    )
                    binding.fragmentHomePopulerSerialRv.adapter = adapter

                } else
                    this.binding.fragmentHomeAllPopulerSerialLl.visibility = View.GONE

                this.binding.fragmentHomeItemsNsv.visibility = View.VISIBLE
                this.binding.fragmentHomeRefresh.isRefreshing = false
            }

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}