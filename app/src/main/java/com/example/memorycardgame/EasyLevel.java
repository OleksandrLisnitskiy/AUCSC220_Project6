package com.example.memorycardgame;

import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EasyLevel extends MainActivity {
    View layout;
    List<List<Integer>> imageViewIds;
    private int matchedPairs = 0;// to keep track of matched pairs in this level

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_level);
        layout = findViewById(R.id.linearLayoutEasy);
        startLevelTimer(false);// calls method to start level timer
        ImageView pauseButton = findViewById(R.id.pauseButton);
        imageViewIds = getImageViewIds(4, 2);
        TextView Score = findViewById(R.id.scoreEasyLevel);

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameTimer != null) { //checks if the game timer is not null and if not cancels the timer
                    gameTimer.cancel();
                }
                CreatePopUpWindow(layout);
            }
        });

        /*
        The nested for loop is used to initialize and set up click listeners for cards in a 4x2 grid
         */
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                //Used to reference image at i,j position of the grid.
                ImageView InfinitStone = findViewById(imageViewIds.get(i).get(j));
                int finalI = i;
                int finalJ = j;
                //Used to set listener for each card in the grid.
                InfinitStone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Used to get the imageResourceId of the card clicked.
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

                                        String imageViewIdName = "imageView" + String.valueOf(card1.get(0)) + (card1.get(1));
                                        int imageViewId = resources.getIdentifier(imageViewIdName, "id", packageName);
                                        ImageView card = findViewById(imageViewId);
                                        card.setOnClickListener(null);
                                        card.setImageResource(R.drawable.empty_background);
                                        imageViewIdName = "imageView" + String.valueOf(card2.get(0)) + (card2.get(1));
                                        imageViewId = resources.getIdentifier(imageViewIdName, "id", packageName);

                                        card = findViewById(imageViewId);
                                        card.setImageResource(R.drawable.empty_background);
                                        card.setOnClickListener(null);
                                        game.flipCounterDict = new ArrayList<>();
                                        matchedPairs++;
                                        if (matchedPairs == 4) { // Check if all pairs are matched
                                            winPopUp(v);// if all cards are matched then congratulations popup screen will appear
                                            gameTimer.cancel();
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
                                        card.setImageResource(R.drawable.card_for_easy_level);
                                        game.flipCounterDict = new ArrayList<>();
                                    }
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
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 2; j++) {
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
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 2; j++) {
                        ImageView InfinitStone = findViewById(imageViewIds.get(i).get(j));

                        InfinitStone.setImageResource(R.drawable.card_for_easy_level);
                    }
                }
            }
        }, 1500);



    }
}



