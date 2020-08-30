package com.arjhox.develop.playrequest.ui.main.play.parameter

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.arjhox.develop.playrequest.R
import com.arjhox.develop.playrequest.databinding.DialogParameterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ParameterDialog: DialogFragment() {

    private val viewModel by viewModel<ParameterDialogViewModel>()

    private lateinit var binding: DialogParameterBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_parameter, null, false)

        init()

        return AlertDialog.Builder(activity)
            .setView(binding.root)
            .create()
    }


    private fun init() {
        binding.let {
            it.lifecycleOwner = this@ParameterDialog
            it.viewModel = viewModel
        }
    }

}