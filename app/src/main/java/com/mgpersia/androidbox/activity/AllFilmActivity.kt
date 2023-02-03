package com.mgpersia.androidbox.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.adapter.FilmItemAllFilmsAdapter
import com.mgpersia.androidbox.data.model.Image
import com.mgpersia.androidbox.databinding.ActivityAllFilmBinding
import com.mgpersia.androidbox.viewModel.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.floor

class AllFilmActivity: AppCompatActivity() {
    lateinit var binding: ActivityAllFilmBinding
    private val sharedViewModel: SharedViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_film)
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
        val list:java.util.ArrayList<Image> = intent.getParcelableArrayListExtra("films")!!
        this.binding.activityAllFilmsTitletv.text = intent.getStringExtra("title").toString()
        val displayMetrics = this.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        val size =  floor(dpWidth / 150).toInt()
        this.binding.activityAllFilmsItemRv.numColumns=size

        var adapter = FilmItemAllFilmsAdapter(this, list, object :
            FilmItemAllFilmsAdapter.EventListener {
            override fun click(item: Image) {
                onClickMovie(item)
            }

            override fun reload() {
                //TODO
            }


        })
        binding.activityAllFilmsItemRv.adapter = adapter


    }

    private fun setListener() {
        this.binding.activityAllFilmsBackBtn.setOnClickListener {
            this.onBackPressed()
        }
    }
}