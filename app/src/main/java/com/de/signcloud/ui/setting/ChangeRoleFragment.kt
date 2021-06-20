package com.de.signcloud.ui.setting

import android.os.Bundle
import android.util.Log
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

class ChangeRoleFragment : Fragment() {

    val viewModel: ChangeRoleViewModel by viewModels { ChangeRoleViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpObserver()
        val options = listOf(
            context?.getString(R.string.student) ?: "",
            context?.getString(R.string.teacher) ?: ""
        )
        return ComposeView(requireContext()).apply {
            setContent {
                SignCloudTheme {
                    ChangeRole(options = options, curRoleIndex = viewModel.initSelectedIndex()) {
                        when (it) {
                            is ChangeRoleEvent.NavigateBack -> {
                                findNavController().popBackStack()
                            }
                            is ChangeRoleEvent.ChangeRole -> {
                                if (viewModel.initSelectedIndex() != it.roleType) {
                                    viewModel.changeRole(it.roleType)
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private fun setUpObserver() {
        viewModel.roleChange.observe(viewLifecycleOwner) { changeRoleResult ->
            val result = changeRoleResult.getOrNull()
            if (result != null) {
                if (result.code == 200) {
                    Toast.makeText(
                        context,
                        context?.getString(R.string.success),
                        Toast.LENGTH_SHORT
                    ).show()

                    findNavController().popBackStack()
                }
            } else {
                Toast.makeText(context, context?.getString(R.string.fail), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}