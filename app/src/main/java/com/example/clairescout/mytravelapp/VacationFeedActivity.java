package com.example.clairescout.mytravelapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.support.v4.app.DialogFragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.media.AudioManager;
import android.content.Context;

import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.example.models.JournalEntry;
import com.example.models.User;
import com.example.models.Photo;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;
import com.spotify.protocol.client.CallResult;

import presenters.ChooseMediaPresenter;
import presenters.SpotifySearchPresenter;
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
    private FloatingActionButton addPhotoButton;
    private FloatingActionButton addTextButton;
    private FloatingActionButton addSongButton;
    private TextView photoFABText;
    private TextView textFABText;
    private TextView songFABText;
    private RecyclerView memoryRecycler;
    private MemoryAdapter memoryAdapter;
    private TextView title;
    private TextView noMemoriesText;

    private String tripID;
    private boolean paused = true;
    private boolean actionButtonsHidden = true;
    public static final int GET_FROM_GALLERY = 3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_feed);

        Intent intent = getIntent();
        tripID = intent.getStringExtra("tripId");
        VacationFeedPresenter.getInstance().setCurrentTrip(tripID);
        currentSongID = VacationFeedPresenter.getInstance().getSongId();
        if (currentSongID != null) {
            RelativeLayout musicBar = findViewById(R.id.music_bar);
            musicBar.setVisibility(View.VISIBLE);
        }
        VacationFeedPresenter.getInstance().setCurrentTrip(tripID);

        initializeWidgets();
    }

    private class MemoryHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView text;
        private ImageView image;
        private ImageView memoryOptions;

        public MemoryHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.memory_title);
            text = itemView.findViewById(R.id.memory_text);
            image = itemView.findViewById(R.id.memory_photo);
            memoryOptions = itemView.findViewById(R.id.edit_delete_button);


        }

        public void bindMemory(final Memory memory) {
            text.setText(memory.getText());
            if (memory instanceof Photo) {
                // image.setImageResource(R.drawable.templemount);
                if (((Photo) memory).getByteArray() != null) {
                    Bitmap compressedBitmap = BitmapFactory.decodeByteArray(((Photo) memory).getByteArray(),0,((Photo) memory).getByteArray().length);
                    image.setImageBitmap(compressedBitmap);

                } else {
//                   int id = getResources().getIdentifier("drawable/jordan_river.JPG", null, null);
//                   image.setImageResource(id);
                    image.setImageDrawable(getApplicationContext().getDrawable(((Photo) memory).getPhotoDrawable()));
                }
                image.setVisibility(View.VISIBLE);
            } else if (memory instanceof JournalEntry) {
                title.setText(((JournalEntry) memory).getTitle());
                title.setVisibility(View.VISIBLE);
            }
            memoryOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (memory instanceof Photo) {
                        showMemoryOptionsDialog(memory.getId(), true);
                    } else {
                        showMemoryOptionsDialog(memory.getId(), false);
                    }

                }
            });
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
                        System.out.println("spotify failed");

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    private void connected() {

        // Play a playlist
        if (currentSongID != null) {

            AudioManager manager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);

            int volume = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
            manager.setStreamVolume(AudioManager.STREAM_MUSIC, 0,0);

            mSpotifyAppRemote.getPlayerApi().play("spotify:track:" + currentSongID);
            while (!manager.isMusicActive())
            {
                System.out.println("music is not active");
            }

            mSpotifyAppRemote.getPlayerApi().pause();

            while (manager.isMusicActive()) {
                System.out.println("music is active");
            }

            manager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
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
                    mSpotifyAppRemote.getPlayerApi().pause();
                    playButton.setImageResource(R.drawable.play_accent);
                    paused = true;
                }
            }
        });

        noMemoriesText = findViewById(R.id.no_memories_text);
        if (!VacationFeedPresenter.getInstance().tripMemoriesExist()) {
            noMemoriesText.setVisibility(View.VISIBLE);
        }

        memoryRecycler = findViewById(R.id.vacation_feed);
        memoryRecycler.setLayoutManager(new LinearLayoutManager(this));

        memoryAdapter = new MemoryAdapter();
        memoryRecycler.setAdapter(memoryAdapter);

        songName = findViewById(R.id.song_name);
        songArtist = findViewById(R.id.song_artist);

        addPhotoButton = findViewById(R.id.camera_button);
        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddMediaActivity();
            }
        });
        addTextButton = findViewById(R.id.text_button);
        addTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddTextActivity();
            }
        });
        addSongButton = findViewById(R.id.spotify_button);
        addSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddSongActivity();
            }
        });

        photoFABText = findViewById(R.id.camera_text);
        textFABText = findViewById(R.id.text_text);
        songFABText = findViewById(R.id.spotify_text);

        addMediaButton = findViewById(R.id.add_media_floating_action_button);
        addMediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionButtonsHidden) {
                    actionButtonsHidden = false;
                    findViewById(R.id.camera_button).setVisibility(View.VISIBLE);
                    findViewById(R.id.text_button).setVisibility(View.VISIBLE);
                    findViewById(R.id.spotify_button).setVisibility(View.VISIBLE);

                    photoFABText.setVisibility(View.VISIBLE);
                    textFABText.setVisibility(View.VISIBLE);
                    songFABText.setVisibility(View.VISIBLE);
                }
                else {
                    actionButtonsHidden = true;
                    findViewById(R.id.camera_button).setVisibility(View.GONE);
                    findViewById(R.id.text_button).setVisibility(View.GONE);
                    findViewById(R.id.spotify_button).setVisibility(View.GONE);

                    photoFABText.setVisibility(View.GONE);
                    textFABText.setVisibility(View.GONE);
                    songFABText.setVisibility(View.GONE);
                }
            }
        });

        android.support.v7.widget.Toolbar myToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        title = findViewById(R.id.toolbar_title);
        title.setText(User.getInstance().getTripById(tripID).getName());
