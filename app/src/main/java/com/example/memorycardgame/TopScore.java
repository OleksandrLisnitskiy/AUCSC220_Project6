package com.example.memorycardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TopScore extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_score);
    }

    public void backButton(View v){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}