package com.example.clairescout.mytravelapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.v4.app.DialogFragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import java.util.List;

import com.example.models.User;
import com.example.models.Photo;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

import presenters.VacationFeedPresenter;
import com.example.models.Trip;
import com.example.models.Memory;

public class VacationFeedActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "bb32999f67d1447fa9cb9b4b74e62db5";
    private static final String REDIRECT_URI = "https://example.com/callback";
    private String currentSongID;
    private SpotifyAppRemote mSpotifyAppRemote;

    private ImageButton playButton;
    private TextView songName;
    private TextView songArtist;
    private FloatingActionButton addMediaButton;
    private Button backToVacations;
    private RecyclerView memoryRecycler;
    private MemoryAdapter memoryAdapter;

    private String tripID;
    private boolean paused = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_feed);

        Intent intent = getIntent();
        currentSongID = intent.getStringExtra("currentSongId"); // Not positive if this will work...
        tripID = intent.getStringExtra("tripId");
        VacationFeedPresenter.getInstance().setCurrentTrip(tripID);

        initializeWidgets();
    }

    private class MemoryHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public MemoryHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.memory_text);
        }

        public void bindMemory(Memory memory) {
            text.setText(memory.getText());
        }
    }

    private class MemoryAdapter extends RecyclerView.Adapter<MemoryHolder> {

        Trip trip = User.getInstance().getTripById(tripID);
        private List<Memory> memories = trip.getMemories();

        @NonNull
        @Override
        public MemoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(VacationFeedActivity.this);
            View view = layoutInflater.inflate(R.layout.item_memory_in_feed, viewGroup, false);
            return new MemoryHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MemoryHolder memoryHolder, int i) {
            if (memories != null) {
                memoryHolder.bindMemory(memories.get(i));
            }
        }

        @Override
        public int getItemCount() {
            if (memories != null) {
                return memories.size();
            }
            return 0;
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.CONNECTOR.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("VacationFeedActivity", "Connected! Yay!");

                        // Now you can start interacting with App Remote
                        connected();

                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("MyActivity", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    private void connected() {
        // Play a playlist
        if (currentSongID != null) {
            mSpotifyAppRemote.getPlayerApi().play("spotify:track:" + currentSongID);
            pauseMusic();
        }

        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState().setEventCallback(new Subscription.EventCallback<PlayerState>() {

            public void onEvent(PlayerState playerState) {
                final Track track = playerState.track;
                if (track != null) {
                    Log.d("VacationFeedActivity", track.name + " by " + track.artist.name);
                }
                songName.setText(track.name);
                songArtist.setText(track.artist.name);
            }
        });
    }

    private void pauseMusic() {
        if (currentSongID != null) {
            mSpotifyAppRemote.getPlayerApi().pause();
        }
    }

    private void initializeWidgets() {
        playButton = findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paused) {
                    mSpotifyAppRemote.getPlayerApi().resume();
                    playButton.setImageResource(R.drawable.pause_accent);
                    paused = false;
                }
                else {
                    pauseMusic();
                    playButton.setImageResource(R.drawable.play_accent);
                    paused = true;
                }
            }
        });

        memoryRecycler = findViewById(R.id.vacation_feed);
        memoryRecycler.setLayoutManager(new LinearLayoutManager(this));

        memoryAdapter = new MemoryAdapter();
        memoryRecycler.setAdapter(memoryAdapter);

        songName = findViewById(R.id.song_name);
        songArtist = findViewById(R.id.song_artist);

        addMediaButton = findViewById(R.id.add_media_floating_action_button);
        addMediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        backToVacations = findViewById(R.id.back_to_vacations_button);
        backToVacations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToMyVacations();
            }
        });
    }

    @Override
    protected void onStop() {
        if (currentSongID != null) {
            super.onStop();
            pauseMusic();
            SpotifyAppRemote.CONNECTOR.disconnect(mSpotifyAppRemote);
        } else {
            super.onStop();
        }
    }

    void showDialog() {
        // Create the fragment and show it as a dialog.
        DialogFragment chooseMediaFragment = ChooseMediaFragment.newInstance();
        ((ChooseMediaFragment) chooseMediaFragment).setTripID(tripID);
        chooseMediaFragment.show(getSupportFragmentManager(), "dialog");
    }

    private void goBackToMyVacations() {
        if (currentSongID != null) {
            pauseMusic();
        }
        Intent intent = new Intent(this, MyVacationsActivity.class);
        startActivity(intent);
    }

}
