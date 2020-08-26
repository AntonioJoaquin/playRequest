package com.arjhox.develop.data.repository

import com.arjhox.develop.data.datasources.RequestRemoteDataSource
import com.arjhox.develop.data.toRequestData
import com.arjhox.develop.domain.models.Request as RequestDomain
import com.arjhox.develop.domain.models.RequestResponse
import com.arjhox.develop.domain.repositories.RequestRepository
import io.reactivex.Single

class RequestRepositoryImpl(
    private val requestRemoteDataSource: RequestRemoteDataSource
): RequestRepository {

    override fun playRequest(request: RequestDomain): Single<RequestResponse> =
        requestRemoteDataSource.playRequest(request.toRequestData())

}