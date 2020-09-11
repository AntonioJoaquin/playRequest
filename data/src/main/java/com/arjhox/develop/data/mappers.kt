package com.arjhox.develop.data

import com.arjhox.develop.data.models.Request
import com.arjhox.develop.data.models.RequestErrorResponse
import com.arjhox.develop.domain.models.RequestResponse
import com.arjhox.develop.domain.models.Request as RequestDomain
import com.arjhox.develop.domain.models.RequestErrorResponse as RequestErrorResponseDomain

fun RequestDomain.toRequestData() = Request(requestType = requestType, path = path, headers = headers, parameters = parameters)


fun RequestErrorResponse.toRequestErrorResponseDomain() = RequestErrorResponseDomain(statusCode = statusCode, errorResponse = RequestResponse(headers, errorResponse))