package com.arjhox.develop.playrequest.ui.common

import java.io.Serializable

// region Header

interface HeaderModel: Serializable {
    var key: String
    var value: String
}

data class Header(
    override var key: String = "",
    override var value: String = ""
): HeaderModel

data class HeaderItemList(
    override var key: String,
    override var value: String,
    val position: Int
): HeaderModel

// endregion


// region Parameter

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

// endregion


data class Request(
    val path: String,
    val headers: Map<String, String> = mapOf(),
    val parameters: Map<String, String> = mapOf()
)