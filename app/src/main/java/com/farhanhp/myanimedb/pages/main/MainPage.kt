package com.farhanhp.myanimedb.pages.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.farhanhp.myanimedb.MainActivity
import com.farhanhp.myanimedb.MainActivityViewModel
import com.farhanhp.myanimedb.MainActivityViewModelFactory
import com.farhanhp.myanimedb.R
import com.farhanhp.myanimedb.abstracts.MainActivityChildFragment
import com.farhanhp.myanimedb.components.BottomBar
import com.farhanhp.myanimedb.databinding.PageMainBinding
import com.farhanhp.myanimedb.datas.Anime
import com.farhanhp.myanimedb.enums.MainPageChildCode
import com.farhanhp.myanimedb.pages.main.favorite.FavoritePage
import com.farhanhp.myanimedb.pages.main.home.HomePage
import com.farhanhp.myanimedb.pages.main.profile.ProfilePage

class MainPage : MainActivityChildFragment() {
  private lateinit var binding: PageMainBinding
  private lateinit var bottomBar: BottomBar
  private lateinit var viewModel: MainPageViewModel

  override fun onAttach(context: Context) {
    super.onAttach(context)

    viewModel = ViewModelProvider(this).get(MainPageViewModel::class.java)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.page_main, container, false)
    bottomBar = binding.bottomBar
    viewModel.currentChildPage.observe(viewLifecycleOwner) {
      bottomBar.setActiveButton(it)
    }
    return binding.root
  }

  fun setBottomBar(
    navController: NavController,
    homePageDirection: NavDirections?,
    favoritePageDirection: NavDirections?,
    profilePageDirection: NavDirections?,
  ) {
    val mainPageChildCodes = MainPageChildCode.values()
    val pageDirections = arrayOf(homePageDirection, favoritePageDirection, profilePageDirection)

    for(i in 0..2) {
      val mainPageChildCode = mainPageChildCodes[i]
      val pageDirection = pageDirections[i]
      when(mainPageChildCode) {
        MainPageChildCode.HOME -> {
          bottomBar.setButtonClickEvent(mainPageChildCode) {
            redirect(navController, pageDirection, mainPageChildCode)
          }
        }
        else -> {
          bottomBar.setButtonClickEvent(mainPageChildCode) {
            if(isAuthorized()) {
              redirect(navController, pageDirection, mainPageChildCode)
            } else {
              redirectToLoginPage()
            }
          }
        }
      }
    }
  }

  fun redirectToHome(navController: NavController, pageDirection: NavDirections?) {
    redirect(navController, pageDirection, MainPageChildCode.HOME)
  }

  private fun redirect(navController: NavController, pageDirection: NavDirections?, mainPageChildCode: MainPageChildCode) {
    if(pageDirection != null) {
      viewModel.setCurrentChildPage(mainPageChildCode)
      navController.navigate(pageDirection)
    }
  }

  private fun isAuthorized(): Boolean {
    val loginUser = mainActivityViewModel.loginUser
    return loginUser != null
  }

  private fun redirectToLoginPage() {
    findNavController().navigate(MainPageDirections.actionMainPageToLoginPage())
  }

  fun redirectToSearchAnimePage(keyword: String) {
    findNavController().navigate(MainPageDirections.actionMainPageToAnimeSearchPage(keyword))
  }

  fun redirectToAnimeDetailPage(anime: Anime) {
    mainActivityViewModel.setSelectedAnime(anime)
    findNavController().navigate(MainPageDirections.actionMainPageToAnimeDetailPage())
  }
}