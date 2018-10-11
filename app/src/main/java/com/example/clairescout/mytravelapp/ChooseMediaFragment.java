package com.example.clairescout.mytravelapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.os.Bundle;

public class ChooseMediaFragment extends DialogFragment {

    private FloatingActionButton addPhotoButton;
    private FloatingActionButton addTextButton;
    private FloatingActionButton addSongButton;

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
                Intent intent = new Intent(getActivity(), AddMediaActivity.class);
                startActivity(intent);
            }
        });
        addTextButton = v.findViewById(R.id.text_button);
        addTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTextActivity.class);
                startActivity(intent);
            }
        });
        addSongButton = v.findViewById(R.id.spotify_button);
        addSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SpotifySearchActivity.class);
                startActivity(intent);
            }
        });
    }


}
