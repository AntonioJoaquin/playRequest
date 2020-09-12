package com.arjhox.develop.playrequest.ui.result

import com.arjhox.develop.playrequest.ui.common.SchedulerProvider
import com.arjhox.develop.playrequest.ui.result.viewmodel.ResultViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val resultModule = module {

    fun provideResultViewModel(schedulerProvider: SchedulerProvider): ResultViewModel =
        ResultViewModel(schedulerProvider)


    viewModel { provideResultViewModel(get()) }

}