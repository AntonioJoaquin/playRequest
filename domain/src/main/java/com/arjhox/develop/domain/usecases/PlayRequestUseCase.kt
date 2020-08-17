package com.arjhox.develop.domain.usecases

import com.arjhox.develop.domain.repositories.RequestRepository

class PlayRequestUseCase(
    private val requestRepository: RequestRepository
) {

    fun playRequest(url: String) = requestRepository.playRequest(url)

}