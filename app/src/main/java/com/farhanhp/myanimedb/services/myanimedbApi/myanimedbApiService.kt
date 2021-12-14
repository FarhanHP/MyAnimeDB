package com.farhanhp.myanimedb.services.myanimedbApi

import android.util.Log
import com.farhanhp.myanimedb.datas.GetAnimeResponse
import com.farhanhp.myanimedb.datas.LoginUser
import com.farhanhp.myanimedb.datas.LoginWithGoogleBody
import com.farhanhp.myanimedb.datas.LoginWithGoogleResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.SocketTimeoutException

private const val BASE_URL = "https://myanimedb-api.herokuapp.com/api/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(
  BASE_URL
).build()

object MyAnimeDbApiService {
  private val retrofitService by lazy {
    retrofit.create(MyAnimeDbApi::class.java)
  }

  suspend fun loginWithGoogle(
    googleToken: String,
    timeoutCount: Int = 0,
  ): LoginWithGoogleResponse? {
    try {
      val response = retrofitService.loginWithGoogle(
        LoginWithGoogleBody(
          googleToken
        )
      ).awaitResponse()

      if(response.isSuccessful) {
        return response.body()
      }
    } catch (error: Throwable) {
      Log.e(TAG, error.message.toString())

      // resend request when heroku server is down due to idling for certain time
      if(error is SocketTimeoutException && timeoutCount < 2) {
        return loginWithGoogle(googleToken, timeoutCount + 1)
      }
    }

    return null
  }

  suspend fun getProfile(
    loginToken: String,
    timeoutCount: Int = 0,
  ): LoginUser? {
    try {
      val response = retrofitService.getProfile(loginToken).awaitResponse()
      if(response.isSuccessful) {
        return response.body()
      }
    } catch (error: Throwable) {
      Log.e(TAG, error.message.toString())

      // resend request when heroku server is down due to idling for certain time
      if(error is SocketTimeoutException && timeoutCount < 2) {
        return getProfile(loginToken, timeoutCount + 1)
      }
    }

    return null
  }

  suspend fun getAnime(
    offset: Int,
    limit: Int,
    loginToken: String?,
    timeoutCount: Int = 0
  ): GetAnimeResponse? {
    try {
      val response = retrofitService.getAnime(offset, limit, loginToken).awaitResponse()
      if(response.isSuccessful) {
        return response.body()
      }
    } catch (error: Throwable) {
      Log.e(TAG, error.message.toString())

      // resend request when heroku server is down due to idling for certain time
      if(error is SocketTimeoutException && timeoutCount < 2) {
        return getAnime(offset, limit, loginToken, timeoutCount + 1)
      }
    }

    return null
  }

  private const val TAG = "MyAnimeDbApiService"
}