package com.farhanhp.myanimedb.pages.main.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.farhanhp.myanimedb.MainActivity
import com.farhanhp.myanimedb.MainActivityViewModel
import com.farhanhp.myanimedb.MainActivityViewModelFactory
import com.farhanhp.myanimedb.R
import com.farhanhp.myanimedb.classes.MainChildPage
import com.farhanhp.myanimedb.components.BottomBar
import com.farhanhp.myanimedb.databinding.PageHomeBinding
import com.farhanhp.myanimedb.pages.main.MainPage

class HomePage : MainChildPage() {
  private lateinit var binding: PageHomeBinding
  private lateinit var mainActivityParent: MainActivity
  private lateinit var mainActivityViewModel: MainActivityViewModel
  private lateinit var mainActivityViewModelFactory: MainActivityViewModelFactory
  private lateinit var viewModel: HomePageViewModel
  private lateinit var viewModelFactory: HomePageViewModelFactory
  private lateinit var animeRecyclerView: RecyclerView
  private lateinit var animeSkeletonRecyclerView: RecyclerView
  private lateinit var animeAdapter: AnimeAdapter
  private lateinit var animeSkeletonAdapter: AnimeSkeletonAdapter

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

    mainActivityParent = requireActivity() as MainActivity
    mainActivityViewModelFactory = MainActivityViewModelFactory(mainActivityParent.application)
    mainActivityViewModel = ViewModelProvider(mainActivityParent, mainActivityViewModelFactory).get(MainActivityViewModel::class.java)
    viewModelFactory = HomePageViewModelFactory(mainActivityViewModel.loginToken)
    viewModel = ViewModelProvider(this, viewModelFactory).get(HomePageViewModel::class.java)
    animeRecyclerView = binding.animeList
    animeAdapter = AnimeAdapter {
      viewModel.fetchAnime()
    }
    animeRecyclerView.adapter = animeAdapter
    viewModel.animeArr.observe(viewLifecycleOwner) {
      animeAdapter.data = it
    }
    animeSkeletonAdapter = AnimeSkeletonAdapter(HomePageViewModel.LIMIT)
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