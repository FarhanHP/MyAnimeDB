package com.farhanhp.myanimedb.pages.main.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.farhanhp.myanimedb.R
import com.farhanhp.myanimedb.datas.Anime
import com.farhanhp.myanimedb.loadImageToView

class AnimeAdapter(
  private val onBottomCallback: ()->Unit
): RecyclerView.Adapter<AnimeAdapter.ViewHolder>() {
  var data = listOf<Anime>()
    set(value) {
      val oldField = field
      field = value
      if(oldField.size <= value.size) {
        notifyItemRangeInserted(oldField.size, value.size)
      } else {
        notifyDataSetChanged()
      }
    }

  override fun getItemCount() = data.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val currentData = data[position]
    loadImageToView(holder.imageView, currentData.imageUrl)
    holder.titleTextView.text = currentData.title
    holder.scoreTextView.text = currentData.score.toString()

    if(data.size - 1 == position) {
      onBottomCallback()
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_anime, parent, false)
    return ViewHolder(itemView)
  }

  class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val imageView: ImageView = itemView.findViewById(R.id.image)
    val titleTextView: TextView = itemView.findViewById(R.id.title)
    val scoreTextView: TextView = itemView.findViewById(R.id.score)
  }

  companion object {
    const val TAG = "AnimeAdapter"
  }
}