package com.example.clairescout.mytravelapp;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.models.Trip;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import presenters.ChooseLocationPresenter;

public class ChooseLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button nextButton;
    boolean chosePoint = false;
    Marker currentMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // put try catch around this
        Intent intent = getIntent();
        String tripId = intent.getStringExtra("tripId");
        ChooseLocationPresenter.getInstance().setCurrentTrip(tripId);

        nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add check if there is no latlng yet
                goToVacation();
            }
        });
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            public void onMapClick(LatLng point){
                chosePoint = true;
                if (currentMarker != null) {
                    currentMarker.remove();
                }
                currentMarker = mMap.addMarker(new MarkerOptions().position(point).title(ChooseLocationPresenter.getInstance().getTripName()));
                ChooseLocationPresenter.getInstance().addLatLong(point);
            }
        });
    }



    public void goToVacation() {
        // should we use trip ids or something?
        if (chosePoint) {
            Intent intent = new Intent(this, VacationFeedActivity.class);
            intent.putExtra("tripId", ChooseLocationPresenter.getInstance().getTripId());
            startActivity(intent);
        } else {
            System.out.println("in the else");
            Toast.makeText(this, "Please choose location", Toast.LENGTH_SHORT).show();
        }

    }
}
