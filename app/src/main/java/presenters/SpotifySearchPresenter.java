package presenters;

import com.example.models.Trip;
import com.example.models.User;
import com.example.models.Song;

import java.util.ArrayList;

public class SpotifySearchPresenter {

    private static SpotifySearchPresenter single_instance = new SpotifySearchPresenter();
    private Trip currentTrip;
    private ArrayList<Song> tempSongs;

    private SpotifySearchPresenter() {
        setTempSongs();
    }

    public static SpotifySearchPresenter getInstance() {
        return single_instance;
    }

    public void setCurrentTrip(String tripId) {
        currentTrip = User.getInstance().getTripById(tripId);
    }

    public String getTripId(){
        return currentTrip.getId();
    }

    public void addSongToTrip(String artist, String name, String album, String id) {
        Song song = new Song(artist, name, album, id);
        currentTrip.setSong(song);
    }

    public void setTempSongs() {
        ArrayList<Song> songs = new ArrayList<>();
        Song song = new Song("The National Parks", "Places", "Places", "1TkzittARXqOUAP9wHTJwH");
        songs.add(song);
        tempSongs = songs;
    }

    public ArrayList<Song> getTempSongs() {
        return tempSongs;
    }

}
