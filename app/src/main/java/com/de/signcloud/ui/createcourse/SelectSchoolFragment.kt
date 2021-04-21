package com.de.signcloud.ui.createcourse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.de.signcloud.ui.theme.SignCloudTheme

class SelectSchoolFragment : Fragment() {

    private val viewModel: SelectSchoolViewModel by viewModels { SelectSchoolViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                SignCloudTheme {

                }
            }
        }
    }

}