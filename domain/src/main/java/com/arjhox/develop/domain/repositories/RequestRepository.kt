package com.arjhox.develop.domain.repositories

import io.reactivex.Single


interface RequestRepository {

    fun playRequest(url: String): Single<String>

}