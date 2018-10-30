package com.example.clairescout.mytravelapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.models.User;


public class MemoryOptionsFragment extends DialogFragment {

    static MemoryOptionsFragment newInstance() {
        return new MemoryOptionsFragment();
    }

    private String memoryId;
    private String tripId;
    private boolean isPhoto;
    private TextView edit;
    private TextView delete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_memory_options, container, false);

        edit = v.findViewById(R.id.edit_memory);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (isPhoto) {
                    intent = new Intent(getContext(), AddMediaActivity.class);
                    intent.putExtra("photoId", memoryId);
                } else {
                    intent = new Intent(getContext(), AddTextActivity.class);
                    intent.putExtra("memoryId", memoryId);
                }

                intent.putExtra("tripId", tripId);
                startActivity(intent);
            }
        });

        delete = v.findViewById(R.id.delete_memory);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.getInstance().getTripById(tripId).deleteMemory(memoryId);
                dismiss();
            }
        });
        return v;
    }

    public void onDismiss(DialogInterface dialog)
    {
        Activity activity = getActivity();
        if(activity instanceof VacationFeedActivity)
            ((VacationFeedActivity)activity).handleDialogClose();
    }

    public void setMemoryId(String memoryId) {
        this.memoryId = memoryId;
    }

    public void setIsPhoto(boolean isPhoto) {
        this.isPhoto = isPhoto;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }
}
