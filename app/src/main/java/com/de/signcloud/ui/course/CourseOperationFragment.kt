package com.de.signcloud.ui.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.de.signcloud.ui.theme.SignCloudTheme

class CourseOperationFragment : Fragment() {

    val viewModel: CourseOperationViewModel by viewModels { CourseOperationViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SignCloudTheme {
                    CourseOperation(isStudent = viewModel.isStudent) {
                        when (it) {
                            
                        }
                    }
                }
            }
        }
    }
}