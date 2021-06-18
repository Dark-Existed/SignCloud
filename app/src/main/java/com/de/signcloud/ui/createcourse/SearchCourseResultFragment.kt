package com.de.signcloud.ui.createcourse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.de.signcloud.bean.GetCourseByCodeResponse
import com.de.signcloud.ui.theme.SignCloudTheme

class SearchCourseResultFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bundle = arguments
        val course = bundle?.getSerializable("course") as GetCourseByCodeResponse.Course
        return ComposeView(requireContext()).apply {
            setContent {
                SignCloudTheme {
                    SearchCourseResult(course = course) {
                        when (it) {
                            is SearchCourseResultEvent.NavigateBack -> {
                                activity?.onBackPressedDispatcher?.onBackPressed()
                            }
                            is SearchCourseResultEvent.OnJoinCourse -> {

                            }
                        }
                    }
                }
            }
        }
    }

    private fun setUpObserver() {

    }


}