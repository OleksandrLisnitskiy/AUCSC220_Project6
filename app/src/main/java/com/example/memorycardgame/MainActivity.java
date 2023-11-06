package com.example.memorycardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private boolean turnedOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Set the onClickListener using the method
    }

    public void openLevelSelection(View v){

        Intent intent = new Intent(this, LevelSelection.class);
        startActivity(intent);
    }

    public void openTopScore(View v){

        Intent intent = new Intent(this, TopScore.class);
        startActivity(intent);
    }

    public void Quit(View v){

        System.exit(0);
    }

    public void SoundChange(View v){
        ImageView button = findViewById(R.id.Sound);

        if (turnedOn) {
            button.setBackgroundResource(R.drawable.sound_button_on); // Switch to image2
        } else {
            button.setBackgroundResource(R.drawable.sound_button_off); // Switch to image1
        }
        turnedOn = !turnedOn;
    }
}