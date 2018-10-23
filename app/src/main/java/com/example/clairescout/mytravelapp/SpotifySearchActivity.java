package com.example.clairescout.mytravelapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.models.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import presenters.SpotifySearchPresenter;

public class SpotifySearchActivity extends AppCompatActivity {

    private RecyclerView songsRecyclerView;
    private SongAdapter songAdapter;
    private String tripID;
    private HashMap<String, Integer> albumCovers = new HashMap<>();

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

        private ImageView albumImage;
        private TextView songTitle;
        private TextView artist;
        private TextView albumTitle;

        public SongHolder(@NonNull View itemView) {
            super(itemView);
            albumImage = itemView.findViewById(R.id.album_image);
            songTitle = itemView.findViewById(R.id.song_title);
            artist = itemView.findViewById(R.id.artist);
            albumTitle = itemView.findViewById(R.id.album);
        }

        public void bindSong(Song song) {
            int albumCover = albumCovers.get(song.getAlbum());
            albumImage.setImageResource(albumCover);
            songTitle.setText(song.getName());
            artist.setText(song.getArtist());
            albumTitle.setText(song.getAlbum());
        }
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
                    String id = song.getId();

                    SpotifySearchPresenter.getInstance().addSongToTrip(songName, artistName, albumName, id);

                    goToVacationFeed(tripID);
                }
            });
        }

        @Override
        public int getItemCount() {
            return songs.size();
        }

        public ArrayList<Song> makeTempSongs() {
            ArrayList<Song> songs = new ArrayList<>();
            Song places = new Song("The National Parks", "Places", "Places", "1TkzittARXqOUAP9wHTJwH");
            songs.add(places);

            Song holiday = new Song("Vampire Weekend", "Holiday", "Contra", "3ciC6GP8rOPxlkCYQIQ3jW");
            songs.add(holiday);

            Song drive = new Song("Ben Rector", "Drive", "Magic", "1Yqovy9hlOeV91IY3Bhcf2");
            songs.add(drive);

            Song lostInParis = new Song("Tom Misch", "Lost in Paris", "Geography", "6lxcWIvMQK3yezxwFfZcKZ");
            songs.add(lostInParis);

            Song africa = new Song("Toto", "Africa", "Toto IV", "2374M0fQpWi3dLnB54qaLX");
            songs.add(africa);

            Song portugal = new Song("WALK THE MOON", "Portugal", "TALKING IS HARD", "3MYWKl8ScgDu3sAvyneMCG");
            songs.add(portugal);

            return songs;
        }
    }

    public void initializeWidgets() {
        makeAlbumMap();

        songsRecyclerView = findViewById(R.id.spotify_song_recycler);
        songsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        songAdapter = new SongAdapter();
        songsRecyclerView.setAdapter(songAdapter);
    }

    public void makeAlbumMap() {
        albumCovers.put("Places", R.drawable.places);
        albumCovers.put("Contra", R.drawable.contra);
        albumCovers.put("Magic", R.drawable.magic);
        albumCovers.put("Geography", R.drawable.geography);
        albumCovers.put("Toto IV", R.drawable.toto);
        albumCovers.put("TALKING IS HARD", R.drawable.talking_is_hard);
    }

    public void goToVacationFeed(String tripId) {
        Intent intent = new Intent(this, VacationFeedActivity.class);
        intent.putExtra("tripId", tripId);
        startActivity(intent);
    }

}
