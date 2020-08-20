package com.arjhox.develop.playrequest.ui.main.play

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.arjhox.develop.playrequest.R
import com.arjhox.develop.playrequest.databinding.FragmentPlayBinding
import com.arjhox.develop.playrequest.ui.common.Header
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayFragment : Fragment() {

    private val viewModel by viewModel<PlayViewModel>()

    private lateinit var binding: FragmentPlayBinding

    private lateinit var navController: NavController
    private var headerFromDialog: Header? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_play,
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()
        val navBackStackEntry: NavBackStackEntry? = navController.getBackStackEntry(R.id.playFragment)
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME
                &&
                navBackStackEntry?.savedStateHandle!!.contains("header")
            ) {
                headerFromDialog = navBackStackEntry.savedStateHandle.get<Header>("header")
                binding.textViewRequestResponse.visibility = View.VISIBLE
                binding.textViewRequestResponse.text = headerFromDialog.toString()
            }
        }

        navBackStackEntry?.lifecycle?.addObserver(observer)
    }


    private fun init() {
        binding.textInputEditTextRequestPath.doOnTextChanged { text, _, _, _ ->
            viewModel.setRequestPath(text.toString())
        }

        binding.buttonAddHeader.setOnClickListener { navController.navigate(R.id.headerDialog) }
    }

}