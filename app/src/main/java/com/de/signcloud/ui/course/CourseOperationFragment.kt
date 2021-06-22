package com.de.signcloud.ui.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.de.signcloud.R
import com.de.signcloud.Screen
import com.de.signcloud.navigate
import com.de.signcloud.ui.theme.SignCloudTheme

class CourseOperationFragment : Fragment() {

    val viewModel: CourseOperationViewModel by viewModels { CourseOperationViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpObserver()
        val bundle = arguments
        return ComposeView(requireContext()).apply {
            setContent {
                SignCloudTheme {
                    CourseOperation(isStudent = viewModel.isStudent) {
                        when (it) {
                            is CourseOperationEvent.NavigateBack -> {
                                findNavController().popBackStack()
                            }
                            is CourseOperationEvent.NavigateToCreateCheckIn -> {
                                findNavController().popBackStack()
                                findNavController().navigate(
                                    R.id.create_check_in_fragment,
                                    bundle
                                )
                            }
                            is CourseOperationEvent.NavigateToCheckIn -> {
                                findNavController().popBackStack()
                                findNavController().navigate(
                                    R.id.check_in_fragment,
                                    bundle
                                )
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
                navigate(navigateTo, Screen.CourseOperation)
            }
        }
    }

}