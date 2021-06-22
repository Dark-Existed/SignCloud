package com.de.signcloud.ui.checkin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.de.signcloud.ui.theme.SignCloudTheme

class CreateCheckInFragment : Fragment() {

    val viewModel: CreateCheckInViewModel by viewModels { CreateCheckInViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bundle = arguments
        val courseCode = bundle?.getString("CourseCode") ?: ""
        return ComposeView(requireContext()).apply {
            setContent {
                SignCloudTheme {
                    CreateCheckIn {
                        when (it) {
                            
                        }
                    }
                }
            }
        }
    }
}