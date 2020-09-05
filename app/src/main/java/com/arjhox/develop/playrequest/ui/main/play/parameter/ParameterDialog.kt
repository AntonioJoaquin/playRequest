package com.arjhox.develop.playrequest.ui.main.play.parameter

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.arjhox.develop.playrequest.R
import com.arjhox.develop.playrequest.databinding.DialogParameterBinding
import com.arjhox.develop.playrequest.ui.common.EventObserver
import com.arjhox.develop.playrequest.ui.common.PARAMETER_KEY
import org.koin.androidx.viewmodel.ext.android.viewModel

class ParameterDialog: DialogFragment() {

    private val viewModel by viewModel<ParameterDialogViewModel>()
    private val args: ParameterDialogArgs by navArgs()

    private lateinit var navController: NavController
    private lateinit var binding: DialogParameterBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_parameter, null, false)

        init()

        return AlertDialog.Builder(activity)
            .setView(binding.root)
            .create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()
    }


    private fun init() {
        binding.let {
            it.lifecycleOwner = this@ParameterDialog
            it.viewModel = viewModel
        }

        viewModel.init(args.parameter)

        initNavigationObserver()
    }

    private fun initNavigationObserver() {
        viewModel.closeDialogWithConfirmationEvent.observe(this@ParameterDialog, EventObserver {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                PARAMETER_KEY,
                it
            )

            dismissAllowingStateLoss()
        })
    }

}