package com.farhanhp.myanimedb.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.farhanhp.myanimedb.R
import com.google.android.material.button.MaterialButton

class SecondaryPageTopBar(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
  private val backBtn: MaterialButton
  private val titleTextView: TextView

  init {
    LayoutInflater.from(context).inflate(R.layout.component_secondary_page_top_bar, this, true)

    backBtn = findViewById(R.id.backBtn)
    titleTextView = findViewById(R.id.title)

    context.theme.obtainStyledAttributes(attrs, R.styleable.SecondaryPageTopBar, 0, 0).apply {
      val title = getString(R.styleable.SecondaryPageTopBar_title)
      if(title != null) {
        setTitle(title)
      }
    }
  }

  fun setBackButtonClickListener(fn: () -> Unit) {
    backBtn.setOnClickListener{ fn() }
  }

  fun setTitle(title: String) {
    titleTextView.text = title
  }
}