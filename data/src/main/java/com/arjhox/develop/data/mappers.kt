package com.arjhox.develop.data

import com.arjhox.develop.data.models.Request
import com.arjhox.develop.domain.models.Request as RequestDomain

fun RequestDomain.toRequestData() = Request(path = path, headers = headers, parameters = parameters)