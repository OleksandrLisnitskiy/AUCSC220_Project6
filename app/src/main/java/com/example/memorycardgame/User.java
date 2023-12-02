package com.example.memorycardgame;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class User {

    private String TopScore;
    private long TopTime;
    private Context context;

    public SQLiteManager sqLiteManager = null;

    public void setNewTopScore(String TopScore, long TopTime, int difficulty) {
        this.TopScore = TopScore;
        this.TopTime = TopTime;

        sqLiteManager.updateTopScore(this.TopScore, this.TopTime, difficulty );
    }

    public List<Object> getTopScore(int difficulty) {
        List<Object> res = sqLiteManager.getTopScores(difficulty);;
        return res;
    }

}
