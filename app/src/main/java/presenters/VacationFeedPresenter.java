package presenters;

import android.util.Log;

import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

public class VacationFeedPresenter {

    private static VacationFeedPresenter single_instance = new VacationFeedPresenter();

    private VacationFeedPresenter() {

    }

    public static VacationFeedPresenter getInstance() {
        return single_instance;
    }

    public void connected(SpotifyAppRemote spotifyAppRemote) {
        // Play a playlist
        spotifyAppRemote.getPlayerApi().play("spotify:user:spotify:playlist:37i9dQZF1DX2sUQwD7tbmL");

        // Subscribe to PlayerState
        spotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState().setEventCallback(new Subscription.EventCallback<PlayerState>() {

            public void onEvent(PlayerState playerState) {
                final Track track = playerState.track;
                if (track != null) {
                    Log.d("VacationFeedActivity", track.name + " by " + track.artist.name);
                }
            }
        });
    }

    public void pauseMusic(SpotifyAppRemote spotifyAppRemote) {
        spotifyAppRemote.getPlayerApi().pause();
    }

}


//try {
//        URL url = new URL(API_URL + "email=" + email + "&apiKey=" + API_KEY);
//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        try {
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//        StringBuilder stringBuilder = new StringBuilder();
//        String line;
//        while ((line = bufferedReader.readLine()) != null) {
//        stringBuilder.append(line).append("\n");
//        }
//        bufferedReader.close();
//        return stringBuilder.toString();
//        }
//        finally{
//        urlConnection.disconnect();
//        }
//        }
//        catch(Exception e) {
//        Log.e("ERROR", e.getMessage(), e);
//        return null;
//        }
