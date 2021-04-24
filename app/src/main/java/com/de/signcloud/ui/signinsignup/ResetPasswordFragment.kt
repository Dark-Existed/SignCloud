package com.de.signcloud.ui.signinsignup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.de.signcloud.Screen
import com.de.signcloud.navigate
import com.de.signcloud.ui.theme.SignCloudTheme
import com.de.signcloud.utils.getOrNull

class ResetPasswordFragment : Fragment() {

    private val viewModel: ResetPasswordViewModel by viewModels { ResetPasswordViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = arguments
        val phone = bundle?.getString("phone", "")
        setUpObserver()
        return ComposeView(requireContext()).apply {
            setContent {
                SignCloudTheme {
                    viewModel.validateCodeLiveData.observeAsState()
                    val validateButtonText by viewModel.validateButtonText.observeAsState("")
                    val validateButtonClickable by viewModel.isValidateButtonClickable.observeAsState(
                        true
                    )
                    val phoneState = remember { phone }
                    ResetPassword(
                        initPhone = phoneState!!,
                        validateButtonText = validateButtonText,
                        validateButtonClickable = validateButtonClickable
                    ) { event ->
                        when (event) {
                            is ResetPasswordEvent.GetValidate -> {
                                viewModel.getValidateCode(event.phone)
                            }
                            is ResetPasswordEvent.ResetPassword -> {
                                viewModel.resetPassword(event)
                            }
                            is ResetPasswordEvent.NavigateBack -> {
                                activity?.onBackPressedDispatcher?.onBackPressed()
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
                navigate(navigateTo, Screen.ResetPassword)
            }
        }
        viewModel.resetPasswordLiveData.observe(viewLifecycleOwner) {
            val result = it.getOrNull()
            if (result != null) {
                when (result.code) {
                    200 -> {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

}