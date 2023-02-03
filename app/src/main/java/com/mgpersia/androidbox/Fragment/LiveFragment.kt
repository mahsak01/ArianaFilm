package com.mgpersia.androidbox.Fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.mgpersia.androidbox.Fragment.bottomSheet.LogoutBottomSheetFragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.activity.LiveChannelActivity
import com.mgpersia.androidbox.adapter.BannerSliderAdapter
import com.mgpersia.androidbox.adapter.LiveItemChannelAdapter
import com.mgpersia.androidbox.data.model.Image
import com.mgpersia.androidbox.data.model.Live
import com.mgpersia.androidbox.databinding.FragmentLiveBinding
import com.mgpersia.androidbox.viewModel.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.streams.toList
import kotlin.system.exitProcess

class LiveFragment : Fragment() {

    private lateinit var binding: FragmentLiveBinding
    private val sharedViewModel: SharedViewModel by viewModel()
    private var isLogOut = 0
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        this.view?.isFocusableInTouchMode = true
        this.view?.requestFocus()
        sharedViewModel.getLive()
        setObserver()
        setListener()
        setInformation()
        setFocus()
        this.binding.fragmentLiveBackBtn.requestFocus()
    }
    private fun setFocus() {
        this.binding.fragmentLiveBackBtn.setOnFocusChangeListener { view, isFocused ->
            // add focus handling logic
            if (isFocused) {
                this.binding.fragmentLiveBackBtn.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_icon_android_tv
                    )
            } else {
                this.binding.fragmentLiveBackBtn.background =
                    requireContext().resources.getDrawable(
                        R.drawable.background_select_text_transparent_android_tv
                    )
            }
        }
    }

    private fun setInformation() {
        val handler = Handler()
        this.binding.fragmentBannerBannerSliderVp.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                handler.removeMessages(0)

                val runnable = Runnable {
                    binding.fragmentBannerBannerSliderVp.currentItem =
                        (position + 1) % binding.fragmentBannerBannerSliderVp.adapter!!.itemCount

                }
                handler.postDelayed(runnable, 10000)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setObserver() {
        this.sharedViewModel.getLiveLiveData.observe(this) {
            this.binding.fragmentLiveBannerSliderShimmer.stopShimmer()
            this.binding.fragmentLiveBannerSliderShimmer.visibility = View.GONE
            this.binding.fragmentBannerBannerSliderLl.visibility = View.VISIBLE
            this.binding.fragmentLiveItemRvShimmer.stopShimmer()
            this.binding.fragmentLiveItemRvShimmer.visibility = View.GONE
            this.binding.fragmentLiveItemRv.visibility = View.VISIBLE

            if (!it.results.isNullOrEmpty()) {
                val displayMetrics = this.resources.displayMetrics
                val dpWidth = displayMetrics.widthPixels / displayMetrics.density
                val imageSliderAdapter =
                    BannerSliderAdapter(this, it.results!!.stream().map { live ->
                        Image(live!!.id!!, live.baner!!,null,null)
                    }.toList())
                this.binding.fragmentBannerBannerSliderVp.adapter = imageSliderAdapter
                this.binding.fragmentBannerBannerSliderVp.post {

                    val layoutParams = this.binding.fragmentBannerBannerSliderVp.layoutParams
                    layoutParams.height =
                        if (dpWidth > 1000) (dpWidth * 0.4).toInt() else (dpWidth*1.3).toInt()
                    this.binding.fragmentBannerBannerSliderVp.layoutParams = layoutParams
                    this.binding.fragmentBannerBannerSliderDi.setViewPager2(this.binding.fragmentBannerBannerSliderVp)
                }

                this.binding.fragmentLiveItemRv.layoutManager =
                    GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)

                var adapter = LiveItemChannelAdapter(it.results as List<Live>,true, object :
                    LiveItemChannelAdapter.EventListener {
                    override fun click(live: Live) {
                        startActivity(
                            Intent(
                                requireActivity(),
                                LiveChannelActivity::class.java
                            ).apply {
                                putExtra(
                                    "live",
                                    live
                                )
                                putExtra(
                                    "lives",
                                    it.results as ArrayList<Live>

                                )
                            })
                    }
                }, requireContext())
                binding.fragmentLiveItemRv.adapter = adapter

            }
            else{
                this.binding.fragmentLiveItemRv.visibility = View.GONE
                this.binding.fragmentBannerBannerSliderLl.visibility = View.GONE

            }

        }
    }

    private fun setListener() {
        this.binding.fragmentLiveBackBtn.setOnClickListener {
            this.requireActivity().onBackPressed()
        }
        this.binding.root.setOnKeyListener { _, p1, _ ->
            if (p1 != KeyEvent.ACTION_DOWN) {
                if (isLogOut==0){
                    val bottomSheetDialog = LogoutBottomSheetFragment(object :LogoutBottomSheetFragment.EventListener{
                        override fun close() {
                            activity!!.finish()
                            exitProcess(0)
                        }

                    })
                    bottomSheetDialog.show(childFragmentManager, null)
                    isLogOut++
                }else{
                    isLogOut=0
                }

            }
            true
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLiveBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}