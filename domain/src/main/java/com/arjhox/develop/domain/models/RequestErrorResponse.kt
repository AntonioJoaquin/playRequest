package com.arjhox.develop.domain.models

data class RequestErrorResponse (
    val statusCode: Int?,
    val errorResponse: RequestResponse
): Throwable()