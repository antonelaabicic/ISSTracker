package hr.algebra.isstracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.karumi.dexter.Dexter
import hr.algebra.isstracker.R
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.SphericalUtil
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import hr.algebra.isstracker.LOCATION_CONTENT_URI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_home, container, false)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        Dexter.withActivity(requireActivity())
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    fetchIssLocationAndSetupListener(true)
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    fetchIssLocationAndSetupListener(false)
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            }).check()
    }

    @SuppressLint("PotentialBehaviorOverride")
    private fun fetchIssLocationAndSetupListener(permissionGranted: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            val cursor: Cursor? = requireContext().contentResolver.query(
                LOCATION_CONTENT_URI, null, null, null, null
            )

            cursor?.let {
                val columnIndexLatitude = it.getColumnIndex("latitude")
                val columnIndexLongitude = it.getColumnIndex("longitude")

                if (it.moveToFirst()) {
                    val latitude = it.getDouble(columnIndexLatitude)
                    val longitude = it.getDouble(columnIndexLongitude)

                    val issLatLng = LatLng(latitude, longitude)

                    activity?.runOnUiThread {
                        mMap.addMarker(
                            MarkerOptions()
                                .position(issLatLng)
                                .title("ISS Location")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        )
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(issLatLng, 3f))

                        if (permissionGranted) {
                            mMap.setOnMarkerClickListener { clickedMarker ->
                                if (clickedMarker.title == "ISS Location") {
                                    if (ActivityCompat.checkSelfPermission(
                                            requireContext(),
                                            Manifest.permission.ACCESS_FINE_LOCATION
                                        ) == PackageManager.PERMISSION_GRANTED
                                    ) {
                                        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                                            try {
                                                if (location != null) {
                                                    val userLatLng = LatLng(location.latitude, location.longitude)
                                                    val distanceInMeters = SphericalUtil.computeDistanceBetween(userLatLng, issLatLng)

                                                    val distanceInKilometers = distanceInMeters / 1000

                                                    Toast.makeText(
                                                        requireContext(),
                                                        "You are ${"%.2f".format(distanceInKilometers)} km away from the ISS.",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                            } catch (e: SecurityException) {
                                                Toast.makeText(requireContext(), "Permission denied for location access.", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    } else {
                                        Toast.makeText(requireContext(), "Location permission is required to calculate the distance.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                true
                            }
                        }
                    }
                }
            }
        }
    }
}