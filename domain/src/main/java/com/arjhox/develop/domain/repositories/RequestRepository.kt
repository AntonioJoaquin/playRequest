package com.arjhox.develop.domain.repositories

import com.arjhox.develop.domain.models.RequestResponse
import io.reactivex.Single


interface RequestRepository {

    fun playRequest(url: String): Single<RequestResponse>

}