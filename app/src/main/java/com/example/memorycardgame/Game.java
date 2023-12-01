package com.example.memorycardgame;

import android.os.Build;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;

public class Game {
    Score score = new Score();
    private int difficulty;
    public LocalDateTime game_time;
    public long easyLevelTime = 90 * 1000;
    public long mediumLevelTime = 150 * 1000;
    public long hardLevelTime = 210 * 1000;
    public Card[][] cardBoard;

    public void start(){
        score.restartScore();
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

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
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

        for (int i = 0; i < sizeX; i++) {
            counter.put(i, 0);
        }
        for (int i = 0; i < sizeX; i++){
            for (int j = 0; j < sizeY; j++)
            {
                int temp = random.nextInt(sizeX);
                while (counter.get(temp) >= sizeY){
                    temp = random.nextInt(sizeX);
                }
                counter.put(temp, (counter.get(temp) + 1));
                cardBoard[i][j] = new Card(String.valueOf(temp), new int[]{i, j});
            }
        }
    }
    public void quit(){

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
