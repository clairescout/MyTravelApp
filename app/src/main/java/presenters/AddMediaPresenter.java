package presenters;

import com.example.models.Trip;

/**
 * Created by clairescout on 9/25/18.
 */

public class AddMediaPresenter {

    private static AddMediaPresenter single_instance = new AddMediaPresenter();
    private Trip currentTrip;

    private AddMediaPresenter() {

    }

    public static AddMediaPresenter getInstance() {
        return single_instance;
    }

    public void addMediaToTrip(String tripId) {

    }

}
