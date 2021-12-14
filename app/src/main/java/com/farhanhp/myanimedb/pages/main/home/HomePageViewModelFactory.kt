package com.farhanhp.myanimedb.pages.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class HomePageViewModelFactory(
  private val loginToken: String?
): ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if(modelClass.isAssignableFrom(HomePageViewModel::class.java)) {
      return HomePageViewModel(loginToken) as T
    }

    throw IllegalArgumentException("Unknown ViewModel class")
  }
}