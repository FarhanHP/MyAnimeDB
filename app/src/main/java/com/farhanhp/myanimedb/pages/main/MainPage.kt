package com.farhanhp.myanimedb.pages.main

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
import com.farhanhp.myanimedb.components.BottomBar
import com.farhanhp.myanimedb.databinding.PageMainBinding
import com.farhanhp.myanimedb.pages.main.favorite.FavoritePage
import com.farhanhp.myanimedb.pages.main.home.HomePage
import com.farhanhp.myanimedb.pages.main.profile.ProfilePage

class MainPage : Fragment() {
  private lateinit var binding: PageMainBinding
  private lateinit var bottomBar: BottomBar
  private lateinit var mainActivityParent: MainActivity
  private lateinit var mainActivityViewModel: MainActivityViewModel
  private lateinit var mainActivityViewModelFactory: MainActivityViewModelFactory

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.page_main, container, false)
    mainActivityParent = requireActivity() as MainActivity
    mainActivityViewModelFactory = MainActivityViewModelFactory(mainActivityParent.application)
    mainActivityViewModel = ViewModelProvider(mainActivityParent, mainActivityViewModelFactory).get(MainActivityViewModel::class.java)

    initBottomBar()
    return binding.root
  }

  private fun initBottomBar() {
    bottomBar = binding.bottomBar
    bottomBar.setActiveButton(BottomBar.Companion.Button.HOME)
  }

  fun setBottomBar(
    navController: NavController,
    homePageDirection: NavDirections?,
    favoritePageDirection: NavDirections?,
    profilePageDirection: NavDirections?,
  ) {
    val buttonCodes = BottomBar.Companion.Button.values()
    val pageDirections = arrayOf(homePageDirection, favoritePageDirection, profilePageDirection)

    for(i in 0..2) {
      val buttonCode = buttonCodes[i]
      val pageDirection = pageDirections[i]
      when(buttonCode) {
        BottomBar.Companion.Button.HOME -> {
          bottomBar.setButtonClickEvent(buttonCode) {
            redirect(navController, pageDirection, buttonCode)
          }
        }
        else -> {
          bottomBar.setButtonClickEvent(buttonCode) {
            if(isAuthorized()) {
              redirect(navController, pageDirection, buttonCode)
            } else {
              redirectToLoginPage()
            }
          }
        }
      }
    }
  }

  fun redirectToHome(navController: NavController, pageDirection: NavDirections?) {
    redirect(navController, pageDirection, BottomBar.Companion.Button.HOME)
  }

  private fun redirect(navController: NavController, pageDirection: NavDirections?, buttonCode: BottomBar.Companion.Button) {
    if(pageDirection != null) {
      bottomBar.setActiveButton(buttonCode)
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
}