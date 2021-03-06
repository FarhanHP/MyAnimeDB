package com.farhanhp.myanimedb.pages.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.farhanhp.myanimedb.*
import com.farhanhp.myanimedb.abstracts.MainChildPage
import com.farhanhp.myanimedb.databinding.PageProfileBinding
import com.farhanhp.myanimedb.datas.LoginUser
import com.google.android.material.button.MaterialButton

class ProfilePage : MainChildPage() {
  private lateinit var binding: PageProfileBinding
  private lateinit var loginUser: LoginUser

  private lateinit var profileImage: ImageView
  private lateinit var fullnameTextView: TextView
  private lateinit var emailTextView: TextView
  private lateinit var logoutBtn: MaterialButton

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.page_profile, container, false)

    initBottomBar(
      ProfilePageDirections.actionProfilePageToHomePage(),
      ProfilePageDirections.actionProfilePageToFavoritePage(),
      null,
    )

    loginUser = mainActivityViewModel.loginUser as LoginUser

    initProfileImage()
    initFullnameTextView()
    initEmailTextView()
    initLogoutButton()

    return binding.root
  }

  private fun initProfileImage() {
    profileImage = binding.profileImage
    loadImageToView(profileImage, loginUser.imageUri)
  }

  private fun initFullnameTextView() {
    fullnameTextView = binding.fullname
    fullnameTextView.text = loginUser.fullname
  }

  private fun initEmailTextView() {
    emailTextView = binding.email
    emailTextView.text = loginUser.email
  }

  private fun initLogoutButton() {
    logoutBtn = binding.logoutBtn
    logoutBtn.setOnClickListener{
      mainActivityViewModel.logout()
      Toast.makeText(context, "Log out success", Toast.LENGTH_SHORT).show()
      redirectToHomePage()
    }
  }

  private fun redirectToHomePage() {
    mainPageParent.redirectToHome(findNavController(), ProfilePageDirections.actionProfilePageToHomePage())
  }

  companion object {
    const val TAG = "ProfilePage"
  }
}