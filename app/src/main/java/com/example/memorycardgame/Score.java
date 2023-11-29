package com.example.memorycardgame;

public class Score {
    private int score = 0;

    public int getScore() {
        return score;
    }

    public int failedTry(){
        score -= 20;
        return score;
    }

    public int successfulTry(){
        score += 20;
        return score;
    }

    public int restartScore(){
        score = 0;
        return score;
    }
}
