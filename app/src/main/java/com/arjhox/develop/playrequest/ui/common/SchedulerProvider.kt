package com.arjhox.develop.playrequest.ui.common

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun io(): Scheduler
    fun ui(): Scheduler
    fun computation(): Scheduler

}