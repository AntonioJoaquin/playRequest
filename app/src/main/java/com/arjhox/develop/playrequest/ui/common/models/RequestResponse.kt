package com.arjhox.develop.playrequest.ui.common.models

import java.io.Serializable

data class RequestResponse(
    val statusCode: Int?,
    val headers: String,
    val response: String
): Serializable