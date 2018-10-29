package presenters;

import com.example.models.JournalEntry;
import com.example.models.Trip;
import com.example.models.User;
import com.example.models.Memory;

import java.util.List;
import java.util.ArrayList;

public class AddTextPresenter {

    private static AddTextPresenter single_instance = new AddTextPresenter();
    private Trip currentTrip;

    private AddTextPresenter() {

    }

    public static AddTextPresenter getInstance() {
        return single_instance;
    }

    public void setCurrentTrip(String tripId) {
        currentTrip = User.getInstance().getTripById(tripId);
    }

    public String getTripId(){
        return currentTrip.getId();
    }

    public void addTextToTrip(String titleEntry, String textEntry) {
//        Memory memory = new Memory(textEntry);
        JournalEntry journalEntry = new JournalEntry(titleEntry, textEntry);

        List<Memory> currMemories = currentTrip.getMemories();
        if (currMemories == null) {
            ArrayList<Memory> memories = new ArrayList<>();
            memories.add(journalEntry);
            currentTrip.setMemories(memories);
        }
        else {
            currMemories.add(journalEntry);
        }
    }

}
