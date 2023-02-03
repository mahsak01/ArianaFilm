package com.mgpersia.androidbox.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.bumptech.glide.Glide
import com.mgpersia.androidbox.Fragment.BannerFragment
import com.mgpersia.androidbox.Fragment.dialog.ChooseResolutionDialogFragment
import com.squareup.picasso.Picasso
import com.mgpersia.androidbox.Fragment.dialog.LogInPhoneDialogFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.adapter.*
import com.mgpersia.androidbox.common.FileDownloadWorker
import com.mgpersia.androidbox.common.RoundedTransformation
import com.mgpersia.androidbox.common.TokenContainer
import com.mgpersia.androidbox.common.UserContainer
import com.mgpersia.androidbox.data.Comment
import com.mgpersia.androidbox.data.model.*
import com.mgpersia.androidbox.data.model.enum.ToastStatus
import com.mgpersia.androidbox.data.model.information.GetFilmDetailInformation
import com.mgpersia.androidbox.databinding.ActivityFilmDetailBinding
import com.mgpersia.androidbox.viewModel.UserSettingViewModel
import com.mgpersia.androidbox.player.PlayerActivity
import com.mgpersia.androidbox.viewModel.LocalShareViewModel
import com.mgpersia.androidbox.viewModel.MainViewModel
import convertDpToPixel
import org.koin.androidx.viewmodel.ext.android.viewModel
import showCustomToast
import workManager
import java.io.File
import java.util.ArrayList
import kotlin.streams.toList

class FilmDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityFilmDetailBinding
    private val userViewModel: UserSettingViewModel by viewModel()
    private val mainViewModel: MainViewModel by viewModel()
    lateinit var getFilmDetailInformation: GetFilmDetailInformation
    private val localShareViewModel: LocalShareViewModel by viewModel()

    var numberItem = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_film_detail)
        if (supportActionBar != null) {
            supportActionBar?.hide();
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        mainViewModel.getFilmDetail(intent.getStringExtra("id_film").toString())
        setFocus()
        this.binding.activityFilmDetailBackBtn.requestFocus(1)
        setInformation()
        setObserver()
    }

    private fun setFocus() {
        this.binding.activityFilmDetailSeasonBtn.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.activityFilmDetailSeasonBtn.background =
                    resources.getDrawable(
                        R.drawable.background_select_icon_android_tv
                    )
            } else {
                this.binding.activityFilmDetailSeasonBtn.background =
                    resources.getDrawable(
                        R.drawable.background_button_setting
                    )
//                this.binding.layoutFilmItemHomePageItemCl.setPadding(0,10,0,0)
            }
        }

        this.binding.activityFilmDetailSendIv.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.activityFilmDetailSendIv.background =
                    resources.getDrawable(
                        R.drawable.background_select_icon_android_tv
                    )
            } else {
                this.binding.activityFilmDetailSendIv.background =
                    resources.getDrawable(
                        R.drawable.background_select_text_no_color_android_tv
                    )
//                this.binding.layoutFilmItemHomePageItemCl.setPadding(0,10,0,0)
            }
        }
        this.binding.activityFilmDetailIdExoPlayerFullScreenBtn.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.activityFilmDetailIdExoPlayerFullScreenBtn.background =
                    resources.getDrawable(
                        R.drawable.background_select_icon_android_tv
                    )
            } else {
                this.binding.activityFilmDetailIdExoPlayerFullScreenBtn.background =
                    resources.getDrawable(
                        R.drawable.background_select_text_no_color_android_tv
                    )
//                this.binding.layoutFilmItemHomePageItemCl.setPadding(0,10,0,0)
            }
        }
        this.binding.activityFilmDetailBackBtn.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.activityFilmDetailBackBtn.background =
                    resources.getDrawable(
                        R.drawable.background_select_icon_android_tv
                    )
            } else {
                this.binding.activityFilmDetailBackBtn.background =
                    resources.getDrawable(
                        R.drawable.background_select_text_no_color_android_tv
                    )
//                this.binding.layoutFilmItemHomePageItemCl.setPadding(0,10,0,0)
            }
        }
        this.binding.activityFilmDetailImbdIv.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.activityFilmDetailImbdIv.background =
                    resources.getDrawable(
                        R.drawable.background_select_text_android_tv
                    )
            } else {
                this.binding.activityFilmDetailImbdIv.background =
                    resources.getDrawable(
                        R.drawable.background_select_text_no_color_android_tv
                    )
//                this.binding.layoutFilmItemHomePageItemCl.setPadding(0,10,0,0)
            }
        }
        this.binding.activityFilmDetailRtIv.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.activityFilmDetailRtIv.background =
                    resources.getDrawable(
                        R.drawable.background_select_text_android_tv
                    )
            } else {
                this.binding.activityFilmDetailRtIv.background =
                    resources.getDrawable(
                        R.drawable.background_select_text_no_color_android_tv
                    )
//                this.binding.layoutFilmItemHomePageItemCl.setPadding(0,10,0,0)
            }
        }
        this.binding.activityFilmDetailPlayBtn.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.activityFilmDetailPlayBtn.background =
                    resources.getDrawable(
                        R.drawable.background_select_text_red__android_tv
                    )
            } else {
                this.binding.activityFilmDetailPlayBtn.background =
                    resources.getDrawable(
                        R.drawable.background_red_button_setting
                    )
//                this.binding.layoutFilmItemHomePageItemCl.setPadding(0,10,0,0)
            }
        }
        this.binding.activityFilmDetailInfoFilmRB.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.activityFilmDetailInfoFilmRB.background =
                    resources.getDrawable(
                        R.drawable.background_select_text_red__android_tv
                    )
            } else {
                if (this.binding.activityFilmDetailInfoFilmRB.isChecked)
                    this.binding.activityFilmDetailInfoFilmRB.background =
                        resources.getDrawable(
                            R.drawable.background_red_button_setting
                        )
                else
                    this.binding.activityFilmDetailInfoFilmRB.background =
                        resources.getDrawable(
                            R.drawable.background_select_text_no_color_android_tv
                        )
//                this.binding.layoutFilmItemHomePageItemCl.setPadding(0,10,0,0)
            }
        }
        this.binding.activityFilmDetailBannerFilmItemRB.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.activityFilmDetailBannerFilmItemRB.background =
                    resources.getDrawable(
                        R.drawable.background_select_text_red__android_tv
                    )
            } else {
                if (this.binding.activityFilmDetailBannerFilmItemRB.isChecked)
                    this.binding.activityFilmDetailBannerFilmItemRB.background =
                        resources.getDrawable(
                            R.drawable.background_red_button_setting
                        )
                else
                    this.binding.activityFilmDetailBannerFilmItemRB.background =
                        resources.getDrawable(
                            R.drawable.background_select_text_no_color_android_tv
                        )
//                this.binding.layoutFilmItemHomePageItemCl.setPadding(0,10,0,0)
            }
        }
        this.binding.activityFilmDetailPlayerFilmItemRB.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.activityFilmDetailPlayerFilmItemRB.background =
                    resources.getDrawable(
                        R.drawable.background_select_text_red__android_tv
                    )
            } else {
                if (this.binding.activityFilmDetailPlayerFilmItemRB.isChecked)
                    this.binding.activityFilmDetailPlayerFilmItemRB.background =
                        resources.getDrawable(
                            R.drawable.background_red_button_setting
                        )
                else
                    this.binding.activityFilmDetailPlayerFilmItemRB.background =
                        resources.getDrawable(
                            R.drawable.background_select_text_no_color_android_tv
                        )
//                this.binding.layoutFilmItemHomePageItemCl.setPadding(0,10,0,0)
            }
        }
        this.binding.activityFilmDetailNotificationIv.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.activityFilmDetailNotificationIv.background =
                    resources.getDrawable(
                        R.drawable.background_select_icon_android_tv
                    )
            } else {
                this.binding.activityFilmDetailNotificationIv.background =
                    resources.getDrawable(
                        R.drawable.background_select_text_no_color_android_tv
                    )
//                this.binding.layoutFilmItemHomePageItemCl.setPadding(0,10,0,0)
            }
        }
        this.binding.activityFilmDetailSaveIv.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.activityFilmDetailSaveIv.background =
                    resources.getDrawable(
                        R.drawable.background_select_icon_android_tv
                    )
            } else {
                this.binding.activityFilmDetailSaveIv.background =
                    resources.getDrawable(
                        R.drawable.background_select_text_no_color_android_tv
                    )
//                this.binding.layoutFilmItemHomePageItemCl.setPadding(0,10,0,0)
            }
        }
        this.binding.activityFilmDetailLikeIv.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.activityFilmDetailLikeIv.background =
                    resources.getDrawable(
                        R.drawable.background_select_icon_android_tv
                    )
            } else {
                this.binding.activityFilmDetailLikeIv.background =
                    resources.getDrawable(
                        R.drawable.background_select_text_no_color_android_tv
                    )
//                this.binding.layoutFilmItemHomePageItemCl.setPadding(0,10,0,0)
            }
        }
        this.binding.activityFilmDetailDownloadTv.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.activityFilmDetailDownloadTv.background =
                    resources.getDrawable(
                        R.drawable.background_select_icon_android_tv
                    )
            } else {
                this.binding.activityFilmDetailDownloadTv.background =
                    resources.getDrawable(
                        R.drawable.background_select_text_no_color_android_tv
                    )
//                this.binding.layoutFilmItemHomePageItemCl.setPadding(0,10,0,0)
            }
        }
    }

    private fun setInformation() {
        val displayMetrics = resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        if (dpWidth > 1000) {
            this.binding.activityFilmDetailItemRG.visibility = View.VISIBLE
            goneItemRG()
            this.binding.activityFilmDetailFilmDescriptionLl.visibility = View.VISIBLE
        } else {
            this.binding.activityFilmDetailItemRG.visibility = View.GONE

        }
    }

    private fun disableShimmer() {
        this.binding.activityFilmDetailBannerShimmer.stopShimmer()
        this.binding.activityFilmDetailBannerShimmer.visibility = View.GONE
        this.binding.activityFilmDetailBannerCl.visibility = View.VISIBLE
        this.binding.activityFilmDetailFilmDescriptionTvShimmer.stopShimmer()
        this.binding.activityFilmDetailFilmDescriptionTvShimmer.visibility = View.GONE
        this.binding.activityFilmDetailFilmDescriptionTv.visibility = View.VISIBLE
        this.binding.activityFilmDetailBannerSliderShimmer.stopShimmer()
        this.binding.activityFilmDetailBannerSliderShimmer.visibility = View.GONE
        this.binding.activityFilmDetailBannerSliderClLL.visibility = View.VISIBLE
        this.binding.activityFilmDetailIdExoPlayerShimmer.stopShimmer()
        this.binding.activityFilmDetailIdExoPlayerShimmer.visibility = View.GONE
        this.binding.activityFilmDetailIdExoPlayerCl.visibility = View.VISIBLE
        this.binding.activityFilmDetailActorRvShimmer.stopShimmer()
        this.binding.activityFilmDetailActorRvShimmer.visibility = View.GONE
        this.binding.activityFilmDetailActorRv.visibility = View.VISIBLE
        this.binding.activityFilmDetailWorkerRvShimmer.stopShimmer()
        this.binding.activityFilmDetailWorkerRvShimmer.visibility = View.GONE
        this.binding.activityFilmDetailWorkerRv.visibility = View.VISIBLE
        this.binding.activityFilmDetailMoviesRvShimmer.stopShimmer()
        this.binding.activityFilmDetailMoviesRvShimmer.visibility = View.GONE
        this.binding.activityFilmDetailMoviesRv.visibility = View.VISIBLE
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun setObserver() {
        mainViewModel.getFilmDetailInformation.observe(this) {
            if (it != null) {
                mainViewModel.getAllCommentOfFilm(intent.getStringExtra("id_film").toString())
            }
            getFilmDetailInformation = mainViewModel.getFilmDetailInformation.value!!
            setInformations()
            disableShimmer()

        }
        mainViewModel.getAllCommentLiveData.observe(this) {
            this.binding.activityFilmDetailCommentsRvShimmer.stopShimmer()
            this.binding.activityFilmDetailCommentsRvShimmer.visibility = View.GONE
            this.binding.activityFilmDetailCommentsRv.visibility = View.VISIBLE
            if (!it.results.isNullOrEmpty()) {
                binding.activityFilmDetailCommentsRv.layoutManager =
                    LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                val adapter =
                    CommentItemAdapter(it.results as List<Comment>, this)
                binding.activityFilmDetailCommentsRv.adapter = adapter
            }

        }
        userViewModel.addCommentFilmLiveData.observe(this) {

            if (it != null) {
                Toast.makeText(this, "کامنت با موفقیت اضافه شد", Toast.LENGTH_LONG)
                    .showCustomToast(
                        "کامنت با موفقیت اضافه شد",
                        this,
                        ToastStatus.OK
                    )
                this.binding.activityFilmDetailCommentTI.text =
                    Editable.Factory.getInstance().newEditable("")
                mainViewModel.getAllCommentOfFilm(intent.getStringExtra("id_film").toString())
            }
        }
        userViewModel.addFavoriteWatchlistLiveData.observe(this) {
            getFilmDetailInformation.is_favorite = true
            this.binding.activityFilmDetailLikeIv.setImageDrawable(
                resources.getDrawable(R.drawable.ic_like_select)
            )
            Toast.makeText(this, "با موفقیت به لیست اضافه شد", Toast.LENGTH_LONG)
                .showCustomToast(
                    "با موفقیت به لیست اضافه شد",
                    this,
                    ToastStatus.OK
                )

        }
        userViewModel.addBookMarkWatchlistLiveData.observe(this) {
            getFilmDetailInformation.is_bookmark = true
            this.binding.activityFilmDetailSaveIv.setImageDrawable(
                resources.getDrawable(R.drawable.ic_saved_select)
            )
            Toast.makeText(this, "با موفقیت به لیست اضافه شد", Toast.LENGTH_LONG)
                .showCustomToast(
                    "با موفقیت به لیست اضافه شد",
                    this,
                    ToastStatus.OK
                )


        }
        userViewModel.addNotificationsLiveData.observe(this) {
            getFilmDetailInformation.is_notif = true
            this.binding.activityFilmDetailNotificationIv.setImageDrawable(
                resources.getDrawable(R.drawable.ic_notification_select)
            )
            Toast.makeText(this, "با موفقیت به لیست اضافه شد", Toast.LENGTH_LONG)
                .showCustomToast(
                    "با موفقیت به لیست اضافه شد",
                    this,
                    ToastStatus.OK
                )


        }

        userViewModel.deleteFavoriteWatchlistLiveData.observe(this) {
            getFilmDetailInformation.is_favorite = false
            this.binding.activityFilmDetailLikeIv.setImageDrawable(
                resources.getDrawable(R.drawable.ic_like_film_detail)
            )
            Toast.makeText(this, "با موفقیت از لیست حذف شد", Toast.LENGTH_LONG)
                .showCustomToast(
                    "با موفقیت از لیست حذف شد",
                    this,
                    ToastStatus.OK
                )


        }

        userViewModel.deleteBookMarkWatchlistLiveData.observe(this) {
            getFilmDetailInformation.is_bookmark = false
            this.binding.activityFilmDetailSaveIv.setImageDrawable(
                resources.getDrawable(R.drawable.ic_saved_film_detail)
            )
            Toast.makeText(this, "با موفقیت از لیست حذف شد", Toast.LENGTH_LONG)
                .showCustomToast(
                    "با موفقیت از لیست حذف شد",
                    this,
                    ToastStatus.OK
                )

        }


    }

    private fun onClickActor(item: ActorFilmDetailInformation) {
        startActivity(
            Intent(
                this,
                ActorDetailActivity::class.java
            ).apply {
                putExtra("id_actor", item.id.toString())
            })
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

    private fun onClickBanner(items: List<Image>) {
        startActivity(
            Intent(
                this,
                ShowBannerActivity::class.java
            ).apply {
                putExtra("banner", items as ArrayList<Image>)
            })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setInformations() {
        if (!getFilmDetailInformation.seasons.isNullOrEmpty())
            this.binding.activityFilmDetailSeasonBtn.visibility = View.VISIBLE
        this.binding.activityFilmDetailEnFilmTv.text = getFilmDetailInformation.name_en
        this.binding.activityFilmDetailYearFilmTv.text =
            getFilmDetailInformation.production_year.toString()
        val displayMetrics = resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        if (dpWidth > 1000) {
            this.binding.activityFilmDetailAgeTv.text =
                "رده سنی : " + getFilmDetailInformation.age_limit!!.name
            this.binding.activityFilmDetailLanguageTv.text =
                "زبان : " + getFilmDetailInformation.lang
            this.binding.activityFilmDetailSubtitleTv.text = "زیرنویس : " +
                    if (getFilmDetailInformation.has_subtitle == true) "دارد" else "ندارد"
            this.binding.activityFilmDetailHourTv.text =
                "زمان : " + getFilmDetailInformation.duration.toString()
            this.binding.activityFilmDetailCountryTv.text =
                "کشور : " + getFilmDetailInformation.countrys!![0].name
            this.binding.activityFilmDetailInfoTv.text =
                if (getFilmDetailInformation.is_multi_lang == true) "چند زبانه" else "یک زبانه"
        } else {
            this.binding.activityFilmDetailAgeTv.text = getFilmDetailInformation.age_limit!!.name
            this.binding.activityFilmDetailLanguageTv.text = getFilmDetailInformation.lang
            this.binding.activityFilmDetailSubtitleTv.text =
                if (getFilmDetailInformation.has_subtitle == true) "دارد" else "ندارد"
            this.binding.activityFilmDetailHourTv.text =
                getFilmDetailInformation.duration.toString()
            this.binding.activityFilmDetailCountryTv.text =
                getFilmDetailInformation.countrys!![0].name
            this.binding.activityFilmDetailInfoTv.text =
                if (getFilmDetailInformation.is_multi_lang == true) "چند زبانه" else "یک زبانه"

        }
        if (getFilmDetailInformation.is_notif == true) this.binding.activityFilmDetailNotificationIv.setImageDrawable(
            resources.getDrawable(R.drawable.ic_notification_select)
        ) else this.binding.activityFilmDetailNotificationIv.setImageDrawable(
            resources.getDrawable(R.drawable.ic_notification_film_detail)
        )

        if (getFilmDetailInformation.is_favorite == true) this.binding.activityFilmDetailLikeIv.setImageDrawable(
            resources.getDrawable(R.drawable.ic_like_select)
        ) else this.binding.activityFilmDetailLikeIv.setImageDrawable(
            resources.getDrawable(R.drawable.ic_like_film_detail)
        )
        if (getFilmDetailInformation.is_bookmark == true) this.binding.activityFilmDetailSaveIv.setImageDrawable(
            resources.getDrawable(R.drawable.ic_saved_select)
        ) else this.binding.activityFilmDetailSaveIv.setImageDrawable(
            resources.getDrawable(R.drawable.ic_saved_film_detail)
        )

        if (localShareViewModel.searchFilm(getFilmDetailInformation.id!!)) this.binding.activityFilmDetailDownloadTv.setImageDrawable(
            resources.getDrawable(R.drawable.ic_download_select)
        ) else this.binding.activityFilmDetailDownloadTv.setImageDrawable(
            resources.getDrawable(R.drawable.ic_download)
        )

        // check download


        val dpHeight = displayMetrics.heightPixels / displayMetrics.density

        if (dpWidth > 1000) {
            val layoutParams = this.binding.activityFilmDetailBannerIv.layoutParams
            layoutParams.height = (dpHeight - 200).toInt()
            this.binding.activityFilmDetailBannerIv.layoutParams = layoutParams
        }
        Picasso.with(this).load(getFilmDetailInformation.cover)
            .placeholder(resources.getDrawable(R.drawable.background_cover_film))
            .error(resources.getDrawable(R.drawable.background_cover_film))
            .fit().centerCrop()
            .transform(RoundedTransformation(15, 0))
            .into(this.binding.activityFilmDetailBannerIv)
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.colors = intArrayOf(
            Color.parseColor(("#CD474747")),
            Color.parseColor(("#CD474747"))
        )
        drawable.cornerRadius = 15F
        drawable.setGradientCenter(0.3f, 0f)
        drawable.gradientType = GradientDrawable.SWEEP_GRADIENT
        drawable.also {
            this.binding.activityFilmDetailBannerIv.foreground = it
        }


        if (dpWidth > 1000) {
            val layoutParams = this.binding.activityFilmDetailImageIv.layoutParams
            layoutParams.height = (dpHeight / 1.5).toInt()
            layoutParams.width = (dpWidth / 4).toInt()
            this.binding.activityFilmDetailImageIv.layoutParams = layoutParams
        }
        Picasso.with(this).load(getFilmDetailInformation.image)
            .placeholder(resources.getDrawable(R.drawable.background_defult_item))
            .error(resources.getDrawable(R.drawable.background_defult_item))
            .fit().centerCrop()
            .transform(RoundedTransformation(15, 0))
            .into(this.binding.activityFilmDetailImageIv)
        this.binding.activityFilmDetailFilmDescriptionTv.text = getFilmDetailInformation.description

        if (getFilmDetailInformation.is_dubbed != null && getFilmDetailInformation.is_dubbed == true)
            this.binding.activityFilmDetailSubTv.text = "دوبله"
        else if (getFilmDetailInformation.has_subtitle != null && getFilmDetailInformation.has_subtitle == true)
            this.binding.activityFilmDetailSubTv.text = "زیرنویس"
        else
            this.binding.activityFilmDetailSubTv.visibility = View.GONE

        setListener(getFilmDetailInformation)


        if (!getFilmDetailInformation.trailer!!.isNullOrEmpty()) {
            //TODO
            val url = "http://95.216.26.66:3080/media/media/topgun-trailer.mp4"

            Picasso.with(this).load(getFilmDetailInformation.cover)
                .placeholder(resources.getDrawable(R.drawable.background_cover_film))
                .error(resources.getDrawable(R.drawable.background_cover_film))
                .fit().centerCrop()
                .transform(RoundedTransformation(15, 0))
                .into(this.binding.activityFilmDetailIdExoPlayerIv)
            numberItem++
        } else {
            this.binding.activityFilmDetailIdExoPlayerCl.visibility = View.GONE
            this.binding.activityFilmDetailIdExoPlayerTv.visibility = View.GONE
            this.binding.activityFilmDetailPlayerFilmItemRB.visibility = View.GONE
        }

        if (!getFilmDetailInformation.actor.isNullOrEmpty()) {
            this.binding.activityFilmDetailActorRv.layoutManager =
                GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)


            val adapterActor = ActorItemHomePageAdapter(
                this,
                getFilmDetailInformation.actor!!,
                object :
                    ActorItemHomePageAdapter.EventListener {
                    override fun click(item: ActorFilmDetailInformation) {
                        onClickActor(item)
                    }

                })
            binding.activityFilmDetailActorRv.adapter = adapterActor
            numberItem++
        } else {
            this.binding.activityFilmDetailActorRv.visibility = View.GONE
            this.binding.activityFilmDetailActorTv.visibility = View.GONE
            this.binding.activityFilmDetailInfoFilmRB.visibility = View.GONE

        }


        if (!getFilmDetailInformation.director.isNullOrEmpty()) {

            this.binding.activityFilmDetailWorkerRv.layoutManager =
                GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)


            val adapterWorker = ActorItemHomePageAdapter(
                this,
                getFilmDetailInformation.director!!,
                object :
                    ActorItemHomePageAdapter.EventListener {
                    override fun click(item: ActorFilmDetailInformation) {
                        onClickActor(item)

                    }

                })
            binding.activityFilmDetailWorkerRv.adapter = adapterWorker

        } else {
            this.binding.activityFilmDetailWorkerRv.visibility = View.GONE
            this.binding.activityFilmDetailWorkerTv.visibility = View.GONE
        }


        if (!getFilmDetailInformation.related_films.isNullOrEmpty()) {

            this.binding.activityFilmDetailMoviesRv.layoutManager =
                GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)


            var adapterMovies = FilmItemHomePageAdapter(
                this,
                getFilmDetailInformation.related_films!!.stream()
                    .map { film ->
                        Image(
                            film!!.id!!,
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
                    FilmItemHomePageAdapter.EventListener {
                    override fun click(item: Image) {
                        onClickMovie(item)
                    }

                },
                this.binding.activityFilmDetailCommentTI.id,
                -1,
                -1,
                this.binding.activityFilmDetailCommentTI.id
            )
            binding.activityFilmDetailMoviesRv.adapter = adapterMovies
        } else {
            this.binding.activityFilmDetailMoviesRv.visibility = View.GONE
            this.binding.activityFilmDetailMoviesTv.visibility = View.GONE
        }




        if (!getFilmDetailInformation.gallery.isNullOrEmpty()) {
            val items = getFilmDetailInformation.gallery!!.stream().map { live ->
                Image(live!!.id!!, live.image.toString(), null, null)
            }.toList()
            val bannerSliderAdapter =
                BannerSliderActivityAdapter(
                    this,
                    items,
                    false,
                    object : BannerFragment.EventListener {
                        override fun click() {
                            onClickBanner(items)
                        }

                    }
                )
            this.binding.activityFilmDetailBannerSliderVp.adapter = bannerSliderAdapter
            this.binding.activityFilmDetailBannerSliderVp.post {
                val height =
                    (((this.binding.activityFilmDetailBannerSliderVp.width - convertDpToPixel(
                        32f,
                        this
                    )) * 173) / 328).toInt()
                val layoutParams = this.binding.activityFilmDetailBannerSliderVp.layoutParams
                layoutParams.height = height
                this.binding.activityFilmDetailBannerSliderVp.layoutParams = layoutParams
                this.binding.activityFilmDetailBannerSliderDi.setViewPager2(this.binding.activityFilmDetailBannerSliderVp)

            }

            if (getFilmDetailInformation.gallery!!.size == 1)
                this.binding.activityFilmDetailBannerSliderDi.visibility = View.GONE

            numberItem++

        } else {
            this.binding.activityFilmDetailBannerSliderCl.visibility = View.GONE
            this.binding.activityFilmDetailBannerSliderDi.visibility = View.GONE
            this.binding.activityFilmDetailBannerSliderTv.visibility = View.GONE
            this.binding.activityFilmDetailBannerFilmItemRB.visibility = View.GONE

        }

        if (numberItem == 1)
            this.binding.activityFilmDetailItemRG.visibility = View.GONE


    }

    private fun goneItemRG() {
        this.binding.activityFilmDetailFilmDescriptionLl.visibility = View.GONE
        this.binding.activityFilmDetailBannerSliderLl.visibility = View.GONE
        this.binding.activityFilmDetailPlayerLl.visibility = View.GONE

    }

    private fun showMovie(resolution: Resolution) {
        startActivity(Intent(this, PlayerActivity::class.java).apply {
            putExtra("name", getFilmDetailInformation.name_en + resolution.resolution)
            putExtra(
                "url",
                resolution.link
            )
            //args.live.url.toString())
        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setListener(getFilmDetailInformation: GetFilmDetailInformation) {
        this.binding.activityFilmDetailBackBtn.setOnClickListener {
            this.onBackPressed()
        }
        this.binding.activityFilmDetailSeasonBtn.setOnClickListener {
            startActivity(Intent(this, AllSeasonActivity::class.java).apply {
                putExtra(
                    "title",
                    "فصل ها"
                )
                putExtra(
                    "seasons",
                    getFilmDetailInformation.seasons as ArrayList<Season>
                )
            })
        }
        this.binding.activityFilmDetailSaveIv.setOnClickListener {
            if (!TokenContainer.token.isNullOrEmpty()) {

                if (getFilmDetailInformation.is_bookmark == true)
                    userViewModel.deleteBookMarkWatchlist(getFilmDetailInformation.id.toString())
                else
                    userViewModel.addBookMarkWatchlist(getFilmDetailInformation.id.toString())

            } else {
                Toast.makeText(this, "ابتدا وارد حساب کاربری خود شوید", Toast.LENGTH_LONG)
                    .showCustomToast(
                        "ابتدا وارد حساب کاربری خود شوید",
                        this,
                        ToastStatus.ERROR
                    )
                val logInPhoneDialogFragment = LogInPhoneDialogFragment()
                logInPhoneDialogFragment.show(this.supportFragmentManager, null)
            }

        }
        this.binding.activityFilmDetailLikeIv.setOnClickListener {
            if (!TokenContainer.token.isNullOrEmpty()) {

                if (getFilmDetailInformation.is_favorite == true)
                    userViewModel.deleteFavoriteWatchlist(getFilmDetailInformation.id.toString())
                else
                    userViewModel.addFavoriteWatchlist(getFilmDetailInformation.id.toString())

            } else {
                Toast.makeText(this, "ابتدا وارد حساب کاربری خود شوید", Toast.LENGTH_LONG)
                    .showCustomToast(
                        "ابتدا وارد حساب کاربری خود شوید",
                        this,
                        ToastStatus.ERROR
                    )
                val logInPhoneDialogFragment = LogInPhoneDialogFragment()
                logInPhoneDialogFragment.show(this.supportFragmentManager, null)
            }

        }
        this.binding.activityFilmDetailNotificationIv.setOnClickListener {
            if (!TokenContainer.token.isNullOrEmpty()) {

                if (getFilmDetailInformation.is_favorite == false)
                    userViewModel.addNotifications(getFilmDetailInformation.id.toString())
                //TODO
//                else
//                    userViewModel.addFavoriteWatchlist(getFilmDetailInformation.id.toString())

            } else {
                Toast.makeText(this, "ابتدا وارد حساب کاربری خود شوید", Toast.LENGTH_LONG)
                    .showCustomToast(
                        "ابتدا وارد حساب کاربری خود شوید",
                        this,
                        ToastStatus.ERROR
                    )
                val logInPhoneDialogFragment = LogInPhoneDialogFragment()
                logInPhoneDialogFragment.show(this.supportFragmentManager, null)
            }

        }

        this.binding.activityFilmDetailPlayBtn.setOnClickListener {
            //TODO
//            if (TokenContainer.token.isNullOrEmpty()) {
//                if (UserContainer.user!!.is_vip == false) {
            val chooseResolutionDialogFragment = ChooseResolutionDialogFragment(
                getFilmDetailInformation.id.toString(),
                object : ChooseResolutionDialogFragment.EventListener {
                    override fun click(resolution: Resolution) {
                        showMovie(resolution)
                    }

                })
            chooseResolutionDialogFragment.show(this.supportFragmentManager, null)

//                } else {
//                    Toast.makeText(this, "ابتدا اشتراک تهیه کنید", Toast.LENGTH_SHORT)
//                        .showCustomToast(
//                            "ابتدا اشتراک تهیه کنید",
//                            this,
//                            ToastStatus.ERROR
//                        )
//                }
//            } else {
//                Toast.makeText(this, "ابتدا وارد حساب کاربری خود شوید", Toast.LENGTH_SHORT)
//                    .showCustomToast(
//                        "ابتدا وارد حساب کاربری خود شوید",
//                        this,
//                        ToastStatus.ERROR
//                    )
//                val logInPhoneDialogFragment = LogInPhoneDialogFragment()
//                logInPhoneDialogFragment.show(this.supportFragmentManager, null)
//            }
        }
        this.binding.activityFilmDetailImbdIv.setOnClickListener {
            val isAppInstalled: Boolean = appInstalledOrNot("com.imdb.mobile")

            if (isAppInstalled) {
                val imdbId = getFilmDetailInformation.imdb!!.split('/')
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse("imdb:///title/" + imdbId[imdbId.size - 1] + "/")
                startActivity(openURL)
            } else {
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(getFilmDetailInformation.imdb)
                startActivity(openURL)
            }

        }
        this.binding.activityFilmDetailRtIv.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(getFilmDetailInformation.rottentomatoes)
            startActivity(openURL)
        }

        this.binding.activityFilmDetailItemRG.setOnCheckedChangeListener { _, _ ->
            if (this.binding.activityFilmDetailInfoFilmRB.isChecked) {

                this.binding.activityFilmDetailBannerFilmItemRB.background =
                    resources.getDrawable(R.drawable.background_select_text_no_color_android_tv)

                this.binding.activityFilmDetailPlayerFilmItemRB.background =
                    resources.getDrawable(R.drawable.background_select_text_no_color_android_tv)
                goneItemRG()

                this.binding.activityFilmDetailFilmDescriptionLl.visibility = View.VISIBLE
            } else if (this.binding.activityFilmDetailBannerFilmItemRB.isChecked) {
                this.binding.activityFilmDetailInfoFilmRB.background =
                    resources.getDrawable(R.drawable.background_select_text_no_color_android_tv)

                this.binding.activityFilmDetailPlayerFilmItemRB.background =
                    resources.getDrawable(R.drawable.background_select_text_no_color_android_tv)
                goneItemRG()

                this.binding.activityFilmDetailBannerSliderLl.visibility = View.VISIBLE
            } else if (this.binding.activityFilmDetailPlayerFilmItemRB.isChecked) {
                this.binding.activityFilmDetailInfoFilmRB.background =
                    resources.getDrawable(R.drawable.background_select_text_no_color_android_tv)

                this.binding.activityFilmDetailBannerFilmItemRB.background =
                    resources.getDrawable(R.drawable.background_select_text_no_color_android_tv)
                goneItemRG()

                this.binding.activityFilmDetailPlayerLl.visibility = View.VISIBLE
            }
        }
        this.binding.activityFilmDetailSendIv.setOnClickListener {
            if (!TokenContainer.token.isNullOrEmpty()) {
                if (!this.binding.activityFilmDetailCommentTI.text.isNullOrEmpty()) {
                    userViewModel.addCommentFilm(
                        this.binding.activityFilmDetailCommentTI.text.toString(),
                        intent.getStringExtra("id_film").toString()

                    )
                } else
                    Toast.makeText(this, "پیام کامنت رو وارد کنید", Toast.LENGTH_LONG)
                        .showCustomToast(
                            "پیام کامنت رو وارد کنید",
                            this,
                            ToastStatus.ERROR
                        )
            } else {
                Toast.makeText(this, "ابتدا وارد حساب کاربری خود شوید", Toast.LENGTH_LONG)
                    .showCustomToast(
                        "ابتدا وارد حساب کاربری خود شوید",
                        this,
                        ToastStatus.ERROR
                    )
                val logInPhoneDialogFragment = LogInPhoneDialogFragment()
                logInPhoneDialogFragment.show(this.supportFragmentManager, null)
            }


        }
        this.binding.activityFilmDetailIdExoPlayerFullScreenBtn.setOnClickListener {
            //TODO
            startActivity(Intent(this, PlayerActivity::class.java).apply {
                putExtra("name", getFilmDetailInformation.name_en)
                putExtra(
                    "url",
                    "http://95.216.26.66:3080/media/media/topgun-trailer.mp4"
                )
                //args.live.url.toString())
            })
        }

        this.binding.activityFilmDetailDownloadTv.setOnClickListener {
            //TODO
//            if (!TokenContainer.token.isNullOrEmpty()) {
//                if (!this.binding.activityFilmDetailCommentTI.text.isNullOrEmpty()) {
            val chooseResolutionDialogFragment = ChooseResolutionDialogFragment(
                getFilmDetailInformation.id.toString(),
                object : ChooseResolutionDialogFragment.EventListener {
                    override fun click(resolution: Resolution) {

                        startDownloadingFile(
                            getFilmDetailInformation.name_en!! + resolution.resolution,
                            //  resolution.link,
                            "http://95.216.26.66:3080/media/media/topgun-trailer.mp4",
                            success = {
                                localShareViewModel.addFilm(
                                    LocalFilmDownload(
                                        0,
                                        getFilmDetailInformation.id,
                                        getFilmDetailInformation.name,
                                        getFilmDetailInformation.name_en,
                                        getFilmDetailInformation.cover,
                                        UserContainer.user!!.email,
                                        Environment.DIRECTORY_DOWNLOADS,
                                        getFilmDetailInformation.is_series == false,
                                        true
                                    )
                                )
                                binding.activityFilmDetailDownloadPb.visibility = View.GONE
                                binding.activityFilmDetailDownloadTv.visibility = View.VISIBLE
                                binding.activityFilmDetailDownloadTv.setImageDrawable(
                                    resources.getDrawable(R.drawable.ic_download_select)
                                )
                            },
                            failed = {
                                binding.activityFilmDetailDownloadPb.visibility = View.GONE
                                binding.activityFilmDetailDownloadTv.visibility = View.VISIBLE
                                binding.activityFilmDetailDownloadTv.setImageDrawable(
                                    resources.getDrawable(R.drawable.ic_download)
                                )
                            },
                            running = {
                                localShareViewModel.addDownloadFilm(
                                    LocalFilmDownload(
                                        0,
                                        getFilmDetailInformation.id,
                                        getFilmDetailInformation.name,
                                        getFilmDetailInformation.name_en,
                                        getFilmDetailInformation.cover,
                                        UserContainer.user!!.email,
                                        resolution.link,
                                        getFilmDetailInformation.is_series == false
                                    )
                                )
                                binding.activityFilmDetailDownloadPb.visibility = View.VISIBLE
                                binding.activityFilmDetailDownloadTv.visibility = View.GONE
                            }
                        )
                    }

                })
            chooseResolutionDialogFragment.show(this.supportFragmentManager, null)
        }
        //else
//                    Toast.makeText(this, "پیام کامنت رو وارد کنید", Toast.LENGTH_LONG)
//                        .showCustomToast(
//                            "پیام کامنت رو وارد کنید",
//                            this,
//                            ToastStatus.ERROR
//                        )
//            } else {
//                Toast.makeText(this, "ابتدا وارد حساب کاربری خود شوید", Toast.LENGTH_LONG)
//                    .showCustomToast(
//                        "ابتدا وارد حساب کاربری خود شوید",
//                        this,
//                        ToastStatus.ERROR
//                    )
//                val logInPhoneDialogFragment = LogInPhoneDialogFragment()
//                logInPhoneDialogFragment.show(this.supportFragmentManager, null)
//            }
//        }

//        this.binding.activityFilmDetailBannerSliderCl.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putParcelableArrayList("banner",  getFilmDetailInformation.gallery!!.stream().map { live ->
//                Image(live!!.id!!, live.image.toString(), null, null)
//            }.toList() as ArrayList<Image?>?)
//            val intent = Intent(this, ShowBannerActivity::class.java)
//            intent.putExtras(bundle)
//            startActivity(intent)
//        }
    }

    private fun appInstalledOrNot(uri: String): Boolean {
        val pm = packageManager
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            return true
        } catch (_: PackageManager.NameNotFoundException) {
        }
        return false
    }

    private fun startDownloadingFile(
        fileName: String,
        url: String,
        success: (String) -> Unit,
        failed: (String) -> Unit,
        running: () -> Unit
    ) {
        val data = Data.Builder()

        data.apply {
            putString(FileParams.KEY_FILE_NAME, fileName)
            putString(FileParams.KEY_FILE_URL, url)
            putString(FileParams.KEY_FILE_TYPE, url.split('.')[url.split('.').size - 1])

        }

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .build()

        val fileDownloadWorker = OneTimeWorkRequestBuilder<FileDownloadWorker>()
            .setConstraints(constraints)
            .setInputData(data.build())
            .build()

        workManager.enqueueUniqueWork(
            "oneFileDownloadWork_${System.currentTimeMillis()}",
            ExistingWorkPolicy.KEEP,
            fileDownloadWorker
        )

        workManager.getWorkInfoByIdLiveData(fileDownloadWorker.id)
            .observe(this) { info ->
                info?.let {
                    when (it.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            success(it.outputData.getString(FileParams.KEY_FILE_URI) ?: "")
                        }
                        WorkInfo.State.FAILED -> {
                            failed("Downloading failed!")
                        }
                        WorkInfo.State.RUNNING -> {

                            running()
                        }
                        else -> {
                            failed("Something went wrong")
                        }
                    }
                }
            }
    }
}