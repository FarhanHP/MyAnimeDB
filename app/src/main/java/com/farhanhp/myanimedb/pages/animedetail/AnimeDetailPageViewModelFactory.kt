package com.farhanhp.myanimedb.pages.animedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.farhanhp.myanimedb.datas.Anime
import java.lang.IllegalArgumentException

class AnimeDetailPageViewModelFactory(
  private val anime: Anime,
  private val loginToken: String?
): ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if(modelClass.isAssignableFrom(AnimeDetailPageViewModel::class.java)) {
      return AnimeDetailPageViewModel(anime, loginToken) as T
    }

    throw IllegalArgumentException("Unknown ViewModel Class")
  }
}