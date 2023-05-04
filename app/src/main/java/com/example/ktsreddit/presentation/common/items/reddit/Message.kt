package com.example.ktsreddit.presentation.common.items.reddit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
class Message<T>(val message: @RawValue T) : Parcelable