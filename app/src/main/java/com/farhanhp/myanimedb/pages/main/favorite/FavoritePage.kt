package com.farhanhp.myanimedb.pages.main.favorite

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.farhanhp.myanimedb.R
import com.farhanhp.myanimedb.abstracts.MainChildPage
import com.farhanhp.myanimedb.adapters.HorizontalAnimeSkeletonAdapter
import com.farhanhp.myanimedb.databinding.PageFavoriteBinding

class FavoritePage : MainChildPage() {
  private lateinit var binding: PageFavoriteBinding
  private lateinit var viewModel: FavoritePageViewModel
  private lateinit var viewModelFactory: FavoritePageViewModelFactory
  private lateinit var favoriteAnimeAdapter: FavoriteAnimeAdapter
  private lateinit var horizontalAnimeSkeletonAdapter: HorizontalAnimeSkeletonAdapter
  private lateinit var favoriteAnimeRecyclerView: RecyclerView
  private lateinit var horizontalAnimeSkeletonRecyclerView: RecyclerView
  private lateinit var noFavoriteAnimeText: TextView

  override fun onAttach(context: Context) {
    super.onAttach(context)

    viewModelFactory = FavoritePageViewModelFactory(mainActivityViewModel.loginToken as String)
    viewModel = ViewModelProvider(this, viewModelFactory).get(FavoritePageViewModel::class.java)
    favoriteAnimeAdapter = FavoriteAnimeAdapter({
      viewModel.fetchAnime()
    }, {
      viewModel.deleteFavoriteAnime(it)
    })
    horizontalAnimeSkeletonAdapter = HorizontalAnimeSkeletonAdapter(FavoritePageViewModel.LIMIT)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.page_favorite, container, false)
    initBottomBar(
      FavoritePageDirections.actionFavoritePageToHomePage(),
      null,
      FavoritePageDirections.actionFavoritePageToProfilePage(),
    )

    noFavoriteAnimeText = binding.noFavoriteAnime
    favoriteAnimeRecyclerView = binding.favoriteAnimeList
    favoriteAnimeRecyclerView.adapter = favoriteAnimeAdapter
    horizontalAnimeSkeletonRecyclerView = binding.favoriteAnimeSkeletonList
    horizontalAnimeSkeletonRecyclerView.adapter = horizontalAnimeSkeletonAdapter

    viewModel.animeArr.observe(viewLifecycleOwner) {
      favoriteAnimeAdapter.data = it
    }

    viewModel.isFetchingAnime.observe(viewLifecycleOwner) {
      horizontalAnimeSkeletonRecyclerView.visibility = when(it) {
        true -> View.VISIBLE
        else -> View.GONE
      }

      noFavoriteAnimeText.visibility = if(viewModel.animeArr.value?.isEmpty() as Boolean && !it) {
        View.VISIBLE
      } else {
        View.GONE
      }
    }
    return binding.root
  }

  companion object {
    const val TAG = "FavoritePage"
  }
}