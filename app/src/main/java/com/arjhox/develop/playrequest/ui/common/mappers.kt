package com.arjhox.develop.playrequest.ui.common

import com.arjhox.develop.domain.models.Request as RequestDomain

fun Request.toRequestDomain() = RequestDomain(path = path, headers = headers)