package com.farhanhp.myanimedb.pages.main.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.farhanhp.myanimedb.R
import com.farhanhp.myanimedb.classes.MainChildPage
import com.farhanhp.myanimedb.components.BottomBar
import com.farhanhp.myanimedb.databinding.PageFavoriteBinding

class FavoritePage : MainChildPage() {
  private lateinit var binding: PageFavoriteBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.page_favorite, container, false)
    initBottomBar(
      FavoritePageDirections.actionFavoritePageToHomePage(),
      null,
      FavoritePageDirections.actionFavoritePageToProfilePage(),
    )
    return binding.root
  }
}