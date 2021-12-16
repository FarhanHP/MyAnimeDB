package com.farhanhp.myanimedb.services.myanimedbApi

import com.farhanhp.myanimedb.datas.*
import retrofit2.Call
import retrofit2.http.*

interface MyAnimeDbApi {
  @POST("user/login/google")
  fun loginWithGoogle(@Body body: LoginWithGoogleBody): Call<LoginWithGoogleResponse>

  @GET("anime/{offset}/{limit}")
  fun getAnime(@Path("offset") offset: Int, @Path("limit") limit: Int, @Header("authorization") loginToken: String?, @Query("keyword") keyword: String? = null): Call<GetAnimeResponse>

  @GET("afterlogin/anime/favorite")
  fun getFavoriteAnime(@Header("authorization") loginToken: String, @Query("offset") offset: Int, @Query("limit") limit: Int): Call<GetAnimeResponse>

  @DELETE("afterlogin/anime/favorite/delete/{id}")
  fun deleteFavoriteAnime(@Header("authorization") loginToken: String, @Path("id") animeId: String): Call<Unit>

  @POST("afterlogin/anime/favorite/add/{id}")
  fun addFavoriteAnime(@Header("authorization") loginToken: String, @Path("id") animeId: String): Call<Anime>

  @POST("afterlogin/anime/score/add/{id}")
  fun addAnimeScore(@Header("authorization") loginToken: String, @Path("id") animeId: String, @Body body: AnimeScoreBody): Call<AnimeScoreResponse>

  @PUT("afterlogin/anime/score/update/{id}")
  fun updateAnimeScore(@Header("authorization") loginToken: String, @Path("id") animeId: String, @Body body: AnimeScoreBody): Call<AnimeScoreResponse>

  @GET("afterlogin/user/profile")
  fun getProfile(@Header("authorization") loginToken: String): Call<LoginUser>
}