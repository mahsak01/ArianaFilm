package com.mgpersia.androidbox.service

import android.widget.ImageView
import com.facebook.drawee.view.SimpleDraweeView

class FrescoLoadingServiceImplement: ImageLoadingService {
    override fun load(imageView: ImageView, imageUrl: String) {
        if (imageView is SimpleDraweeView)
            imageView.setImageURI(imageUrl)
        else
            throw  IllegalStateException("ImageView must be instance of SimpleDraweeView")
    }
}