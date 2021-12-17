package com.farhanhp.myanimedb.pages.animedetail

import android.content.Context
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginTop
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.farhanhp.myanimedb.R
import com.farhanhp.myanimedb.abstracts.SecondaryPage
import com.farhanhp.myanimedb.components.AnimeScoreDialog
import com.farhanhp.myanimedb.components.Chip
import com.farhanhp.myanimedb.components.SecondaryPageTopBar
import com.farhanhp.myanimedb.databinding.PageAnimeDetailBinding
import com.farhanhp.myanimedb.datas.Anime
import com.farhanhp.myanimedb.loadImageToView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class AnimeDetailPage : SecondaryPage() {
  private lateinit var binding: PageAnimeDetailBinding
  private lateinit var topBar: SecondaryPageTopBar
  private lateinit var image: ImageView
  private lateinit var title: TextView
  private lateinit var episodes: TextView
  private lateinit var userCount: TextView
  private lateinit var score: Chip
  private lateinit var description: TextView
  private lateinit var card: MaterialCardView
  private lateinit var favoriteBtn: MaterialButton
  private lateinit var scoreBtn: MaterialButton
  private lateinit var viewModel: AnimeDetailPageViewModel
  private lateinit var viewModelFactory: AnimeDetailPageViewModelFactory
  private lateinit var loadingDialog: View
  private lateinit var animeScoreDialog: AnimeScoreDialog
  private lateinit var youtubeThumbnail: ImageView
  private lateinit var youtubeThumbnailContainer: MaterialCardView
  private lateinit var noVideoText: TextView
  private lateinit var playButtonContainer: LinearLayout
  private lateinit var playButton: MaterialCardView

  override fun onAttach(context: Context) {
    super.onAttach(context)

    viewModelFactory = AnimeDetailPageViewModelFactory(mainActivityViewModel.selectedAnime as Anime, mainActivityViewModel.loginToken)
    viewModel = ViewModelProvider(this, viewModelFactory).get(AnimeDetailPageViewModel::class.java)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.page_anime_detail, container, false)
    topBar = binding.topbar
    topBar.setBackButtonClickListener {
      openPriorPage()
    }
    image = binding.image
    title = binding.titleTextview
    episodes = binding.episodes
    userCount = binding.users
    score = binding.score
    description = binding.description
    card = binding.card
    favoriteBtn = binding.favoriteBtn
    scoreBtn = binding.scoreBtn
    loadingDialog = binding.loadingDialog.root
    animeScoreDialog = binding.scoreDialog
    youtubeThumbnail = binding.youtubeThumbnail
    youtubeThumbnailContainer = binding.youtubeThumbnailContainer
    noVideoText = binding.noVideoText
    playButton = binding.playButton
    playButtonContainer = binding.playButtonContainer

    animeScoreDialog.setOnCancel {
      viewModel.closeScoreAnimeDialog()
    }

    animeScoreDialog.setOnSubmit { score, isUpdate ->
      when(isUpdate) {
        true -> viewModel.updateAnimeScore(score, {onUpdateAnimeScoreSuccess()}, {onUpdateAnimeScoreFail()})
        else -> viewModel.addAnimeScore(score, {onAddAnimeScoreSuccess()}, {onAddAnimeScoreFail()})
      }
    }

    viewModel.animeScoreLoading.observe(viewLifecycleOwner) {
      animeScoreDialog.setLoading(it)
    }

    viewModel.selectedAnime.observe(viewLifecycleOwner) {
      loadImageToView(image, it.imageUrl, true)
      topBar.setTitle(it.title)
      title.text = it.title
      episodes.text = "${when(it.episodeCount){
        null -> 0
        else -> it.episodeCount
      }} Episodes"
      userCount.text = "${it.userCount} Users"
      score.setText(it.score.toString())
      description.text = it.description
      animeScoreDialog.setType(it.scoreGiven != -1)
    }

    viewModel.selectedAnime.observe(viewLifecycleOwner) {
      if(it.youtubeVideoId != null) {
        playButtonContainer.visibility = View.VISIBLE
        val anime = it
        playButton.setOnClickListener{
          openYoutubeActivity(anime.youtubeVideoId as String)
        }
        noVideoText.visibility = View.GONE
        loadImageToView(youtubeThumbnail, "https://img.youtube.com/vi/${it.youtubeVideoId}/mqdefault.jpg", true)
      } else {
        playButtonContainer.visibility = View.GONE
        noVideoText.visibility = View.VISIBLE
      }
    }

    viewModel.selectedAnime.observe(viewLifecycleOwner) {
      val anime = it
      favoriteBtn.text = when(it.isFavorite) {
        true -> "Remove Favorite"
        else -> "Add Favorite"
      }
      scoreBtn.text = when(it.scoreGiven) {
        -1 -> "Add Score"
        else -> "Edit Score"
      }

      favoriteBtn.setOnClickListener{
        favoriteBtnClickHandler(anime)
      }

      scoreBtn.setOnClickListener{
        scoreBtnClickHandler()
      }
    }

    viewModel.selectedAnime.observe(viewLifecycleOwner) {
      when(it.scoreGiven) {
        -1 -> {
          animeScoreDialog.setType(false)
          animeScoreDialog.setScore(1)
        } else -> {
          animeScoreDialog.setType(true)
          animeScoreDialog.setScore(it.scoreGiven)
        }
      }
    }

    viewModel.openScoreDialog.observe(viewLifecycleOwner) {
      animeScoreDialog.visibility = when(it) {
        true -> View.VISIBLE
        else -> View.GONE
      }
    }

    viewModel.favoriteLoading.observe(viewLifecycleOwner) {
      loadingDialog.visibility = when(it) {
        true -> View.VISIBLE
        else -> View.GONE
      }
    }

    return binding.root
  }

  private fun favoriteBtnClickHandler(anime: Anime) {
    if(viewModel.loginToken != null) {
      if(anime.isFavorite) {
        viewModel.removeFavoriteAnime({onRemoveFavoriteAnimeSuccess()}, {onRemoveFavoriteAnimeFail()})
      } else {
        viewModel.addFavoriteAnime({onAddFavoriteAnimeSuccess()}, {onAddFavoriteAnimeFail()})
      }
    } else {
      redirectToLoginPage()
    }
  }

  private fun onRemoveFavoriteAnimeSuccess() {
    showToast("Remove favorite anime success")
  }

  private fun onRemoveFavoriteAnimeFail() {
    showToast("Fail to remove favorite anime")
  }

  private fun onAddFavoriteAnimeSuccess() {
    showToast("Success to add favorite anime")
  }

  private fun onAddFavoriteAnimeFail() {
    showToast("Fail to add favorite anime")
  }

  private fun onAddAnimeScoreSuccess() {
    showToast("Add score success")
    viewModel.closeScoreAnimeDialog()
  }

  private fun onAddAnimeScoreFail() {
    showToast("Fail to add score")
  }

  private fun onUpdateAnimeScoreSuccess() {
    showToast("Update score success")
    viewModel.closeScoreAnimeDialog()
  }

  private fun onUpdateAnimeScoreFail() {
    showToast("Fail to update score")
  }

  private fun showToast(text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
  }

  private fun scoreBtnClickHandler() {
    if(viewModel.loginToken != null) {
      viewModel.openScoreAnimeDialog()
    } else {
      redirectToLoginPage()
    }
  }

  private fun redirectToLoginPage() {
    findNavController().navigate(AnimeDetailPageDirections.actionAnimeDetailPageToLoginPage())
  }
}