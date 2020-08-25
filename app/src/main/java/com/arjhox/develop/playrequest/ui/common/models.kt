package com.arjhox.develop.playrequest.ui.common

import java.io.Serializable

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