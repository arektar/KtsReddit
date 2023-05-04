package com.example.ktsreddit.presentation.common.utils

import org.ocpsoft.prettytime.PrettyTime
import java.util.*

fun Long.convertLongToTime(): String {
    return PrettyTime().format(Date(this * 1000))
}