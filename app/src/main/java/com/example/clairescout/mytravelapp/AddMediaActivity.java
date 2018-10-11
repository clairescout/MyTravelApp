package com.example.clairescout.mytravelapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import presenters.AddMediaPresenter;

public class AddMediaActivity extends AppCompatActivity {

    private Button uploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_media);

        uploadButton = findViewById(R.id.upload);
        uploadButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               AddMediaPresenter.getInstance().addMediaToTrip("temp");
               goToVacationFeed();
           }
        });
    }

    public void goToVacationFeed() {
        Intent intent = new Intent(this, VacationFeedActivity.class);
        startActivity(intent);
    }

}
