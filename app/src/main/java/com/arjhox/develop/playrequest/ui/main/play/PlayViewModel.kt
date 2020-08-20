package com.arjhox.develop.playrequest.ui.main.play

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arjhox.develop.domain.common.LoadingState
import com.arjhox.develop.domain.models.RequestResponse
import com.arjhox.develop.domain.usecases.PlayRequestUseCase
import com.arjhox.develop.playrequest.ui.common.Event
import com.arjhox.develop.playrequest.ui.common.Header
import com.arjhox.develop.playrequest.ui.common.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class PlayViewModel(
    private val playRequestUseCase: PlayRequestUseCase,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    // region Navigation Variables

    private val _openHeaderDialog = MutableLiveData<Event<Header>>()
    val openHeaderDialog: LiveData<Event<Header>>
        get() = _openHeaderDialog

    // endregion


    private val _requestPath = MutableLiveData<String>()
    val requestPath: LiveData<String>
        get() = _requestPath

    private val _loading = MutableLiveData<LoadingState>()
    val loading: LiveData<LoadingState>
        get() = _loading

    private val _requestResult = MutableLiveData<RequestResponse>()
    val requestResult: LiveData<RequestResponse>
        get() = _requestResult


    private val disposables = CompositeDisposable()


    override fun onCleared() {
        disposables.clear()

        super.onCleared()
    }


    // Test url -> "https://jsonplaceholder.typicode.com/users"
    fun playRequest(requestPath: String) {
        _loading.postValue(LoadingState.LOADING)

        disposables.add(
            playRequestUseCase.playRequest(requestPath)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    {
                        _loading.postValue(LoadingState.LOADED)
                        _requestResult.postValue(it)
                    },
                    {
                        _loading.postValue(LoadingState.error(it.message))
                    }
                )
        )
    }

    fun openHeaderDialogClicked(header: Header = Header()) =
        _openHeaderDialog.postValue(Event(header))


    fun setRequestPath(newRequestPath: String) =
        this._requestPath.postValue(newRequestPath)

}