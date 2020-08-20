package com.arjhox.develop.playrequest.ui.main.play.header

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arjhox.develop.domain.common.headersItems
import com.arjhox.develop.playrequest.ui.common.Header

class HeaderDialogViewModel: ViewModel() {

    private val _headers = MutableLiveData<List<String>>()
    val headers: LiveData<List<String>>
        get() = _headers

    val headerKeySelectedObserver = ObservableField<String>()
    val headerValueEnterObserver = ObservableField<String>()

    lateinit var header: Header
        private set


    fun init(initHeader: Header) {
        _headers.postValue(headersItems)
        header = initHeader

        initObservers()
    }


    private fun initObservers() {
        headerKeySelectedObserver.apply {
            addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    header.key = this@apply.get().toString()
                }
            })
        }

        headerValueEnterObserver.apply {
            addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    header.value = this@apply.get().toString()
                }
            })
        }
    }

}