package com.arjhox.develop.playrequest.ui.main.play.header

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arjhox.develop.domain.common.CUSTOM_HEADER
import com.arjhox.develop.domain.common.headerTypes
import com.arjhox.develop.playrequest.ui.common.Event
import com.arjhox.develop.playrequest.ui.common.models.HeaderModel

class HeaderDialogViewModel: ViewModel() {

    val headerTypeSelectedObserver = ObservableField<String>()
    val headerKeyEnteredObserver = ObservableField<String>()
    val headerValueEnteredObserver = ObservableField<String>()

    lateinit var headerModel: HeaderModel
        private set

    private val _keyType = MutableLiveData<String>("")
    val keyType: LiveData<String>
        get() = _keyType

    private val _key = MutableLiveData<String>("")
    val key: LiveData<String>
        get() = _key

    private val _value = MutableLiveData<String>("")
    val value: LiveData<String>
        get() = _value

    private val _closeDialogWithConfirmationEvent = MutableLiveData<Event<HeaderModel>>()
    val closeDialogWithConfirmationEvent: LiveData<Event<HeaderModel>>
        get() = _closeDialogWithConfirmationEvent


    private var isACustomKey: Boolean = false


    fun init(initHeader: HeaderModel) {
        headerModel = initHeader
        isACustomKey = (headerModel.key.isNotBlank() && !headerTypes.contains(headerModel.key))
        _key.postValue(headerModel.key)
        _value.postValue(headerModel.value)

        initObserversField()
    }


    private fun initObserversField() {
        headerTypeSelectedObserver.apply {
            addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    _keyType.postValue(this@apply.get().toString())

                    if (this@apply.get() == CUSTOM_HEADER) {
                        _key.postValue(if (isACustomKey) headerModel.key else "")
                    } else {
                        _key.postValue(this@apply.get().toString())
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
        when {
            currentKey.isNullOrBlank() -> {
                _key.postValue(null)
            }
            currentValue.isNullOrBlank() -> {
                _value.postValue(null)
            }
            else -> {
                headerModel.key = currentKey
                headerModel.value = currentValue

                _closeDialogWithConfirmationEvent.postValue(Event(headerModel))
            }
        }
    }

}