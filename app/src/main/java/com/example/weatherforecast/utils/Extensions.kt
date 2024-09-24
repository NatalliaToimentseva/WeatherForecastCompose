package com.example.weatherforecasts.utils

import android.content.Context
import android.widget.Toast

fun Context.makeToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}