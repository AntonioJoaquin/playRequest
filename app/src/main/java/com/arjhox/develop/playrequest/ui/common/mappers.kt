package com.arjhox.develop.playrequest.ui.common

import com.arjhox.develop.domain.models.RequestResponse as RequestResponseDomain
import com.arjhox.develop.playrequest.ui.common.models.Request
import com.arjhox.develop.playrequest.ui.common.models.RequestResponse
import com.arjhox.develop.domain.models.Request as RequestDomain

fun Request.toRequestDomain() = RequestDomain(requestType = requestType, path = path, headers = headers, parameters = parameters)


fun RequestResponseDomain.toRequestResponse() = RequestResponse(statusCode = statusCode, headers = headers, response = response)