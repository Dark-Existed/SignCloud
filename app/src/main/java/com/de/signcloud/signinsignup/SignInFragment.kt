package com.de.signcloud.signinsignup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.de.signcloud.Screen
import com.de.signcloud.navigate
import com.de.signcloud.ui.theme.SignCloudTheme

class SignInFragment : Fragment() {

    private val viewModel: SignInViewModel by viewModels { SignInViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.navigateTo.observe(viewLifecycleOwner) { navigateToEvent ->
            navigateToEvent.getContentIfNotHandled()?.let { navigateTo ->
                navigate(navigateTo, Screen.SignIn)
            }
        }
        return ComposeView(requireContext()).apply {
            setContent {
                SignCloudTheme {
                    SignIn(
                        onNavigationEvent = { event ->
                            when (event) {
                                is SignInEvent.SignIn -> {
                                    viewModel.signIn(event.phone, event.password)
                                }
                                SignInEvent.NavigateBack -> {
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