package com.de.signcloud.ui.checkin

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.de.signcloud.R
import com.de.signcloud.ui.theme.SignCloudTheme
import com.de.signcloud.utils.getOrNull

class CreateCheckInFragment : Fragment() {

    val viewModel: CreateCheckInViewModel by viewModels()

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        private const val REQUEST_CODE_PERMISSIONS = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpObserver()
        val bundle = arguments
        val courseCode = bundle?.getString("CourseCode") ?: ""
        return ComposeView(requireContext()).apply {
            setContent {
                val addressName = viewModel.addressName.observeAsState()
                SignCloudTheme {
                    CreateCheckIn(locationName = addressName.value ?: "") {
                        when (it) {
                            is CreateCheckInEvent.NavigateBack -> {
                                findNavController().popBackStack()
                            }
                            is CreateCheckInEvent.RefreshLocation -> viewModel.getLocation()
                            is CreateCheckInEvent.CreateOneStepCheckIn -> {
                                val checkInData = CreateCheckInData(courseCode, "oneStep")
                                viewModel.createCheckIn(checkInData)
                            }
                            is CreateCheckInEvent.CreateTimeLimitCheckIn -> {
                                val checkInData =
                                    CreateCheckInData(courseCode, "time", it.minutes.toInt())
                                viewModel.createCheckIn(checkInData)
                            }
                        }
                    }
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (allPermissionsGranted()) {
            viewModel.getLocation()
        } else {
            requestPermissions(
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun setUpObserver() {
        viewModel.createCheckInLivaData.observe(viewLifecycleOwner) { createResult ->
            val result = createResult.getOrNull()
            if (result != null) {
                when (result.code) {
                    200 -> {
                        findNavController().popBackStack()
                        Toast.makeText(
                            requireContext(),
                            requireContext().getString(R.string.create_success),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    400 -> {
                        Toast.makeText(
                            requireContext(),
                            requireContext().getString(R.string.finish_current_check_in),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } else {
                Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.create_fail),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                viewModel.getLocation()
            } else {
                Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.no_location_permission),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


}