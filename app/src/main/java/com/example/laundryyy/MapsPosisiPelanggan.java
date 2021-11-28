package com.example.laundryyy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApi;
import com.google.android.libraries.maps.CameraUpdateFactory;
import com.google.android.libraries.maps.GoogleMap;
import com.google.android.libraries.maps.OnMapReadyCallback;
import com.google.android.libraries.maps.SupportMapFragment;
import com.google.android.libraries.maps.model.LatLng;
import com.google.android.libraries.maps.model.MarkerOptions;
import com.google.android.libraries.maps.model.PolylineOptions;

public class MapsPosisiPelanggan extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String nama, alamat, latitude, longitude;
    private Location mLastlocation;
    private GoogleApi mGoogleApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_posisi_pelanggan);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(getIntent().putExtras() != null){
            Bundle bundle = getIntent().getExtras();
            nama = bundle.getString("data1");
            alamat = bundle.getString("data2");
            longitude = bundle.getString("data3");
            latitude = bundle.getString("data4");
        } else{
            nama = getIntent().getStringExtra("data1");
            alamat = getIntent().getStringExtra("data2");
            longitude = getIntent().getStringExtra("data3");
            latitude = getIntent().getStringExtra("data4");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        mMap.setMyLocationEnabled(true);
        getAllDataLocation();

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

        Toast.makeText(this, "Lokasi Ditemukan", Toast.LENGTH_SHORT).show();
        LatLng MyLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker (new MarkerOptions().position(MyLocation).title("Latitude: "+location.getLatitude()).snippet("Longitude "+location.getLongitude()));

        mMap.setMyLocationEnabled(true);
        double Lat = Double.parseDouble(latitude);
        double Long = Double.parseDouble(longitude);

        LatLng posisi = new LatLng(Lat, Long);
        mMap.addMarker(new MarkerOptions().position(posisi).title(nama));
        mMap.addMarker(new MarkerOptions().position(posisi).title(alamat));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posisi,15.Of));
        mMap.addPolyline(new PolylineOptions().add(MyLocation,posisi)
                .width(10).color(Color.RED));
    }

    private void getAllDataLocation(){

    }
}