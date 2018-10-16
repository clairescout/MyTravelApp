package presenters;

import com.example.models.Photo;
import com.example.models.Trip;
import com.example.models.User;

public class VacationFeedPresenter {

    private static VacationFeedPresenter single_instance = new VacationFeedPresenter();
    private Trip currentTrip;

    private VacationFeedPresenter() {

    }

    public static VacationFeedPresenter getInstance() {
        return single_instance;
    }

    public void setCurrentTrip(String tripId) {

        currentTrip = User.getInstance().getTripById(tripId);
    }

    public String getTripId(){
        return currentTrip.getId();
    }

    public String getTripName(){
        return currentTrip.getName();
    }

    public void addPhoto(Photo photo){
        currentTrip.addMemory(photo);
    }

}
