package com.mgpersia.androidbox.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mgpersia.androidbox.data.model.Image
import com.mgpersia.androidbox.Fragment.BannerFragment

class BannerSliderAdapter(
    fragment: Fragment,
    val images: List<Image>,
    private val is_home_page: Boolean = false,
    private val eventListener: BannerFragment.EventListener? = null
) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = images.size

    override fun createFragment(position: Int): Fragment =
        BannerFragment.newInstance(images[position], is_home_page, eventListener)
}