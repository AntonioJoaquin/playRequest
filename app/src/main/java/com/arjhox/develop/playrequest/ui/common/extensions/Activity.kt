package com.arjhox.develop.playrequest.ui.common.extensions

import android.app.Activity
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun <T : ViewDataBinding> Activity.setBindingView(
    @LayoutRes layoutRes: Int
): T = DataBindingUtil.setContentView(this, layoutRes)