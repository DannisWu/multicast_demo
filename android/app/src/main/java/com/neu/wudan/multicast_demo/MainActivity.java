package com.neu.wudan.multicast_demo;

import android.content.Context;
import android.inputmethodservice.ExtractEditText;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    private static String TAG = MainActivity.class.getSimpleName();
    private Button mConnect = null;

    WifiManager.MulticastLock mMulticastLock;
    WifiManager mWifiManager;
    private MulticastClient mMulticastClient;
    private String mMulticastServer = "239.0.0.123";
    private int mMulticastPort = 8888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConnect = (Button)findViewById(R.id.connect);
        //mConnect.setOnClickListener(new MyButtonListener());

        mWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        mMulticastLock = mWifiManager.createMulticastLock("MulticastClent");
        mMulticastLock.acquire();
    }

    public void buttonConnectClick(View view)
    {
        ExtractEditText mMultiServerText = (ExtractEditText)findViewById(R.id.editText_server);
        mMulticastServer = mMultiServerText.getText().toString();

        ExtractEditText mMultiPortText = (ExtractEditText)findViewById(R.id.editText_port);
        mMulticastPort = Integer.parseInt(mMultiPortText.getText().toString());

        mMulticastClient = new MulticastClient(mMulticastServer, mMulticastPort);
    }
}
