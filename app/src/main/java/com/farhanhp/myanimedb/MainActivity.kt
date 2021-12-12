package com.farhanhp.myanimedb

import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.coroutineScope

class MainActivity : AppCompatActivity() {
  private lateinit var viewModelFactory: MainActivityViewModelFactory
  private lateinit var viewModel: MainActivityViewModel
  private lateinit var oneTapClient: SignInClient
  private lateinit var signUpRequest: BeginSignInRequest
  private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
  private var showOneTapUI = true

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    viewModelFactory = MainActivityViewModelFactory(application)
    viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)

    /*oneTapClient = Identity.getSignInClient(this)
    signUpRequest = BeginSignInRequest.builder()
      .setGoogleIdTokenRequestOptions(
        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
          .setSupported(true)
          // Your server's client ID, not your Android client ID.
          .setServerClientId(getString(R.string.web_client_id))
          // Show all accounts on the device.
          .setFilterByAuthorizedAccounts(false)
          .build())
      .build()

    oneTapClient.beginSignIn(signUpRequest)
      .addOnSuccessListener(this) { result ->
        try {
          startIntentSenderForResult(
            result.pendingIntent.intentSender, REQ_ONE_TAP,
            null, 0, 0, 0)
        } catch (e: IntentSender.SendIntentException) {
          Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
        }
      }
      .addOnFailureListener(this) { e ->
        // No Google Accounts found. Just continue presenting the signed-out UI.
        Log.d(TAG, e.localizedMessage)
      }*/
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    when (requestCode) {
      REQ_ONE_TAP -> {
        handleLogin(data)
      }
    }
  }

  private fun handleLogin(data: Intent?) {
    try {
      val credential = oneTapClient.getSignInCredentialFromIntent(data)
      val idToken = credential.googleIdToken
      when {
        idToken != null -> {
          viewModel.loginWithGoogle(idToken, {

          }, {

          })
        }
        else -> {
          // Shouldn't happen.
          Log.d(TAG, "No ID token!")
        }
      }
    } catch (e: ApiException) {
      Log.d(TAG, e.message.toString())
    }
  }

  companion object {
    const val TAG = "MainActivity"
  }
}