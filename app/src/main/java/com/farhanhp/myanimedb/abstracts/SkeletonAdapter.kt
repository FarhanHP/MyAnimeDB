package com.farhanhp.myanimedb.abstracts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalArgumentException

abstract class SkeletonAdapter(
  skeletonCount: Int,
  private val viewHolderLayout: Int
): RecyclerView.Adapter<SkeletonAdapter.ViewHolder>() {
  private val data = mutableListOf<Int>()

  init {
    if(skeletonCount < 1) {
      throw IllegalArgumentException("skeletonCount must greater than 1")
    } else {
      for(i in 1..skeletonCount) {
        data.add(i)
      }
    }
  }

  override fun getItemCount() = data.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(viewHolderLayout, parent, false)
    return ViewHolder(view)
  }

  class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}