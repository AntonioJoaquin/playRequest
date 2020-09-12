package com.arjhox.develop.playrequest.ui.main.play.header

import com.arjhox.develop.playrequest.ui.common.models.Header
import com.arjhox.develop.playrequest.ui.common.models.HeaderItemList


class HeaderListener(
    private val clickListener: (headerItemList: HeaderItemList) -> Unit,
    private val deleteListener: (header: Header) -> Unit
) {

    fun onClick(header: Header, position: Int) = clickListener(HeaderItemList(header.key, header.value, position))
    fun onDelete(header: Header) = deleteListener(header)

}