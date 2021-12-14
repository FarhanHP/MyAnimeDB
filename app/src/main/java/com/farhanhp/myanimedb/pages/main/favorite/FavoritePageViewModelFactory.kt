package com.farhanhp.myanimedb.pages.main.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class FavoritePageViewModelFactory(
  private val loginToken: String
): ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if(modelClass.isAssignableFrom(FavoritePageViewModel::class.java)) {
      return FavoritePageViewModel(loginToken) as T
    }

    throw IllegalArgumentException("Unknown ViewModel class")
  }
}