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
    /**
     * This method initializes the MediaPlayer with a background sound, sets it to loop continuously
     * at maximum volume using Audio manager.
     */
    public static void init(Context context) {// Method to initialize the sound.
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.bg_sound);
            mediaPlayer.setLooping(true); // Set looping to true so that the theme songs never stops
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
    /**
     * This method switches the sound state on or off and starts or pauses the MediaPlayer accordingly.
     */
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


    /**
     * This method stops playing the background sound when the user quits the game
     */
    public static void stopSound() {
        if (mediaPlayer != null) { // Check if the MediaPlayer has been initialized
            if (mediaPlayer.isPlaying()) { // Check if the MediaPlayer is currently playing
                mediaPlayer.stop(); // Stop the playback
            }
            mediaPlayer.release(); // Release the resources used by the MediaPlayer
            mediaPlayer = null; // Set mediaPlayer to null to indicate it's no longer usable
        }
    }

}