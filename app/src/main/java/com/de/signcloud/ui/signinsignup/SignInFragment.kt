package com.de.signcloud.ui.signinsignup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.de.signcloud.R
import com.de.signcloud.Screen
import com.de.signcloud.bean.SignInResponse
import com.de.signcloud.navigate
import com.de.signcloud.navigateWithArgs
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
        val bundle = arguments
        val initPhone = bundle?.getString("phone", "")
        viewModel.phone.value = initPhone
        setUpObserver()
        return ComposeView(requireContext()).apply {
            id = R.id.sign_in_fragment
            setContent {
                SignCloudTheme {
                    viewModel.validateCodeLiveData.observeAsState()
                    viewModel.phone.observeAsState()
                    val validateButtonText by viewModel.validateButtonText.observeAsState("")
                    val validateButtonClickable by viewModel.isValidateButtonClickable.observeAsState(
                        true
                    )
                    SignIn(
                        initPhone = viewModel.phone.value!!,
                        validateButtonText = validateButtonText,
                        validateButtonClickable = validateButtonClickable,
                        onPhoneChange = {
                            viewModel.phone.value = it
                        }
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
                                val args = Bundle()
                                args.putString("phone", event.phone)
                                navigateWithArgs(Screen.ResetPassword, Screen.SignUp, args)
                            }
                            is SignInEvent.NavigateBack -> {
                                findNavController().popBackStack()
                                navigate(Screen.Welcome, Screen.SignIn)
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
                when (result.code) {
                    200 -> viewModel.navigateToHome()
                    400 -> Toast.makeText(context, context?.getString(R.string.password_error), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, context?.getString(R.string.network_error), Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.signInWithValidateCodeLivaData.observe(viewLifecycleOwner) {
            val result = it.getOrNull()
            if (result != null) {
                when (result.code) {
                    200 -> viewModel.navigateToHome()
                    400 -> Toast.makeText(context, context?.getString(R.string.validate_code_error), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, context?.getString(R.string.network_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

}