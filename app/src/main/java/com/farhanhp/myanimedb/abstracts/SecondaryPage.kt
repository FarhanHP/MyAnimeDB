package com.farhanhp.myanimedb.abstracts

import androidx.fragment.app.Fragment

abstract class SecondaryPage: Fragment() {
  protected fun openPriorPage() {
    activity?.onBackPressed()
  }
}