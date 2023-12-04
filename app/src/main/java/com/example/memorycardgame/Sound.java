package com.example.memorycardgame;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.AudioManager;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Sound extends MainActivity {
    private static MediaPlayer mediaPlayer;
    protected static boolean isSoundOn = true;

    public static void init(Context context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.bg_sound);
            mediaPlayer.setLooping(true); // Set looping
            mediaPlayer.setVolume(1.0f, 1.0f);

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
        isSoundOn = !isSoundOn;
        if (isSoundOn) {
            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        } else {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
            }
        }
    }

    public static boolean isSoundOn() {
        return isSoundOn;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onResume() {
        super.onResume();
        updateSoundButton();
        if (isSoundOn && mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
        if (isGamePaused) {
            startLevelTimer(false);
            isGamePaused = false;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        if (gameTimer != null) {
            gameTimer.cancel();
            isGamePaused = true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
