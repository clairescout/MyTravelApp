package com.example.clairescout.mytravelapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.models.Trip;

import presenters.NewVacationPresenter;

public class NewVacationActivity extends AppCompatActivity {

    /*

        TODO:
            Make text disappear when you click in it.
            figure out how to do date picker
            add more data to the intent, pass the trip
            add more data to the trip.
            Change Button to MaterialButton

     */

    private EditText vacationName;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vacation);
        vacationName = findViewById(R.id.trip_name);
        nextButton = findViewById(R.id.new_trip_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trip trip = NewVacationPresenter.getInstance().createTrip(vacationName.getText().toString());
                goToChooseLocation(trip);
            }
        });
    }

    public void goToChooseLocation(Trip trip) {
        Intent intent = new Intent(this, ChooseLocationActivity.class);
        startActivity(intent);
    }

}
