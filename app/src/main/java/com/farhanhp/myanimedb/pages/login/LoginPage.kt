package com.farhanhp.myanimedb.pages.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.farhanhp.myanimedb.MainActivity
import com.farhanhp.myanimedb.R
import com.farhanhp.myanimedb.abstracts.SecondaryPage
import com.farhanhp.myanimedb.components.SecondaryPageTopBar
import com.farhanhp.myanimedb.databinding.PageLoginBinding
import com.google.android.material.card.MaterialCardView

class LoginPage : SecondaryPage() {
  private lateinit var binding: PageLoginBinding
  private lateinit var topbar: SecondaryPageTopBar
  private lateinit var googleSignIn: MaterialCardView
  private lateinit var loadingDialog: View
  private lateinit var viewModel: LoginPageViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.page_login, container, false)
    viewModel = ViewModelProvider(this).get(LoginPageViewModel::class.java)
    topbar = binding.topbar
    topbar.setBackButtonClickListener {
      openPriorPage()
    }
    loadingDialog = binding.loadingDialog.root
    viewModel.loggingIn.observe(viewLifecycleOwner) {
      loadingDialog.visibility = when(it) {
        true -> View.VISIBLE
        else -> View.GONE
      }
    }
    googleSignIn = binding.signinGoogleButton
    googleSignIn.setOnClickListener{
      viewModel.setLoggingIn(true)
      mainActivityParent.startGoogleSignIn({
        findNavController().navigate(LoginPageDirections.actionLoginPageToMainPage())
        viewModel.setLoggingIn(false)
      }, {
        Toast.makeText(context, "Fail to login try again later", Toast.LENGTH_LONG).show()
        viewModel.setLoggingIn(false)
      })
    }

    return binding.root
  }
}