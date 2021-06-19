package com.de.signcloud.ui.createcourse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.de.signcloud.R
import com.de.signcloud.bean.GetCourseByCodeResponse
import com.de.signcloud.ui.theme.SignCloudTheme
import com.de.signcloud.utils.getOrNull

class SearchCourseResultFragment : Fragment() {

    private val viewModel: SearchCourseResultViewModel by viewModels { SearchCourseResultViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpObserver()
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
                                viewModel.joinCourse(it.code)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setUpObserver() {
        viewModel.joinCourseResult.observe(viewLifecycleOwner) { joinResult ->
            val result = joinResult.getOrNull()
            if (result != null) {
                if (result) {
                    findNavController().popBackStack()
                    Toast.makeText(
                        context,
                        context?.getText(R.string.join_success),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                // TODO: 2021/6/18 add why join fail
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
            }
        }
    }


}