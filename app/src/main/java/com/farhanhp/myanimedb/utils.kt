package com.farhanhp.myanimedb

import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun loadImageToView(imageView: ImageView, imageUrl: String, fitCenter: Boolean = false) {
  val imageUri = imageUrl.toUri().buildUpon().scheme("https").build()
  val glide = Glide.with(imageView.context).load(imageUri)
  if(fitCenter) {
    val requestOption = RequestOptions()
    requestOption.centerCrop()
    glide.apply(requestOption)
  }

  glide.into(imageView)
}