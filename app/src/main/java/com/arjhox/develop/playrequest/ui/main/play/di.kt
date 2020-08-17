package com.arjhox.develop.playrequest.ui.main.play

import com.arjhox.develop.domain.repositories.RequestRepository
import com.arjhox.develop.domain.usecases.PlayRequestUseCase
import com.arjhox.develop.playrequest.ui.common.SchedulerProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val playModule = module {

    fun providePlayRequestUseCase(requestRepository: RequestRepository): PlayRequestUseCase =
        PlayRequestUseCase(requestRepository)

    fun providePlayViewModel(playRequestUseCase: PlayRequestUseCase, schedulerProvider: SchedulerProvider): PlayViewModel =
        PlayViewModel(playRequestUseCase, schedulerProvider)

    factory {  providePlayRequestUseCase(get()) }
    viewModel { providePlayViewModel(get(), get()) }

}