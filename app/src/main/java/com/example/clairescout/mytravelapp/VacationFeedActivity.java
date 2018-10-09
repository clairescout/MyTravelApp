package com.example.clairescout.mytravelapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import android.util.Log;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

public class VacationFeedActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "bb32999f67d1447fa9cb9b4b74e62db5";
    private static final String REDIRECT_URI = "https://example.com/callback";
    private String currentSongID = "37i9dQZF1DX7K31D69s4M1";
    private SpotifyAppRemote mSpotifyAppRemote;

    private ImageButton playButton;
    private TextView songName;
    private TextView songArtist;
    private FloatingActionButton addMediaButton;

    private boolean paused = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_feed);

        initializeWidgets();
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
        mSpotifyAppRemote.getPlayerApi().play("spotify:user:spotify:playlist:" + currentSongID);

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
        mSpotifyAppRemote.getPlayerApi().pause();
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

        songName = findViewById(R.id.song_name);
        songArtist = findViewById(R.id.song_artist);

        addMediaButton = findViewById(R.id.add_media_floating_action_button);
        addMediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.CONNECTOR.disconnect(mSpotifyAppRemote);
    }

}
