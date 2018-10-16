package com.example.clairescout.mytravelapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.models.Song;
import com.example.models.Trip;
import com.example.models.User;

import java.util.ArrayList;
import java.util.List;

import presenters.AddTextPresenter;
import presenters.SpotifySearchPresenter;

public class SpotifySearchActivity extends AppCompatActivity {

    private RecyclerView songsRecyclerView;
    private SongAdapter songAdapter;
    private String tripID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify_search);

        Intent intent = getIntent();
        tripID = intent.getStringExtra("tripId");
        SpotifySearchPresenter.getInstance().setCurrentTrip(tripID);

        initializeWidgets();
    }

    private class SongHolder extends RecyclerView.ViewHolder {

//        private ImageView albumImage;
        private TextView songTitle;
        private TextView artist;
        private TextView albumTitle;

        public SongHolder(@NonNull View itemView) {
            super(itemView);
//            albumImage = itemView.findViewById(R.id.album_image);
            songTitle = itemView.findViewById(R.id.song_title);
            artist = itemView.findViewById(R.id.artist);
            albumTitle = itemView.findViewById(R.id.album);
        }

        public void bindSong(Song song) {
           //  albumImage.setImageDrawable(song.get); TODO: don't know how to do this
            songTitle.setText(song.getName());
            artist.setText(song.getArtist());
            albumTitle.setText(song.getAlbum());
        }

//        @Override
//        public void onClick(View v) {
//            String songName = songTitle.getText().toString();
//            String artistName = artist.getText().toString();
//            String albumName = albumTitle.getText().toString();
//            String id = "1TkzittARXqOUAP9wHTJwH"; // TODO: get actual id from Spotify API, this is just the Places ID
//
//            SpotifySearchPresenter.getInstance().addSongToTrip(songName, artistName, albumName, id);
//
//            goToVacationFeed(tripID);
//        }
    }

    private class SongAdapter extends RecyclerView.Adapter<SongHolder> {

        private List<Song> songs = makeTempSongs(); // TODO: get songs from spotify api

        @NonNull
        @Override
        public SongHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(SpotifySearchActivity.this);
            View view = layoutInflater.inflate(R.layout.item_in_spotify_search, viewGroup, false);
            return new SpotifySearchActivity.SongHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SongHolder songHolder, int i) {
            final Song song = songs.get(i);
            songHolder.bindSong(song);
            songHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String songName = song.getName();
                    String artistName = song.getArtist();
                    String albumName = song.getAlbum();
                    String id = "1TkzittARXqOUAP9wHTJwH"; // TODO: get actual id from Spotify API, this is just the Places ID

                    SpotifySearchPresenter.getInstance().addSongToTrip(songName, artistName, albumName, id);

                    goToVacationFeed(tripID, song.getId());
                }
            });
        }

        @Override
        public int getItemCount() {
            return songs.size();
        }

        public ArrayList<Song> makeTempSongs() {
            ArrayList<Song> songs = new ArrayList<>();
            Song song = new Song("The National Parks", "Places", "Places", "1TkzittARXqOUAP9wHTJwH");
            songs.add(song);
            return songs;
        }
    }

    public void initializeWidgets() {
        songsRecyclerView = findViewById(R.id.spotify_song_recycler);
        songsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        songAdapter = new SongAdapter();
        songsRecyclerView.setAdapter(songAdapter);
    }

    public void goToVacationFeed(String tripId, String songId) {
        Intent intent = new Intent(this, VacationFeedActivity.class);
        intent.putExtra("tripId", tripId);
        intent.putExtra("currentSongId", songId);
        startActivity(intent);
    }

}
