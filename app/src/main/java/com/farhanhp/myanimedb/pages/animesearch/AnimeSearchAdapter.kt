package com.farhanhp.myanimedb.pages.animesearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.farhanhp.myanimedb.R
import com.farhanhp.myanimedb.components.HorizontalAnimeCard
import com.farhanhp.myanimedb.datas.Anime

class AnimeSearchAdapter(
  private val onBottomCallback: () -> Unit
): RecyclerView.Adapter<AnimeSearchAdapter.ViewHolder>() {
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
    data[position].let {
      holder.root.setAttribute(it.imageUrl, it.title, it.description, it.score)
    }

    if(data.size - 1 == position) {
      onBottomCallback()
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_anime_search, parent, false)
    return ViewHolder(view)
  }

  class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val root: HorizontalAnimeCard = itemView.findViewById(R.id.root)
  }
}