package com.arjhox.develop.playrequest.ui.main.play.header

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arjhox.develop.domain.common.CUSTOM_HEADER
import com.arjhox.develop.domain.common.headerTypes
import com.arjhox.develop.playrequest.ui.common.Event
import com.arjhox.develop.playrequest.ui.common.HeaderModel

class HeaderDialogViewModel: ViewModel() {

    private val _headers = MutableLiveData<List<String>>()
    val headers: LiveData<List<String>>
        get() = _headers

    val headerTypeSelectedObserver = ObservableField<String>()
    val headerKeyEnteredObserver = ObservableField<String>()
    val headerValueEnteredObserver = ObservableField<String>()

    lateinit var headerModel: HeaderModel
        private set

    private val _key = MutableLiveData<String>("")
    val key: LiveData<String>
        get() = _key

    private val _value = MutableLiveData<String>("")
    val value: LiveData<String>
        get() = _value

    private val _closeDialogWithConfirmationEvent = MutableLiveData<Event<HeaderModel>>()
    val closeDialogWithConfirmationEvent: LiveData<Event<HeaderModel>>
        get() = _closeDialogWithConfirmationEvent


    fun init(initHeader: HeaderModel) {
        _headers.postValue(headerTypes)

        headerModel = initHeader
        _key.postValue(headerModel.key)
        _value.postValue(headerModel.value)

        initObserversField()
    }


    private fun initObserversField() {
        headerTypeSelectedObserver.apply {
            addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    if (this@apply.get() == CUSTOM_HEADER) {
                        _key.postValue(this@apply.get().toString())
                    } else {
                        _key.postValue(null)
                    }
                }
            })
        }

        headerKeyEnteredObserver.apply {
            addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    _key.postValue(this@apply.get().toString())
                }
            })
        }

        headerValueEnteredObserver.apply {
            addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    _value.postValue(this@apply.get().toString())
                }
            })
        }
    }


    fun onConfirmHeader(currentKey: String?, currentValue: String?) {
        if (currentValue.isNullOrEmpty()) {
            _value.postValue(null)
        } else {
            headerModel.key = currentKey!!
            headerModel.value = currentValue

            _closeDialogWithConfirmationEvent.postValue(Event(headerModel))
        }
    }

}