package com.farhanhp.myanimedb.pages.animesearch

import com.farhanhp.myanimedb.abstracts.AnimeViewModel
import com.farhanhp.myanimedb.services.myanimedbApi.MyAnimeDbApiService

class AnimeSearchPageViewModel(
  private val keyword: String,
  private val loginToken: String?
): AnimeViewModel() {
  override val fetchAnimeFromApi = suspend {
    MyAnimeDbApiService.getAnime(_animeArr.value?.size as Int, LIMIT, loginToken, keyword)
  }

  init {
    fetchAnime()
  }
}