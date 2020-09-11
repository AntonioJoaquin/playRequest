package com.arjhox.develop.playrequest.ui.common.base

import androidx.fragment.app.Fragment
import com.arjhox.develop.domain.common.LoadingState
import com.arjhox.develop.playrequest.ui.common.views.LoadingDialog

abstract class BaseFragment: Fragment() {

    private var _loadingDialog: LoadingDialog? = null
    val loadingDialog: LoadingDialog?
        get() = _loadingDialog


    override fun onDestroy() {
        _loadingDialog?.dismiss()

        super.onDestroy()
    }


    fun manageLoadingState(loadingState: LoadingState) {
        when(loadingState.status) {
            LoadingState.Status.RUNNING -> showLoadingDialog()
            LoadingState.Status.SUCCESS -> hideLoadingDialog()
            LoadingState.Status.FAILED -> hideLoadingDialog()
        }
    }

    private fun showLoadingDialog() {
        _loadingDialog?.dismiss()
        _loadingDialog = LoadingDialog(requireContext())
        _loadingDialog?.let {
            it.setOnCancelListener(null)
            it.setCancelable(false)

            if (!it.isShowing) {
                it.show()
            }
        }
    }

    private fun hideLoadingDialog() {
        _loadingDialog?.hide()
    }

}