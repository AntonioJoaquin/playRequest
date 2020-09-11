package com.arjhox.develop.domain.models

import com.arjhox.develop.domain.common.GET

data class Request(
    val requestType: String = GET,
    val path: String = "",
    val headers: Map<String, String> = mapOf(),
    val parameters: Map<String, String> = mapOf()
)