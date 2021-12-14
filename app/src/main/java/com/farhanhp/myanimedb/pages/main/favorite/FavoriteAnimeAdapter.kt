package com.farhanhp.myanimedb.pages.main.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.farhanhp.myanimedb.R
import com.farhanhp.myanimedb.components.HorizontalAnimeCard
import com.farhanhp.myanimedb.datas.Anime
import com.google.android.material.button.MaterialButton

class FavoriteAnimeAdapter(
  private val onBottomCallback: () -> Unit,
  private val deleteFavoriteAnime: (animeId: String) -> Unit
): RecyclerView.Adapter<FavoriteAnimeAdapter.ViewHolder>() {
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

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    data[position].let {
      holder.animeCard.setAttribute(it.imageUrl, it.title, it.description, it.score)
      val animeId = it.id
      holder.deleteButton.setOnClickListener{
        deleteFavoriteAnime(animeId)
      }
    }

    if(data.size - 1 == position) {
      onBottomCallback()
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_favorite_anime, parent, false)
    return ViewHolder(view)
  }

  override fun getItemCount() = data.size

  class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val animeCard: HorizontalAnimeCard = itemView.findViewById(R.id.animeCard)
    val deleteButton: MaterialButton = itemView.findViewById(R.id.deleteButton)
  }
}