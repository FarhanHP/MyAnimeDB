package com.farhanhp.myanimedb.pages.main.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farhanhp.myanimedb.datas.Anime
import com.farhanhp.myanimedb.services.myanimedbApi.MyAnimeDbApiService
import kotlinx.coroutines.launch

class FavoritePageViewModel(
  private val loginToken: String
): ViewModel() {
  private val _isFetchingFavoriteAnime = MutableLiveData(false)
  val isFetchingFavoriteAnime: LiveData<Boolean>
    get() = _isFetchingFavoriteAnime

  private val _favoriteAnimeArr = MutableLiveData(listOf<Anime>())
  val favoriteAnimeArr: LiveData<List<Anime>>
    get() = _favoriteAnimeArr

  private var favoriteAnimeCount = 1

  init {
    fetchFavoriteAnime()
  }

  fun fetchFavoriteAnime() {
    var animeArr = _favoriteAnimeArr.value as List<Anime>
    val isFetchingAnime = _isFetchingFavoriteAnime.value as Boolean
    if(!isFetchingAnime && favoriteAnimeCount > animeArr.size) {
      _isFetchingFavoriteAnime.value = true
      viewModelScope.launch {
        fetchFavoriteAnimeFromApi()
        _isFetchingFavoriteAnime.value = false
      }
    }
  }

  private fun addNewAnimeMembers(newAnimeArrMembers: List<Anime>) {
    val newAnimeArr = _favoriteAnimeArr.value?.toMutableList()
    newAnimeArr?.addAll(newAnimeArrMembers)
    _favoriteAnimeArr.value = newAnimeArr
  }

  fun deleteFavoriteAnime(animeId: String) {
    _isFetchingFavoriteAnime.value = true
    viewModelScope.launch {
      _favoriteAnimeArr.value = listOf()
      MyAnimeDbApiService.deleteFavoriteAnime(loginToken, animeId)
      fetchFavoriteAnimeFromApi()
      _isFetchingFavoriteAnime.value = false
    }
  }

  private suspend fun fetchFavoriteAnimeFromApi() {
    MyAnimeDbApiService.getFavoriteAnime(loginToken, _favoriteAnimeArr.value?.size as Int, LIMIT).let {
      if(it != null) {
        favoriteAnimeCount = it.count
        addNewAnimeMembers(it.data)
      }
    }
  }

  companion object {
    const val TAG = "FavoritePageViewModel"
    const val LIMIT = 20
  }
}