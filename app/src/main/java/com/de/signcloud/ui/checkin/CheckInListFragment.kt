package com.de.signcloud.ui.checkin

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

class CheckInListFragment : Fragment() {

    private val viewModel: CheckInListViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        val bundle = arguments
        val courseCode = bundle?.getString("CourseCode") ?: ""
        viewModel.getCheckInList(courseCode)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val checkInList = viewModel.checkInList.observeAsState()
                SignCloudTheme {
                    CheckInList(
                        checkInList = checkInList.value?.getOrNull() ?: emptyList()
                    ) { event ->
                        when (event) {
                            is CheckInListEvent.NavigateBack -> {
                                findNavController().popBackStack()
                            }
                            is CheckInListEvent.NavigateToCheckInDetail -> {
                                val bundle = Bundle()
                                bundle.putSerializable("CheckInInfo", event.checkInInfo)
                                navigate(Screen.CheckInDetail, Screen.CheckInList, bundle)
                            }
                        }
                    }
                }
            }
        }
    }


}