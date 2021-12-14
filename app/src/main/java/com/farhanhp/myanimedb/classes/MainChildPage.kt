package com.farhanhp.myanimedb.classes

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.farhanhp.myanimedb.pages.main.MainPage

abstract class MainChildPage: Fragment() {
  private lateinit var mainPageParent: MainPage

  protected fun initBottomBar(
    homePageDirection: NavDirections?,
    favoritePageDirection: NavDirections?,
    profilePageDirection: NavDirections?,
  ) {
    mainPageParent = parentFragment?.parentFragment as MainPage
    mainPageParent.setBottomBar(findNavController(), homePageDirection, favoritePageDirection, profilePageDirection)
  }
}