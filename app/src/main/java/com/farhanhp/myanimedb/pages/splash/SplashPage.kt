package com.farhanhp.myanimedb.pages.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.farhanhp.myanimedb.MainActivity
import com.farhanhp.myanimedb.MainActivityViewModel
import com.farhanhp.myanimedb.MainActivityViewModelFactory
import com.farhanhp.myanimedb.R

class SplashPage : Fragment() {
  private lateinit var mainActivityParent: MainActivity
  private lateinit var mainActivityViewModel: MainActivityViewModel
  private lateinit var mainActivityViewModelFactory: MainActivityViewModelFactory

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val view = inflater.inflate(R.layout.page_splash, container, false)
    mainActivityParent = requireActivity() as MainActivity
    mainActivityViewModelFactory = MainActivityViewModelFactory(mainActivityParent.application)
    mainActivityViewModel = ViewModelProvider(mainActivityParent, mainActivityViewModelFactory).get(MainActivityViewModel::class.java)
    mainActivityViewModel.checkLoginStatus({
      redirectToHomePage()
    }, {
      redirectToHomePage()
    })

    // Inflate the layout for this fragment
    return view
  }

  private fun redirectToHomePage() {
    findNavController().navigate(SplashPageDirections.actionSplashPageToMainPage())
  }
}