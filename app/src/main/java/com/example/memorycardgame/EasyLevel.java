package com.example.memorycardgame;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

public class EasyLevel extends MainActivity {
    View layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_level);
        layout = findViewById(R.id.linearLayoutEasy);

        ImageView pauseButton = findViewById(R.id.pauseEasy);

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