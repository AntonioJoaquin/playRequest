package com.arjhox.develop.playrequest.ui.main.play

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arjhox.develop.domain.common.LoadingState
import com.arjhox.develop.domain.models.RequestResponse
import com.arjhox.develop.domain.usecases.PlayRequestUseCase
import com.arjhox.develop.playrequest.ui.common.*
import io.reactivex.disposables.CompositeDisposable

class PlayViewModel(
    private val playRequestUseCase: PlayRequestUseCase,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    // region Navigation Variables

    private val _openHeaderDialogEvent = MutableLiveData<Event<HeaderModel>>()
    val openHeaderDialogEvent: LiveData<Event<HeaderModel>>
        get() = _openHeaderDialogEvent

    // endregion


    private val _requestPath = MutableLiveData<String>()
    val requestPath: LiveData<String>
        get() = _requestPath

    private val _loading = MutableLiveData<LoadingState>()
    val loading: LiveData<LoadingState>
        get() = _loading

    private val _headers = MutableLiveData<List<Header>>()
    val headers: LiveData<List<Header>>
        get() = _headers
    private val headersList = arrayListOf<Header>()

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

    fun openHeaderDialogClicked(headerModel: HeaderModel = Header()) =
        _openHeaderDialogEvent.postValue(Event(headerModel))


    fun setRequestPath(newRequestPath: String) =
        this._requestPath.postValue(newRequestPath)


    // region HeaderAdapter

    fun insertNewHeaderToRequest(header: Header) {
        // TODO: return if header has been added to list: if it is false must show a message
        if (!headersList.contains(header)) {
            headersList.add(header)
            _headers.postValue(headersList)
        }
    }

    fun updateHeaderToRequest(headerItemList: HeaderItemList) {
        headersList[headerItemList.position] = Header(headerItemList.key, headerItemList.value)
        _headers.postValue(headersList)
    }

    fun deleteHeaderFromRequest(header: Header) {
        headersList.remove(header)
        _headers.postValue(headersList)
    }

    // endregion

}