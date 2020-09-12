package com.arjhox.develop.playrequest.ui.main.play

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.Observer
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.arjhox.develop.domain.common.requestTypes
import com.arjhox.develop.playrequest.R
import com.arjhox.develop.playrequest.databinding.FragmentPlayBinding
import com.arjhox.develop.playrequest.ui.common.EventObserver
import com.arjhox.develop.playrequest.ui.common.HEADER_KEY
import com.arjhox.develop.playrequest.ui.common.PARAMETER_KEY
import com.arjhox.develop.playrequest.ui.common.base.BaseFragment
import com.arjhox.develop.playrequest.ui.common.extensions.bindingInflate
import com.arjhox.develop.playrequest.ui.common.extensions.showToast
import com.arjhox.develop.playrequest.ui.common.models.*
import com.arjhox.develop.playrequest.ui.main.play.adapters.HeaderAdapter
import com.arjhox.develop.playrequest.ui.main.play.adapters.ParameterAdapter
import com.arjhox.develop.playrequest.ui.main.play.adapters.SimpleSpinnerAdapter
import com.arjhox.develop.playrequest.ui.main.play.header.HeaderListener
import com.arjhox.develop.playrequest.ui.main.play.parameter.ParameterListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayFragment : BaseFragment() {

    private val viewModel by viewModel<PlayViewModel>()

    private var binding: FragmentPlayBinding? = null

    private lateinit var navController: NavController
    private var headerModelFromDialog: HeaderModel? = null
    private var parameterModelFromDialog: ParameterModel? = null

    private val requestTypeAdapter = SimpleSpinnerAdapter(requestTypes)
    private val headerAdapter = HeaderAdapter(
        HeaderListener(
            clickListener = { viewModel.openHeaderDialogClicked(it) },
            deleteListener = { viewModel.deleteHeaderFromRequest(it) }
        )
    )
    private val parameterAdapter = ParameterAdapter(
        ParameterListener(
            clickListener = { viewModel.openParameterDialogClicked(it) },
            deleteListener = { viewModel.deleteParameterFromRequest(it) }
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
            it.requestTypeAdapter = this.requestTypeAdapter
            it.layoutHeaders.headerAdapter = this.headerAdapter
            it.layoutParameters.parameterAdapter = this.parameterAdapter
        }

        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding?.textInputEditTextRequestPath?.doOnTextChanged { text, _, _, _ ->
            viewModel.setRequestPath(text.toString())
        }

        binding?.layoutHeaders?.buttonAddHeader?.setOnClickListener { viewModel.openHeaderDialogClicked() }
        binding?.layoutParameters?.buttonAddParameter?.setOnClickListener { viewModel.openParameterDialogClicked() }
    }

    private fun initObservers() {
        viewModel.showToastMessageEvent.observe(viewLifecycleOwner, EventObserver {
            context?.showToast(resources.getString(it))
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            manageLoadingState(it)
        })

        initNavigationObserver()
    }

    private fun initNavigationObserver() {
        viewModel.navigationEvent.observe(viewLifecycleOwner, EventObserver {
            val action: NavDirections = when (it) {
                is HeaderModel -> PlayFragmentDirections.openHeaderDialogAction(it)
                is ParameterModel -> PlayFragmentDirections.openParameterDialogAction(it)
                else -> PlayFragmentDirections.goToResultActivityAction(it as RequestResponse)
            }

            navController.navigate(action)
        })
    }

    private fun initNavigation(view: View) {
        navController = view.findNavController()

        val navBackStackEntry: NavBackStackEntry? = navController.getBackStackEntry(R.id.playFragment)
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (navBackStackEntry?.savedStateHandle!!.contains(HEADER_KEY)) {
                    getHeader(navBackStackEntry)
                } else if (navBackStackEntry.savedStateHandle.contains(PARAMETER_KEY)) {
                    getParameter(navBackStackEntry)
                }
            }
        }

        navBackStackEntry?.lifecycle?.addObserver(observer)
    }

    private fun getHeader(navBackStackEntry: NavBackStackEntry) {
        headerModelFromDialog = navBackStackEntry.savedStateHandle.get<Header>(HEADER_KEY)
        headerModelFromDialog?.let {
            if (headerModelFromDialog is Header) {
                viewModel.insertNewHeaderToRequest(it as Header)
            } else if (it is HeaderItemList) {
                viewModel.updateHeaderToRequest(it)
            }
        }

        navBackStackEntry.savedStateHandle.remove<Header>(HEADER_KEY)
    }

    private fun getParameter(navBackStackEntry: NavBackStackEntry) {
        parameterModelFromDialog = navBackStackEntry.savedStateHandle.get<Parameter>(PARAMETER_KEY)
        parameterModelFromDialog?.let {
            if (parameterModelFromDialog is Parameter) {
                viewModel.insertNewParameterToRequest(it as Parameter)
            } else if (it is ParameterItemList) {
                viewModel.updateParameterToRequest(it)
            }
        }

        navBackStackEntry.savedStateHandle.remove<Parameter>(PARAMETER_KEY)
    }

}