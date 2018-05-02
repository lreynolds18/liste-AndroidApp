package com.example.lucas.liste;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


public class GPS {
    // variables for gps
    private static final String TAG = "Camera";
    private Location lastKnownLocation;
    // LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    // String locationProvider = locationManager.NETWORK_PROVIDER;

    private Context mContext;

    public GPS(Context mContext) {
        this.mContext = mContext;
    }

    public void startCollectingGPS() {
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                lastKnownLocation = location;
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };
        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    private void stopCollectingGPS() {}

    private void getLastGPS() {}
}
