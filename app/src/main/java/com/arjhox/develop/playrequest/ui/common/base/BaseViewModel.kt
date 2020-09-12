package com.arjhox.develop.playrequest.ui.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arjhox.develop.domain.common.LoadingState
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel: ViewModel() {

    private val _loading = MutableLiveData<LoadingState>()
    val loading: LiveData<LoadingState>
        get() = _loading

    private val disposables = CompositeDisposable()


    override fun onCleared() {
        disposables.clear()

        super.onCleared()
    }

}