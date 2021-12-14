package com.farhanhp.myanimedb.pages.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginPageViewModel: ViewModel() {
  private val _loggingIn = MutableLiveData(false)
  val loggingIn: LiveData<Boolean>
    get() = _loggingIn

  fun setLoggingIn(value: Boolean) {
    _loggingIn.value = value
  }
}