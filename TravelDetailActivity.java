package com.matin.driverbarbanet.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.matin.driverbarbanet.R;
import com.matin.driverbarbanet.customView.AppButton;

public class TravelDetailActivity extends AppCompatActivity implements
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    GoogleMap mMap;
    private AppButton startTravel, cancelTeravel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_detail);

        init();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maplite);
        mapFragment.getMapAsync(this);


        startTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TravelDetailActivity.this, MissionActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        Bundle bundle = getIntent().getParcelableExtra("position");
        if (bundle != null) {
            LatLng sourceLocat = bundle.getParcelable("latlangsaddress");
            LatLng disLocat = bundle.getParcelable("latlangdaddress");
            assert sourceLocat != null;
            mMap.addMarker(new MarkerOptions().position(sourceLocat).title("Marker  "));
            assert disLocat != null;
            mMap.addMarker(new MarkerOptions().position(disLocat).title("Marker  "));
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sourceLocat,10));
            final LatLngBounds bounds = new LatLngBounds.Builder()
                    .include(sourceLocat)
                    .include(disLocat)
                    .build();
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bounds.getCenter(), 10));
            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 30),2000,null);
                }
            });
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(marker.getPosition())
                .zoom(15)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        return true;
    }

    private void init() {
        startTravel = findViewById(R.id.startMission);
        cancelTeravel = findViewById(R.id.cancelMission);

    }
}
