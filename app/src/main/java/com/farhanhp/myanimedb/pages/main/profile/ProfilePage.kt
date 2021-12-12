package com.farhanhp.myanimedb.pages.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.farhanhp.myanimedb.R
import com.farhanhp.myanimedb.classes.MainChildPage
import com.farhanhp.myanimedb.components.BottomBar
import com.farhanhp.myanimedb.databinding.PageProfileBinding
import com.farhanhp.myanimedb.pages.main.MainPage

class ProfilePage : MainChildPage() {
  private lateinit var binding: PageProfileBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.page_profile, container, false)
    initBottomBar(
      ProfilePageDirections.actionProfilePageToHomePage(),
      ProfilePageDirections.actionProfilePageToFavoritePage(),
      null,
    )
    return binding.root
  }
}