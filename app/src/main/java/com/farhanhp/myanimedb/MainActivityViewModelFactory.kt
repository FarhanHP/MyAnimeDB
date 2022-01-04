package com.farhanhp.myanimedb

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.identity.SignInClient

class MainActivityViewModelFactory(
  private val application: Application,
  private val oneTapClient: SignInClient,
): ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if(modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
      return MainActivityViewModel(application, oneTapClient) as T
    }

    throw IllegalArgumentException("Unknown ViewModel class")
  }
}