package com.example.ipcconnect;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyBinder extends Binder {
    private MediaPlayer mPlayer = null;
    private Context ctx;

    public MyBinder(Context cx){
        ctx = cx;
    }

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        reply.writeString(data.readString() + " mp3"); //service回复给调用者的内容
        if(code==1)
            this.play();
        else if(code == 2)
            this.stop();
        return true;
    }

    public void play(){
        if(mPlayer != null) return;
        mPlayer = MediaPlayer.create(ctx, R.raw.taomagan);
        try{
            mPlayer.start();
        }catch(Exception e){
            Log.e("StartPlay","error:" + e.getMessage(),e);
        }
    }

    public void stop(){
        if(mPlayer != null){
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }
}
