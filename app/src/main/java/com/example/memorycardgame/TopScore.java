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

         List<Object> easyTopValue = sqLiteManager.getTopScores(1); // Get the Easy level top time and score from the dataBase
         List<Object> mediumTopValue = sqLiteManager.getTopScores(2); // Get the Medium level top time and score from the dataBase
         List<Object> hardTopValue = sqLiteManager.getTopScores(3); // Get the Hard level top time and score from the dataBase

        TextView easyTopScore = findViewById(R.id.scoreEasy); // Getting the textView of the Easy level Top Score
        TextView mediumTopScore = findViewById(R.id.scoreMedium); // Getting the textView of the Medium level Top Score
        TextView hardTopScore = findViewById(R.id.scoreHard); // Getting the textView of the Hard level Top Score
        TextView easyTopTime = findViewById(R.id.timeEasy); // Getting the textView of the Easy level Top Time
        TextView mediumTopTime = findViewById(R.id.timeMedium); // Getting the textView of the Easy level Top Time
        TextView hardTopTime = findViewById(R.id.timeHard); // Getting the textView of the Easy level Top Time

        easyTopScore.setText(String.valueOf(easyTopValue.get(0))); // Set the new value for Easy top Score
        mediumTopScore.setText(String.valueOf(mediumTopValue.get(0))); // Set the new value for Medium top Score
        hardTopScore.setText(String.valueOf(hardTopValue.get(0))); // Set the new value for Hard top Score

        easyTopTime.setText(intToMinutes((int) easyTopValue.get(1))); // Set the new value for Easy top Time
        mediumTopTime.setText( intToMinutes((int) mediumTopValue.get(1))); // Set the new value for Medium top Time
        hardTopTime.setText(intToMinutes((int) hardTopValue.get(1))); // Set the new value for Hard top Time
    }

    public String intToMinutes(int millis){

        // Create a Duration object from milliseconds
        Duration duration = Duration.ofMillis(millis);

        // Get minutes and seconds
        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();

        // Format the result to min:sec format
        return String.format("%d:%02d", minutes, seconds);
    }


}