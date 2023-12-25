package com.example.test5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Message message;
    private ImageView imageView;
    int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        Button button = findViewById(R.id.button1);

        int[] p = new int[]{
                R.drawable.p1,
                R.drawable.p2,
                R.drawable.p3
        };

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what == 0x123){
                    imageView.setImageResource(p[current++]);
                    if(current >= 3) current = 0;
                }
            }
        };

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 0x123;
                handler.sendMessage(message);
            }
        }, 0, 1000);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.VISIBLE);
                new Thread(){
                    @Override
                    public void run(){
                        Message message = new Message();
                        message.what = 0x123;

                        Bundle bundle = new Bundle();
                        bundle.putString("name", "xr");
                        message.setData(bundle);

                        handler.sendMessage(message);
                    }
                }.start();
            }
        });
    }
}
