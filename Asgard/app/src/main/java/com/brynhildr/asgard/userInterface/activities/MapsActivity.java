package com.brynhildr.asgard.userInterface.activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.brynhildr.asgard.R;
import com.brynhildr.asgard.local.EventWithID;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private double latitude;
    private double longitude;
    private ImageButton mapBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        Intent intent = getIntent();
        final EventWithID event = (EventWithID) intent.getSerializableExtra("Event");
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(event.getCOLUMN_NAME_VENUE(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address address = addresses.get(0);
        latitude = address.getLatitude();
        longitude = address.getLongitude();
        //Set the fragment
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mMap = mapFragment.getMap();
        mapBtn = (ImageButton) findViewById(R.id.return_map);
        mapBtn.setImageResource(R.drawable.ic_mapreturn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Used when map is ready.
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.addMarker(new MarkerOptions().position(
                new LatLng(latitude, longitude)).title("Event's Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
    }

    /**
     * Get the current and set the marker on the map.
     * @param googleMap
     * @return
     */
    public double[] getCurrentLocation(GoogleMap googleMap) {
        Location myLocation  = googleMap.getMyLocation();
        double dLatitude = 0;
        double dLongitude = 0;
        if (myLocation != null) {
            /**
             * Get current location and set the marker
             */
            dLatitude = myLocation.getLatitude();
            dLongitude = myLocation.getLongitude();
            System.out.println("dLatitude---->" + dLatitude);
            System.out.println("dLongitude---->" + dLongitude);
            googleMap.addMarker(new MarkerOptions().position(
                    new LatLng(dLatitude, dLongitude)).title("My Location"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dLatitude, dLongitude), 15));
        }
        else
        {
            /**
             * Deal with the exception.
             */
            Toast.makeText(this, "The current location is unavailable", Toast.LENGTH_SHORT).show();
        }
        double[] location = {dLatitude, dLongitude};
        return location;
    }

}
