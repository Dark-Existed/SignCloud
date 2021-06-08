package com.de.signcloud.ui.createcourse

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.de.signcloud.ui.theme.SignCloudTheme

class SelectSchoolFragment : Fragment() {

    private val viewModel: CreateCourseViewModel by activityViewModels { CreateCourseViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                SignCloudTheme {
                    val schoolSuggestions = viewModel.schoolsSuggestions.observeAsState()
                    schoolSuggestions.value?.let {
                        SelectSchool(allSchools = it) { school, college ->

                        }
                    }
                }
            }
        }
    }

}