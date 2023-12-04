package com.example.memorycardgame;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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

        List<List<Integer>> imageViewIds = getImageViewIds();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                ImageView InfinitStone = findViewById(imageViewIds.get(i).get(j));
                System.out.println(imageViewIds.get(i).get(j));
                int finalI = i;
                int finalJ = j;
                InfinitStone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int imageResourceId = getResources().getIdentifier(game.cardBoard[finalI][finalJ].getImagePath(), "drawable", getPackageName());
                        InfinitStone.setImageResource(imageResourceId);
                    }
                });
            }
        }

    }


}


