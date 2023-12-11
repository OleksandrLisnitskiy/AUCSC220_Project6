package com.example.memorycardgame;

public class Score {
    private int score = 0;//variable to store the score value


    public int getScore() {
        return score;
    } // returns the current score

    public int failedTry(){// Decrements the score by 20 points in the case of a failed try, but ensures the score does not drop below 0
        if (score < 20){
            score = 0;
        }
        else {
            score -= 20;
        }
        return score;
    }

    public int successfulTry(){//Increments the score by 20 points in the case of a successful try. Returns the updated score.
        score += 20;
        return score;
    }

    public int restartScore(){//Resets the score to 0. Used when the user restarts the game.
        return score;
    }
}
