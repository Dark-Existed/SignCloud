package com.de.signcloud.ui.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.de.signcloud.ui.theme.SignCloudTheme

class CreateCourseResultFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bundle = arguments
        val courseCode = bundle?.getString("courseCode", "")
        val imageUrl = bundle?.getString("imageUrl", "")
        return ComposeView(requireContext()).apply {
            setContent {
                SignCloudTheme {
                    CreateResult(
                        courseCode = courseCode!!,
                        imageUrl = imageUrl!!
                    ) { event ->
                        when (event) {
                            is CreateResultEvent.NavigateBack -> {
                                findNavController().popBackStack()
                            }
                        }
                    }
                }
            }
        }
    }

}