package com.de.signcloud.ui.setting

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
import com.de.signcloud.ui.theme.SignCloudTheme
import com.de.signcloud.utils.getOrNull

class SetUserInfoFragment : Fragment() {

    private val viewModel: SetUserInfoModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpObserver()
        return ComposeView(requireContext()).apply {
            setContent {
                SignCloudTheme {
                    SetUserInfo(textState = viewModel.textState) { event ->
                        when (event) {
                            is SetUserInfoEvent.NavigateBack -> {
                                findNavController().popBackStack()
                            }
                            is SetUserInfoEvent.SetUserInfo -> {
                                viewModel.setUserInfo()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setUpObserver() {
        viewModel.setUserInfoResult.observe(viewLifecycleOwner) { setUserInfoResult ->
            val result = setUserInfoResult.getOrNull()
            if (result != null) {
                if (result.code == 200) {
                    findNavController().popBackStack()
                    Toast.makeText(
                        requireContext(),
                        requireContext().getString(R.string.success),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.fail),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}