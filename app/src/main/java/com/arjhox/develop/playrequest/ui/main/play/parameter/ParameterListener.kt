package com.arjhox.develop.playrequest.ui.main.play.parameter

import com.arjhox.develop.playrequest.ui.common.Parameter
import com.arjhox.develop.playrequest.ui.common.ParameterItemList

class ParameterListener(
    private val clickListener: (parameterItemList: ParameterItemList) -> Unit,
    private val deleteListener: (parameter: Parameter) -> Unit
) {

    fun onClick(parameter: Parameter, position: Int) = clickListener(ParameterItemList(parameter.key, parameter.value, position))
    fun onDelete(parameter: Parameter) = deleteListener(parameter)

}