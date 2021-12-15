package com.farhanhp.myanimedb.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.farhanhp.myanimedb.R
import com.google.android.material.button.MaterialButton

class HomePageTopBar(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
  private val searchButton: MaterialButton
  private val defaultField: ConstraintLayout
  private val searchField: SearchAnimeField

  init {
    LayoutInflater.from(context).inflate(R.layout.component_homepage_top_bar, this, true)

    searchButton = findViewById(R.id.searchButton)
    defaultField = findViewById(R.id.defaultField)
    searchField = findViewById(R.id.searchField)

    searchButton.setOnClickListener{
      openSearchField()
    }

    searchField.setCloseBtnClickListener {
      closeSearchField()
    }
  }

  private fun openSearchField() {
    searchField.visibility = VISIBLE
    defaultField.visibility = GONE
  }

  private fun closeSearchField() {
    searchField.visibility = GONE
    defaultField.visibility = VISIBLE
  }

  fun setOnSearchListener(fn: (keyword: String) -> Unit) {
    searchField.setOnSearchListener {
      fn(it)
    }
  }
}