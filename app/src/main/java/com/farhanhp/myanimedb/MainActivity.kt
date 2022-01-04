package com.farhanhp.myanimedb

import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.coroutineScope

class MainActivity : AppCompatActivity() {
  private lateinit var viewModelFactory: MainActivityViewModelFactory
  private lateinit var viewModel: MainActivityViewModel
  lateinit var oneTapClient: SignInClient
  private lateinit var signUpRequest: BeginSignInRequest
  private val REQ_ONE_TAP = 1  // Can be any integer unique to the Activity

  private var googleSignInSuccessCallback: (()->Unit)? = null
  private var googleSignInFailCallback: (()->Unit)? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    initGoogleSignIn()
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    viewModelFactory = MainActivityViewModelFactory(application, oneTapClient)
    viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
  }

  fun openYoutubePlayerActivity(youtubeId: String) {
    val intent = Intent(this, YoutubePlayerActivity::class.java).apply {
      putExtra(YoutubePlayerActivity.EXTRA_MESSAGE_VIDEO_ID, youtubeId)
    }
    startActivity(intent)
  }

  private fun initGoogleSignIn() {
    oneTapClient = Identity.getSignInClient(this)
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
  }

  fun startGoogleSignIn(successCallback: (()->Unit)?, failCallback: (()->Unit)?) {
    googleSignInFailCallback = failCallback
    googleSignInSuccessCallback = successCallback
    oneTapClient.beginSignIn(signUpRequest)
      .addOnSuccessListener(this) { result ->
        try {
          startIntentSenderForResult(
            result.pendingIntent.intentSender, REQ_ONE_TAP,
            null, 0, 0, 0)
        } catch (e: IntentSender.SendIntentException) {
          googleSignInFailCallback?.invoke()
          Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
        }
      }
      .addOnFailureListener(this) { e ->
        // No Google Accounts found. Just continue presenting the signed-out UI.
        googleSignInFailCallback?.invoke()
        Log.e(TAG, e.stackTraceToString())
      }
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
            googleSignInSuccessCallback?.invoke()
          }, {
            googleSignInFailCallback?.invoke()
          })
        }
        else -> {
          // Shouldn't happen.
          googleSignInFailCallback?.invoke()
          Log.d(TAG, "No ID token!")
        }
      }
    } catch (e: ApiException) {
      googleSignInFailCallback?.invoke()
      Log.d(TAG, e.message.toString())
    }
  }

  companion object {
    const val TAG = "MainActivity"
  }
}