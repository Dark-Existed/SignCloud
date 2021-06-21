package com.de.signcloud.ui.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.de.signcloud.bean.Course
import com.de.signcloud.ui.theme.SignCloudTheme

class CourseDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bundle = arguments
        val course = bundle?.getSerializable("course") as Course

        return ComposeView(requireContext()).apply {
            setContent {
                SignCloudTheme {
                    CourseDetail(course = course) {
                        when (it) {
                            CourseDetailEvent.NavigateBack -> {
                                findNavController().popBackStack()
                            }
                        }
                    }
                }
            }
        }
    }
}