package com.example.memorycardgame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class HardLevel extends MainActivity {
    View layout;
    List<List<Integer>> imageViewIds;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        matchedPairs = 0;
        setContentView(R.layout.activity_hard_level);
        layout = findViewById(R.id.linearLayoutHard);
        TextView Score = findViewById(R.id.scoreHardLevel);
        startLevelTimer(false);
        ImageView pauseButton = findViewById(R.id.pauseButton);

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameTimer != null) {//checks if the game timer is not null and if not cancels the timer
                    gameTimer.cancel();
                }
                CreatePopUpWindow(layout);
            }
        });
        imageViewIds = getImageViewIds(6, 4);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                ImageView InfinitStone = findViewById(imageViewIds.get(i).get(j));
                int finalI = i;
                int finalJ = j;
                InfinitStone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int imageResourceId = getResources().getIdentifier(game.cardBoard[finalI][finalJ].getImagePath(), "drawable", getPackageName());


                        InfinitStone.setImageResource(imageResourceId);
                        List<Integer> coordinates = new ArrayList<>();
                        coordinates.add(finalI);
                        coordinates.add(finalJ);
                        game.flipCounterDict.add(coordinates);
                        Resources resources = getResources();
                        String packageName = getPackageName();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (game.flipCounterDict.size() >= 2) {

                                    List<Integer> card1 = game.flipCounterDict.get(0);
                                    List<Integer> card2 = game.flipCounterDict.get(1);
                                    if(game.cardBoard[card1.get(0)][card1.get(1)].getImagePath().equals(
                                            game.cardBoard[card2.get(0)][card2.get(1)].getImagePath())){
                                        Score.setText("Score:  " + game.score.successfulTry());

                                        String imageViewIdName = "imageView" + String.valueOf(card1.get(0)) + card1.get(1);
                                        int imageViewId = resources.getIdentifier(imageViewIdName, "id", packageName);
                                        ImageView card = findViewById(imageViewId);
                                        card.setImageResource(R.drawable.empty_background);
                                        imageViewIdName = "imageView" + String.valueOf(card2.get(0)) + (card2.get(1));
                                        imageViewId = resources.getIdentifier(imageViewIdName, "id", packageName);
                                        card = findViewById(imageViewId);
                                        card.setImageResource(R.drawable.empty_background);
                                        game.flipCounterDict = new ArrayList<>();
                                        matchedPairs++;
                                        if (matchedPairs == 12) { // Check if all pairs are matched
                                            winPopUp(v);  // if all cards are matched then congratulations popup screen will appear
                                            gameTimer.cancel();
                                            long timeToComplete = game.easyLevelTime -  timeLeftInMillis;

                                            game.quit(timeToComplete);
                                        }
                                    }
                                    else {
                                        Score.setText("Score:  " + game.score.failedTry());

                                        String imageViewIdName = "imageView" + String.valueOf(card1.get(0)) + (card1.get(1));
                                        int imageViewId = resources.getIdentifier(imageViewIdName, "id", packageName);
                                        ImageView card = findViewById(imageViewId);
                                        card.setImageResource(R.drawable.card_for_easy_level);
                                        imageViewIdName = "imageView" + String.valueOf(card2.get(0)) + (card2.get(1));
                                        imageViewId = resources.getIdentifier(imageViewIdName, "id", packageName);
                                        card = findViewById(imageViewId);
                                        card.setImageResource(R.drawable.card_for_hard_level);
                                        game.flipCounterDict = new ArrayList<>();
                                    }
                                    System.out.println(game.score.getScore());
                                }
                            }
                        }, 500);
                    }
                });
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 4; j++) {
                        int imageResourceId = getResources().getIdentifier(game.cardBoard[i][j].getImagePath(), "drawable", getPackageName());
                        ImageView InfinitStone = findViewById(imageViewIds.get(i).get(j));

                        InfinitStone.setImageResource(imageResourceId);
                    }
                }
            }
        }, 500);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 4; j++) {
                        ImageView InfinitStone = findViewById(imageViewIds.get(i).get(j));

                        InfinitStone.setImageResource(R.drawable.card_for_hard_level);
                    }
                }
            }
        }, 4000);



    }



}




