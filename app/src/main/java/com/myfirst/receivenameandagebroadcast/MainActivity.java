package com.myfirst.receivenameandagebroadcast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mName,mAge;
    private Button mSend;
    private LocalBroadcastManager localBroadcastManager;
    private Intent intent;
    private LocalReceiver localReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        localBroadcastManager = LocalBroadcastManager.getInstance(MainActivity.this);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent("com.local");
                intent.putExtra("name","Yash");
                intent.putExtra("age","22");
                localBroadcastManager.sendBroadcast(intent);
                receiveLocalBroadcast();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    private void receiveLocalBroadcast() {
        IntentFilter intentFilter = new IntentFilter("com.local");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);
    }

    private void initViews() {
        mName = findViewById(R.id.name);
        mAge = findViewById(R.id.age);
        mSend = findViewById(R.id.send);
    }

    private class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mName.setText(intent.getStringExtra("name"));
            mAge.setText(intent.getStringExtra("age"));
        }
    }

}