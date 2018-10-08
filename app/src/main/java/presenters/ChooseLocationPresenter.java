package presenters;

import com.example.clairescout.mytravelapp.ChooseLocationActivity;
import com.example.models.Trip;
import com.example.models.User;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by clairescout on 9/25/18.
 */

public class ChooseLocationPresenter {

    private static ChooseLocationPresenter single_instance = new ChooseLocationPresenter();
    private Trip currentTrip;

    private ChooseLocationPresenter() {

    }

    public static ChooseLocationPresenter getInstance() {
        return single_instance;
    }

    public void addLatLong(LatLng latlng){
        currentTrip.setLatitude(latlng.latitude);
        currentTrip.setLongitude(latlng.longitude);
    }

    public void addTripToUser(){
        User.getInstance().addTrip(currentTrip);
    }

    public void setCurrentTrip(Trip trip) {
        currentTrip = trip;
    }
}
