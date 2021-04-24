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
import com.de.signcloud.R
import com.de.signcloud.Screen
import com.de.signcloud.navigate
import com.de.signcloud.ui.theme.SignCloudTheme
import com.de.signcloud.utils.getOrNull

class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModels { SignUpViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpObserver()

        return ComposeView(requireContext()).apply {
            id = R.id.sign_up_fragment
            setContent {
                SignCloudTheme {
                    viewModel.validateCodeLiveData.observeAsState()
                    val validateButtonText by viewModel.validateButtonText.observeAsState("")
                    val validateButtonClickable by viewModel.isValidateButtonClickable.observeAsState(
                        true
                    )

                    SignUp(
                        validateButtonText = validateButtonText,
                        validateButtonClickable = validateButtonClickable
                    ) { event ->
                        when (event) {
                            is SignUpEvent.GetValidate -> {
                                viewModel.getValidateCode(event.phone)
                            }
                            is SignUpEvent.SignUp -> {
                                viewModel.signUp(event)
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

    private fun setUpObserver() {
        viewModel.navigateTo.observe(viewLifecycleOwner) { navigateToEvent ->
            navigateToEvent.getContentIfNotHandled()?.let { navigateTo ->
                navigate(navigateTo, Screen.SignUp)
            }
        }
        viewModel.signUpLiveData.observe(viewLifecycleOwner) {
            val result = it.getOrNull()
            if (result != null) {
                if (result.code == 200) {
                    viewModel.navigateToHome()
                }
            }
        }
    }

}