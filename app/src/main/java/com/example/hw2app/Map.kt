package com.example.hw2app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.hw2app.databinding.ActivityMapBinding

class Map : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap
    private lateinit var binding: ActivityMapBinding
    private val FINE_LOCATION = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkForPermissions(Manifest.permission.ACCESS_FINE_LOCATION, "location", FINE_LOCATION)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        val seattle = LatLng(47.6062, -122.3321)
        this.googleMap.addMarker(MarkerOptions().position(seattle).title("Seattle"))
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(seattle))
        this.googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(47.6205, -122.3493))
                .title("Space Needle")
        )

    }

    private fun checkForPermissions(permission: String, name: String, requestCode: Int) {
        when {
            ContextCompat.checkSelfPermission(applicationContext, permission) == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(applicationContext, "$name permission granted.", Toast.LENGTH_SHORT).show()
            }
            shouldShowRequestPermissionRationale(permission) -> showDialog(permission, name, requestCode)

            else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        }
    }

    private fun showDialog(permission: String, name: String, requestCode: Int) {
        val builder = AlertDialog.Builder(this)

        builder.apply {
            setMessage("Permission is required.")
            setTitle("Permission required")
            setPositiveButton("OK") { dialog, which ->
                ActivityCompat.requestPermissions(this@Map, arrayOf(permission), requestCode)
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        results: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, results)
        fun permissionCheck(name: String) {
            if (results.isEmpty() || results[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "$name permission refused.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "$name permission granted.", Toast.LENGTH_SHORT).show()
            }
        }

        when (requestCode) {
            FINE_LOCATION -> permissionCheck("location")
        }
    }



}