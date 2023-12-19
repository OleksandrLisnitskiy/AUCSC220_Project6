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
import java.util.function.IntFunction;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EasyLevel extends MainActivity {
    View layout;
    List<List<Integer>> imageViewIds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        matchedPairs = 0;
        setContentView(R.layout.activity_easy_level);
        layout = findViewById(R.id.linearLayoutEasy);
        // calls method to start level timer.
        startLevelTimer(false);
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

                        /*
                        Set ImageResource of the card clicked and then disables the card to avoid it
                         from being clicked again.
                         */
                        if (game.flipCounterDict.size() < 2) {
                            flipFront(InfinitStone, imageResourceId);

                            InfinitStone.setEnabled(false);
                            List<Integer> coordinates = new ArrayList<>();
                            //Add coordinates of the card clicked to Array.
                            coordinates.add(finalI);
                            coordinates.add(finalJ);
                            game.flipCounterDict.add(coordinates);

                        }
                        Resources resources = getResources();
                        String packageName = getPackageName();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            /**
                             * The following method is used to run the game mechanics.
                             */
                            public void run() {
                                if (game.flipCounterDict.size() >= 2) {

                                    List<Integer> card1 = game.flipCounterDict.get(0);
                                    List<Integer> card2 = game.flipCounterDict.get(1);
                                    //Checks if the images on the 2 cards match.
                                    if(game.cardBoard[card1.get(0)][card1.get(1)].getImagePath().equals(
                                            game.cardBoard[card2.get(0)][card2.get(1)].getImagePath())){
                                        //If they do match then score is updated as a successful try.
                                        Score.setText("Score:  " + game.score.successfulTry());

                                        //Get the ID's of the matched cards.
                                        String imageViewIdName = "imageView" + String.valueOf(card1.get(0)) + (card1.get(1));
                                        int imageViewId = resources.getIdentifier(imageViewIdName, "id", packageName);

                                        /*
                                        Helps disable click listeners for both cards matched so they
                                        can't be clicked again.
                                         */
                                        ImageView card = findViewById(imageViewId);
                                        card.setEnabled(false);
                                        card.setImageResource(R.drawable.empty_background);
                                        imageViewIdName = "imageView" + String.valueOf(card2.get(0)) + (card2.get(1));
                                        imageViewId = resources.getIdentifier(imageViewIdName, "id", packageName);

                                        card = findViewById(imageViewId);
                                        card.setImageResource(R.drawable.empty_background);
                                        card.setEnabled(false);
                                        game.flipCounterDict = new ArrayList<>();
                                        matchedPairs++;
                                        if (matchedPairs == 4) { // Check if all pairs are matched
                                            winPopUp(v);// if all cards are matched then congratulations popup screen will appear
                                            gameTimer.cancel();

                                            long timeToComplete = game.easyLevelTime -  timeLeftInMillis;

                                            game.quit(timeToComplete);
                                        }
                                    }
                                    /*
                                    Is executed when the cards don't match.
                                    Executes a failed try scenario where score is updated for a
                                    failed try. Unmatched cards are flipped back and click listener
                                    is enabled.
                                     */
                                    else {
                                        Score.setText("Score:  " + game.score.failedTry());

                                        //Getting ID's of unmatched cards.
                                        String imageViewIdName = "imageView" + String.valueOf(card1.get(0)) + (card1.get(1));
                                        int imageViewId = resources.getIdentifier(imageViewIdName, "id", packageName);
                                        //Flip back unmatched card.
                                        ImageView card = findViewById(imageViewId);
//                                        card.setImageResource(R.drawable.card_for_easy_level);
                                        flipBack(card);
                                        //Enabling the click listeners for first card.
                                        card.setEnabled(true);
                                        imageViewIdName = "imageView" + String.valueOf(card2.get(0)) + (card2.get(1));
                                        imageViewId = resources.getIdentifier(imageViewIdName, "id", packageName);
                                        card = findViewById(imageViewId);
                                        flipBack(card);

//                                        card.setImageResource(R.drawable.card_for_easy_level);
                                        //Enabling the click listeners for second card.
                                        card.setEnabled(true);
                                        //Resets the flipCounterDict for the next flip.
                                        game.flipCounterDict = new ArrayList<>();
                                    }
//
                                }

                            }
                        }, 1000);
                    }
                });
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        /*
        Delays execution to show the card images after 500 milliseconds of the game starting by
        running a nested for loop.
        */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 2; j++) {
                        int imageResourceId = getResources().getIdentifier(game.cardBoard[i][j].getImagePath(), "drawable", getPackageName());
                        ImageView InfinitStone = findViewById(imageViewIds.get(i).get(j));

                        flipFront(InfinitStone, imageResourceId);
                    }
                }
            }
        }, 250);


        //Delays the execution to hide the card images after 1500 milliseconds by running a nested for loop.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 2; j++) {
                        ImageView InfinitStone = findViewById(imageViewIds.get(i).get(j));
                        flipBack(InfinitStone);
                        //Sets the card image to a default back of the card image.
                    }
                }
            }
        }, 1750);



    }
}



