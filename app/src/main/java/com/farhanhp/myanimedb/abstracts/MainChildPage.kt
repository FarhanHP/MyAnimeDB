package com.farhanhp.myanimedb.abstracts

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.farhanhp.myanimedb.MainActivity
import com.farhanhp.myanimedb.MainActivityViewModel
import com.farhanhp.myanimedb.MainActivityViewModelFactory
import com.farhanhp.myanimedb.pages.main.MainPage

abstract class MainChildPage: MainActivityChildFragment() {
  protected lateinit var mainPageParent: MainPage

  override fun onAttach(context: Context) {
    super.onAttach(context)

    mainPageParent = parentFragment?.parentFragment as MainPage
  }

  protected fun initBottomBar(
    homePageDirection: NavDirections?,
    favoritePageDirection: NavDirections?,
    profilePageDirection: NavDirections?,
  ) {
    mainPageParent.setBottomBar(findNavController(), homePageDirection, favoritePageDirection, profilePageDirection)
  }
}