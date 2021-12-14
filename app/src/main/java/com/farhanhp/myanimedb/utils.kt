package com.farhanhp.myanimedb

import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.Glide

fun loadImageToView(imageView: ImageView, imageUrl: String) {
  val imageUri = imageUrl.toUri().buildUpon().scheme("https").build()
  Glide.with(imageView.context).load(imageUri).into(imageView)
}