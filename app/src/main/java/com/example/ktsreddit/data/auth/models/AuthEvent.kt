package com.example.ktsreddit.data.auth.models

import android.content.Intent
import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

sealed interface AuthEvent

@Parcelize
class AuthToast(  @StringRes val toast: Int) : AuthEvent, Parcelable

@Parcelize
class AuthIntent(val intent: Intent) : AuthEvent, Parcelable

@Parcelize
object AuthSuccess : AuthEvent, Parcelable

@Parcelize
object AuthDefault : AuthEvent, Parcelable