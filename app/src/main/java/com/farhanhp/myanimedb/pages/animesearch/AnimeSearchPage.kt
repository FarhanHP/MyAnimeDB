package com.farhanhp.myanimedb.pages.animesearch

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.farhanhp.myanimedb.R
import com.farhanhp.myanimedb.abstracts.AnimeViewModel
import com.farhanhp.myanimedb.abstracts.SecondaryPage
import com.farhanhp.myanimedb.adapters.HorizontalAnimeSkeletonAdapter
import com.farhanhp.myanimedb.components.SecondaryPageTopBar
import com.farhanhp.myanimedb.databinding.PageAnimeSearchBinding

class AnimeSearchPage : SecondaryPage() {
  private lateinit var binding: PageAnimeSearchBinding
  private lateinit var noAnimeTextView: TextView
  private lateinit var topbar: SecondaryPageTopBar
  private lateinit var keywordTextView: TextView
  private lateinit var animeList: RecyclerView
  private lateinit var skeletonAnimeList: RecyclerView
  private lateinit var keyword: String
  private lateinit var viewModel: AnimeSearchPageViewModel
  private lateinit var viewModelFactory: AnimeSearchPageViewModelFactory
  private val horizontalAnimeSkeletonAdapter = HorizontalAnimeSkeletonAdapter(AnimeViewModel.LIMIT)
  private lateinit var animeSearchAdapter: AnimeSearchAdapter

  override fun onAttach(context: Context) {
    super.onAttach(context)

    keyword = AnimeSearchPageArgs.fromBundle(requireArguments()).keyword
    viewModelFactory = AnimeSearchPageViewModelFactory(keyword, mainActivityViewModel.loginToken as String)
    viewModel = ViewModelProvider(this, viewModelFactory).get(AnimeSearchPageViewModel::class.java)
    animeSearchAdapter = AnimeSearchAdapter {
      viewModel.fetchAnime()
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.page_anime_search, container, false)
    noAnimeTextView = binding.noAnimeTextView
    topbar = binding.topbar
    topbar.setBackButtonClickListener {
      openPriorPage()
    }
    keywordTextView = binding.keywordTextview
    keywordTextView.text = "Keyword: \"${keyword}\""

    animeList = binding.animeList
    animeList.adapter = animeSearchAdapter

    viewModel.animeArr.observe(viewLifecycleOwner) {
      animeSearchAdapter.data = it
    }

    skeletonAnimeList = binding.skeletonAnimeList
    skeletonAnimeList.adapter = horizontalAnimeSkeletonAdapter

    viewModel.isFetchingAnime.observe(viewLifecycleOwner) {
      skeletonAnimeList.visibility = when(it) {
        true -> View.VISIBLE
        else -> View.GONE
      }

      noAnimeTextView.visibility = if(viewModel.animeArr.value?.isEmpty() as Boolean && !it) {
        View.VISIBLE
      } else {
        View.GONE
      }
    }

    return binding.root
  }
}