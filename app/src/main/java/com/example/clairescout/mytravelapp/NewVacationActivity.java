package com.example.clairescout.mytravelapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.models.Trip;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import presenters.NewVacationPresenter;

public class NewVacationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText vacationName;
    private TextView startDate;
    private TextView endDate;
    private Button nextButton;
    private boolean start = false;
    private Date startDateObject = new Date();
    private Date endDateObject = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vacation);
        initializeWidgets();
    }

    public void goToChooseLocation(String tripId) {
        Intent intent = new Intent(this, VacationFeedActivity.class);
        intent.putExtra("tripId", tripId);
        startActivity(intent);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        setDate(cal);
    }

    private void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        if (start) {
            startDate.setText(dateFormat.format(calendar.getTime()));
            startDateObject = calendar.getTime();
        } else {
            endDate.setText(dateFormat.format(calendar.getTime()));
            endDateObject = calendar.getTime();
        }


    }

    public void datePicker(View view){

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            return new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);
        }

    }

    private void initializeWidgets() {
        vacationName = findViewById(R.id.trip_name);
        vacationName.setImeOptions(EditorInfo.IME_ACTION_DONE);

        vacationName.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    startDate.performClick();
                    return true;
                }
                return false;
            }
        });

        nextButton = findViewById(R.id.new_trip_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: add checks for null
                String tripId = NewVacationPresenter.getInstance().createTrip(vacationName.getText().toString(), startDateObject, endDateObject);
                goToChooseLocation(tripId);
            }
        });
        startDate = findViewById(R.id.start_date);


        endDate = findViewById(R.id.end_date);
//        startDate.setPaintFlags(startDate.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
//        endDate.setPaintFlags(endDate.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start = true;
                datePicker(v);
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start = false;
                datePicker(v);
            }
        });
    }
}
