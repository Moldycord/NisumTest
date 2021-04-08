package com.example.nisumtest.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImageWithGlide(imageUrl: String) {
    Glide.with(this).load(imageUrl).into(this)
}