package com.example.memorycardgame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.time.Duration;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TopScore extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_score);

        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);

         List<Object> easyTopValue = sqLiteManager.getTopScores(1);
         List<Object> mediumTopValue = sqLiteManager.getTopScores(2);
         List<Object> hardTopValue = sqLiteManager.getTopScores(3);

        TextView easyTopScore = findViewById(R.id.scoreEasy);
        TextView mediumTopScore = findViewById(R.id.scoreMedium);
        TextView hardTopScore = findViewById(R.id.scoreHard);
        TextView easyTopTime = findViewById(R.id.timeEasy);
        TextView mediumTopTime = findViewById(R.id.timeMedium);
        TextView hardTopTime = findViewById(R.id.timeHard);

        easyTopScore.setText(String.valueOf(easyTopValue.get(0)));
        mediumTopScore.setText(String.valueOf(mediumTopValue.get(0)));
        hardTopScore.setText(String.valueOf(hardTopValue.get(0)));

        easyTopTime.setText(intToMinutes((int) easyTopValue.get(1)));
        mediumTopTime.setText( intToMinutes((int) mediumTopValue.get(1)));
        hardTopTime.setText(intToMinutes((int) hardTopValue.get(1)));
    }

    public String intToMinutes(int millis){
         // Replace this with your actual milliseconds value

        // Create a Duration object from milliseconds
        Duration duration = Duration.ofMillis(millis);

        // Get minutes and seconds
        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();

        // Format the result to min:sec format
        return String.format("%d:%02d", minutes, seconds);
    }


}