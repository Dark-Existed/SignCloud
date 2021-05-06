package com.de.signcloud.ui.signinsignup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.de.signcloud.Screen
import com.de.signcloud.navigate
import com.de.signcloud.navigateWithArgs
import com.de.signcloud.ui.theme.SignCloudTheme
import com.de.signcloud.utils.getOrNull

class SignInWithGithubFragment : Fragment() {

    private val viewModel: SignInWithGithubViewModel by viewModels { SignInWithGithubViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpObserver()
        return ComposeView(requireContext()).apply {
            setContent {
                SignCloudTheme {
                    SignInWithGithub(
                        loadUrl = viewModel.url,
                        handlerUrl = {
                            if (viewModel.isRedirectUrl(it)) {
                                val code = viewModel.getGithubCodeFromUrl(it)
                                if (code != null) {
                                    viewModel.signInWithGithub(code)
                                }
                                true
                            } else {
                                false
                            }
                        }
                    ) { event ->
                        when (event) {
                            is SignInWithGithubEvent.NavigateBack -> {
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
                navigate(navigateTo, Screen.SignInWithGithub)
            }
        }
        viewModel.signInWithGithubLiveData.observe(viewLifecycleOwner) {
            val result = it.getOrNull()
            if (result != null) {
                when (result.code) {
                    200 -> {
                        viewModel.navigateToHome()
                    }
//                    406 -> {
//                        val bundle = Bundle()
//                        bundle.putInt("githubId", result.data!!.githubId!!)
//                        navigateWithArgs(Screen.BindPhone, Screen.SignInWithGithub, bundle)
//                    }
                }
            }
        }
    }

}