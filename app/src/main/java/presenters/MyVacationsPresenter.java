package presenters;

import com.example.clairescout.mytravelapp.R;
import com.example.models.Photo;
import com.example.models.Trip;
import com.example.models.User;

import java.util.List;

/**
 * Created by clairescout on 9/25/18.
 */

public class MyVacationsPresenter {

    private static MyVacationsPresenter single_instance = new MyVacationsPresenter();
    private boolean isInitialized = false;

    private MyVacationsPresenter() {

    }

    public static MyVacationsPresenter getInstance() {
        return single_instance;
    }

    public void initializeTripsMemories() {
        if (!isInitialized ) {
            isInitialized = true;
            List<Trip> trips = User.getInstance().getTrips();
            for (int i = 0; i < trips.size(); i++ ){
                Trip trip = trips.get(i);
                if (i == 0 ) {
                    Photo photo2 = new Photo();
                    photo2.setPhotoDrawable(R.drawable.aida);
                    photo2.setText("Aida");
                    trip.addMemory(photo2);
                    Photo photo1 = new Photo();
                    photo1.setPhotoDrawable(R.drawable.leo);
                    photo1.setText("Leo is cute");
                    trip.addMemory(photo1);

                } else if (i == 1) {
                    Photo photo2 = new Photo();
                    photo2.setPhotoDrawable(R.drawable.bench);
                    photo2.setText("The family having a great time at the cabin");
                    trip.addMemory(photo2);
//                    Photo photo1 = new Photo();
//                    photo1.setPhotoDrawable(R.drawable.aida);
//                    photo1.setText("Sweet angel");
//                    trip.addMemory(photo1);
                } else if (i == 2) {
                    Photo photo1 = new Photo();
                    photo1.setPhotoDrawable(R.drawable.tyler);
                    photo1.setText("Tyler was so happy to be playing with blocks");
                    trip.addMemory(photo1);
//                    Photo photo2 = new Photo();
//                    photo2.setPhotoDrawable(R.drawable.sundance);
//                    photo2.setText("Such a fun weekend with family");
//                    trip.addMemory(photo2);
                }
            }
        }
    }


}
