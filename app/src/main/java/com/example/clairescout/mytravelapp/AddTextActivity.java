package com.example.clairescout.mytravelapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import presenters.AddTextPresenter;

public class AddTextActivity extends AppCompatActivity {

    private EditText userEnteredText;
    private Button uploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_text);

        Intent intent = getIntent();
        final String tripID = intent.getStringExtra("tripId");
        AddTextPresenter.getInstance().setCurrentTrip(tripID);

        userEnteredText = findViewById(R.id.memory_text);

        uploadButton = findViewById(R.id.upload);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTextPresenter.getInstance().addTextToTrip(userEnteredText.getText().toString());
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
