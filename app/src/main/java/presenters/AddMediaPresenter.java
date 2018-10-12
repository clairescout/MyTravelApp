package presenters;

import com.example.models.Trip;
import com.example.models.Photo;
import com.example.models.Memory;
import com.example.models.User;

import java.util.List;
import java.util.ArrayList;

public class AddMediaPresenter {

    private static AddMediaPresenter single_instance = new AddMediaPresenter();
    private Trip currentTrip;

    private AddMediaPresenter() {

    }

    public static AddMediaPresenter getInstance() {
        return single_instance;
    }

    public void setCurrentTrip(String tripId) {
        currentTrip = User.getInstance().getTripById(tripId);
    }

    public String getTripId(){
        return currentTrip.getId();
    }

    public void addMediaToTrip(String textEntry) {

        Photo photo = new Photo(textEntry, "");

        List<Memory> currMemories = currentTrip.getMemories();
        if (currMemories == null) {
            ArrayList<Memory> memories = new ArrayList<>();
            memories.add(photo);
            currentTrip.setMemories(memories);
        }
        else {
            currMemories.add(photo);
        }

    }

}
