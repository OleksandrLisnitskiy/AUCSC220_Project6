package com.example.memorycardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.PopupWindow;
import android.widget.TextView;

import android.media.MediaPlayer;
import android.media.AudioManager;
import android.content.Context;
import android.os.CountDownTimer;
import java.util.Locale;
import android.os.Handler;

import android.widget.Toast;
import android.os.Looper;

public class MainActivity extends AppCompatActivity {
    public static boolean isSoundOn = true;

    public static Game game = new Game();
    private MediaPlayer mediaPlayer;
    private TextView timeTextView; // This is the TextView for the timer
    protected CountDownTimer gameTimer;
    private final long startTime = 120 * 1000; // 2 minutes in milliseconds
    private final long interval = 1000; // 1 second interval
    protected boolean isGamePaused = false;
    private long timeLeftInMillis = startTime;


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


        Button endGame = findViewById(R.id.quitButton);

        endGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                System.exit(0);
            }
        });
    }


    public void onButton1Clicked(View v) {

        Intent intent = new Intent(this, EasyLevel.class);
        startActivity(intent);
        game.setDifficulty(1);
        game.start();
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 2; j++){
                System.out.println(game.cardBoard[i][j].getImagePath());
            }
        }
    }
    public void onButton2Clicked(View v) {

        Intent intent = new Intent(this, MediumLevel.class);
        startActivity(intent);
        game.setDifficulty(2);
        game.start();
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                System.out.println(game.cardBoard[i][j].getImagePath());
            }
        }
    }


    public void onButton3Clicked(View v) {

        Intent intent = new Intent(this, HardLevel.class);
        startActivity(intent);
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
        if (isGamePaused) {
            startLevelTimer();
            isGamePaused = false;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        if (gameTimer != null) {
            gameTimer.cancel();
            isGamePaused = true;
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


    public void startLevelTimer() {
        timeTextView = findViewById(R.id.gameTimer);

        if (gameTimer != null) {
            gameTimer.cancel();
        }

        gameTimer = new CountDownTimer(timeLeftInMillis, interval) {
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText(timeLeftInMillis);
            }

            public void onFinish() {
                timeTextView.setText("Time: 00:00");
                showGameEnd();
            }
        }.start();
    }

    private void updateTimerText(long timeMillis) {
        int minutes = (int) (timeMillis / 1000) / 60;
        int seconds = (int) (timeMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timeTextView.setText("Time: " + timeFormatted);
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

    public void backButton(View v){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void CreatePopUpWindow(View layout) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUpView = inflater.inflate(R.layout.pause_window, null);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        boolean focusable = false;
        PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);
        layout.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
            }
        });
        Button resumButton, quitButton, restartButton;

        resumButton = (Button) popUpView.findViewById(R.id.resumeButton);
        quitButton = (Button) popUpView.findViewById(R.id.stopGameButton);
        restartButton = (Button) popUpView.findViewById(R.id.restartButton);

        resumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                popupWindow.dismiss();

            }
        });
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                popupWindow.dismiss();
                //        startLevelTimer(R.id.gameTimer);
                game.restart();
                for(int i = 0; i < 4; i++){
                    for(int j = 0; j < 2; j++){
                        System.out.println(game.cardBoard[i][j].getImagePath());
                    }
                }

            }
        });
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                game.quit();
                popupWindow.dismiss();
                backButton(view);
            }
        });

    }

}