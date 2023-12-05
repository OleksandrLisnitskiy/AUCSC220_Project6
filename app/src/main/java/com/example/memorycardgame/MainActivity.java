package com.example.memorycardgame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.os.Handler;

import android.widget.Toast;
import android.os.Looper;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {
    public static boolean isSoundOn = true;

    public static Game game = new Game();
    private MediaPlayer mediaPlayer;
    private TextView timeTextView; // This is the TextView for the timer
    protected CountDownTimer gameTimer;
    private final long interval = 1000; // 1 second interval
    protected boolean isGamePaused = false;
    public long startTime;
    private long timeLeftInMillis = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateSoundButton();
        Sound.init(this);


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
        game.setDifficulty(1, this);
        game.start();


//        TESTING
//        for(int i = 0; i < 4; i++){
//
//            for(int j = 0; j < 2; j++){
//                System.out.println(game.cardBoard[i][j].getImagePath());
//            }
//        }
    }
    public void onButton2Clicked(View v) {

        Intent intent = new Intent(this, MediumLevel.class);
        startActivity(intent);
        game.setDifficulty(2, this);
        game.start();

//        TESTING
//        for(int i = 0; i < 4; i++){
//            for(int j = 0; j < 4; j++){
//                System.out.println(game.cardBoard[i][j].getImagePath());
//            }
//        }
    }


    public void onButton3Clicked(View v) {

        Intent intent = new Intent(this, HardLevel.class);
        startActivity(intent);
        game.setDifficulty(3, this);
        game.start();

        //        TESTING
//        for(int i = 0; i < 6; i++){
//            for(int j = 0; j < 4; j++){
//                System.out.println(game.cardBoard[i][j].getImagePath());
//            }
//        }
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
        Sound.toggleSound();
        isSoundOn = Sound.isSoundOn();
        updateSoundButton();
    }
    protected void updateSoundButton() {
        ImageView button = findViewById(R.id.Sound);
        if (isSoundOn) {
            button.setBackgroundResource(R.drawable.sound_button_on);
        } else {
            button.setBackgroundResource(R.drawable.sound_button_off);
        }
    }


    public void startLevelTimer(boolean restart) {
        timeTextView = findViewById(R.id.gameTimer);

        if (gameTimer != null) {
            gameTimer.cancel();
        }
        if(restart ||timeLeftInMillis == -1){
            switch (game.getDifficulty()){
                case 1:
                    timeLeftInMillis = game.easyLevelTime;
                    break;
                case 2:
                    timeLeftInMillis = game.mediumLevelTime;
                    break;
                case 3:
                    timeLeftInMillis = game.hardLevelTime;
                    break;
            }
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
        };gameTimer.start();
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

        resumButton =  popUpView.findViewById(R.id.resumeButton);
        quitButton =  popUpView.findViewById(R.id.stopGameButton);
        restartButton =  popUpView.findViewById(R.id.restartButton);

        resumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                popupWindow.dismiss();
                startLevelTimer(false);
            }
        });
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                popupWindow.dismiss();
                timeLeftInMillis = startTime;
                startLevelTimer(true);
                game.restart();
                List<List<Integer>> imageViewIds;
                switch (game.getDifficulty()){
                    case 1:
                        imageViewIds = getImageViewIds(4, 2);
                        for (int i = 0; i < 4; i++) {
                            for (int j = 0; j < 2; j++) {
                                ImageView card = findViewById(imageViewIds.get(i).get(j));
                                card.setImageResource(R.drawable.card_for_easy_level);
                            }
                            }
                        break;
                    case 2:
                        imageViewIds = getImageViewIds(4, 4);
                        for (int i = 0; i < 4; i++) {
                            for (int j = 0; j < 4; j++) {
                                ImageView card = findViewById(imageViewIds.get(i).get(j));
                                card.setImageResource(R.drawable.card_for_medium_level);
                            }
                        }
                        break;
                    case 3:
                        imageViewIds = getImageViewIds(6, 4);
                        for (int i = 0; i < 6; i++) {
                            for (int j = 0; j < 4; j++) {
                                ImageView card = findViewById(imageViewIds.get(i).get(j));
                                card.setImageResource(R.drawable.card_for_hard_level);
                            }
                        }
                        break;
                }


            }
        });
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                game.quit(0);
                gameTimer.cancel();
                popupWindow.dismiss();
                backButton(view);
            }
        });

    }
    protected List<List<Integer>> getImageViewIds(int sizeX, int sizeY) {
        List<List<Integer>> imageViewIds = new ArrayList<>();
        Resources resources = getResources();
        String packageName = getPackageName();

        for (int i = 0; i < sizeX; i++) {
            List<Integer> InnerArray = new ArrayList<>();
            for (int j = 0; j < sizeY; j++) {
                String imageViewIdName = "imageView" + String.valueOf(i) + j;
                int imageViewId = resources.getIdentifier(imageViewIdName, "id", packageName);

                InnerArray.add(imageViewId);

            }
            imageViewIds.add(InnerArray);
        }

        return imageViewIds;
    }

}