package com.farhanhp.myanimedb.pages.main.home

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
import com.farhanhp.myanimedb.databinding.PageHomeBinding
import com.farhanhp.myanimedb.pages.main.MainPage

class HomePage : MainChildPage() {
  private lateinit var binding: PageHomeBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = DataBindingUtil.inflate(inflater, R.layout.page_home, container, false)
    initBottomBar(
      null,
      HomePageDirections.actionHomePageToFavoritePage(),
      HomePageDirections.actionHomePageToProfilePage()
    )
    return binding.root
  }

}