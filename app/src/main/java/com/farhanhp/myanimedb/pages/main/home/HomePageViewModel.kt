package com.farhanhp.myanimedb.pages.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farhanhp.myanimedb.datas.Anime
import com.farhanhp.myanimedb.datas.LoginUser
import com.farhanhp.myanimedb.services.myanimedbApi.MyAnimeDbApiService
import kotlinx.coroutines.launch

class HomePageViewModel(
  private val loginToken: String?
): ViewModel() {
  private var _isFetchingAnime = MutableLiveData(false)
  val isFetchingAnime: LiveData<Boolean>
    get() = _isFetchingAnime
  private var animeCount: Int = 1
  private val _animeArr = MutableLiveData(listOf<Anime>())
  val animeArr: LiveData<List<Anime>>
    get() = _animeArr

  init {
    fetchAnime()
  }

  fun fetchAnime() {
    var animeArr = _animeArr.value as List<Anime>
    val isFetchingAnime = _isFetchingAnime.value as Boolean
    if(!isFetchingAnime && animeCount > animeArr.size) {
      _isFetchingAnime.value = true
      viewModelScope.launch {
        MyAnimeDbApiService.getAnime(animeArr.size, LIMIT, loginToken).let {
          if(it != null) {
            animeCount = it.count
            addNewAnimeMembers(it.data)
          }
        }
        _isFetchingAnime.value = false
      }
    }
  }

  private fun addNewAnimeMembers(newAnimeArrMembers: List<Anime>) {
    val newAnimeArr = _animeArr.value?.toMutableList()
    newAnimeArr?.addAll(newAnimeArrMembers)
    _animeArr.value = newAnimeArr
  }

  companion object {
    const val TAG = "HomePageViewModel"
    const val LIMIT = 20
  }
}