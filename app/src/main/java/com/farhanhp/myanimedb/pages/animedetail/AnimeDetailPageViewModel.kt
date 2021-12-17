package com.farhanhp.myanimedb.pages.animedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farhanhp.myanimedb.datas.Anime
import com.farhanhp.myanimedb.services.myanimedbApi.MyAnimeDbApiService
import kotlinx.coroutines.launch

class AnimeDetailPageViewModel(
  private val anime: Anime,
  val loginToken: String?
): ViewModel() {
  private val _selectedAnime = MutableLiveData(anime)
  private val _favoriteLoading = MutableLiveData(false)
  private val _animeScoreLoading = MutableLiveData(false)
  private val _openScoreDialog = MutableLiveData(false)

  val selectedAnime: LiveData<Anime>
    get() = _selectedAnime
  val favoriteLoading: LiveData<Boolean>
    get() = _favoriteLoading
  val animeScoreLoading: LiveData<Boolean>
    get() = _animeScoreLoading
  val openScoreDialog: LiveData<Boolean>
    get() = _openScoreDialog

  fun addFavoriteAnime(
    successCallback: ()->Unit,
    failCallback: ()->Unit
  ) {
    if(loginToken != null) {
      _favoriteLoading.value = true
      viewModelScope.launch {
        val newAnime = MyAnimeDbApiService.addFavoriteAnime(loginToken, _selectedAnime.value?.id as String)

        if(newAnime != null) {
          _selectedAnime.value = newAnime
          syncAnimeSource(newAnime.isFavorite, newAnime.score, newAnime.userCount)
          successCallback()
        } else {
          failCallback()
        }

        _favoriteLoading.value = false
      }
    } else {
      failCallback()
    }
  }

  fun removeFavoriteAnime(
    successCallback: () -> Unit,
    failCallback: () -> Unit
  ) {
    if(loginToken != null) {
      _favoriteLoading.value = true
      viewModelScope.launch {
        MyAnimeDbApiService.deleteFavoriteAnime(loginToken, _selectedAnime.value?.id as String)
        val newAnime = _selectedAnime.value as Anime
        newAnime.isFavorite = false
        _selectedAnime.value = newAnime
        syncAnimeSource(newAnime.isFavorite, newAnime.score, newAnime.userCount)
        successCallback()

        _favoriteLoading.value = false
      }
    } else {
      failCallback()
    }
  }

  fun addAnimeScore(
    score: Int,
    successCallback: () -> Unit,
    failCallback: () -> Unit
  ) {
    if(loginToken != null) {
      _animeScoreLoading.value = true
      viewModelScope.launch {
        val response = MyAnimeDbApiService.addAnimeScore(loginToken, _selectedAnime.value?.id as String, score)

        if(response != null) {
          response.let {
            val newAnime = _selectedAnime.value as Anime
            newAnime.scoreGiven = score
            newAnime.score = it.newAverageScore
            newAnime.userCount = it.newUserCount
            _selectedAnime.value = newAnime
            syncAnimeSource(newAnime.isFavorite, newAnime.score, newAnime.userCount)
            successCallback()
          }
        } else {
          failCallback()
        }

        _animeScoreLoading.value = false
      }
    } else {
      failCallback()
    }
  }

  fun updateAnimeScore(
    newScore: Int,
    successCallback: () -> Unit,
    failCallback: () -> Unit
  ) {
    if(loginToken != null) {
      _animeScoreLoading.value = true
      viewModelScope.launch {
        val response = MyAnimeDbApiService.updateAnimeScore(loginToken, _selectedAnime.value?.id as String, newScore)

        if(response != null) {
          response.let {
            val newAnime = _selectedAnime.value as Anime
            newAnime.scoreGiven = newScore
            newAnime.score = it.newAverageScore
            newAnime.userCount = it.newUserCount
            syncAnimeSource(newAnime.isFavorite, newAnime.score, newAnime.userCount)
            _selectedAnime.value = newAnime
            successCallback()
          }
        } else {
          failCallback()
        }

        _animeScoreLoading.value = false
      }
    } else {
      failCallback()
    }
  }

  fun openScoreAnimeDialog() {
    _openScoreDialog.value = true
  }

  fun closeScoreAnimeDialog() {
    _openScoreDialog.value = false
  }


  // desperate attempt to sync anime data from prior page
  private fun syncAnimeSource(isFavorite: Boolean, averageScore: Double, userCount: Int) {
    anime.isFavorite = isFavorite
    anime.score = averageScore
    anime.userCount = userCount
  }
}