package com.arjhox.develop.data

import com.arjhox.develop.data.datasources.RequestRemoteDataSource
import com.arjhox.develop.data.repository.RequestRepositoryImpl
import com.arjhox.develop.domain.repositories.RequestRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import com.arjhox.develop.domain.models.Request as RequestDomain


class RequestRepositoryTest {

    private lateinit var remoteDataSource: RequestRemoteDataSource
    private lateinit var requestRepository: RequestRepository


    @Before
    fun setUp() {
        this.remoteDataSource = mock()
        this.requestRepository = RequestRepositoryImpl(remoteDataSource)
    }


    @Test
    fun `playRequest from repository should call playRequest from request remote dataSource`() {
        val request = RequestDomain()

        requestRepository.playRequest(request)

        verify(remoteDataSource).playRequest(request.toRequestData())
    }

}