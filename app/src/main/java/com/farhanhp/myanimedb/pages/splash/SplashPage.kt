package com.farhanhp.myanimedb.pages.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.farhanhp.myanimedb.MainActivity
import com.farhanhp.myanimedb.MainActivityViewModel
import com.farhanhp.myanimedb.MainActivityViewModelFactory
import com.farhanhp.myanimedb.R
import com.farhanhp.myanimedb.abstracts.MainActivityChildFragment
import com.farhanhp.myanimedb.databinding.PageSplashBinding

class SplashPage : MainActivityChildFragment() {
  private lateinit var binding: PageSplashBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.page_splash, container, false)
    mainActivityViewModel.checkLoginStatus({
      redirectToHomePage()
    }, {
      redirectToHomePage()
    })

    // Inflate the layout for this fragment
    return binding.root
  }

  private fun redirectToHomePage() {
    findNavController().navigate(SplashPageDirections.actionSplashPageToMainPage())
  }
}