package com.example.memorycardgame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

@RequiresApi(api = Build.VERSION_CODES.O)
public class HardLevel extends MainActivity {
    View layout;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_level);
        layout = findViewById(R.id.linearLayoutHard);
        startLevelTimer(false);
        ImageView pauseButton = findViewById(R.id.pauseButton);

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameTimer != null) {
                    gameTimer.cancel();
                }
                CreatePopUpWindow(layout);
            }
        });
    }



}




