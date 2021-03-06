package com.farhanhp.myanimedb.components

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.farhanhp.myanimedb.R
import com.farhanhp.myanimedb.enums.MainPageChildCode
import com.google.android.material.button.MaterialButton

class BottomBar(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
  private val homeButton: MaterialButton
  private val favoriteButton: MaterialButton
  private val accountButton: MaterialButton
  private val homeButtonIndicator: View
  private val favoriteButtonIndicator: View
  private val accountBtnIndicator: View

  init {
    LayoutInflater.from(context).inflate(R.layout.component_bottom_bar, this, true)

    homeButton = findViewById(R.id.homeBtn)
    favoriteButton = findViewById(R.id.favoriteBtn)
    accountButton = findViewById(R.id.accountBtn)
    homeButtonIndicator = findViewById(R.id.homeBtnIndicator)
    favoriteButtonIndicator= findViewById(R.id.favoriteBtnIndicator)
    accountBtnIndicator = findViewById(R.id.accountBtnIndicator)
  }

  fun setActiveButton(mainPageChildCode: MainPageChildCode) {
    when(mainPageChildCode) {
      MainPageChildCode.HOME -> {
        applyActiveButtonStyle(homeButtonIndicator, homeButton)
        applyPassiveButtonStyle(favoriteButtonIndicator, favoriteButton)
        applyPassiveButtonStyle(accountBtnIndicator, accountButton)
      }
      MainPageChildCode.FAVORITE -> {
        applyPassiveButtonStyle(homeButtonIndicator, homeButton)
        applyActiveButtonStyle(favoriteButtonIndicator, favoriteButton)
        applyPassiveButtonStyle(accountBtnIndicator, accountButton)
      }
      MainPageChildCode.PROFILE -> {
        applyPassiveButtonStyle(homeButtonIndicator, homeButton)
        applyPassiveButtonStyle(favoriteButtonIndicator, favoriteButton)
        applyActiveButtonStyle(accountBtnIndicator, accountButton)
      }
    }
  }

  private fun applyActiveButtonStyle(indicator: View, button: MaterialButton) {
    indicator.background = ContextCompat.getDrawable(context, R.color.purple)
    button.iconTint = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.purple))
  }

  private fun applyPassiveButtonStyle(indicator: View, button: MaterialButton) {
    indicator.background = ContextCompat.getDrawable(context, R.color.dark_purple)
    button.iconTint = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.purple_50p))
  }

  fun setButtonClickEvent(mainPageChildCode: MainPageChildCode, fn: ()->Unit) {
    val selectedButton = when(mainPageChildCode) {
      MainPageChildCode.HOME -> homeButton
      MainPageChildCode.PROFILE -> accountButton
      MainPageChildCode.FAVORITE -> favoriteButton
    }

    selectedButton.setOnClickListener{ fn() }
  }
}