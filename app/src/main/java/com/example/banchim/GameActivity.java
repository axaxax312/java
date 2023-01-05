package com.example.banchim;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    MediaPlayer mp_bg;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mp_bg = MediaPlayer.create(this, R.raw.bg_music);
        if(mp_bg != null){
            mp_bg.start();
            mp_bg.setLooping(true);
        }
        setContentView(new GameView(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mp_bg != null){
            mp_bg.stop();
            mp_bg.release();
        }
    }
}
