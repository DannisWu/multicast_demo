package com.neu.wudan.multicast_demo;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    private static String TAG = MainActivity.class.getSimpleName();

    WifiManager.MulticastLock mMulticastLock;
    WifiManager mWifiManager;
    private MulticastClient mMulticastClient;
    private String mMulticastServer = "239.0.0.123";
    private int mMulticastPort = 8888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        mMulticastLock = mWifiManager.createMulticastLock("MulticastClent");
        mMulticastLock.acquire();

        mMulticastClient = new MulticastClient(mMulticastServer, mMulticastPort);
    }
}
