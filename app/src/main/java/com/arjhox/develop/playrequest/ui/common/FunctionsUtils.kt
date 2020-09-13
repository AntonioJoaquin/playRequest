package com.arjhox.develop.playrequest.ui.common

fun isHtmlResponse(response: String?): Boolean {
    val htmlStartingString = "<!DOCTYPE"
    val firstCharsNumber = htmlStartingString.length

    response?.let {
        return it.length>firstCharsNumber &&
                it.substring(0, firstCharsNumber)==htmlStartingString
    } ?: run {
        return false
    }
}