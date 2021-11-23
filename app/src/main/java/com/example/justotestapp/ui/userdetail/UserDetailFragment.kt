package com.example.justotestapp.ui.userdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.justotestapp.R
import com.example.justotestapp.databinding.FragmentUserDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class UserDetailFragment : Fragment(R.layout.fragment_user_detail) {

    private lateinit var binding: FragmentUserDetailBinding
    private val args by navArgs<UserDetailFragmentArgs>()

    private val callback = OnMapReadyCallback { googleMap ->
        val lat = args.latitude
        val long = args.longitude
        val locState = args.state
        val location = LatLng(long.toDouble(), lat.toDouble())
        googleMap.addMarker(MarkerOptions().position(location).title("Marker in $locState"))
        //googleMap.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserDetailBinding.bind(view)

        binding = FragmentUserDetailBinding.bind(view)
        binding.ivProfile.load("${args.photouser}")
        binding.nameUser.text = args.nameuser
        binding.genderUser.text = args.gender
        binding.mailUser.text = args.email
        binding.phoneNumber.text = args.phonenumber
        binding.celNumber.text = args.celphone
        binding.idName.text = args.namecard
        binding.userNat.text = args.nat

        val street = args.streetname
        val number = args.streetnumber
        val city = args.city
        val state = args.state
        binding.addressUser.text = "$street $number  $city  $state"

        val mapFragment = childFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }
}