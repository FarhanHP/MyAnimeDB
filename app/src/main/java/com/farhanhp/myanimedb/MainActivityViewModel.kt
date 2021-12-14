package com.farhanhp.myanimedb

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farhanhp.myanimedb.datas.LoginUser
import com.farhanhp.myanimedb.services.myanimedbApi.MyAnimeDbApiService
import kotlinx.coroutines.launch

class MainActivityViewModel(
  private val application: Application
): ViewModel() {
  var loginToken: String? = null
    get() {
      if(field == null) {
        field = application.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).getString(
          LOGIN_TOKEN_KEY, null)
      }
      return field
    }
    set(value) {
      application.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit().putString(LOGIN_TOKEN_KEY, value).apply()
      field = value
    }

  var loginUser: LoginUser? = null
    private set

  fun checkLoginStatus(
    activeCallback: () -> Unit,
    notActiveCallback: () -> Unit,
  ){
    if(loginUser != null) {
      activeCallback()
    } else if(loginToken != null) {
      viewModelScope.launch {
        val loginUserRes = MyAnimeDbApiService.getProfile(loginToken as String)
        if(loginUserRes != null) {
          loginUser = loginUserRes
          activeCallback()
        } else {
          notActiveCallback()
        }
      }
    } else {
      notActiveCallback()
    }
  }

  fun loginWithGoogle(
    googleToken: String,
    successCallback: ()->Unit,
    failCallback: ()->Unit
  ) {
    viewModelScope.launch {
      val response = MyAnimeDbApiService.loginWithGoogle(googleToken)
      if(response != null) {
        loginToken = response.loginToken
        loginUser = response.data

        successCallback()
      } else {
        failCallback()
      }
    }
  }

  fun logout() {
    loginUser = null
    loginToken = null
  }

  companion object {
    const val SHARED_PREFERENCE_NAME = "MY-ANIME-DB"
    const val LOGIN_TOKEN_KEY = "LOGIN_TOKEN"
    const val TAG = "MainActivityViewModel"
  }
}