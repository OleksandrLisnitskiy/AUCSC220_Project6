package com.example.memorycardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.media.MediaPlayer;
import android.media.AudioManager;
import android.content.Context;
import android.os.CountDownTimer;
import java.util.Locale;
import android.os.Handler;
import android.content.Intent;
import android.widget.Toast;
import android.os.Looper;
import java.io.IOException;
public class MainActivity extends AppCompatActivity {
    public static boolean isSoundOn = true;

    public static Game game = new Game();
    private MediaPlayer mediaPlayer;
    private TextView timeTextView; // This is the TextView for the timer
    private CountDownTimer gameTimer;
    private final long startTime = 120 * 1000; // 2 minutes in milliseconds // 1 minute 30 seconds in milliseconds
    private final long interval = 1000; // 1 second interval

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateSoundButton();
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
        }
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.background_music);
            mediaPlayer.setVolume(1.0f, 1.0f);
            mediaPlayer = MediaPlayer.create(this, R.raw.bg_sound);
            mediaPlayer.setLooping(true);
            if (isSoundOn) {
                mediaPlayer.start();
            }
        }
        
    }
    public void onButton1Clicked(View v) {

        setContentView(R.layout.activity_easy_level);
        startLevelTimer(R.id.gameTimer);
        game.setDifficulty(1);
        game.start();
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 2; j++){
                System.out.println(game.cardBoard[i][j].getImagePath());
            }
        }
    }
    public void onButton2Clicked(View v) {

        setContentView(R.layout.activity_medium_level);
        startLevelTimer(R.id.gameTimer);
        game.setDifficulty(2);
        game.start();
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                System.out.println(game.cardBoard[i][j].getImagePath());
            }
        }
    }


    public void onButton3Clicked(View v) {

        setContentView(R.layout.activity_hard_level);
        startLevelTimer(R.id.gameTimer);

        game.setDifficulty(3);
        game.start();
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 4; j++){
                System.out.println(game.cardBoard[i][j].getImagePath());
            }
        }
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
    private void startLevelTimer(int textViewId) {
        timeTextView = findViewById(textViewId); // Find the TextView in the current level's layout

        // Cancel any existing timer to prevent multiple timers from running
        if (gameTimer != null) {
            gameTimer.cancel();
        }

        // Create a new timer
        gameTimer = new CountDownTimer(startTime, interval) {
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 60000;
                long seconds = (millisUntilFinished % 60000) / 1000;
                timeTextView.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
            }

            public void onFinish() {
                timeTextView.setText("00:00");
                showGameEnd();
            }
        }.start();
    }

    private void showGameEnd() {
        // Show a toast message for immediate feedback
        Toast.makeText(this, "Time's Up!", Toast.LENGTH_SHORT).show();

        // Handler to add a delay before switching the view
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_main);
            }
        }, 3000); // 3 second wait

    }

}