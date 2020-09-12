package com.arjhox.develop.playrequest.play

import com.arjhox.develop.playrequest.ui.common.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TrampolineSchedulerProvider: SchedulerProvider {

    override fun io(): Scheduler = Schedulers.trampoline()

    override fun ui(): Scheduler = Schedulers.trampoline()

    override fun computation(): Scheduler = Schedulers.trampoline()

}