package com.neu.wudan.multicast_demo;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

/**
 * Created by wudan on 2016/5/13.
 */
public class MulticastClient {
    private static String TAG = MulticastClient.class.getSimpleName();

    public MulticastClient(String server, int port, Context context) {
        new Thread(new MulticastClientReceiver(server, port, context)).start();
    }

    private String getHexString(byte[] b, int lenth) {
        StringBuffer sbBuffer = new StringBuffer();
        for (int i = 0; i < lenth; i++)
        {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1)
            {
                hex = '0' + hex;
            }
            sbBuffer.append(hex.toUpperCase() + " ");
        }

        return sbBuffer.toString();
    }

    private class MulticastClientReceiver implements Runnable {
        private MulticastSocket mSocket;
        private InetAddress mGroup;

        public MulticastClientReceiver(String server, int port, Context context) {
            try {
                mSocket = new MulticastSocket(port);
                mGroup = InetAddress.getByName(server);
            } catch (IOException ioe) {
                Log.e(TAG, "MulticastClientReceiver: creating multicast socket: ", ioe);
                ioe.printStackTrace();
            }

            if (mSocket == null) {
                new AlertDialog.Builder(context)
                        .setTitle("错误提示")
                        .setMessage("mSocket is null");
            }
        }

        @Override
        public void run() {
            try {
                mSocket.joinGroup(mGroup);
                Log.d(TAG, "MulticastClientReceiver: JoinGroup done");

                byte[] buf = new byte[256];
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    mSocket.receive(packet);

                    Log.d(TAG, "MulticastClientReceiver: " + getHexString(buf, buf.length));
                }
            } catch (SocketException se) {
                Log.e(TAG, "MulticastClientReceiver: Error creating socket: ", se);
                se.printStackTrace();
            } catch (IOException ioe) {
                Log.e(TAG, "MulticastClientReceiver: Error creating multicast socket: ", ioe);
                ioe.printStackTrace();
            }
        }
    }

}
