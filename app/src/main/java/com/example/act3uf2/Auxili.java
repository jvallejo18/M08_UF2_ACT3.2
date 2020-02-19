package com.example.act3uf2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Auxili extends AppCompatActivity {

    Button panicBtn;
    LocationManager locationManager;
    LocationListener locationListener;
    String txtPosition;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auxili);
        panicBtn = (Button)findViewById(R.id.help);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                txtPosition = "Latitud:" + location.getLatitude() + ", Longitud:" + location.getLongitude();
                Log.d(" POSITION: " , txtPosition);
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

        panicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Auxili.this, txtPosition, Toast.LENGTH_LONG).show();
            }
        });

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET}, 10);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);

    }

}


