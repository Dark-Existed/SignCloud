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
import androidx.navigation.fragment.findNavController
import com.baidu.mapapi.model.LatLng
import com.de.signcloud.R
import com.de.signcloud.bean.CheckInInfo
import com.de.signcloud.ui.theme.SignCloudTheme
import com.de.signcloud.utils.getOrNull

class CheckInFragment : Fragment() {

    private val viewModel: CheckInViewModel by viewModels()


    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        private const val REQUEST_CODE_PERMISSIONS = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = arguments
        val checkInInfo = bundle?.getSerializable("CheckInInfo") as CheckInInfo
        super.onCreate(savedInstanceState)
        if (allPermissionsGranted()) {
            viewModel.getLocation()
        } else {
            requestPermissions(
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        if (checkInInfo.mode == "time") {
            viewModel.beginCountDown(checkInInfo.endTime)
        }
        viewModel.setDestination(
            LatLng(checkInInfo.latitude.toDouble(), checkInInfo.longitude.toDouble())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bundle = arguments
        val checkInInfo = bundle?.getSerializable("CheckInInfo") as CheckInInfo
        setUpObserver()
        return ComposeView(requireContext()).apply {
            setContent {
                val addressName = viewModel.addressName.observeAsState("")
                val countDownText = viewModel.countDownText.observeAsState("")
                val countDownInt = viewModel.countDownInt.observeAsState(-1)
                SignCloudTheme {
                    CheckIn(
                        checkInInfo = checkInInfo,
                        locationName = addressName.value,
                        countDownText = countDownText.value,
                        countDownInt = countDownInt.value
                    ) { event ->
                        when (event) {
                            is CheckInEvent.NavigateBack -> findNavController().popBackStack()
                            is CheckInEvent.RefreshLocation -> viewModel.getLocation()
                            is CheckInEvent.CheckIn -> {
                                viewModel.checkIn(checkInInfo.id)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setUpObserver() {
        viewModel.checkInResult.observe(viewLifecycleOwner) { checkInResult ->
            val result = checkInResult.getOrNull()
            if (result != null) {
                findNavController().popBackStack()
                Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.check_in_success),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.check_in_fail),
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