package com.mgpersia.androidbox.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mgpersia.androidbox.Fragment.LoadingFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.adapter.FollowingItemSettingAdapter
import com.mgpersia.androidbox.data.model.Following
import com.mgpersia.androidbox.databinding.ActivityFollowingSettingBinding
import com.mgpersia.androidbox.viewModel.UserSettingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowingSettingActivity : AppCompatActivity() {


    private lateinit var binding: ActivityFollowingSettingBinding
    private val userSettingViewModel: UserSettingViewModel by viewModel()
    private var loadingFragment: LoadingFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_following_setting)
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
        userSettingViewModel.getAllFollowing()
        setObserver()
        setListener()

    }

    private fun setListener() {
        this.binding.ActivityFollowingSettingBackBtn.setOnClickListener {
            this.onBackPressed()
        }
    }

    private fun clickActor(following: Following) {
        startActivity(
            Intent(
                this,
                ActorDetailActivity::class.java
            ).apply {
                putExtra("id_actor", following.id.toString())
            })
    }

    private fun setObserver() {
        userSettingViewModel.getAllFollowingLiveData.observe(this) {
            if (!it.results!!.isNullOrEmpty()) {
                this.binding.ActivityFollowingSettingEmptyLayout.root.visibility = View.GONE
                this.binding.ActivityFollowingSettingFollowRv.visibility = View.VISIBLE
                binding.ActivityFollowingSettingFollowRv.layoutManager =
                    LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                val adapter = FollowingItemSettingAdapter(
                    it.results as List<Following>,
                    this,
                    object : FollowingItemSettingAdapter.EventListener {
                        override fun click(following: Following) {
                            clickActor(following)
                        }

                    })
                binding.ActivityFollowingSettingFollowRv.adapter = adapter
                if (loadingFragment != null && loadingFragment!!.showsDialog)
                    loadingFragment!!.dismiss()
            } else {
                this.binding.ActivityFollowingSettingEmptyLayout.root.visibility = View.VISIBLE
                this.binding.ActivityFollowingSettingFollowRv.visibility = View.GONE
                if (loadingFragment != null && loadingFragment!!.showsDialog)
                    loadingFragment!!.dismiss()

            }

        }
    }
}