package com.farhanhp.myanimedb.pages.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farhanhp.myanimedb.enums.MainPageChildCode

class MainPageViewModel: ViewModel() {
  private val _currentChildPage = MutableLiveData(MainPageChildCode.HOME)
  val currentChildPage: LiveData<MainPageChildCode>
    get() = _currentChildPage

  fun setCurrentChildPage(mainPageChildCode: MainPageChildCode) {
    _currentChildPage.value = mainPageChildCode
  }
}