package com.arjhox.develop.playrequest.ui.main.play

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arjhox.develop.domain.common.GET
import com.arjhox.develop.domain.common.LoadingState
import com.arjhox.develop.domain.models.RequestErrorResponse
import com.arjhox.develop.domain.models.RequestResponse
import com.arjhox.develop.domain.usecases.PlayRequestUseCase
import com.arjhox.develop.playrequest.R
import com.arjhox.develop.playrequest.ui.common.Event
import com.arjhox.develop.playrequest.ui.common.SchedulerProvider
import com.arjhox.develop.playrequest.ui.common.models.*
import com.arjhox.develop.playrequest.ui.common.toRequestDomain
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction

class PlayViewModel(
    private val playRequestUseCase: PlayRequestUseCase,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    // region Navigation Variables

    private val _openDialogEvent = MutableLiveData<Event<*>>()
    val openDialogEvent: LiveData<Event<*>>
        get() = _openDialogEvent

     // endregion


    // region Show Variables

    private val _showHeadersList = MutableLiveData<Boolean>()
    val showHeadersList: LiveData<Boolean>
        get() = _showHeadersList

    private val _showParametersList = MutableLiveData<Boolean>()
    val showParametersList: LiveData<Boolean>
        get() = _showParametersList

    private val _showToastMessageEvent = MutableLiveData<Event<Int>>()
    val showToastMessageEvent: LiveData<Event<Int>>
        get() = _showToastMessageEvent

    // endregion


    val requestTypeSelectedObserver = ObservableField<String>()
    private var requestType = GET

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

    private val _parameters = MutableLiveData<List<Parameter>>()
    val parameters: LiveData<List<Parameter>>
        get() = _parameters
    private val parametersList = arrayListOf<Parameter>()

    private val _requestResult = MutableLiveData<RequestResponse>()
    val requestResult: LiveData<RequestResponse>
        get() = _requestResult


    private val disposables = CompositeDisposable()


    init {

        _showHeadersList.postValue(true)
        _showParametersList.postValue(true)

        requestTypeSelectedObserver.apply {
            addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    requestType = this@apply.get().toString()
                }

            })
        }

    }


    override fun onCleared() {
        disposables.clear()

        super.onCleared()
    }


    // Test url -> "https://jsonplaceholder.typicode.com/users"
    fun playRequest(requestPath: String) {
        _loading.postValue(LoadingState.LOADING)

        disposables.add(
            Single.zip<Map<String, String>, Map<String, String>, Request>(
                mapHeaders(headersList),
                mapParameters(parametersList),
                BiFunction { headersMap, parametersMap ->
                    Request(requestType, requestPath, headersMap, parametersMap)
                })
                .flatMap { request -> playRequestUseCase.playRequest(request.toRequestDomain()) }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    {
                        _loading.postValue(LoadingState.LOADED)
                        _requestResult.postValue(it)
                    },
                    {
                        if (it is RequestErrorResponse) {
                            _requestResult.postValue(it.errorResponse)
                            _loading.postValue(LoadingState.error("Error ${it.statusCode}"))
                        } else {
                            _loading.postValue(LoadingState.error(it.message))

                            setToastMessage(R.string.error_notification)
                        }
                    }
                )
        )
    }

    fun openHeaderDialogClicked(headerModel: HeaderModel = Header()) =
        _openDialogEvent.postValue(Event(headerModel))

    fun openParameterDialogClicked(parameter: ParameterModel = Parameter()) =
        _openDialogEvent.postValue(Event(parameter))


    fun setRequestPath(newRequestPath: String) =
        this._requestPath.postValue(newRequestPath)


    private fun setToastMessage(message: Int) {
        _showToastMessageEvent.postValue(Event(message))
    }


    // region HeaderAdapter

    fun mapHeaders(headers: List<Header>): Single<Map<String, String>> {
        return Single.create {
            val headersMap = mutableMapOf<String, String>()

            for (header in headers) {
                headersMap[header.key] = header.value
            }

            it.onSuccess(headersMap)
        }
    }

    fun insertNewHeaderToRequest(header: Header) {
        if (!headersList.contains(header)) {
            headersList.add(header)
            _headers.postValue(headersList)
        } else {
            setToastMessage(R.string.element_already_exists)
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

    fun setHeadersListVisible(canDisplay: Boolean) {
        if (headersList.isNotEmpty()) {
            _showHeadersList.postValue(!canDisplay)
        }
    }

    // endregion


    // region ParameterAdapter

    fun mapParameters(parametersList: List<Parameter>): Single<Map<String, String>> {
        return Single.create {
            val parametersMap = mutableMapOf<String, String>()

            for (parameter in parametersList) {
                parametersMap[parameter.key] = parameter.value
            }

            it.onSuccess(parametersMap)
        }
    }

    fun insertNewParameterToRequest(parameter: Parameter) {
        if (!parametersList.contains(parameter)) {
            parametersList.add(parameter)
            _parameters.postValue(parametersList)
        } else {
            setToastMessage(R.string.element_already_exists)
        }
    }

    fun updateParameterToRequest(parameterItemList: ParameterItemList) {
        parametersList[parameterItemList.position] = Parameter(parameterItemList.key, parameterItemList.value)
        _parameters.postValue(parametersList)
    }

    fun deleteParameterFromRequest(parameter: Parameter) {
        parametersList.remove(parameter)
        _parameters.postValue(parametersList)
    }

    fun setParametersListVisible(canDisplay: Boolean) {
        if (parametersList.isNotEmpty()) {
            _showParametersList.postValue(!canDisplay)
        }
    }

    // endregion

}