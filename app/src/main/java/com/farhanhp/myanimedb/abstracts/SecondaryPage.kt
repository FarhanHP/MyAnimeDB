package com.farhanhp.myanimedb.abstracts

import androidx.fragment.app.Fragment

abstract class SecondaryPage: MainActivityChildFragment(){
  protected fun openPriorPage() {
    activity?.onBackPressed()
  }
}