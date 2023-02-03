package com.mgpersia.androidbox.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.squareup.picasso.Picasso
import com.mgpersia.androidbox.Fragment.dialog.LogInPhoneDialogFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.adapter.FilmItemHomePageAdapter
import com.mgpersia.androidbox.common.RoundedTransformation
import com.mgpersia.androidbox.common.TokenContainer
import com.mgpersia.androidbox.data.model.Image
import com.mgpersia.androidbox.data.model.enum.ToastStatus
import com.mgpersia.androidbox.data.model.information.GetActorDetailInformation
import com.mgpersia.androidbox.databinding.ActivityActorDetailBinding
import com.mgpersia.androidbox.viewModel.MainViewModel
import com.mgpersia.androidbox.viewModel.UserSettingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import showCustomToast
import kotlin.streams.toList


class ActorDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityActorDetailBinding
    lateinit var getActorDetailInformation: GetActorDetailInformation
    private val mainViewModel: MainViewModel by viewModel()
    private val userViewModel: UserSettingViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_actor_detail)
        if (supportActionBar != null) {
            supportActionBar?.hide();
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        mainViewModel.getActorDetail(intent.getStringExtra("id_actor").toString())
        setObserver()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setObserver() {
        mainViewModel.getActorDetailInformation.observe(this) {
            if (it != null) {
                getActorDetailInformation = it
                setInformation()
                setListener(it)
            }
        }
        userViewModel.followActorLiveData.observe(this){
            if (it.message=="successe"){
                getActorDetailInformation.is_followed_user=true
                this.binding.activityActorDetailUnfollowBtn.visibility = View.VISIBLE
                this.binding.activityActorDetailFollowBtn.visibility = View.GONE
                Toast.makeText(this, "با موفقیت فالو شد", Toast.LENGTH_LONG)
                    .showCustomToast(
                        "با موفقیت فالو شد",
                        this,
                        ToastStatus.ERROR
                    )

            }
        }

        userViewModel.unFollowActorLiveData.observe(this){
            if (it==true){
                getActorDetailInformation.is_followed_user=false
                this.binding.activityActorDetailUnfollowBtn.visibility = View.GONE
                this.binding.activityActorDetailFollowBtn.visibility = View.VISIBLE
                Toast.makeText(this, "با موفقیت انفالو شد", Toast.LENGTH_LONG)
                    .showCustomToast(
                        "با موفقیت انفالو شد",
                        this,
                        ToastStatus.ERROR
                    )

            }
        }
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
    private fun setInformation() {
        this.binding.activityActorDetailBannerShimmer.stopShimmer()
        this.binding.activityActorDetailBannerShimmer.visibility = View.GONE
        this.binding.activityActorDetailBannerCl.visibility = View.VISIBLE

        this.binding.activityActorDetailActorDescriptionTvShimmer.stopShimmer()
        this.binding.activityActorDetailActorDescriptionTvShimmer.visibility = View.GONE
        this.binding.activityActorDetailActorDescriptionTv.visibility = View.VISIBLE

        this.binding.activityActorDetailMoviesRvShimmer.stopShimmer()
        this.binding.activityActorDetailMoviesRvShimmer.visibility = View.GONE
        this.binding.activityActorDetailMoviesRv.visibility = View.VISIBLE

        Picasso.with(this).load(getActorDetailInformation.image_path)
            .transform(RoundedTransformation(10, 0))
            .fit().centerCrop()
            .into(this.binding.activityActorDetailImageIv)


        this.binding.activityActorDetailOccupationTv.text = getActorDetailInformation.occupation
        this.binding.activityActorDetailEnNameTv.text = getActorDetailInformation.name_en
        this.binding.activityActorDetailPNameTv.text = getActorDetailInformation.name
        this.binding.activityActorDetailYearFilmTv.text =
            getActorDetailInformation.bdate!!.substring(0, 4)


        if (getActorDetailInformation.is_followed_user == true) {
            this.binding.activityActorDetailUnfollowBtn.visibility = View.VISIBLE
            this.binding.activityActorDetailFollowBtn.visibility = View.GONE

        } else {
            this.binding.activityActorDetailUnfollowBtn.visibility = View.GONE
            this.binding.activityActorDetailFollowBtn.visibility = View.VISIBLE
        }

        this.binding.activityActorDetailActorDescriptionTv.text = getActorDetailInformation.bio

        if (!getActorDetailInformation.films.isNullOrEmpty()) {
            this.binding.activityActorDetailMoviesRv.layoutManager =
                GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)


            var adapter = FilmItemHomePageAdapter(
                this,
                getActorDetailInformation.films!!.stream().map { film ->
                    Image(film!!.id!!, film.image, film.cover, film.name_en,null,film.is_dubbed,film.has_subtitle,film.imdb_id)
                }.toList() as List<Image>,
                object :
                    FilmItemHomePageAdapter.EventListener {
                    override fun click(item: Image) {
                        onClickMovie(item)
                    }

                })
            binding.activityActorDetailMoviesRv.adapter = adapter

        } else {
            this.binding.activityActorDetailMoviesRv.visibility = View.GONE
            this.binding.activityActorDetailMoviesTv.visibility = View.GONE
        }


    }

    private fun setListener(getActorDetailInformation: GetActorDetailInformation) {
        this.binding.activityActorDetailBackBtn.setOnClickListener {
            this.onBackPressed()
        }
        this.binding.activityActorDetailImbdIv.setOnClickListener {
            val isAppInstalled: Boolean = appInstalledOrNot("com.imdb.mobile")
            if (isAppInstalled){
                val imdbId= getActorDetailInformation.imdb!!.split('/')
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse("imdb:///name/"+imdbId[imdbId.size-1]+"/")
                startActivity(openURL)
            }else{
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(getActorDetailInformation.imdb)
                startActivity(openURL)
            }

        }
        this.binding.activityActorDetailRtIv.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(getActorDetailInformation.rottentomatoes)
            startActivity(openURL)
        }

        this.binding.activityActorDetailFollowBtn.setOnClickListener {
            if (!TokenContainer.token.isNullOrEmpty()) {
                userViewModel.followActorLiveData(getActorDetailInformation.id.toString())

            } else{
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
        this.binding.activityActorDetailUnfollowBtn.setOnClickListener {
            if (!TokenContainer.token.isNullOrEmpty()) {

                userViewModel.unFollowActorLiveData(getActorDetailInformation.id.toString())

            } else{
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
    }

    private fun appInstalledOrNot(uri: String): Boolean {
        val pm = packageManager
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
        }
        return false
    }
}