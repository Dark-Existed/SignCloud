package com.de.signcloud.ui.signinsignup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.de.signcloud.ui.theme.SignCloudTheme

class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModels { SignUpViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                SignCloudTheme {

                    viewModel.validateCodeLiveData.observeAsState()
                    val validateButtonText by viewModel.validateButtonText.observeAsState("")
                    val validateButtonClickable by viewModel.isValidateButtonClickable.observeAsState(true)

                    SignUp(
                        validateButtonText = validateButtonText,
                        validateButtonClickable = validateButtonClickable
                    ) { event ->
                        when (event) {
                            is SignUpEvent.SignUp -> {
                                viewModel.signUp(event.phone, event.password)
                            }
                            is SignUpEvent.GetValidate -> {
                                viewModel.getValidateCode(event.phone)
                            }
                            is SignUpEvent.SignIn -> {

                            }
                            is SignUpEvent.NavigateBack -> {
                                activity?.onBackPressedDispatcher?.onBackPressed()
                            }
                        }
                    }

                }
            }
        }
    }
}