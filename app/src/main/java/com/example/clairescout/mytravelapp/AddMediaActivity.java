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

import java.io.FileNotFoundException;
import java.io.IOException;

import presenters.AddMediaPresenter;

public class AddMediaActivity extends AppCompatActivity {

    private EditText userEnteredText;
    private Button uploadButton;
    private ImageView imageView;

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
        final String tripID = intent.getStringExtra("tripId");
        String photoId = intent.getStringExtra("photoId");
        AddMediaPresenter.getInstance().setCurrentTrip(tripID);
        byte[] byteArray = AddMediaPresenter.getInstance().getCurrentPhotoBytes(photoId);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.id.chosenImage, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;
        Bitmap compressedBitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
        imageView = findViewById(R.id.chosenImage);
        imageView.setImageBitmap(compressedBitmap);

        userEnteredText = findViewById(R.id.photo_caption);


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
    }

}
