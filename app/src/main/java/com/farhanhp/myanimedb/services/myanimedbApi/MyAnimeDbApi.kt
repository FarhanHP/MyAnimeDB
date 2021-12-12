package com.farhanhp.myanimedb.services.myanimedbApi

import com.farhanhp.myanimedb.datas.LoginUser
import com.farhanhp.myanimedb.datas.LoginWithGoogleBody
import com.farhanhp.myanimedb.datas.LoginWithGoogleResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MyAnimeDbApi {
  @POST("user/login/google")
  fun loginWithGoogle(@Body body: LoginWithGoogleBody): Call<LoginWithGoogleResponse>

  @GET("afterlogin/user/profile")
  fun getProfile(@Header("authorization") loginToken: String): Call<LoginUser>
}