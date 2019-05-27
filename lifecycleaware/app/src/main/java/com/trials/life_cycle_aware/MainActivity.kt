package com.trials.life_cycle_aware

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import android.util.Log
import androidx.annotation.Nullable
import androidx.lifecycle.Observer


class MainActivity : AppCompatActivity() {

    private val mGpsListener = MyLocationListener()
    private lateinit var mLiveDataTimerViewModel: LiveDataTimerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_LOCATION_PERMISSION_CODE
            )
        } else {
            bindLocationListener()
            subscribe()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            bindLocationListener()
            subscribe()
        } else {
            Toast.makeText(this, "This sample requires Location access", Toast.LENGTH_LONG).show()
        }
    }

    private fun subscribe() {
        val elapsedTimeObserver = object : Observer<Long> {
            override fun onChanged(aLong: Long?) {
                val newText = this@MainActivity.resources.getString(
                    R.string.seconds, aLong
                )
                findViewById<TextView>(R.id.timer_textview).text = newText
                Log.d(TAG, "Updating timer")
            }
        }

        mLiveDataTimerViewModel.elapsedTime.observe(this, elapsedTimeObserver)
    }

    private fun bindLocationListener() {
        BoundLocationManager.bindLocationListenerIn(this, mGpsListener, applicationContext)
    }

    private inner class MyLocationListener : LocationListener {
        override fun onLocationChanged(location: Location) {
            val textView = findViewById<TextView>(R.id.location)
            textView.text = String.format("%s, %s", location.latitude.toString(), location.longitude.toString())
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

        override fun onProviderEnabled(provider: String) {
            Toast.makeText(
                this@MainActivity,
                "Provider enabled: $provider", Toast.LENGTH_SHORT
            ).show()
        }

        override fun onProviderDisabled(provider: String) {}
    }

    companion object {
        private val TAG by lazy { MainActivity::class.java.simpleName }
        private const val REQUEST_LOCATION_PERMISSION_CODE = 1
    }
}