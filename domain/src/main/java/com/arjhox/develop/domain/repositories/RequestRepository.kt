package com.arjhox.develop.domain.repositories

import com.arjhox.develop.domain.models.Request
import com.arjhox.develop.domain.models.RequestResponse
import io.reactivex.Single


interface RequestRepository {

    fun playRequest(request: Request): Single<RequestResponse>

}