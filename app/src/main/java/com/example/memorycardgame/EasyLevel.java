package com.example.memorycardgame;

import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EasyLevel extends MainActivity {
    View layout;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_level);
        layout = findViewById(R.id.linearLayoutEasy);
        startLevelTimer(false);
        ImageView pauseButton = findViewById(R.id.pauseButton);

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameTimer != null) {
                    gameTimer.cancel();
                }
                CreatePopUpWindow(layout);
            }
        });

        List<List<Integer>> imageViewIds = getImageViewIds(4, 2);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
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
                        if (game.flipCounterDict.size() == 2) {

                            List<Integer> card1 = game.flipCounterDict.get(0);
                            List<Integer> card2 = game.flipCounterDict.get(1);
                            if(game.cardBoard[card1.get(0)][card1.get(1)].getImagePath().equals(
                                    game.cardBoard[card2.get(0)][card2.get(1)].getImagePath())){
                                game.score.successfulTry();
                                String imageViewIdName = "imageView" + String.valueOf(card1.get(0)) + (card1.get(1));
                                int imageViewId = resources.getIdentifier(imageViewIdName, "id", packageName);
                                ImageView card = findViewById(imageViewId);
                                card.setImageResource(R.drawable.empty_background);
                                imageViewIdName = "imageView" + String.valueOf(card2.get(0)) + (card2.get(1));
                                imageViewId = resources.getIdentifier(imageViewIdName, "id", packageName);
                                card = findViewById(imageViewId);
                                card.setImageResource(R.drawable.empty_background);
                                game.flipCounterDict = new ArrayList<>();

                            }
                            else {

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
//
                    }
                });
            }
        }

    }
}



