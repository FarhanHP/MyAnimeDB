package com.farhanhp.myanimedb.components

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import com.farhanhp.myanimedb.R
import com.google.android.material.card.MaterialCardView

class SearchAnimeField(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
  private val closeButton: MaterialCardView
  private val editText: EditText

  init {
    LayoutInflater.from(context).inflate(R.layout.component_search_anime_field, this, true)

    closeButton = findViewById(R.id.closeBtn)
    editText = findViewById(R.id.editText)
  }

  fun setCloseBtnClickListener(fn: () -> Unit) {
    closeButton.setOnClickListener{
      fn()
    }
  }

  fun setOnSearchListener(fn: (keyword: String) -> Unit) {
    editText.setOnKeyListener(OnKeyListener {_, keyCode, event ->
      val value = editText.text.toString()
      if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP && value.isNotEmpty()) {
        fn(value)
        return@OnKeyListener true
      }
      false
    })
  }
}