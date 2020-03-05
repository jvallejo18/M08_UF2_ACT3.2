package com.example.act3uf2;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

public class GoogleMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    String txtPosition;
    Button addMarkerBtn;
    LatLng newPosition;
    LatLng startTracking;
    LatLng finishTracking;
    Polyline polyline;
    boolean tracking = false;
    double lat;
    double lng;
    int i = 0;
    PolylineOptions rectOptions = new PolylineOptions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_google_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        addMarkerBtn = (Button)findViewById(R.id.buttonGMaps);
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
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);



        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                newPosition = new LatLng(location.getLatitude(), location.getLongitude());
                if (tracking){
                    lng = location.getLongitude();
                    lat = location.getLatitude();
                    Log.d("HOLA ESTOY TRUE", "HOLA ESTOY TRUE");
                    rectOptions.add(new LatLng(lat, lng)).width(25)
                            .color(Color.BLUE)
                            .geodesic(true);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(newPosition));
                    polyline = mMap.addPolyline(rectOptions);
                }
            }
            @Override
            public void onProviderDisabled(String provider) {
                Log.d("Latitude","disable");
            }
            @Override
            public void onProviderEnabled(String provider) {
                Log.d("Latitude","enable");
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("Latitude","status");
            }
        };

        addMarkerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;

                if (!tracking) {
                    tracking = true;
                    startTracking = newPosition;
                    String nameLocation = getAddress(lat, lng);

                    mMap.addMarker(new MarkerOptions().position(startTracking).title("INICI").icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(startTracking));
                }
                else{
                    tracking = false;
                    finishTracking = newPosition;
                    String nameLocation = getAddress(lat, lng);
                    mMap.addMarker(new MarkerOptions().position(finishTracking).title("FINAL"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(finishTracking));
                }
                if (i%3 == 0){
                    if (tracking) {
                        tracking = false;
                    }
                    polyline.remove();
                    mMap.clear();
                    Toast.makeText(GoogleMaps.this, "Esborrem el tracking anterior", Toast.LENGTH_LONG).show();
                }

            }
        });

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET}, 10);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    }

    private String getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(GoogleMaps.this, Locale.getDefault());
        String add = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            add = obj.getAddressLine(0);
            Log.v("IGA", "Address" + add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return add;
    }
}
