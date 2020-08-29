package com.arjhox.develop.playrequest.ui.main.play

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.arjhox.develop.playrequest.databinding.ItemHeaderBinding
import com.arjhox.develop.playrequest.ui.common.*
import com.arjhox.develop.playrequest.ui.main.play.adapters.HeaderAdapter
import com.arjhox.develop.playrequest.ui.main.play.header.HeaderListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayFragment : Fragment() {

    private val viewModel by viewModel<PlayViewModel>()

    private var binding: FragmentPlayBinding? = null

    private lateinit var navController: NavController
    private var headerModelFromDialog: HeaderModel? = null

    private val headerAdapter = HeaderAdapter(
        HeaderListener(
            clickListener = { header, position ->
                viewModel.openHeaderDialogClicked(HeaderItemList(header.key, header.value, position))
            },
            deleteListener = { viewModel.deleteHeaderFromRequest(it) }
        )
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = container?.bindingInflate(R.layout.fragment_play)

        init()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNavigation(view)
    }


    private fun init() {
        binding?.let {
            it.lifecycleOwner = this@PlayFragment
            it.viewModel = this.viewModel
            it.layoutHeaders.headerAdapter = this.headerAdapter
        }

        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding?.textInputEditTextRequestPath?.doOnTextChanged { text, _, _, _ ->
            viewModel.setRequestPath(text.toString())
        }

        binding?.layoutHeaders?.buttonAddHeader?.setOnClickListener { viewModel.openHeaderDialogClicked() }
    }

    private fun initObservers() {
        viewModel.showToastMessageEvent.observe(viewLifecycleOwner, EventObserver {
            context?.showToast(resources.getString(it))
        })

        initNavigationObservers()
    }

    private fun initNavigationObservers() {
        viewModel.openHeaderDialogEvent.observe(viewLifecycleOwner, EventObserver {
            val action = PlayFragmentDirections.openHeaderDialogAction(it)
            navController.navigate(action)
        })
    }

    private fun initNavigation(view: View) {
        navController = view.findNavController()

        val navBackStackEntry: NavBackStackEntry? = navController.getBackStackEntry(R.id.playFragment)
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME && navBackStackEntry?.savedStateHandle!!.contains(headerKey)) {
                headerModelFromDialog = navBackStackEntry.savedStateHandle.get<Header>(headerKey)
                headerModelFromDialog?.let {
                    if (headerModelFromDialog is Header) {
                        viewModel.insertNewHeaderToRequest(it as Header)
                    } else if (it is HeaderItemList) {
                        viewModel.updateHeaderToRequest(it)
                    }
                }

                navBackStackEntry.savedStateHandle.remove<Header>(headerKey)
            }
        }

        navBackStackEntry?.lifecycle?.addObserver(observer)
    }

}