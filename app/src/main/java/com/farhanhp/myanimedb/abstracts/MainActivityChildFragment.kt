package com.farhanhp.myanimedb.abstracts

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.farhanhp.myanimedb.MainActivity
import com.farhanhp.myanimedb.MainActivityViewModel
import com.farhanhp.myanimedb.MainActivityViewModelFactory

abstract class MainActivityChildFragment: Fragment() {
  protected lateinit var mainActivityParent: MainActivity
  private lateinit var mainActivityViewModelFactory: MainActivityViewModelFactory
  protected lateinit var mainActivityViewModel: MainActivityViewModel

  override fun onAttach(context: Context) {
    super.onAttach(context)

    mainActivityParent = requireActivity() as MainActivity
    mainActivityViewModelFactory = MainActivityViewModelFactory(mainActivityParent.application)
    mainActivityViewModel = ViewModelProvider(mainActivityParent, mainActivityViewModelFactory).get(MainActivityViewModel::class.java)
  }

  protected fun openYoutubeActivity(youtubeId: String) {
    mainActivityParent.openYoutubePlayerActivity(youtubeId)
  }
}