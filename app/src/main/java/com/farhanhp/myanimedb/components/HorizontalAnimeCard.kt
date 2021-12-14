package com.farhanhp.myanimedb.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.farhanhp.myanimedb.R
import com.farhanhp.myanimedb.loadImageToView

class HorizontalAnimeCard(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
  private val imageView: ImageView
  private val titleTextView: TextView
  private val descriptionTextView: TextView
  private val scoreChip: Chip

  init {
    LayoutInflater.from(context).inflate(R.layout.component_horizontal_anime_card, this, true)

    imageView = findViewById(R.id.image)
    titleTextView = findViewById(R.id.title)
    descriptionTextView = findViewById(R.id.description)
    scoreChip = findViewById(R.id.score)
  }

  fun setAttribute(imageUrl: String, title: String, description: String, score: Double) {
    loadImageToView(imageView, imageUrl, true)
    titleTextView.text = title
    descriptionTextView.text = description
    scoreChip.setText(score.toString())
  }
}