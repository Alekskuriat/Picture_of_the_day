package com.example.pictureoftheday.domain

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.toast(str:String){
    Toast.makeText(context, str, Toast.LENGTH_LONG).show()
}

fun View.showSnackBar(text: String, actionText: String, action: (View) -> Unit){
    Snackbar.make(this, text, Snackbar.LENGTH_INDEFINITE).setAction(actionText, action).show()
}