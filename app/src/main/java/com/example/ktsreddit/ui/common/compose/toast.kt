package com.example.ktsreddit.ui.common.compose

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import timber.log.Timber


fun toast(@StringRes stringRes:Int,context:Context) {
    Toast.makeText(context, stringRes, Toast.LENGTH_SHORT).show()
}


fun toast( string:String) {
    Timber.d(string)
}

fun toast(@StringRes stringRes:Int) {
    Timber.d(stringRes.toString())
}