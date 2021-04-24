package com.de.signcloud.ui.signinsignup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.de.signcloud.Screen
import com.de.signcloud.navigate
import com.de.signcloud.ui.theme.SignCloudTheme
import com.de.signcloud.utils.getOrNull

class BindPhoneFragment : Fragment() {

    private val viewModel: BindPhoneViewModel by viewModels { BindPhoneViewModelFactory() }

    var githubId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = arguments
        githubId = bundle?.getInt("githubId", 0)!!
        setUpObserver()
        return ComposeView(requireContext()).apply {
            setContent {
                SignCloudTheme {
                    viewModel.validateCodeLiveData.observeAsState()
                    val validateButtonText by viewModel.validateButtonText.observeAsState("")
                    val validateButtonClickable by viewModel.isValidateButtonClickable.observeAsState(
                        true
                    )
                    BindPhone(
                        validateButtonText = validateButtonText,
                        validateButtonClickable = validateButtonClickable
                    ) { event ->
                        when (event) {
                            is BindPhoneEvent.GetValidate -> {
                                viewModel.getValidateCode(event.phone)
                            }
                            is BindPhoneEvent.OnBindPhone -> {
                                viewModel.bindPhone(event, githubId)
                            }
                            is BindPhoneEvent.NavigateBack -> {

                            }
                        }
                    }
                }
            }
        }
    }


    private fun setUpObserver() {
        viewModel.navigateTo.observe(viewLifecycleOwner) { navigateToEvent ->
            navigateToEvent.getContentIfNotHandled()?.let { navigateTo ->
                navigate(navigateTo, Screen.BindPhone)
            }
        }
        viewModel.bindPhoneLiveData.observe(viewLifecycleOwner) {
            val result = it.getOrNull()
            if (result != null) {
                when (result.code) {
                    200 -> {
                        viewModel.navigateToHome()
                    }
                }
            }
        }
    }

}