package com.arjhox.develop.playrequest.ui.result.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.arjhox.develop.playrequest.R
import com.arjhox.develop.playrequest.databinding.FragmentResultBinding
import com.arjhox.develop.playrequest.ui.common.base.BaseFragment
import com.arjhox.develop.playrequest.ui.common.extensions.bindingInflate
import com.arjhox.develop.playrequest.ui.result.viewmodel.ResultViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResultFragment: BaseFragment() {

    private val viewModel by viewModel<ResultViewModel>()
    private val arguments by navArgs<ResultFragmentArgs>()

    private var binding: FragmentResultBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = container?.bindingInflate(R.layout.fragment_result)

        init()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    private fun init() {
        binding?.let {
            it.lifecycleOwner = this@ResultFragment
            it.viewModel = viewModel
        }

        viewModel.initRequestResponseVariables(arguments.response)
    }

}