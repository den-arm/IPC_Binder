package com.example.ipcconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private IBinder ib=null;
    private Button btn,btn2,btn3;
    public TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.button);
        btn.setOnClickListener(this);
        btn2= findViewById(R.id.button2);
        btn2.setOnClickListener(this);
        btn3= findViewById(R.id.button3);
        btn3.setOnClickListener(this);
        tv = findViewById(R.id.textView);

        //startService(...) //可以省略，调用bindService时，如果没有启动，系统会自动启动服务
        Intent intent = new Intent("com.example.ipcconnect.REMOTE_SERVICE");
        intent.setPackage("com.example.ipcconnect");//android 5.0后需要显示声明
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);

    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder ibinder) {
            ib = ibinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void onClick(View v){
        switch (v.getId()){
            case R.id.button:
                Parcel pc = Parcel.obtain();
                Parcel pc_reply = Parcel.obtain();
                pc.writeString("playing");
                try{
                    ib.transact(1,pc,pc_reply,0);
                    tv.setText(pc_reply.readString());
                }
                catch(Exception e) { e.printStackTrace(); }
                break;
            case R.id.button2:
                pc = Parcel.obtain();
                pc_reply= Parcel.obtain();
                pc.writeString("stop");
                try {
                    ib.transact(2, pc, pc_reply, 0);
                    tv.setText(pc_reply.readString());
                }catch(Exception e){
                    e.printStackTrace();}
                break;
            case R.id.button3:
                finish();
                break;

        }
    }
}

