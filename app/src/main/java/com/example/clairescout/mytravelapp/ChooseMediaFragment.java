package com.example.clairescout.mytravelapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.widget.ImageButton;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.os.Bundle;
import android.widget.Toast;

import com.example.models.Photo;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import presenters.ChooseMediaPresenter;

public class ChooseMediaFragment extends DialogFragment {

//    private FloatingActionButton addPhotoButton;
//    private FloatingActionButton addTextButton;
//    private FloatingActionButton addSongButton;
    private ImageButton addPhotoButton;
    private ImageButton addTextButton;
    private ImageButton addSongButton;
    private String tripID;
    public static final int GET_FROM_GALLERY = 3;

    static ChooseMediaFragment newInstance() {
        return new ChooseMediaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_choose_media, container, false);

        initializeButtons(v);

        return v;
    }

    private void initializeButtons(View v) {
        addPhotoButton = v.findViewById(R.id.camera_button);
        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), AddMediaActivity.class);
//                intent.putExtra("tripId", tripID);
//                startActivity(intent);
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });
        addTextButton = v.findViewById(R.id.text_button);
        addTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTextActivity.class);
                intent.putExtra("tripId", tripID);
                startActivity(intent);
            }
        });
        addSongButton = v.findViewById(R.id.spotify_button);
        addSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SpotifySearchActivity.class);
                intent.putExtra("tripId", tripID);
                startActivity(intent);
            }
        });
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
        ChooseMediaPresenter.getInstance().setTrip(tripID);
    }

    public String getTripID() {
        return tripID;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), selectedImage);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Toast.makeText(this.getActivity(), "Uploading Image", Toast.LENGTH_LONG).show();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Photo photo = new Photo();
                photo.setByteArray(stream.toByteArray());
                ChooseMediaPresenter.getInstance().addPhoto(photo);
                Intent intent = new Intent(getActivity(), AddMediaActivity.class);
                intent.putExtra("tripId", tripID);
                intent.putExtra("photoId", photo.getId());
                startActivity(intent);
                // TODO: send image to the next activity
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }



}
