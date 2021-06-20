package com.de.signcloud.ui.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.de.signcloud.R
import com.de.signcloud.Screen
import com.de.signcloud.navigate
import com.de.signcloud.ui.theme.SignCloudTheme
import com.de.signcloud.utils.getOrNull

class CreateCourseFragment : Fragment() {

    private val viewModel: CreateCourseViewModel by activityViewModels { CreateCourseViewModelFactory() }

    override fun onResume() {
        super.onResume()
        viewModel.state.initSemesterSelectedText()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpObserver()
        return ComposeView(requireContext()).apply {
            setContent {
                SignCloudTheme {
                    CreateCourse(
                        gradeItems = viewModel.gradeItems.value!!,
                        semesterItems = viewModel.semesterItems.value!!,
                        state = viewModel.state
                    ) { event ->
                        when (event) {
                            is CreateCourseEvent.SelectSchool -> {
                                viewModel.navigateToSelectSchool()
                            }
                            is CreateCourseEvent.OnCourseCreate -> {
                                viewModel.createCourse(event.state)
                            }
                            is CreateCourseEvent.NavigateBack -> {
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
                navigate(navigateTo, Screen.CreateCourse)
            }
        }
        viewModel.createCourseLiveData.observe(viewLifecycleOwner) { createCourseResult ->
            val result = createCourseResult.getOrNull()
            if (result != null) {
                if (result.code == 200) {
                    val args = Bundle()
                    args.putString("courseCode", result.data?.code)
                    args.putString("imageUrl", result.data?.imageUrl)
                    findNavController().popBackStack()
                    navigate(Screen.CreateCourseResult, Screen.CreateCourse, args)
                } else {
                    Toast.makeText(context, context?.getString(R.string.create_course_fail), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, context?.getString(R.string.network_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        viewModel.state.clearState()
        super.onDestroy()
    }
}