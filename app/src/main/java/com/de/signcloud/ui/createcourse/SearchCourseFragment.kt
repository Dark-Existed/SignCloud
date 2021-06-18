package com.de.signcloud.ui.createcourse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.de.signcloud.Screen
import com.de.signcloud.navigate
import com.de.signcloud.ui.theme.SignCloudTheme
import com.de.signcloud.utils.getOrNull

class SearchCourseFragment : Fragment() {

    private val viewModel: SearchCourseViewModel by viewModels { SearchCourseViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpObserver()
        return ComposeView(requireContext()).apply {
            setContent {
                SignCloudTheme {
                    SearchCourse { event ->
                        when (event) {
                            is SearchCourseEvent.NavigateBack -> {
                                findNavController().popBackStack()
                            }
                            is SearchCourseEvent.OnSearchCourse -> {
                                viewModel.getCourse(event.code)
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
                navigate(navigateTo, Screen.SearchCourse)
            }
        }
        viewModel.course.observe(viewLifecycleOwner) { searchCourseResult ->
            val result = searchCourseResult.getOrNull()
            if (result != null) {
                findNavController().popBackStack()
                val bundle = Bundle()
                bundle.putSerializable("course", result)
                navigate(Screen.SearchCourseResult, Screen.SearchCourse, bundle)
            }
        }
    }
}