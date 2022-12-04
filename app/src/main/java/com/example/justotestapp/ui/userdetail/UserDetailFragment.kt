package com.example.justotestapp.ui.userdetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import coil.load
import com.example.justotestapp.R
import com.example.justotestapp.data.model.User
import com.example.justotestapp.databinding.FragmentUserDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson

class UserDetailFragment : Fragment(R.layout.fragment_user_detail) {

    private lateinit var binding: FragmentUserDetailBinding
    private lateinit var user: User
    private lateinit var mapFragment: SupportMapFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserDetailBinding.bind(view)

        requireArguments().let {
            user = it.getParcelable("user")!!
        }

        binding = FragmentUserDetailBinding.bind(view)
        binding.ivProfile.load("${user.thumbnail}")
        binding.nameUser.text = user.first
        binding.lastnameUser.text = user.last

        Log.i("testLog", "setupObserver result.data -> ${Gson().toJson(user)}")
        val street = user.location
        val state = user.state
        binding.addressUser.text = "$street $state"
    }

    override fun onResume() {
        super.onResume()

        val callback = OnMapReadyCallback { googleMap ->
            val lat = user.latitude
            val long = user.longitude
            val locState = user.state
            val location = LatLng(lat!!.toDouble(), long!!.toDouble())
            val cameraPosition = CameraPosition.Builder()
                .target(location)
                .zoom(4f)
                .bearing(90f)
                .tilt(30f)
                .build()
            googleMap.addMarker(MarkerOptions().position(location).title("Marker in $locState"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }

        mapFragment = childFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(callback)
    }
}
