package com.arjhox.develop.playrequest.ui.main.play

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.arjhox.develop.playrequest.R
import com.arjhox.develop.playrequest.databinding.PlayFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayFragment : Fragment() {

    private val viewModel by viewModel<PlayViewModel>()

    private lateinit var binding: PlayFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.play_fragment,
            container,
            false
        )
        binding.let {
            it.lifecycleOwner = this@PlayFragment
            it.viewModel = this.viewModel
        }

        init()

        return binding.root
    }


    private fun init() {
        binding.textInputEditTextRequest.doOnTextChanged { _, _, _, count ->
            viewModel.setCanShowActionFab(count > 0)
        }
    }

}