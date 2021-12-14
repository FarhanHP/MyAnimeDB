package com.farhanhp.myanimedb.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.farhanhp.myanimedb.R

class Chip(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
  private val textView: TextView

  init {
    LayoutInflater.from(context).inflate(R.layout.component_chip, this, true)
    textView = findViewById(R.id.text)
  }

  fun setText(text: String) {
    textView.text = text
  }
}