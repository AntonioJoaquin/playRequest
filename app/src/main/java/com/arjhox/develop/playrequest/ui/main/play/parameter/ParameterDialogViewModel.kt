package com.arjhox.develop.playrequest.ui.main.play.parameter

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arjhox.develop.playrequest.ui.common.Event
import com.arjhox.develop.playrequest.ui.common.ParameterModel

class ParameterDialogViewModel: ViewModel() {

    val parameterKeyEnteredObserver = ObservableField<String>()
    val parameterValueEnteredObserver = ObservableField<String>()

    lateinit var parameterModel: ParameterModel
        private set

    private val _key = MutableLiveData<String>("")
    val key: LiveData<String>
        get() = _key

    private val _value = MutableLiveData<String>("")
    val value: LiveData<String>
        get() = _value

    private val _closeDialogWithConfirmationEvent = MutableLiveData<Event<ParameterModel>>()
    val closeDialogWithConfirmationEvent: LiveData<Event<ParameterModel>>
        get() = _closeDialogWithConfirmationEvent


    fun init(initParameter: ParameterModel) {
        parameterModel = initParameter
        _key.postValue(parameterModel.key)
        _value.postValue(parameterModel.value)

        initObserversField()
    }


    private fun initObserversField() {
        parameterKeyEnteredObserver.apply {
            addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    _key.postValue(this@apply.get().toString())
                }

            })
        }

        parameterValueEnteredObserver.apply {
            addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    _value.postValue(this@apply.get().toString())
                }

            })
        }
    }


    fun onConfirmParameter(currentKey: String?, currentValue: String?) {
        when {
            currentKey.isNullOrBlank() -> {
                _key.postValue(null)
            }
            currentValue.isNullOrBlank() -> {
                _value.postValue(null)
            }
            else -> {
                parameterModel.key = currentKey
                parameterModel.value = currentValue

                _closeDialogWithConfirmationEvent.postValue(Event(parameterModel))
            }
        }
    }

}