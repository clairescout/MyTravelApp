package presenters;

import com.example.models.Trip;

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

    public Trip createTrip(String vacationName) {
        // TODO: add actual data to this trip
        return new Trip();
    }
}
