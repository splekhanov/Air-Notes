package com.example.myapplication.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object Focus {

    fun openSoftKeyboard(view: View) {
        view.requestFocus()
        // open the soft keyboard
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

}