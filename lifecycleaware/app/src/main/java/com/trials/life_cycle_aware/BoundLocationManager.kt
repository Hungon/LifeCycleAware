package com.trials.life_cycle_aware


import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log

object BoundLocationManager {
    fun bindLocationListenerIn(
        lifecycleOwner: LifecycleOwner,
        listener: LocationListener, context: Context
    ) {
        BoundLocationListener(lifecycleOwner, listener, context)
    }

    @SuppressWarnings("MissingPermission")
    internal class BoundLocationListener(
        lifecycleOwner: LifecycleOwner,
        private val listener: LocationListener, private val context: Context
    ) : LifecycleObserver {
        private lateinit var locationManager: LocationManager

        init {
            lifecycleOwner.lifecycle.addObserver(this)
        }

        @OnMyLifeCycleEvent(MyLifeCycle.Event.ON_ANIMATE)
        fun onAnimate() {

        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun addLocationListener() {
            // Note: Use the Fused Location Provider from Google Play Services instead.
            // https://developers.google.com/android/reference/com/google/android/gms/location/FusedLocationProviderApi
            locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, listener)
            Log.d("BoundLocationMgr", "Listener added")
            // Force an update with the last location, if available.
            val lastLocation = locationManager.getLastKnownLocation(
                LocationManager.GPS_PROVIDER
            )
            if (lastLocation != null) {
                listener.onLocationChanged(lastLocation)
            }
        }


        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun removeLocationListener() {
            if (!::locationManager.isInitialized) return
            locationManager.removeUpdates(listener)
            Log.d("BoundLocationMgr", "Listener removed")
        }
    }
}
