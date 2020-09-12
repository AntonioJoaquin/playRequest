package com.arjhox.develop.playrequest.play.ui.play.di

import com.arjhox.develop.data.services.dataModule
import com.arjhox.develop.playrequest.appModule

fun configureTestAppModules() =
    listOf(appModule, dataModule, playModuleTest)