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
import androidx.lifecycle.Observer
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.arjhox.develop.playrequest.R
import com.arjhox.develop.playrequest.databinding.FragmentPlayBinding
import com.arjhox.develop.playrequest.ui.common.EventObserver
import com.arjhox.develop.playrequest.ui.common.Header
import com.arjhox.develop.playrequest.ui.common.headerKey
import com.arjhox.develop.playrequest.ui.main.play.adapters.HeaderAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayFragment : Fragment() {

    private val viewModel by viewModel<PlayViewModel>()

    private lateinit var binding: FragmentPlayBinding

    private lateinit var navController: NavController
    private var headerFromDialog: Header? = null

    private val headerAdapter = HeaderAdapter()


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

        init()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNavigation(view)
    }


    private fun init() {
        binding.let {
            it.lifecycleOwner = this@PlayFragment
            it.headerAdapter = this.headerAdapter
            it.viewModel = this.viewModel
        }

        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding.textInputEditTextRequestPath.doOnTextChanged { text, _, _, _ ->
            viewModel.setRequestPath(text.toString())
        }

        binding.buttonAddHeader.setOnClickListener { viewModel.openHeaderDialogClicked() }
    }

    private fun initObservers() {
        viewModel.headers.observe(viewLifecycleOwner, Observer {
            it.let(headerAdapter::submitList)
        })

        viewModel.openHeaderDialog.observe(viewLifecycleOwner, EventObserver {
            val action = PlayFragmentDirections.openHeaderDialogAction(it)
            navController.navigate(action)
        })
    }

    private fun initNavigation(view: View) {
        navController = view.findNavController()

        val navBackStackEntry: NavBackStackEntry? = navController.getBackStackEntry(R.id.playFragment)
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME && navBackStackEntry?.savedStateHandle!!.contains("header")) {
                headerFromDialog = navBackStackEntry.savedStateHandle.get<Header>(headerKey)
                headerFromDialog?.let {
                    viewModel.insertNewHeaderToRequest(it)
                }

                navBackStackEntry.savedStateHandle.remove<Header>(headerKey)
            }
        }

        navBackStackEntry?.lifecycle?.addObserver(observer)
    }

}