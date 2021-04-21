package com.de.signcloud.ui.createcourse

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

class CreateCourseFragment : Fragment() {

    private val viewModel: CreateCourseViewModel by viewModels { CreateCourseViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpObserver()
        return ComposeView(requireContext()).apply {
            setContent {
                SignCloudTheme {
                    CreateCourse(
                        gradeItems = viewModel.gradeItems.value!!,
                        semesterItems = viewModel.semesterItems.value!!
                    ) { event ->
                        when (event) {
                            CreateCourseEvent.NavigateBack -> {
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
    }

}