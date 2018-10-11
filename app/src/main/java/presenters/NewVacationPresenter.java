package presenters;

import com.example.models.Trip;
import com.example.models.User;

import java.util.Date;

/**
 * Created by clairescout on 9/25/18.
 */

public class NewVacationPresenter {

    private static NewVacationPresenter single_instance = new NewVacationPresenter();

    private NewVacationPresenter() {

    }

    public static NewVacationPresenter getInstance() {
        return single_instance;
    }

    public String createTrip(String vacationName, Date startDate, Date endDate) {
        Trip trip = new Trip(vacationName, startDate, endDate);
        User.getInstance().addTrip(trip);
        return trip.getId();
    }
}
