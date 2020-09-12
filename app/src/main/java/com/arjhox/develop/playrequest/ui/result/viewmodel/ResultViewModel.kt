package com.arjhox.develop.playrequest.ui.result.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arjhox.develop.playrequest.ui.common.SchedulerProvider
import com.arjhox.develop.playrequest.ui.common.base.BaseViewModel
import com.arjhox.develop.playrequest.ui.common.models.RequestResponse

class ResultViewModel(
    private val schedulerProvider: SchedulerProvider
): BaseViewModel() {

    private val _headers = MutableLiveData<String>()
    val headers: LiveData<String>
        get() = _headers

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response


    fun initRequestResponseVariables(requestResponse: RequestResponse) {
        _headers.postValue(requestResponse.headers)
        _response.postValue(requestResponse.response)
    }

}