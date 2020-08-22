package com.arjhox.develop.playrequest.ui.main.play.header

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arjhox.develop.domain.common.headersItems
import com.arjhox.develop.playrequest.ui.common.Event
import com.arjhox.develop.playrequest.ui.common.HeaderModel

class HeaderDialogViewModel: ViewModel() {

    private val _headers = MutableLiveData<List<String>>()
    val headers: LiveData<List<String>>
        get() = _headers

    val headerKeySelectedObserver = ObservableField<String>()
    val headerValueEnterObserver = ObservableField<String>()

    lateinit var headerModel: HeaderModel
        private set
    private lateinit var key: String
    private val _value = MutableLiveData<String>("")
    val value: LiveData<String>
        get() = _value

    private val _closeDialogWithConfirmationEvent = MutableLiveData<Event<HeaderModel>>()
    val closeDialogWithConfirmationEvent: LiveData<Event<HeaderModel>>
        get() = _closeDialogWithConfirmationEvent


    fun init(initHeader: HeaderModel) {
        _headers.postValue(headersItems)

        headerModel = initHeader
        key = headerModel.key
        _value.postValue(headerModel.value)

        initObserversField()
    }


    private fun initObserversField() {
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
                    _value.postValue(this@apply.get().toString())
                }
            })
        }
    }


    fun onConfirmHeader(currentValue: String?) {
        if (currentValue.isNullOrEmpty()) {
            _value.postValue(null)
        } else {
            headerModel.key = key
            headerModel.value = currentValue

            _closeDialogWithConfirmationEvent.postValue(Event(headerModel))
        }
    }

}