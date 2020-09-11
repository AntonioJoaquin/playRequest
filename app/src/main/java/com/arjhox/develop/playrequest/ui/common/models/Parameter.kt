package com.arjhox.develop.playrequest.ui.common.models

import java.io.Serializable

interface ParameterModel: Serializable {
    var key: String
    var value: String
}

data class Parameter(
    override var key: String = "",
    override var value: String = ""
): ParameterModel

data class ParameterItemList(
    override var key: String,
    override var value: String,
    val position: Int
): ParameterModel