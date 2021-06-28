package com.de.signcloud.ui.course

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
import com.baidu.mapapi.utils.DistanceUtil
import com.de.signcloud.R
import com.de.signcloud.Screen
import com.de.signcloud.navigate
import com.de.signcloud.ui.theme.SignCloudTheme
import com.de.signcloud.utils.getOrNull

class CourseOperationFragment : Fragment() {

    val viewModel: CourseOperationViewModel by viewModels { CourseOperationViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpObserver()
        val bundle = arguments
        val courseCode = bundle?.getString("CourseCode") ?: ""
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
                                navigate(Screen.CreateCheckIn, Screen.CourseOperation, bundle)
                            }
                            is CourseOperationEvent.NavigateToCheckInList -> {
                                findNavController().popBackStack()
                                navigate(Screen.CheckInList, Screen.CourseOperation, bundle)
                            }
                            is CourseOperationEvent.NavigateToCheckIn -> {
                                viewModel.getCurrentCheckIn(courseCode)
                            }
                            is CourseOperationEvent.NavigateToCourseStudent -> {
                                findNavController().popBackStack()
                                navigate(Screen.CourseStudent, Screen.CourseOperation, bundle)
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
        viewModel.currentCheckIn.observe(viewLifecycleOwner) { currentCheckInResult ->
            val result = currentCheckInResult.getOrNull()
            if (result != null) {
                findNavController().popBackStack()
                val bundle = Bundle()
                bundle.putSerializable("CheckInInfo", result.data)
                navigate(Screen.CheckIn, Screen.CourseOperation, bundle)
            } else {
                Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.no_check_in_available),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}