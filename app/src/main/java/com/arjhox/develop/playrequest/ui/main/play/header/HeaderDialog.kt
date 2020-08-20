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
import com.arjhox.develop.playrequest.R
import com.arjhox.develop.playrequest.databinding.DialogHeaderBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable

class HeaderDialog: DialogFragment() {

    private val viewModel by viewModel<HeaderDialogViewModel>()


    private lateinit var navController: NavController


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DataBindingUtil.inflate<DialogHeaderBinding>(LayoutInflater.from(context), R.layout.dialog_header, null, false)
        binding.let {
            it.lifecycleOwner = this@HeaderDialog
            it.viewModel = viewModel
        }
        binding.buttonOk.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                "header",
                viewModel.header
            )

            dismissAllowingStateLoss()
        }
        viewModel.init()

        return AlertDialog.Builder(activity)
            .setView(binding.root)
            .create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()
    }

}