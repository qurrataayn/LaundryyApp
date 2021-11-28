package com.example.laundryyy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laundryyy.AddData.DataLaundry;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.maps.CameraUpdateFactory;
import com.google.android.libraries.maps.GoogleMap;
import com.google.android.libraries.maps.OnMapReadyCallback;
import com.google.android.libraries.maps.SupportMapFragment;
import com.google.android.libraries.maps.model.BitmapDescriptorFactory;
import com.google.android.libraries.maps.model.LatLng;
import com.google.android.libraries.maps.model.Marker;
import com.google.android.libraries.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.view.View;

import java.util.ArrayList;
import java.util.List;

public class MapsLaundry extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Location mLaslocation;
    private GoogleApi mGoogleApi;
    private List<DataLaundry> mListMarker = new ArrayList<>();
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_laundry);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setupGoogleAPI();
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        mMap.setMyLocationEnabled(true);
        getAllDataLocation();
    }

    private void getAllDataLocation(){
        database = FirebaseDatabase.getInstance().getReference("admin");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mListMarker.clear();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    DataLaundry add=postSnapshot.getValue(DataLaundry.class);
                    mListMarker.add(add);
                }
                for(int i=0; i<mListMarker.size(); i++){
                    LatLng lokasi = new LatLng(mListMarker.get(i).getLatitude(), mListMarker.get(i).getLongitude());
                    mMap.addMarker(new MarkerOptions().position(lokasi)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)))
                            .title(mListMarker.get(i).getNama()+"\n"+mListMarker.get(i).getAlamat()
                            +"\n"+mListMarker.get(i).getNomer()+"\n"+mListMarker.get(i).getInformasi())
                            .snippet(mListMarker.get(i).getId());
                    mMap.setInfoWindowAdapter(iwa);
                    mMap.setOnInfoWindowClickListener((marker) -> {
                        Intent intent = new Intent(MapsLaundry.this, ReservasiActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("nama", marker.getSnippet());
                        intent.putExtra("reservasi", bundle);
                        startActivity(intent);
                    });
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasi,11.of));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    GoogleMap.InfoWindowAdapter iwa = new GoogleMap.InfoWindowAdapter(){
        @Override
        public View getInfoWindow (Marker marker){ return null;}
        @Override
        public View getInfoContents(Marker marker){
            View view = getLayoutInflater().inflate(R.layout.infowindow, null);
            TextView title = (TextView) view.findViewById(R.id.tvTitle);
            TextView alamat = (TextView) view.findViewById(R.id.tvAlamat);
            title.setText(marker.getTitle());
            alamat.setText(marker.getSnippet());
            return view;
        }
    };

    private void setupGoogleAPI(){
        mGoogleApi = new GoogleApi()
                .Settings.Builder(this)
                .addApi (LocationServices.API)
                .addConnectionCallbacks(this)
                .addConnectionFailedLinstener(this)
                .build();
    }
    @Override
    public void onStart(){
        super.onStart();
        mGoogleApi.connect();
    }

    @Override
    protected void onStop(){
        super.onStop();
        mGoogleApi.dissconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle){
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission()){
            return;
        }mLaslocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApi.asGoogleApiClient());
    } if (mLaslocation != null){
        Toast.makeText(this, "Koneksi ke Lokasi Google API", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i){

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult){

    }
}
