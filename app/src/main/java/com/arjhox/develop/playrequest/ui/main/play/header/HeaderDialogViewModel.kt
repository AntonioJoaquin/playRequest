package com.arjhox.develop.playrequest.ui.main.play.header

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arjhox.develop.domain.common.headersItems
import com.arjhox.develop.playrequest.ui.common.Header
import com.arjhox.develop.playrequest.ui.common.HeaderModel

class HeaderDialogViewModel: ViewModel() {

    private val _headers = MutableLiveData<List<String>>()
    val headers: LiveData<List<String>>
        get() = _headers

    val headerKeySelectedObserver = ObservableField<String>()
    val headerValueEnterObserver = ObservableField<String>()

    private lateinit var header: HeaderModel
    private lateinit var key: String
    private lateinit var value: String


    fun init(initHeader: HeaderModel) {
        _headers.postValue(headersItems)
        header = initHeader
        key = header.key
        value = header.value

        initObservers()
    }


    private fun initObservers() {
        headerKeySelectedObserver.apply {
            addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    key = this@apply.get().toString()
                }
            })
        }

        headerValueEnterObserver.apply {
            addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    value = this@apply.get().toString()
                }
            })
        }
    }


    fun getHeaderModel(): HeaderModel {
        header.key = key
        header.value = value

        return header
    }

}