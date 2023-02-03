package com.mgpersia.androidbox.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.adapter.ActorItemAllActorAdapter
import com.mgpersia.androidbox.data.model.ActorFilmDetailInformation
import com.mgpersia.androidbox.data.model.enum.ToastStatus
import com.mgpersia.androidbox.databinding.ActivityAllActorsBinding
import com.mgpersia.androidbox.viewModel.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import showCustomToast
import kotlin.math.floor

class AllActorActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAllActorsBinding
    private val sharedViewModel: SharedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_actors)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()

        if (intent.getBooleanExtra("isActor",false))
            sharedViewModel.getAllActor()
        else
            sharedViewModel.getAllDirector()
        setInformation()
        setObserver()
        setListener()
    }

    private fun clickActor(item: ActorFilmDetailInformation){
        startActivity(
            Intent(
                this,
                ActorDetailActivity::class.java
            ).apply {
                putExtra("id_actor", item.id.toString())
            })
    }

    private fun setInformation() {
        this.binding.activityAllActorsTitletv.text =  intent.getStringExtra("title").toString()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setObserver() {
        this.sharedViewModel.errorHandlingLiveData.observe(this) {
            Toast.makeText(this, "خطا در دریافت اطلاعات از سرور", Toast.LENGTH_LONG)
                .showCustomToast(
                    "خطا در دریافت اطلاعات از سرور",
                    this,
                    ToastStatus.ERROR
                )
        }
        this.sharedViewModel.getAllActorLiveData.observe(this){
            val list = ArrayList<ActorFilmDetailInformation>()
            for (item in it.results!!)
                list.add(ActorFilmDetailInformation(item!!.id, item.image_path,item.name,item.name_en))
            this.binding.activityAllActorsItemRvShimmer.stopShimmer()
            this.binding.activityAllActorsItemRvShimmer.visibility = View.GONE
            this.binding.activityAllActorsItemRv.visibility = View.VISIBLE
            val displayMetrics = this.resources.displayMetrics
            val dpWidth = displayMetrics.widthPixels / displayMetrics.density
            val size =  floor(dpWidth / 120).toInt()

            this.binding.activityAllActorsItemRv.layoutManager =
                GridLayoutManager(this, size, GridLayoutManager.VERTICAL, false)


            val adapter = ActorItemAllActorAdapter(this, list, object :
                ActorItemAllActorAdapter.EventListener {
                override fun click(item: ActorFilmDetailInformation) {
                    clickActor(item)
                }
            })
            binding.activityAllActorsItemRv.adapter = adapter
        }
        this.sharedViewModel.getAllDirectorLiveData.observe(this){
            val list = ArrayList<ActorFilmDetailInformation>()
            for (item in it.results!!)
                list.add(ActorFilmDetailInformation(item!!.id, item.image_path,item.name,item.name_en))
            this.binding.activityAllActorsItemRvShimmer.stopShimmer()
            this.binding.activityAllActorsItemRvShimmer.visibility = View.GONE
            this.binding.activityAllActorsItemRv.visibility = View.VISIBLE
            val displayMetrics = this.resources.displayMetrics
            val dpWidth = displayMetrics.widthPixels / displayMetrics.density
            val size =  floor(dpWidth / 120).toInt()
            this.binding.activityAllActorsItemRv.layoutManager =
                GridLayoutManager(this, size, GridLayoutManager.VERTICAL, false)

            val adapter = ActorItemAllActorAdapter(this, list, object :
                ActorItemAllActorAdapter.EventListener {
                override fun click(item: ActorFilmDetailInformation) {
                    clickActor(item)

                }
            })
            binding.activityAllActorsItemRv.adapter = adapter
        }


    }

    private fun setListener() {
        this.binding.activityAllActorsBackBtn.setOnClickListener {
            this.onBackPressed()

        }
    }

}