package com.example.memorycardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity {
    private boolean turnedOn = false;
    public static boolean isSoundOn = true;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateSoundButton();
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.background_music);
            mediaPlayer.setLooping(true);
            if (isSoundOn) {
                mediaPlayer.start();
            }
        }
    }
    public void onButton1Clicked(View v) {
        setContentView(R.layout.activity_easy_level);
    }
    public void onButton2Clicked(View v) {
        setContentView(R.layout.activity_medium_level);
    }
    public void onButton3Clicked(View v) {
        setContentView(R.layout.activity_hard_level);
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

    public void SoundChange(View v) {
        isSoundOn = !isSoundOn;
        if (isSoundOn) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start(); // Start playing only if it's not already playing
            }
        } else {
            mediaPlayer.pause(); // Pause the sound
        }
        updateSoundButton();
    }


    private void updateSoundButton() {
        ImageView button = findViewById(R.id.Sound);
        if (isSoundOn) {
            button.setBackgroundResource(R.drawable.sound_button_on);
        } else {
            button.setBackgroundResource(R.drawable.sound_button_off);
        }

    }
    protected void onResume() {
        super.onResume();
        updateSoundButton();
        if (isSoundOn && mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void playSound(View view) { // Call this method when you want to play the sound
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


}