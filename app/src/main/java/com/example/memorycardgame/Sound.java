package com.example.memorycardgame;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.AudioManager;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Sound extends MainActivity {
    private static MediaPlayer mediaPlayer;// Static MediaPlayer instance to play sound.
    protected static boolean isSoundOn = true;// Boolean flag to track if the sound is on or off.

    public static void init(Context context) {// Method to initialize the sound.
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.bg_sound);
            mediaPlayer.setLooping(true); // Set looping so that the theme songs never stops
            mediaPlayer.setVolume(1.0f, 1.0f);// Sets volume to maximum.

            // Set volume to max
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
            }

            // Start playing if sound is on
            if (isSoundOn) {
                mediaPlayer.start();
            }
        }
    }
    public static void toggleSound() {
        isSoundOn = !isSoundOn;// Toggles the state of the sound.
        if (isSoundOn) {
            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                mediaPlayer.start();// Start the MediaPlayer if it's not already playing.
            }

        } else {
            if (mediaPlayer != null) {
                mediaPlayer.pause();// Pause the MediaPlayer.
            }
        }
    }

    public static boolean isSoundOn() {// Method to return the current state of the sound.
        return isSoundOn;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onResume() {
        super.onResume();
        updateSoundButton();// Updates the sound button's appearance based on the sound state.
        // Restart sound playback if sound is on and the MediaPlayer is not playing.
        if (isSoundOn && mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
        if (isGamePaused) { // Resume game timer if the game was paused.
            startLevelTimer(false);
            isGamePaused = false;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onPause() {
        super.onPause();
        // Pause sound playback if the MediaPlayer is playing.
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release and nullify the MediaPlayer when the activity is destroyed.
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
