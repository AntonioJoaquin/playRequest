package com.arjhox.develop.domain.models

/* TODO: Change variables type to
    headers -> Map<String, String>
    response -> List<JSONObject>
    At the moment these types are kept for a basic implementation
 */
data class RequestResponse (
    val statusCode: Int?,
    val headers: String,
    val response: String
)
