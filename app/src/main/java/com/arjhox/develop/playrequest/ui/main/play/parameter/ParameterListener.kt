package com.arjhox.develop.playrequest.ui.main.play.parameter

import com.arjhox.develop.playrequest.ui.common.Parameter

class ParameterListener(
    private val clickListener: (parameter: Parameter, position: Int) -> Unit,
    private val deleteListener: (parameter: Parameter) -> Unit
) {

    fun onClick(parameter: Parameter, position: Int) = clickListener(parameter, position)
    fun onDelete(parameter: Parameter) = deleteListener(parameter)

}