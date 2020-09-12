package com.arjhox.develop.domain.models

data class RequestErrorResponse (
    val requestResponse: RequestResponse
): Throwable()