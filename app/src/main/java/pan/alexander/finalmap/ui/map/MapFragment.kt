package pan.alexander.finalmap.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import pan.alexander.finalmap.R
import pan.alexander.finalmap.databinding.MapFragmentBinding
import pan.alexander.finalmap.domain.entities.MapMarker
import pan.alexander.finalmap.ui.base.BaseFragment
import pan.alexander.finalmap.utils.PermissionUtils
import pan.alexander.finalmap.utils.app
import javax.inject.Inject

private const val NO_TAG = -1

class MapFragment : BaseFragment<MapFragmentBinding>(), OnMapReadyCallback {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: MapViewModel by viewModels(factoryProducer = { factory })

    @Inject
    lateinit var fusedLocation: dagger.Lazy<FusedLocationProviderClient>

    private var map: GoogleMap? = null

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrElse(Manifest.permission.ACCESS_FINE_LOCATION) { false } -> {
                getLastKnownLocation()
            }
            permissions.getOrElse(Manifest.permission.ACCESS_COARSE_LOCATION) { false } -> {
                getLastKnownLocation()
            }
            else -> Unit
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {
        context?.let { context ->
            if (GoogleApiAvailability.getInstance()
                    .isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS
            ) {
                fusedLocation.get()?.lastLocation?.addOnSuccessListener {
                    it?.let {
                        setMarker(it.latitude, it.longitude, "", NO_TAG)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requireContext().app.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMap()
    }

    private fun initMap() {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        checkLocationPermissions()

        observeMapOnClick()

        observeMarkers()

        observeMarkerOnClick()
    }

    private fun checkLocationPermissions() {
        activity?.let { activity ->
            PermissionUtils.checkLocationPermission(
                activity,
                locationPermissionRequest
            ) {
                when (it) {
                    PermissionUtils.LocationPermission.ACCESS_FINE_LOCATION -> {
                        getLastKnownLocation()
                    }
                    PermissionUtils.LocationPermission.ACCESS_COARSE_LOCATION -> {
                        getLastKnownLocation()
                    }
                    PermissionUtils.LocationPermission.NO_PERMISSION -> Unit
                }
            }
        }
    }

    private fun observeMapOnClick() {
        map?.setOnMapClickListener {
            viewModel.addMarker(
                MapMarker(
                    0,
                    it.latitude,
                    it.longitude,
                    "",
                    ""
                )
            )
        }
    }

    private fun observeMarkers() {
        viewModel.getAllMarkers().observe(viewLifecycleOwner) {
            map?.clear()
            it.forEach { marker ->
                setMarker(
                    marker.lat,
                    marker.lon,
                    marker.name,
                    marker.id
                )
            }
        }
    }

    @SuppressLint("PotentialBehaviorOverride")
    private fun observeMarkerOnClick() {
        map?.setOnMarkerClickListener {
            navigateToMarkerDetails(it)
            true
        }
    }

    private fun navigateToMarkerDetails(marker: Marker) {
        (marker.tag as? Int)?.let {
            findNavController().navigate(
                R.id.navigate_to_marker_details,
                bundleOf("markerId" to it)
            )
        }
    }

    private fun setMarker(
        latitude: Double,
        longitude: Double,
        title: String,
        tag: Int
    ) {
        map?.addMarker(
            MarkerOptions()
                .position(LatLng(latitude, longitude))
                .title(title)
        ).also {
            if (tag != NO_TAG) {
                it?.tag = tag
            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): MapFragmentBinding = MapFragmentBinding.inflate(
        inflater,
        container,
        false
    )

}
