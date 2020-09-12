package com.arjhox.develop.data.models

import com.android.volley.VolleyError

data class RequestErrorResponse(
    val statusCode: Int?,
    val headers: String,
    val errorResponse: String
): VolleyError()