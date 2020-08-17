package com.arjhox.develop.data.services

import android.content.Context
import com.arjhox.develop.data.datasources.RequestRemoteDataSource
import com.arjhox.develop.data.datasources.RequestRemoteDataSourceImpl
import com.arjhox.develop.data.repository.RequestRepositoryImpl
import com.arjhox.develop.domain.repositories.RequestRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    fun provideRequestRemoteDataSource(context: Context): RequestRemoteDataSource =
        RequestRemoteDataSourceImpl(context)

    fun provideRequestRepository(requestRemoteDataSource: RequestRemoteDataSource): RequestRepository =
        RequestRepositoryImpl(requestRemoteDataSource)


    single { provideRequestRemoteDataSource(androidContext()) }
    single { provideRequestRepository(get()) }

}