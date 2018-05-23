package com.example.coffee.android_hw9;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;

public class MediaPlayService extends Service {

    private MediaPlayer player;

    @Override
    public void onCreate() {
        player = MediaPlayer.create(this,R.raw.song);
        player.setLooping(true);
        player.setVolume(1000.0f,1000.0f);
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return super.onStartCommand(intent,flags, startId);
    }
}
