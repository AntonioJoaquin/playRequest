package com.arjhox.develop.playrequest.ui.main.play.header

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
import com.arjhox.develop.playrequest.databinding.DialogHeaderBinding
import com.arjhox.develop.playrequest.ui.common.headerKey
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeaderDialog: DialogFragment() {

    private val viewModel by viewModel<HeaderDialogViewModel>()
    private val args: HeaderDialogArgs by navArgs()

    private lateinit var navController: NavController
    private lateinit var binding: DialogHeaderBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_header, null, false)
        binding.let {
            it.lifecycleOwner = this@HeaderDialog
            it.viewModel = viewModel
        }

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
        binding.buttonOk.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                headerKey,
                viewModel.getHeaderModel()
            )

            dismissAllowingStateLoss()
        }

        viewModel.init(args.header)
    }

}