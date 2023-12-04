package com.example.memorycardgame;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.annotation.RequiresApi;

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
    }

    protected void onCreate2(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_level);
    }

    public void onCardClick(View view) {
        ImageView cardImage = findViewById(R.id.imageView12);
        cardImage.setImageResource(R.drawable.mindstone);
    }

    public void onCardClick2(View view){
        ImageView cardImage2 = findViewById(R.id.imageView13);
        cardImage2.setImageResource(R.drawable.timestone);
    }
    public void onCardClick3(View view){
        ImageView cardImage3 = findViewById(R.id.imageView14);
        cardImage3.setImageResource(R.drawable.spacestone);
    }
    public void onCardClick4(View view){
        ImageView cardImage4 = findViewById(R.id.imageView15);
        cardImage4.setImageResource(R.drawable.realitystone);
    }
    public void onCardClick5(View view) {
        ImageView cardImage5 = findViewById(R.id.imageView16);
        cardImage5.setImageResource(R.drawable.spacestone);
    }

    public void onCardClick6(View view){
        ImageView cardImage6 = findViewById(R.id.imageView17);
        cardImage6.setImageResource(R.drawable.realitystone);
    }
    public void onCardClick7(View view){
        ImageView cardImage7 = findViewById(R.id.imageView18);
        cardImage7.setImageResource(R.drawable.mindstone);
    }
    public void onCardClick8(View view){
        ImageView cardImage8 = findViewById(R.id.imageView19);
        cardImage8.setImageResource(R.drawable.timestone);
    }



}


