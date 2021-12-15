package com.farhanhp.myanimedb.pages.main.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farhanhp.myanimedb.abstracts.AnimeViewModel
import com.farhanhp.myanimedb.datas.Anime
import com.farhanhp.myanimedb.datas.GetAnimeResponse
import com.farhanhp.myanimedb.services.myanimedbApi.MyAnimeDbApiService
import kotlinx.coroutines.launch

class FavoritePageViewModel(
  private val loginToken: String
): AnimeViewModel() {
  override val fetchAnimeFromApi = suspend {
    MyAnimeDbApiService.getFavoriteAnime(loginToken, _animeArr.value?.size as Int, LIMIT)
  }

  init {
    fetchAnime()
  }

  fun deleteFavoriteAnime(animeId: String) {
    _isFetchingAnime.value = true
    viewModelScope.launch {
      _animeArr.value = listOf()
      MyAnimeDbApiService.deleteFavoriteAnime(loginToken, animeId)
      fetchAnimeFromApiAndUpdateData()
      _isFetchingAnime.value = false
    }
  }

  companion object {
    const val TAG = "FavoritePageViewModel"
    const val LIMIT = 20
  }
}