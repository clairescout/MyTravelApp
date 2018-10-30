package com.example.clairescout.mytravelapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.models.Photo;

import java.io.FileNotFoundException;
import java.io.IOException;

import presenters.AddMediaPresenter;

public class AddMediaActivity extends AppCompatActivity {

    private EditText userEnteredText;
    private Button uploadButton;
    private ImageView imageView;
    private boolean isEdit = false;
    private String tripID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_media);

        initializeWidgets();
    }

    public void goToVacationFeed(String tripId) {
        Intent intent = new Intent(this, VacationFeedActivity.class);
        intent.putExtra("tripId", tripId);
        startActivity(intent);
    }

    public void initializeWidgets() {

        Intent intent = getIntent();
        tripID = intent.getStringExtra("tripId");
        String photoId = intent.getStringExtra("photoId");
        AddMediaPresenter.getInstance().setCurrentTrip(tripID);
        Photo photo = AddMediaPresenter.getInstance().getCurrentPhoto(photoId);
        if (photo.getByteArray() != null) {
            byte[] byteArray = photo.getByteArray();
            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
            imageView = findViewById(R.id.chosenImage);
            imageView.setImageBitmap(compressedBitmap);
        } else {
            imageView.setImageDrawable(getApplicationContext().getDrawable(photo.getPhotoDrawable()));
        }


//        if (((Photo) memory).getByteArray() != null) {
//            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(((Photo) memory).getByteArray(),0,((Photo) memory).getByteArray().length);
//            vacationImage.setImageBitmap(compressedBitmap);
//            vacationImage.setVisibility(View.VISIBLE);
//            break;
//        } else {
//            vacationImage.setImageDrawable(getApplicationContext().getDrawable(((Photo) memory).getPhotoDrawable()));
//        }

        userEnteredText = findViewById(R.id.photo_caption);
        if (AddMediaPresenter.getInstance().hasText()) {
            userEnteredText.setText(AddMediaPresenter.getInstance().getText());
            isEdit = true;
        }


        uploadButton = findViewById(R.id.upload);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMediaPresenter.getInstance().addMediaToTrip(userEnteredText.getText().toString());
                goToVacationFeed(tripID);
            }
        });


        android.support.v7.widget.Toolbar myToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.addmedia_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (isEdit) {
            Intent intent = new Intent(this, VacationFeedActivity.class);
            intent.putExtra("tripId", tripID);
            startActivity(intent);
        } else {
            AddMediaPresenter.getInstance().deleteMemory();
            Intent intent = new Intent(this, VacationFeedActivity.class);
            intent.putExtra("tripId", tripID);
            startActivity(intent);
        }

    }

}
