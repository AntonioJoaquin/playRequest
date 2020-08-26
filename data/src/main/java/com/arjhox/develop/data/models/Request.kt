package com.arjhox.develop.data.models

data class Request(
    val path: String,
    val headers: Map<String, String> = mapOf()
)