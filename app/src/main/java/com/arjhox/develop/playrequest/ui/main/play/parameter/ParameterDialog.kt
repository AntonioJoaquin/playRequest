package com.arjhox.develop.playrequest.ui.main.play.parameter

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.arjhox.develop.playrequest.R
import com.arjhox.develop.playrequest.databinding.DialogParameterBinding

class ParameterDialog: DialogFragment() {

    private lateinit var binding: DialogParameterBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_parameter, null, false)

        return AlertDialog.Builder(activity)
            .setView(binding.root)
            .create()
    }

}