package com.arjhox.develop.playrequest.ui.result.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.navArgs
import com.arjhox.develop.playrequest.R
import com.arjhox.develop.playrequest.databinding.ActivityResultBinding
import com.arjhox.develop.playrequest.ui.common.extensions.setBindingView
import com.arjhox.develop.playrequest.ui.result.viewmodel.ResultViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResultActivity : AppCompatActivity() {

    private val viewModel by viewModel<ResultViewModel>()
    private val binding: ActivityResultBinding by lazy {
        setBindingView<ActivityResultBinding>(R.layout.activity_result).apply {
            lifecycleOwner = this@ResultActivity
        }
    }
    private val arguments by navArgs<ResultActivityArgs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = this.viewModel

        viewModel.initRequestResponseVariables(arguments.response)
    }
    
}