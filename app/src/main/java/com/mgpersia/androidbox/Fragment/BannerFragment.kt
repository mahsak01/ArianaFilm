package com.mgpersia.androidbox.Fragment

import EXTRA_KEY_DATA
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mgpersia.androidbox.R
import com.mgpersia.androidbox.activity.FilmDetailActivity
import com.mgpersia.androidbox.data.model.Image
import com.mgpersia.androidbox.service.ImageLoadingService
import org.koin.android.ext.android.inject

class BannerFragment(
    private val is_home_page: Boolean = false,
    private val image: Image,
    val eventListener: EventListener?
) :
    Fragment() {
    private val imageLoadingService: ImageLoadingService by inject()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (is_home_page) {
            val view: View = inflater.inflate(R.layout.layout_banner_home, container, false)
            val imageView: ImageView = view.findViewById(R.id.simpleDraweeView2) as ImageView
            (view.findViewById(R.id.layoutBannerHome_completeBtn) as TextView).setOnClickListener {
                startActivity(
                    Intent(
                        requireActivity(),
                        FilmDetailActivity::class.java
                    ).apply {
                        putExtra("id_film", image.film?.id.toString())
                    })
            }
            (view.findViewById(R.id.layoutBannerHome_trielrBtn) as TextView).setOnClickListener {
                startActivity(
                    Intent(
                        requireActivity(),
                        FilmDetailActivity::class.java
                    ).apply {
                        putExtra("id_film", image.film?.id.toString())
                    })
            }
            val image = requireArguments().getParcelable<Image>(EXTRA_KEY_DATA)
                ?: throw IllegalStateException("image not null")
            imageLoadingService.load(imageView, image.image!!)
            imageView.setOnFocusChangeListener { view, isFocused ->
                // add focus handling logic
                if(isFocused) {
                    imageView.setPadding(5,5,5,5)
                    imageView.background = requireContext().resources.getDrawable(R.drawable.background_select_android_tv)
                } else {
                    imageView.setPadding(5,5,5,5)
                    imageView.background = requireContext().resources.getDrawable(R.drawable.background_select_transparent_android_tv)
                }
            }
            (view.findViewById(R.id.layoutBannerHome_completeBtn) as TextView).setOnFocusChangeListener { view, isFocused ->
                // add focus handling logic
                if(isFocused) {
                    imageView.background = requireContext().resources.getDrawable(R.drawable.background_select_android_tv)
                } else {
                    imageView.background = requireContext().resources.getDrawable(R.drawable.background_red_button_setting)
                }
            }
            (view.findViewById(R.id.layoutBannerHome_trielrBtn) as TextView).setOnFocusChangeListener { view, isFocused ->
                // add focus handling logic
                if(isFocused) {
                    imageView.background = requireContext().resources.getDrawable(R.drawable.background_select_android_tv)
                } else {
                    imageView.background = requireContext().resources.getDrawable(R.drawable.background_border_white_button)
                }
            }

            return view

        } else {
            val view: View = inflater.inflate(R.layout.layout_banner, container, false)
            val imageView: ImageView = (view.findViewById(R.id.SimpleDraweeView3)) as ImageView
            imageView.setOnClickListener {
                if (eventListener != null)
                    eventListener.click()
            }

            val image = requireArguments().getParcelable<Image>(EXTRA_KEY_DATA)
                ?: throw IllegalStateException("image not null")
            imageLoadingService.load(imageView, image.image!!)

            return view
        }
    }

    companion object {
        fun newInstance(
            image: Image,
            is_home_page: Boolean = false,
            eventListener: EventListener?

        ): BannerFragment {
            val bannerFragment = BannerFragment(is_home_page, image, eventListener)
            bannerFragment.arguments = Bundle().apply {
                putParcelable(EXTRA_KEY_DATA, image)
            }
            return bannerFragment
        }
    }

    interface EventListener {
        fun click()
    }
}