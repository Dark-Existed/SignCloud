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
import com.de.signcloud.bean.CheckInInfo
import com.de.signcloud.ui.theme.SignCloudTheme

class CheckInDetailFragment : Fragment() {

    private val viewModel: CheckInDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val checkInInfo = arguments?.getSerializable("CheckInInfo") as CheckInInfo
        viewModel.setMode(checkInInfo.mode)
        if (checkInInfo.mode == "time") {
            viewModel.beginCountDown(checkInInfo.endTime)
        }
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val checkInInfo = arguments?.getSerializable("CheckInInfo") as CheckInInfo
        return ComposeView(requireContext()).apply {
            setContent {
                val isEnable = viewModel.isEnable.observeAsState()
                SignCloudTheme {
                    CheckInDetail(
                        isEnable = (isEnable.value ?: true) && (checkInInfo.isFinished == 0)
                    ) { event ->
                        when (event) {
                            is CheckInDetailEvent.NavigateBack -> findNavController().popBackStack()

                        }
                    }
                }
            }
        }
    }
}