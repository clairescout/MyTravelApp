package com.example.clairescout.mytravelapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.models.Trip;
import com.example.models.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class MyVacationsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private RecyclerView vacationsList;
    private VacationAdapter vacationAdapter;
    private FloatingActionButton addVacationButton;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vacations);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.my_map);
        mapFragment.getMapAsync(this);

        initializeWidgits();
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

        // Add markers for each trip in list
        for (Trip trip : User.getInstance().getTrips()) {
            LatLng latlng = new LatLng(trip.getLatitude(), trip.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latlng).title(trip.getName()));
        }

    }


    private class VacationHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView vacationName;
        private TextView startDate;
        private TextView endDate;
        private String tripId;
        public VacationHolder(@NonNull View itemView) {
            super(itemView);
            vacationName = itemView.findViewById(R.id.vacation_name);
            startDate = itemView.findViewById(R.id.start_date);
            endDate = itemView.findViewById(R.id.end_date);
            ConstraintLayout constraintLayout = itemView.findViewById(R.id.vacation_item_constraint);
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToVacationFeed(tripId);
                }
            });
        }

        public void bindVacation(Trip trip) {
            vacationName.setText(trip.getName());
            startDate.setText(trip.getStartDateString());
            endDate.setText(trip.getEndDateString());
            tripId = trip.getId();
        }


        @Override
        public void onClick(View v) {
            goToVacationFeed(tripId);
        }
    }

    private class VacationAdapter extends RecyclerView.Adapter<VacationHolder> {

        private List<Trip> trips = User.getInstance().getTrips();

        @NonNull
        @Override
        public VacationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(MyVacationsActivity.this);
            View view = layoutInflater.inflate(R.layout.item_vacation_in_list, viewGroup, false);
            return new VacationHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VacationHolder vacationHolder, int i) {
            vacationHolder.bindVacation(trips.get(i));
        }

        @Override
        public int getItemCount() {
            return trips.size();
        }
    }

    public void initializeWidgits() {
        addVacationButton = findViewById(R.id.add_vacation_floating_button);
        addVacationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNewVacationActivity();
            }
        });


        vacationsList = findViewById(R.id.recycler_trip_list);
        vacationsList.setLayoutManager(new LinearLayoutManager(this));

        vacationAdapter = new VacationAdapter();
        vacationsList.setAdapter(vacationAdapter);

        android.support.v7.widget.Toolbar myToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.my_vacations_toolbar);

        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        title = findViewById(R.id.my_vacations_title);
        title.setText("My Vacations");
    }

    public void goToNewVacationActivity() {
        Intent intent = new Intent(this, NewVacationActivity.class);
        startActivity(intent);
    }

    public void goToVacationFeed(String tripId){
        Intent intent = new Intent(this, VacationFeedActivity.class);
        intent.putExtra("tripId", tripId);
        startActivity(intent);
    }
}
