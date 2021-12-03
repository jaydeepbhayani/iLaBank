package com.jaydeepbhayani.ilabank.core

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.jaydeepbhayani.ilabank.R

/**
 *  [BaseActivity]
 *  @author
 *  created by Jaydeep Bhayani on 03/12/2021
 */

abstract class BaseActivity : AppCompatActivity() {

    fun showSnackBar(container: View, message: String, buttonText: String) {
        val snackbar = Snackbar.make(container, message, BaseTransientBottomBar.LENGTH_INDEFINITE)
        snackbar.setAction(buttonText) { view -> snackbar.dismiss() }
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        snackbar.show()
    }

    val isConnectedToInternet: Boolean
        get() {
            val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }

    fun hideKeyboard() {
        val view = currentFocus
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
