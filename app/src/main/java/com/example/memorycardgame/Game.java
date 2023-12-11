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
    Score score = new Score(); // Initializing the score class
    public List<List<Integer>> flipCounterDict = new ArrayList<>(); //
    private int difficulty; // Difficulty level of the game
    User user = new User(); // Initializing User class to control the top score
    public LocalDateTime game_time; // date and time when the level is started
    public long easyLevelTime = 60 * 1000; // Initial time for Easy level
    public long mediumLevelTime = 120 * 1000; // Initial time for Medium level
    public long hardLevelTime = 150 * 1000; // Initial time for Hard level
    public Card[][] cardBoard; // Initializing empty game board

    // arrays with the names of the images for each level
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
    private final String[] mediumLevelImages = {"avengersymbol",
            "blackpanthersymbol",
            "hammer",
            "spider",
            "symbol1",
            "symbol2",
            "symbol3",
            "shield"};

    public void start(){
        score.restartScore(); // Set the score to zero
        flipCounterDict = new ArrayList<>(); // Initializing the empty array to store the positions of flipped cards later.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            game_time = LocalDateTime.now(); // set the date of the game to the current date
        }
        switch (difficulty){
        // Create the card board with random cards on each position depending on the difficulty
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


    }

    public int getDifficulty() {
        return difficulty;
    }



    private void createCardBoard(int sizeX, int sizeY){
        Random random = new Random(); // Initialize Instance of Random class
        HashMap<Integer, Integer> counter = new HashMap<>(); // Dictionary to Index of the elements and the number of it's occurrences in the card board
        cardBoard = new Card[sizeX][sizeY]; // create empty card board with the size depending on the difficulty

        for (int i = 0; i < Math.round(sizeX * sizeY/2); i++) {
            counter.put(i, 0); // set number of occurrence on each key to 0
        }
        for (int i = 0; i < sizeX; i++){
            for (int j = 0; j < sizeY; j++)
            {
                int temp = random.nextInt(Math.round(sizeX * sizeY/2));
                while (counter.get(temp) >= 2){
                    // if number of occurrences of this card is more than 2 then get the different card
                    temp = random.nextInt(Math.round(sizeX * sizeY/2));
                }
                counter.put(temp, (counter.get(temp) + 1)); // increment the occurrence of the element in the dictionary
                switch (difficulty){
                    // Add the card instance to the card board
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

        List<Object> lastTop = user.getTopScore(difficulty); // Get the last top score from the data base

        if ((int) lastTop.get(0) < score.getScore() || (int) lastTop.get(1) > timeToComplete ){
            // if the current score or time is better then before, replace it in data base
            user.setNewTopScore(String.valueOf(score.getScore()), timeToComplete, difficulty);
        }
    }
    public void restart(){

        this.start();
    }

}
