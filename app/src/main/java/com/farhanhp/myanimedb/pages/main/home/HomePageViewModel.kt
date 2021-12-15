package com.farhanhp.myanimedb.pages.main.home
import com.farhanhp.myanimedb.abstracts.AnimeViewModel
import com.farhanhp.myanimedb.services.myanimedbApi.MyAnimeDbApiService

class HomePageViewModel(loginToken: String?): AnimeViewModel() {
  override val fetchAnimeFromApi = suspend {
    MyAnimeDbApiService.getAnime(animeArr.value?.size as Int, LIMIT, loginToken)
  }

  init {
    fetchAnime()
  }
}