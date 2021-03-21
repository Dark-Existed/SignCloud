package com.de.signcloud.signinsignup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
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
                    SignUp(
                        onNavigationEvent = { event ->
                            when (event) {
                                is SignUpEvent.SignUp -> {
                                    viewModel.signUp(event.phone, event.password)
                                }
                                SignUpEvent.NavigateBack -> {
                                    activity?.onBackPressedDispatcher?.onBackPressed()
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}