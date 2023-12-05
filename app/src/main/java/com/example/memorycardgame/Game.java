package com.example.memorycardgame;

import android.app.UiAutomation;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Game {
    Score score = new Score();
    public List<List<Integer>> flipCounterDict = new ArrayList<>();
    private int difficulty;
    User user = new User();
    public LocalDateTime game_time;
    public long easyLevelTime = 90 * 1000;
    public long mediumLevelTime = 150 * 1000;
    public long hardLevelTime = 210 * 1000;
    public Card[][] cardBoard;
    private final String[] easyLevelImages = {"spacestone",
            "mindstone",
            "timestone",
            "realitystone"};
    private final String[] hardLevelImages = {"antman",
            "avengers",
            "blackpanther",
            "blackwidow",
            "captainamerica",
            "captainmarvel",
            "drstrange",
            "guardians",
            "hulk",
            "ironman",
            "spiderman",
            "thor"};
    private final String[] mediumLevelImages = {"antman",
            "avengers",
            "blackpanther",
            "blackwidow",
            "captainamerica",
            "captainmarvel",
            "drstrange",
            "guardians"};

    public void start(){
        score.restartScore();
        flipCounterDict = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            game_time = LocalDateTime.now();
        }
        switch (difficulty){

            case 1:
                createCardBoard(4, 2);
                break;
            case 2:
                createCardBoard(4, 4);
                break;
            case 3:
                createCardBoard(6, 4);
                break;
        }
    }

    public void setDifficulty(int difficulty, Context context) {
        this.difficulty = difficulty;
        user.sqLiteManager = SQLiteManager.instanceOfDatabase(context);

    }

    public int getDifficulty() {
        return difficulty;
    }

    public void removeCard(int x, int y){
        cardBoard[x][y] = null;

    }

    private void createCardBoard(int sizeX, int sizeY){
        Random random = new Random();
        HashMap<Integer, Integer> counter = new HashMap<>();
        cardBoard = new Card[sizeX][sizeY];

        for (int i = 0; i < Math.round(sizeX * sizeY/2); i++) {
            counter.put(i, 0);
        }
        for (int i = 0; i < sizeX; i++){
            for (int j = 0; j < sizeY; j++)
            {
//                System.out.println();
                int temp = random.nextInt(Math.round(sizeX * sizeY/2));
                while (counter.get(temp) >= 2){
                    temp = random.nextInt(Math.round(sizeX * sizeY/2));
                }
                counter.put(temp, (counter.get(temp) + 1));
                switch (difficulty){
                    case 1:
                        cardBoard[i][j] = new Card(easyLevelImages[temp], new int[]{i, j});
                        break;
                    case 2:
                        cardBoard[i][j] = new Card(mediumLevelImages[temp], new int[]{i, j});
                        break;
                    case 3:
                        cardBoard[i][j] = new Card(hardLevelImages[temp], new int[]{i, j});
                }

            }
        }
    }
    public void quit(long timeToComplete){

        List<Object> lastTop = user.getTopScore(difficulty);
        if ((int) lastTop.get(0) < score.getScore() && (long) lastTop.get(1) > timeToComplete ){
            user.setNewTopScore(String.valueOf(score.getScore()), timeToComplete, difficulty);
        }

    }
    public void restart(){

        this.start();
    }




    public void checkCards(int x1, int y1, int x2, int y2){
        if (cardBoard[x1][y1].getImagePath().equals(cardBoard[x2][y2].getImagePath())){
            score.successfulTry();
            removeCard(x1, y1);
            removeCard(x2, y2);
        }
        else {
            score.failedTry();
        }

    }
}
