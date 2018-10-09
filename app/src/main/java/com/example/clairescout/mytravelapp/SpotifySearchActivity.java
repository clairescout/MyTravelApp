package com.example.clairescout.mytravelapp;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.models.Song;
import com.example.models.User;

import java.util.ArrayList;
import java.util.List;

public class SpotifySearchActivity extends AppCompatActivity {

    private RecyclerView songsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify_search);

        songsRecyclerView = findViewById(R.id.spotify_song_recycler);
        songsRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private class SongHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView albumImage;
        private TextView songTitle;
        private TextView artist;
        private TextView albumTitle;

        public SongHolder(@NonNull View itemView) {
            super(itemView);
            albumImage = itemView.findViewById(R.id.album_image);
            songTitle = itemView.findViewById(R.id.song_name);
            artist = itemView.findViewById(R.id.song_artist);
            albumTitle = itemView.findViewById(R.id.album);
        }

        public void bindSong(Song song) {
           //  albumImage.setImageDrawable(song.get); TODO: don't know how to do this
            songTitle.setText(song.getName());
            artist.setText(song.getArtist());
            albumTitle.setText(song.getAlbum());
        }

        @Override
        public void onClick(View v) {
            // TODO: add song to trip
            // TODO: go to vacation feed page

        }
    }

    private class SongAdapter extends RecyclerView.Adapter<SongHolder> {

        private List<Song> songs = new ArrayList<>();
        // TODO: get songs from spotify api

        @NonNull
        @Override
        public SongHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(SpotifySearchActivity.this);
            View view = layoutInflater.inflate(R.layout.item_in_spotify_search, viewGroup, false);
            return new SpotifySearchActivity.SongHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SongHolder songHolder, int i) {
            songHolder.bindSong(songs.get(i));

        }

        @Override
        public int getItemCount() {
            return songs.size();
        }
    }

}
