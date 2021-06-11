package com.de.signcloud.ui.signinsignup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.de.signcloud.R
import com.de.signcloud.Screen
import com.de.signcloud.navigate
import com.de.signcloud.ui.theme.SignCloudTheme
import com.de.signcloud.utils.getOrNull

class WelcomeFragment : Fragment() {

    private val viewModel: WelcomeViewModel by viewModels { WelcomeViewModelFactory() }

    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpObserver()

        return ComposeView(requireContext()).apply {
            setContent {
                SignCloudTheme {
                    WelcomeScreen { event ->
                        when (event) {
                            is WelcomeEvent.SignInSignUp -> {
                                bundle.putString("phone", event.phone)
                                viewModel.handleContinue(event.phone)
                            }
                            is WelcomeEvent.SignInWithGitHub -> viewModel.navigateToSignInWithGithub()
                        }
                    }
                }
            }
        }
    }

    private fun setUpObserver() {
        viewModel.navigateTo.observe(viewLifecycleOwner) { navigateToEvent ->
            navigateToEvent.getContentIfNotHandled()?.let { navigateTo ->
                navigate(navigateTo, Screen.Welcome)
            }
        }
        viewModel.isPhoneExistLivaData.observe(viewLifecycleOwner) { isPhoneExistResult ->
            val result = isPhoneExistResult.getOrNull()
            if (result != null) {
                if (result) {
                    findNavController().popBackStack()
                    navigate(Screen.SignIn, Screen.Welcome, bundle)
                } else {
                    findNavController().popBackStack()
                    navigate(Screen.SignUp, Screen.Welcome, bundle)
                }
            } else {
                Toast.makeText(context, context?.getString(R.string.network_error), Toast.LENGTH_SHORT).show()
            }
        }
    }
}

