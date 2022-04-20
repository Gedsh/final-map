package pan.alexander.finalmap.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.Window
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import pan.alexander.finalmap.R
import pan.alexander.finalmap.databinding.LocationPermissionRationaleDialogBinding

object PermissionUtils {
    fun checkLocationPermission(
        activity: Activity?,
        locationPermissionRequest: ActivityResultLauncher<Array<out String>>,
        block: (permission: LocationPermission) -> Unit
    ) {

        activity?.let {
            when {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    block(LocationPermission.ACCESS_FINE_LOCATION)
                }

                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    block(LocationPermission.ACCESS_COARSE_LOCATION)
                }

                shouldShowRequestPermissionRationale(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) -> {
                    showLocationPermissionRationaleDialog(it, locationPermissionRequest, block)
                }

                shouldShowRequestPermissionRationale(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) -> {
                    showLocationPermissionRationaleDialog(it, locationPermissionRequest, block)
                }

                else -> {
                    showLocationPermissionRationaleDialog(it, locationPermissionRequest, block)
                }
            }
        }
    }

    private fun requestLocationPermission(
        requestPermissionLauncher: ActivityResultLauncher<Array<out String>>
    ) {
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun showLocationPermissionRationaleDialog(
        activity: Activity,
        requestPermissionLauncher: ActivityResultLauncher<Array<out String>>,
        block: (permission: LocationPermission) -> Unit
    ) {
        val binding =
            LocationPermissionRationaleDialogBinding.inflate(LayoutInflater.from(activity))

        val dialog = AlertDialog.Builder(activity)
            .setView(binding.root)
            .show()

        dialog.window?.apply {
            setBackgroundDrawable(
                ContextCompat.getDrawable(activity, R.drawable.bg_permission_dialog_rationale)
            )

            changeDialogWidth(activity, dialog)
        }

        binding.rationaleDialogAllowWhenUsingButton.setOnClickListener {
            requestLocationPermission(requestPermissionLauncher)
            dialog?.dismiss()
        }
        binding.rationaleDialogAllowOnceButton.setOnClickListener {
            requestLocationPermission(requestPermissionLauncher)
            dialog?.dismiss()
        }
        binding.rationaleDialogDoNotAllowButton.setOnClickListener {
            block(LocationPermission.NO_PERMISSION)
            dialog?.dismiss()
        }

    }

    private fun changeDialogWidth(activity: Activity, dialog: AlertDialog?) {
        val displayRectangle = Rect()

        val window: Window = activity.window
        window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
        dialog?.window?.setLayout(
            (displayRectangle.width() * 0.8f).toInt(), dialog.window?.attributes?.height ?: 350
        )
    }

    enum class LocationPermission {
        NO_PERMISSION,
        ACCESS_FINE_LOCATION,
        ACCESS_COARSE_LOCATION
    }
}
