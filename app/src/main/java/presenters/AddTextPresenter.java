package presenters;

import com.example.models.Trip;
import com.example.models.User;
import com.example.models.Memory;

import java.util.List;
import java.util.ArrayList;

public class AddTextPresenter {

    private static AddTextPresenter single_instance = new AddTextPresenter();
    private Trip currentTrip;
    private Memory currentMemory;

    private AddTextPresenter() {

    }

    public static AddTextPresenter getInstance() {
        return single_instance;
    }

    public void setCurrentTrip(String tripId) {
        currentTrip = User.getInstance().getTripById(tripId);
    }

    public void setCurrentMemory(String memoryId) {
        if (memoryId == null) {
            currentMemory = new Memory();
        } else {
            currentMemory = currentTrip.getMemoryById(memoryId);
        }
    }

    public String getTripId(){
        return currentTrip.getId();
    }

    public void addTextToTrip(String textEntry) {
        currentMemory.setText(textEntry);

        List<Memory> currMemories = currentTrip.getMemories();
        if (currMemories == null) {
            ArrayList<Memory> memories = new ArrayList<>();
            memories.add(currentMemory);
            currentTrip.setMemories(memories);
        }
        else {
            currentTrip.addMemory(currentMemory);
        }
    }

    public String getText() {
        return currentMemory.getText();
    }

}
