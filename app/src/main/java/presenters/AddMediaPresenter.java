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
    private Photo currentPhoto;

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
        currentPhoto.setText(textEntry);
        currentTrip.addMemory(currentPhoto);

//        Photo photo = new Photo(textEntry, "");
//
//        List<Memory> currMemories = currentTrip.getMemories();
//        if (currMemories == null) {
//            ArrayList<Memory> memories = new ArrayList<>();
//            memories.add(photo);
//            currentTrip.setMemories(memories);
//        }
//        else {
//            currMemories.add(photo);
//        }


    }

    public byte[] getCurrentPhotoBytes(String photoId) {
        Memory memory = currentTrip.getMemoryById(photoId);
        if (memory instanceof  Photo) {
            currentPhoto = (Photo) memory;
            return currentPhoto.getByteArray();
        }
        // TODO: throw error
        else return new byte[0];
    }

    public boolean hasText() {
        if (currentPhoto.getText() != null && currentPhoto.getText() != "" ) {
            return true;
        }
        return false;
    }

    public String getText() {
        return currentPhoto.getText();
    }

    public void deleteMemory() {
        currentTrip.deleteMemory(currentPhoto.getId());
    }

}
