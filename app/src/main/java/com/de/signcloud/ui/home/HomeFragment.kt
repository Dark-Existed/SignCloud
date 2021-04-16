package com.de.signcloud.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.de.signcloud.ui.theme.SignCloudTheme
import com.google.accompanist.insets.ProvideWindowInsets

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                ProvideWindowInsets {
                    SignCloudTheme {
                        Home(onSnackSelected = { /*TODO*/ })
                    }
                }
            }
        }
    }
}