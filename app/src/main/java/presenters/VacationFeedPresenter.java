package presenters;

import com.example.models.Photo;
import com.example.models.Trip;
import com.example.models.User;
import com.example.models.Memory;

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

    public String getSongId() {
        if (currentTrip.getSong() != null) {
            return currentTrip.getSong().getId();
        }
        return null;
    }

    public void addInstructionCard() {
        if (currentTrip.getMemories().size() == 0) {
            Memory instructionCard = new Memory("Welcome to your vacation feed! Click the add button to add a new memory!", "temp_memory");
            currentTrip.addMemory(instructionCard);
        }
    }

    public void removeInstructionCard() {
        for (Memory m : currentTrip.getMemories()) {
            if (m.getId().equals("temp_memory")) {
                currentTrip.getMemories().remove(m);
            }
        }
    }

}
