package com.mgpersia.androidbox.service

import android.widget.ImageView


interface ImageLoadingService {
    fun  load(imageView: ImageView, imageUrl:String)
}