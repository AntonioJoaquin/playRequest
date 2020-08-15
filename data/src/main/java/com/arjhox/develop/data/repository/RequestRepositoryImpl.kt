package com.arjhox.develop.data.repository

import com.arjhox.develop.data.datasources.RequestRemoteDataSource
import com.arjhox.develop.domain.repositories.RequestRepository
import io.reactivex.Single

class RequestRepositoryImpl(
    private val requestRemoteDataSource: RequestRemoteDataSource
): RequestRepository {

    override fun playRequest(url: String): Single<String> =
        requestRemoteDataSource.playRequest(url)

}