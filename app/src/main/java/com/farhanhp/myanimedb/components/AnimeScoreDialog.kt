package com.farhanhp.myanimedb.components

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.farhanhp.myanimedb.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.lang.IllegalArgumentException

class AnimeScoreDialog(context: Context, attrs: AttributeSet): RelativeLayout(context, attrs) {
  private val setScoreButton: MaterialButton
  private val cancelButton: MaterialButton
  private val decrementButton: MaterialCardView
  private val incrementButton: MaterialCardView
  private val scoreTextView: TextView

  private var isUpdate = false // to determine serScoreButton text
  private var score = 0
  private var oldScore = 0

  init {
    LayoutInflater.from(context).inflate(R.layout.component_anime_score_dialog, this, true)

    setScoreButton = findViewById(R.id.setScoreBtn)
    cancelButton = findViewById(R.id.cancelButton)
    decrementButton = findViewById(R.id.decrementButton)
    incrementButton = findViewById(R.id.incrementButton)
    scoreTextView = findViewById(R.id.scoreTextView)

    decrementButton.setOnClickListener{
      decrement()
    }

    incrementButton.setOnClickListener{
      increment()
    }
  }

  fun setType(isUpdate: Boolean) {
    this.isUpdate = isUpdate
    setScoreButton.text = when(isUpdate) {
      true -> UPDATE_SCORE
      else -> ADD_SCORE
    }
  }

  fun setScore(newScore: Int) {
    if(newScore > 10 || newScore < 1) {
      throw IllegalArgumentException("Score must within 1 to 10 range")
    }
    oldScore = newScore
    score = newScore
    scoreTextView.text = newScore.toString()
  }

  fun setOnCancel(fn: () -> Unit) {
    cancelButton.setOnClickListener{
      score = oldScore
      scoreTextView.text = oldScore.toString()
      fn()
    }
  }

  fun setOnSubmit(submitCallback: (score: Int, isUpdate: Boolean)->Unit) {
    setScoreButton.setOnClickListener{
      submitCallback(score, isUpdate)
    }
  }

  fun setLoading(loading: Boolean) {
    if(loading) {
      setScoreButton.text = when(isUpdate) {
        true -> UPDATING_SCORE
        else -> ADDING_SCORE
      }
      disableButton(cancelButton)
      disableButton(setScoreButton)
      disableButton(decrementButton)
      disableButton(incrementButton)
    } else {
      setScoreButton.text = when(isUpdate) {
        true -> UPDATE_SCORE
        else -> ADD_SCORE
      }
      enableButton(cancelButton)
      enableButton(setScoreButton)
      enableButton(decrementButton)
      enableButton(incrementButton)
    }
  }

  private fun disableButton(button: View) {
    button.isClickable = false
    button.isEnabled = false
  }

  private fun enableButton(button: View) {
    button.isClickable = true
    button.isEnabled = true
  }

  private fun decrement() {
    if(score - 1 >= 1) {
      score--
      scoreTextView.text = score.toString()
    }
  }

  private fun increment() {
    if(score + 1 <= 10) {
      score++
      scoreTextView.text = score.toString()
    }
  }

  companion object {
    const val UPDATE_SCORE = "Update Score"
    const val ADD_SCORE = "Add Score"
    const val UPDATING_SCORE = "Updating Score"
    const val ADDING_SCORE = "Adding Score"
  }
}