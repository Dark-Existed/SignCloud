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
import com.de.signcloud.R
import com.de.signcloud.Screen
import com.de.signcloud.bean.SignInResponse
import com.de.signcloud.navigate
import com.de.signcloud.ui.theme.SignCloudTheme
import com.de.signcloud.utils.Result
import com.de.signcloud.utils.getOrNull
import com.de.signcloud.utils.isSuccess

class SignInFragment : Fragment() {

    private val viewModel: SignInViewModel by viewModels { SignInViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpObserver()
        return ComposeView(requireContext()).apply {
            id = R.id.sign_in_fragment
            setContent {
                SignCloudTheme {
                    viewModel.validateCodeLiveData.observeAsState()
                    val validateButtonText by viewModel.validateButtonText.observeAsState("")
                    val validateButtonClickable by viewModel.isValidateButtonClickable.observeAsState(
                        true
                    )

                    SignIn(
                        validateButtonText = validateButtonText,
                        validateButtonClickable = validateButtonClickable
                    ) { event ->
                        when (event) {
                            is SignInEvent.SignInWithPassword -> {
                                viewModel.signInWithPassword(event)
                            }
                            is SignInEvent.GetValidate -> {
                                viewModel.getValidateCode(event.phone)
                            }
                            is SignInEvent.SignInWithValidateCode -> {
                                viewModel.signInWithValidateCode(event)
                            }
                            is SignInEvent.ResetPassword -> {
                                viewModel.navigateToResetPassword()
                            }
                            is SignInEvent.NavigateBack -> {
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
                navigate(navigateTo, Screen.SignIn)
            }
        }
        viewModel.signInWithPasswordLiveData.observe(viewLifecycleOwner, {
            val result = it.getOrNull()
            if (result != null) {
                viewModel.navigateToHome()
            }
        })
        viewModel.signInWithValidateCodeLivaData.observe(viewLifecycleOwner) {
            val result = it.getOrNull()
            if (result != null) {
                viewModel.navigateToHome()
            }
        }
    }

}