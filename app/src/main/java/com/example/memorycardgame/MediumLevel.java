package com.example.memorycardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

public class MediumLevel extends MainActivity {
    View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medium_level);
        ImageView pauseButton = findViewById(R.id.pauseMedium);

        layout = findViewById(R.id.linearLayoutMedium);


        // Set a click listener for the button
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // The code to be executed when the button is clicked
                // For example, you can open a new activity, show a message, etc.
                CreatePopUpWindow(layout);
            }
        });

    }
}