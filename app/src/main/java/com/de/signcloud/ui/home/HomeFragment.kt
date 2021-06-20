package com.de.signcloud.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.de.signcloud.Screen
import com.de.signcloud.navigate
import com.de.signcloud.ui.theme.SignCloudTheme
import com.de.signcloud.utils.getOrNull
import com.google.accompanist.insets.ProvideWindowInsets

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels { HomeViewModelFactory() }

    override fun onResume() {
        super.onResume()
        if (viewModel.isStudent) {
            viewModel.updateJoinedCourseList()
        } else {
            viewModel.updateCreateCourseList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpObserver()
        return ComposeView(requireContext()).apply {
            setContent {
                val courseCreateList = viewModel.courseCreateList.observeAsState()
                val courseJoinedList = viewModel.courseJoinedList.observeAsState()
                ProvideWindowInsets {
                    SignCloudTheme {
                        Home(
                            isStudent = viewModel.isStudent,
                            courseCreateList = courseCreateList.value?.getOrNull()?.courses
                                ?: emptyList(),
                            courseJoinedList = courseJoinedList.value?.getOrNull() ?: emptyList()
                        ) { event ->
                            when (event) {
                                HomeEvent.NavigateToCreateCourse -> viewModel.navigateToCreateCourse()
                                HomeEvent.NavigateToJoinCourse -> viewModel.navigateToJoinCourse()
                                HomeEvent.NavigateToChangeRole -> viewModel.navigateToChangeRole()
                                HomeEvent.SignOut -> {
                                    viewModel.signOut()
                                    activity?.finish()
                                }
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
                navigate(navigateTo, Screen.Home)
            }
        }
    }

}