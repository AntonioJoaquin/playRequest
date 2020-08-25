package com.arjhox.develop.playrequest.ui.main.play.header

import com.arjhox.develop.playrequest.ui.common.Header

class HeaderListener(
    private val clickListener: (header: Header, position: Int) -> Unit,
    private val deleteListener: (header: Header) -> Unit
) {

    fun onClick(header: Header, position: Int) = clickListener(header, position)
    fun onDelete(header: Header) = deleteListener(header)

}