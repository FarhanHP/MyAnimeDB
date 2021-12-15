package com.farhanhp.myanimedb.pages.animesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class AnimeSearchPageViewModelFactory(
  private val keyword: String,
  private val loginToken: String
): ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if(modelClass.isAssignableFrom(AnimeSearchPageViewModel::class.java)) {
      return AnimeSearchPageViewModel(keyword, loginToken) as T
    }

    throw IllegalArgumentException("Unknown ViewModel class")
  }
}