//        myToolbar.setTitleTextColor(0xFFFFFFFF);
//        getSupportActionBar().setTitle(User.getInstance().getTripById(tripID).getName());

    }

    @Override
    protected void onStop() {
        if (currentSongID != null) {
            mSpotifyAppRemote.getPlayerApi().pause();
            SpotifyAppRemote.CONNECTOR.disconnect(mSpotifyAppRemote);
        }
        super.onStop();
    }

    void showDialog() {
        // Create the fragment and show it as a dialog.
        DialogFragment chooseMediaFragment = ChooseMediaFragment.newInstance();
        ((ChooseMediaFragment) chooseMediaFragment).setTripID(tripID);
        chooseMediaFragment.show(getSupportFragmentManager(), "dialog");
    }

    void showMemoryOptionsDialog(String memoryId, boolean isPhoto) {
        DialogFragment memoryOptionsFragment = MemoryOptionsFragment.newInstance();
        ((MemoryOptionsFragment) memoryOptionsFragment).setMemoryId(memoryId);
        ((MemoryOptionsFragment) memoryOptionsFragment).setIsPhoto(isPhoto);
        ((MemoryOptionsFragment) memoryOptionsFragment).setTripId(tripID);
        memoryOptionsFragment.show(getSupportFragmentManager(), "dialog");
    }

    public void handleDialogClose() {
        memoryAdapter.notifyDataSetChanged();
    }

    private void goBackToMyVacations() {
        if (currentSongID != null) {
            mSpotifyAppRemote.getPlayerApi().pause();
        }
        Intent intent = new Intent(this, MyVacationsActivity.class);
        startActivity(intent);
    }

    private void goToAddMediaActivity() {
//        Intent intent = new Intent(this, AddMediaActivity.class);
//        intent.putExtra("tripId", tripID);
//        startActivity(intent);
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
    }

    private void goToAddTextActivity() {
        Intent intent = new Intent(this, AddTextActivity.class);
        intent.putExtra("tripId", tripID);
        startActivity(intent);
    }

    private void goToAddSongActivity() {
        Intent intent = new Intent(this, SpotifySearchActivity.class);
        intent.putExtra("tripId", tripID);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                // Toast.makeText(this, "Uploading Image", Toast.LENGTH_LONG).show();
                Bitmap resized = Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()*0.1), (int)(bitmap.getHeight()*0.1), true);
                resized.compress(Bitmap.CompressFormat.PNG, 20, stream);
                Photo photo = new Photo();
                photo.setByteArray(stream.toByteArray());
                VacationFeedPresenter.getInstance().addPhoto(photo);
                Intent intent = new Intent(this, AddMediaActivity.class);
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MyVacationsActivity.class);
        startActivity(intent);
    }




}
