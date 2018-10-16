package presenters;

import com.example.models.Photo;
import com.example.models.Trip;
import com.example.models.User;

public class ChooseMediaPresenter {
    private static ChooseMediaPresenter single_instance;
    private Trip currentTrip;

    public static ChooseMediaPresenter getInstance() {
        if (single_instance == null) {
            single_instance = new ChooseMediaPresenter();
        }
        return single_instance;
    }

    private ChooseMediaPresenter() {

    }

    public void setTrip(String tripId){
        currentTrip = User.getInstance().getTripById(tripId);
    }

    public void addPhoto(Photo photo){
        currentTrip.addMemory(photo);
    }

}
