package com.example.memorycardgame;

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
        Random random = new Random();
        HashMap <Integer, Integer> counter;
        switch (difficulty){

            case 1:
                counter = new HashMap<>();
                cardBoard = new Card[4][2];

                for (int i = 0; i < 4; i++) {
                    counter.put(i, 0);
                }
                for (int i = 0; i < 4; i++){
                    for (int j = 0; j < 2; j++)
                    {
                        int temp = random.nextInt(4);
                        while (counter.get(temp) >= 2){
                            temp = random.nextInt(4);
                        }
                        counter.put(temp, (counter.get(temp) + 1));
                        cardBoard[i][j] = new Card(String.valueOf(temp), new int[]{i, j});
                    }
                }
                break;
            case 2:
                counter = new HashMap<>();
                cardBoard = new Card[4][4];

                for (int i = 0; i < 8; i++) {
                    counter.put(i, 0);
                }
                for (int i = 0; i < 4; i++){
                    for (int j = 0; j < 4; j++)
                    {
                        int temp = random.nextInt(8);
                        while (counter.get(temp) >= 2){
                            temp = random.nextInt(8);
                        }
                        counter.put(temp, (counter.get(temp) + 1));
                        cardBoard[i][j] = new Card(String.valueOf(temp), new int[]{i, j});
                    }
                }
                break;
            case 3:
                counter = new HashMap<>();
                cardBoard = new Card[6][4];

                for (int i = 0; i < 12; i++) {
                    counter.put(i, 0);
                }
                for (int i = 0; i < 6; i++){
                    for (int j = 0; j < 4; j++)
                    {
                        int temp = random.nextInt(12);
                        while (counter.get(temp) >= 2){
                            temp = random.nextInt(12);
                        }
                        counter.put(temp, (counter.get(temp) + 1));
                        cardBoard[i][j] = new Card(String.valueOf(temp), new int[]{i, j});
                    }
                }
                break;
        }


    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void removeCard(Card card){
        int[] pos = card.getPosition();
        cardBoard[pos[0]][pos[1]] = null;

    }
    public void quit(){

    }
    public void restart(){
        this.start();
    }

    public void pause(){

    }

    public void resume(){

    }


    public boolean checkCards(int x1, int y1, int x2, int y2){
        return cardBoard[x1][y1].getImagePath().equals(cardBoard[x2][y2].getImagePath());

    }
}
