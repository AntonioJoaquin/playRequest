package com.arjhox.develop.playrequest

import com.arjhox.develop.playrequest.ui.common.SchedulerProvider
import com.arjhox.develop.playrequest.ui.common.SchedulerProviderImpl
import org.koin.dsl.module

val appModule = module {

    fun provideSchedulerProvider(): SchedulerProvider =
        SchedulerProviderImpl()


    single { provideSchedulerProvider() }

}