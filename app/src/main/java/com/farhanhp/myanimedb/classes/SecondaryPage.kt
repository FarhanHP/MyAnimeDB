package com.farhanhp.myanimedb.classes

import android.util.Log
import androidx.fragment.app.Fragment

abstract class SecondaryPage: Fragment() {
  protected fun openPriorPage() {
    activity?.onBackPressed()
  }
}