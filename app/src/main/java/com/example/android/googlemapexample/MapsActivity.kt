package com.example.android.googlemapexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    val latitude = 37.422160
    val longitude = -122.084270
    val homeLatLng = LatLng(latitude, longitude)
    val zoomLevel = 15f
    // Add a marker in Sydney and move the camera
    val sydney = LatLng(-34.0, 151.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        Log.d(this@MapsActivity.javaClass.simpleName, "onMapReady: ");
        map = googleMap


        //map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
        map.addMarker(MarkerOptions().position(homeLatLng).title("Home"))
        setMapLongClick(map)
        setPoiClick(map)
    }

    private fun setMapLongClick(map:GoogleMap) {
        Log.d(this@MapsActivity.javaClass.simpleName, "setMapLongClick: ");
        map.setOnMapLongClickListener { latLng ->
            Log.d(this@MapsActivity.javaClass.simpleName, "setMapLongClick: ");
            // A Snippet is Additional text that's displayed below the title.
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
                latLng.latitude,
                latLng.longitude
            )
            map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(getString(R.string.dropped_pin))
                    .snippet(snippet)
            )
        }
    }

    private fun setPoiClick(map: GoogleMap) {
        Log.d(this@MapsActivity.javaClass.simpleName, "setPoiClick: ");
        map.setOnPoiClickListener { poi ->
            val poiMarker = map.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name)
            )
            poiMarker.showInfoWindow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d(this@MapsActivity.javaClass.simpleName, "onCreateOptionsMenu: ");
        val inflater = menuInflater
        inflater.inflate(R.menu.map_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        // Change the map type based on the user's selection.
        R.id.normal_map -> {
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
