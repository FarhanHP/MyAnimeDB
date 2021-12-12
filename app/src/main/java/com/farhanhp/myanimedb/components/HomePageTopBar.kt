package com.farhanhp.myanimedb.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.farhanhp.myanimedb.R
import com.google.android.material.button.MaterialButton

class HomePageTopBar(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
  private val searchButton: MaterialButton

  init {
    LayoutInflater.from(context).inflate(R.layout.component_homepage_top_bar, this, true)

    searchButton = findViewById(R.id.searchButton)
  }
}