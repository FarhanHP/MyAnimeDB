package com.farhanhp.myanimedb.abstracts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farhanhp.myanimedb.datas.Anime
import com.farhanhp.myanimedb.datas.GetAnimeResponse
import kotlinx.coroutines.launch

abstract class AnimeViewModel: ViewModel() {
  abstract val fetchAnimeFromApi: suspend () -> GetAnimeResponse?
  protected var _isFetchingAnime = MutableLiveData(false)
  val isFetchingAnime: LiveData<Boolean>
    get() = _isFetchingAnime
  private var animeCount: Int = 1
  protected val _animeArr = MutableLiveData(listOf<Anime>())
  val animeArr: LiveData<List<Anime>>
    get() = _animeArr

  fun fetchAnime() {
    val animeArr = _animeArr.value as List<Anime>
    val isFetchingAnime = _isFetchingAnime.value as Boolean
    if(!isFetchingAnime && animeCount > animeArr.size) {
      _isFetchingAnime.value = true
      viewModelScope.launch {
        fetchAnimeFromApiAndUpdateData()
        _isFetchingAnime.value = false
      }
    }
  }

  protected suspend fun fetchAnimeFromApiAndUpdateData(){
    fetchAnimeFromApi().let {
      if(it != null) {
        animeCount = it.count
        addNewAnimeMembers(it.data)
      }
    }
  }

  private fun addNewAnimeMembers(newAnimeArrMembers: List<Anime>) {
    val newAnimeArr = _animeArr.value?.toMutableList()
    newAnimeArr?.addAll(newAnimeArrMembers)
    _animeArr.value = newAnimeArr
  }

  companion object {
    const val TAG = "AnimeViewModel"
    const val LIMIT = 20
  }
}