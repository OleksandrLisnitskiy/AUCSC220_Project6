package com.example.memorycardgame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.PopupWindow;
import android.widget.TextView;

import android.media.MediaPlayer;
import android.media.AudioManager;
import android.content.Context;
import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.os.Handler;

import android.widget.Toast;
import android.os.Looper;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {
    protected static boolean isSoundOn = true;
    public static Game game = new Game();
    protected int matchedPairs = 0;// to keep track of matched pairs in this level
    private TextView timeTextView; // This is the TextView for the timer
    protected CountDownTimer gameTimer;//object for managing the game timer.
    private final long interval = 1000; // 1 second interval
    protected boolean isGamePaused = false; // flag to indicate if the game is paused.
    public long startTime;// variable to store the start time of the timer.
    public long timeLeftInMillis = -1;// variable to store the remaining time in milliseconds.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateSoundButton();//updates the sound button
        Sound.init(this); // initialise the sound
        Button endGame = findViewById(R.id.quitButton);

        endGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                finish();
                Sound.stopSound(); // Stop the sound playback
            }
        });
    }

    /**
     * Function to send user to the Medium difficulty level during the level selection activity
     * @param v - View of the current layout User is in.
     */
    public void onButton2Clicked(View v) {

        Intent intent = new Intent(this, MediumLevel.class); // create Intent with the activity to send user to
        startActivity(intent); // use the intent to start new activity
        game.setDifficulty(2, this); // Set the difficulty of current game to Medium(2)
        game.start();

    }

    /**
     * Function to send user to the Easy difficulty level during the level selection activity
     * @param v - View of the current layout User is in.
     */
    public void onButton1Clicked(View v) {

        Intent intent = new Intent(this, EasyLevel.class);
        startActivity(intent);
        game.setDifficulty(1, this);
        game.start();

    }

    /**
     * Function to send user to the Hard difficulty level during the level selection activity
     * @param v - View of the current layout User is in.
     */
    public void onButton3Clicked(View v) {
        Intent intent = new Intent(this, HardLevel.class);
        startActivity(intent);
        game.setDifficulty(3, this);
        game.start();

    }

    /**
     * Function to send user to the Level Selection Activity from the main page.
     * @param v - View of the current layout User is in.
     */
    public void openLevelSelection(View v){
        Intent intent = new Intent(this, LevelSelection.class);
        startActivity(intent);
    }

    /**
     * Function to send user to the Top Score Activity from the main page.
     * @param v - View of the current layout User is in.
     */
    public void openTopScore(View v){
        Intent intent = new Intent(this, TopScore.class);
        startActivity(intent);
    }
    /**
     * Method ensures that the sound button's visual state is updated
     */
    protected void onResume() {
        super.onResume();
        updateSoundButton();
    }
    /**
     * Method designed to be called when the user interacts with a sound control element .
     * @param v - View of the current layout User is in.
     */
    public void SoundChange(View v) {
        Sound.toggleSound();// Toggles the sound state (on/off).
        isSoundOn = Sound.isSoundOn();// Updates the 'isSoundOn' variable with the current sound state.
        updateSoundButton();// Calls the method to update the visual state of the sound button.
    }
    /**
     * Method to update the visual appearance of a button based on the current sound state.
     */
    protected void updateSoundButton() {
        ImageView button = findViewById(R.id.Sound);
        if (isSoundOn) {// If sound is enabled:
            button.setBackgroundResource(R.drawable.sound_button_on);
        } else {// If sound is disabled:
            button.setBackgroundResource(R.drawable.sound_button_off);
        }
    }
    /**
     * This method initializes or resets a countdown timer for a game level based on the game's
     * difficulty, updating the display accordingly.
     * @param restart - determines whether to reset the timer to its initial value
     */

    public void startLevelTimer(boolean restart) {
        timeTextView = findViewById(R.id.gameTimer);//Initializing the TextView for the timer.

        if (gameTimer != null) {
            gameTimer.cancel();// Cancel the existing timer if it's not null.
        }
        if(restart ||timeLeftInMillis == -1){
            switch (game.getDifficulty()){// Setting the initial time based on game difficulty.
                case 1:
                    timeLeftInMillis = game.easyLevelTime;// Time for easy level.
                    break;
                case 2:
                    timeLeftInMillis = game.mediumLevelTime;// Time for medium level.
                    break;
                case 3:
                    timeLeftInMillis = game.hardLevelTime;// Time for hard level.
                    break;
            }
        }
        // Creating a new CountDownTimer with the remaining time and interval.
        gameTimer = new CountDownTimer(timeLeftInMillis, interval) {
            public void onTick(long millisUntilFinished) { // Method called at every interval of the timer.
                timeLeftInMillis = millisUntilFinished;// Updating the remaining time.
                updateTimerText(timeLeftInMillis);  // Updating the timer display.

            }

            public void onFinish() {// Method called when the timer finishes.
                timeTextView.setText("Time: 00:00");// Setting the timer text to zero after game ends
                showGameEnd();// Calling method to handle the end of the game method below.
            }
        };gameTimer.start();// Starting the timer.
    }
    /**
     * Method formats the remaining time in minutes and seconds and updates the timer's TextView display.
     * @param timeMillis - represents the remaining time in milliseconds
     */
    private void updateTimerText(long timeMillis) { // Method to format and update the timer text.
        int minutes = (int) (timeMillis / 1000) / 60;// Calculating minutes.
        int seconds = (int) (timeMillis / 1000) % 60;// Calculating seconds.
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);// Formatting time in mm:ss format.
        timeTextView.setText("Time: " + timeFormatted);
    }
    /**
     * Method to display the the popup as soon as the timer and the game ends.
     */

    private void showGameEnd() {// Method to handle actions at the end of the timer.
        // Show a toast message for immediate feedback
        View layout = findViewById(R.id.imageView00);
        // Handler to add a delay before switching the view
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {

                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popUpView = inflater.inflate(R.layout.loosing_popup, null);
                int width = ViewGroup.LayoutParams.MATCH_PARENT; // Setting width of the pop up
                int height = ViewGroup.LayoutParams.MATCH_PARENT; // Setting height of pop up
                boolean focusable = false; // this attribute is responsible for closing the pop up is user clicks outside of it
                PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);
                layout.post(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow.showAtLocation(layout, Gravity.BOTTOM, 0, 0); // Show the pop up at the specific location
                    }
                });
            }
        }, 2000); // 2 second wait

    }

    /**
     * Sends User to the last activity he was before
     * @ View v - view to work with in this function
     */
    public void backButton(View v){

        finish(); // close current activity
    }

    /**
     * Function to show the pop up with congratulations to the user after he finish the game.
     * Works only when all matches are found but not when quits the game by pressing "quit" button.
     * @param layout - layout on which to show the pop up
     */
    public void winPopUp(View layout){
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUpView = inflater.inflate(R.layout.congratulations_popup, null);
        int width = ViewGroup.LayoutParams.MATCH_PARENT; // Setting width of the pop up
        int height = ViewGroup.LayoutParams.MATCH_PARENT; // Setting height of pop up
        boolean focusable = false; // this attribute is responsible for closing the pop up is user clicks outside of it
        PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);
        layout.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(layout, Gravity.BOTTOM, 0, 0); // Show the pop up at the specific location
            }
        });

    }

    /**
     * Function to display pause pop up after user press pause button during the game.
     * This pop up includes Resume, Restart and Quit buttons. Actions for the are coded below.
     * @param layout - view on which to display the pop up.
     */
    public void CreatePopUpWindow(View layout) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUpView = inflater.inflate(R.layout.pause_window, null); // Converting .xml file to the View for later operations
        int width = ViewGroup.LayoutParams.MATCH_PARENT; // Setting width of the pop up
        int height = ViewGroup.LayoutParams.MATCH_PARENT; // Setting height of pop up
        boolean focusable = false; // this attribute is responsible for closing the pop up is user clicks outside of it
        PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable); // Creating a pop up view from previously set parameters
        layout.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(layout, Gravity.BOTTOM, 0, 0); // show the popup at the specific location
            }
        });
        Button resumButton, quitButton, restartButton; // Initializing button variables

        resumButton =  popUpView.findViewById(R.id.resumeButton); // Getting button view from the pop up layout
        quitButton =  popUpView.findViewById(R.id.stopGameButton);
        restartButton =  popUpView.findViewById(R.id.restartButton);

        resumButton.setOnClickListener(new View.OnClickListener() {
            // function to add action to the button when it is clicked
            @Override
            public void onClick(View view){
                popupWindow.dismiss(); // close pop up
                startLevelTimer(false); // resume the timer to continue game
            }
        });
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                popupWindow.dismiss(); // close pop up
                timeLeftInMillis = startTime; // set the timer to initial value on this level
                startLevelTimer(true); // restart the timer

                TextView Score;
                switch (game.getDifficulty()) {
                    case 1:
                        Score = findViewById(R.id.scoreEasyLevel);
                        Score.setText("Score:  0");
                        break;
                    case 2:
                        Score = findViewById(R.id.scoreMediumLevel);
                        Score.setText("Score:  0");
                        break;
                    case 3:
                        Score = findViewById(R.id.scoreHardLevel);
                        Score.setText("Score:  0");
                        break;
                }
                matchedPairs = 0;
                game.restart(); // restart the game logic(score, new game board, new random positions)
                List<List<Integer>> imageViewIds; // Initializing array for id's of cards on the board

                //Get the dimensions of the card board
                int cardBoardX = game.cardBoard.length; // top to bottom
                int cardBoardY = game.cardBoard[0].length; // left to right

                imageViewIds = getImageViewIds(cardBoardX, cardBoardY); // get the array of ID's of cards on the board

                // Assign each card on the board new background
                for (int i = 0; i < cardBoardX; i++) {
                    for (int j = 0; j < cardBoardY; j++) {
                        ImageView card = findViewById(imageViewIds.get(i).get(j)); // get the view of specific card from the layout depending on ID
                        card.setImageResource(R.drawable.card_for_easy_level); // change the background so the user will see the back of the card
                    }
                }
                new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < game.cardBoard.length; i++) {
                                for (int j = 0; j < game.cardBoard[0].length; j++) {
                                    int imageResourceId = getResources().getIdentifier(game.cardBoard[i][j].getImagePath(), "drawable", getPackageName());
                                    ImageView InfinitStone = findViewById(imageViewIds.get(i).get(j));

                                    InfinitStone.setImageResource(imageResourceId);
                                    InfinitStone.setEnabled(true);
                                }
                            }
                        }
                    }, 500);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < game.cardBoard.length; i++) {
                                for (int j = 0; j < game.cardBoard[0].length; j++) {
                                    ImageView InfinitStone = findViewById(imageViewIds.get(i).get(j));

                                    InfinitStone.setImageResource(R.drawable.card_for_easy_level);
                                }
                            }
                        }
                    }, 3000);

                }

        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                game.quit(0); // forced exit from the game
                gameTimer.cancel(); // stop the timer
                popupWindow.dismiss(); // close the popup
                finish(); // close current layout
            }
        });



    }

    /**
     * Function to get the ID's of the cards on the board to later change the background of those cards.
     * @param sizeX - size of the current game board from top to the bottom
     * @param sizeY - size of the current board left to the right
     * @return Nested list of ID's of the cards on the board
     */
    protected List<List<Integer>> getImageViewIds(int sizeX, int sizeY) {
        List<List<Integer>> imageViewIds = new ArrayList<>(); // initialize empty nested array for later inputting the ID's
        Resources resources = getResources();
        String packageName = getPackageName(); // get the name of the current package

        for (int i = 0; i < sizeX; i++) {
            List<Integer> InnerArray = new ArrayList<>(); // Initialize empty array to pass the ID's there and later add to the main array as an inner array
            for (int j = 0; j < sizeY; j++) {
                String imageViewIdName = "imageView" + String.valueOf(i) + j; // crate the ID's of each card as it is written in xml file
                int imageViewId = resources.getIdentifier(imageViewIdName, "id", packageName); // Get the actual ID of the card by it's name created before

                InnerArray.add(imageViewId); // add the ID to the inner array

            }
            imageViewIds.add(InnerArray); // add the array of ID's to the main array
        }

        return imageViewIds;
    }

}