package com.de.signcloud.ui.course

import android.Manifest.permission.CAMERA
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.de.signcloud.R
import com.de.signcloud.Screen
import com.de.signcloud.databinding.FragmentScancodeBinding
import com.de.signcloud.navigate
import com.de.signcloud.utils.BarcodeAnalyzer
import com.de.signcloud.utils.getOrNull
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

class ScanCodeFragment : Fragment() {

    private val viewModel: ScanCodeViewModel by viewModels { ScanCodeViewModelFactory() }
    private var processingBarcode = AtomicBoolean(false)

    private lateinit var cameraExecutor: ExecutorService

    private var _binding: FragmentScancodeBinding? = null
    private val binding get() = _binding!!

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScancodeBinding.inflate(inflater, container, false)
        viewModel.progressState.observe(viewLifecycleOwner) {
            binding.scanCodeProgressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.course.observe(viewLifecycleOwner) { searchCourseResult ->
            val result = searchCourseResult.getOrNull()
            if (result != null) {
                when (result.code) {
                    200 -> {
                        findNavController().popBackStack()
                        val bundle = Bundle()
                        bundle.putSerializable("course", result.course)
                        navigate(Screen.SearchCourseResult, Screen.SearchCourse, bundle)
                    }
                    400 -> {
                        Toast.makeText(
                            requireContext(),
                            requireContext().getString(R.string.course_not_found),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    override fun onResume() {
        super.onResume()
        processingBarcode.set(false)
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(
                    binding.scanCodePreviewView.surfaceProvider
                )
            }
            val imageAnalysis = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, BarcodeAnalyzer { barcode ->
                        if (processingBarcode.compareAndSet(false, true)) {
                            viewModel.searchCode(barcode)
                        }
                    })
                }
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(requireContext()))
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
                startCamera()
            } else {
                Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.no_camera_permission),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    override fun onDestroyView() {
        cameraExecutor.shutdown()
        _binding = null
        super.onDestroyView()
    }
}