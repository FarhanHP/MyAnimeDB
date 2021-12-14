package com.farhanhp.myanimedb.pages.main.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.farhanhp.myanimedb.R
import com.farhanhp.myanimedb.abstracts.MainChildPage
import com.farhanhp.myanimedb.databinding.PageHomeBinding

class HomePage : MainChildPage() {
  private lateinit var binding: PageHomeBinding
  private lateinit var viewModel: HomePageViewModel
  private lateinit var viewModelFactory: HomePageViewModelFactory
  private lateinit var animeRecyclerView: RecyclerView
  private lateinit var animeSkeletonRecyclerView: RecyclerView
  private lateinit var animeAdapter: AnimeAdapter
  private lateinit var animeSkeletonAdapter: AnimeSkeletonAdapter

  override fun onAttach(context: Context) {
    super.onAttach(context)

    viewModelFactory = HomePageViewModelFactory(mainActivityViewModel.loginToken)
    viewModel = ViewModelProvider(this, viewModelFactory).get(HomePageViewModel::class.java)
    animeAdapter = AnimeAdapter {
      viewModel.fetchAnime()
    }
    animeSkeletonAdapter = AnimeSkeletonAdapter(HomePageViewModel.LIMIT)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = DataBindingUtil.inflate(inflater, R.layout.page_home, container, false)
    initBottomBar(
      null,
      HomePageDirections.actionHomePageToFavoritePage(),
      HomePageDirections.actionHomePageToProfilePage()
    )

    animeRecyclerView = binding.animeList
    animeRecyclerView.adapter = animeAdapter
    viewModel.animeArr.observe(viewLifecycleOwner) {
      animeAdapter.data = it
    }
    animeSkeletonRecyclerView = binding.animeSkeletonList
    animeSkeletonRecyclerView.adapter = animeSkeletonAdapter
    viewModel.isFetchingAnime.observe(viewLifecycleOwner) {
      animeSkeletonRecyclerView.visibility = when(it) {
        true -> View.VISIBLE
        else -> View.GONE
      }
    }

    return binding.root
  }

  companion object {
    const val TAG = "HomePage"
  }
}