package com.example.memorycardgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;



public class LevelSelection extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);
    }

    public void backButton(View v){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}