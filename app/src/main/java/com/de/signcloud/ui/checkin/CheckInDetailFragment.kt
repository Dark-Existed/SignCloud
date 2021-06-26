package com.de.signcloud.ui.checkin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.de.signcloud.R
import com.de.signcloud.bean.CheckInInfo
import com.de.signcloud.ui.theme.SignCloudTheme
import com.de.signcloud.utils.getOrNull

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
        val checkInInfo = arguments?.getSerializable("CheckInInfo") as CheckInInfo
        viewModel.getStudentCheckInStatus(checkInInfo.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpObserver()
        val checkInInfo = arguments?.getSerializable("CheckInInfo") as CheckInInfo
        return ComposeView(requireContext()).apply {
            setContent {
                val isEnable = viewModel.isEnable.observeAsState()
                val isFinishCheckInSuccess = viewModel.isFinishCheckInSuccess.observeAsState()
                val checkInStatusList = viewModel.studentCheckInStatus.observeAsState()
                SignCloudTheme {
                    CheckInDetail(
                        isEnable = (isEnable.value
                            ?: true) && (checkInInfo.isFinished == 0) && (isFinishCheckInSuccess.value == false),
                        checkInList = checkInStatusList.value?.getOrNull()?.checkInList
                            ?: emptyList(),
                        uncheckInList = checkInStatusList.value?.getOrNull()?.uncheckInList
                            ?: emptyList()
                    ) { event ->
                        when (event) {
                            is CheckInDetailEvent.NavigateBack -> findNavController().popBackStack()
                            is CheckInDetailEvent.RefreshList -> {
                                viewModel.getStudentCheckInStatus(checkInInfo.id)
                            }
                            is CheckInDetailEvent.FinishCheckIn -> {
                                viewModel.finishCheckIn(checkInInfo.id)
                            }
                        }
                    }
                }
            }
        }
    }


    private fun setUpObserver() {
        viewModel.finishCheckInResult.observe(viewLifecycleOwner) { finishCheckInResult ->
            val result = finishCheckInResult.getOrNull()
            if (result != null) {
                when (result.code) {
                    200 -> {
                        viewModel.isFinishCheckInSuccess.value = true
                        Toast.makeText(
                            requireContext(),
                            requireContext().getString(R.string.finish_success),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    400 -> {
                        viewModel.isFinishCheckInSuccess.value = true
                        Toast.makeText(
                            requireContext(),
                            requireContext().getString(R.string.check_in_has_finish),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.network_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}