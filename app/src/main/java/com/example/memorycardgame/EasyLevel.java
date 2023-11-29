package com.example.memorycardgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class EasyLevel extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_level);

//        ImageView pause = findViewById(R.id.pause);
//
//        pause.setOnClickListener(v -> {
//            ConstraintLayout layout = findViewById(R.id.popupWindow);
//            ConstraintLayout originalLayer = findViewById(R.id.linearLayout);
//
//            ConstraintSet constraintSet = new ConstraintSet();
//            constraintSet.clone(layout);
//
//            constraintSet.constrainWidth(R.id.popupWindow, originalLayer.getWidth());
//            constraintSet.constrainHeight(R.id.popupWindow, originalLayer.getHeight());
//        });


    }

    public void onPause(View v){
        ConstraintLayout layout = findViewById(R.id.popupWindow);
        ConstraintLayout originalLayer = findViewById(R.id.linearLayout);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);

        constraintSet.constrainWidth(R.id.popupWindow, originalLayer.getWidth());
        constraintSet.constrainHeight(R.id.popupWindow, originalLayer.getHeight());
    }

}