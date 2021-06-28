package com.de.signcloud.ui.checkin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.de.signcloud.ui.theme.SignCloudTheme
import com.de.signcloud.utils.getOrNull

class CourseStudentFragment : Fragment() {

    private val viewModel: CourseStudentViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        val bundle = arguments
        val courseCode = bundle?.getString("CourseCode") ?: ""
        viewModel.getCourseStudent(courseCode)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bundle = arguments
        val courseCode = bundle?.getString("CourseCode") ?: ""
        return ComposeView(requireContext()).apply {
            setContent {
                val courseMembers = viewModel.courseMembers.observeAsState()
                SignCloudTheme {
                    CourseStudent(
                        courseMembers = courseMembers.value?.getOrNull()
                            ?: emptyList()
                    ) { event ->
                        when (event) {
                            is CourseStudentEvent.NavigateBack -> {
                                findNavController().popBackStack()
                            }
                            is CourseStudentEvent.RefreshList -> {
                                viewModel.getCourseStudent(courseCode)
                            }
                        }
                    }
                }
            }
        }
    }
}