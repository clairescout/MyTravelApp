package com.example.clairescout.mytravelapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import presenters.AddMediaPresenter;

public class AddMediaActivity extends AppCompatActivity {

    private EditText userEnteredText;
    private Button uploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_media);

        Intent intent = getIntent();
        final String tripID = intent.getStringExtra("tripId");
        AddMediaPresenter.getInstance().setCurrentTrip(tripID);

        userEnteredText = findViewById(R.id.photo_caption);

        uploadButton = findViewById(R.id.upload);
        uploadButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               AddMediaPresenter.getInstance().addMediaToTrip(userEnteredText.getText().toString());
               goToVacationFeed(tripID);
           }
        });
    }

    public void goToVacationFeed(String tripId) {
        Intent intent = new Intent(this, VacationFeedActivity.class);
        intent.putExtra("tripId", tripId);
        startActivity(intent);
    }

}
