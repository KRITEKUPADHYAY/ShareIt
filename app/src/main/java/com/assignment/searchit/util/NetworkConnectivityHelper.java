package com.assignment.searchit.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class NetworkConnectivityHelper {

    Context context;
    public static boolean isConnected;
    public NetworkConnectivityHelper(Context context) {
        this.context = context;
    }

    public void startNetworkCallBack() {
        ConnectivityManager connect = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest.Builder networkRequest = new NetworkRequest.Builder();
        connect.registerNetworkCallback(networkRequest.build(), new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                isConnected = true;
            }

            @Override
            public void onUnavailable() {
                super.onUnavailable();
                isConnected = false;
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                isConnected = false;
            }
        });
    }

    public void stopNetworkCallBack() {
        ConnectivityManager connect = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        connect.unregisterNetworkCallback(new ConnectivityManager.NetworkCallback());
    }

}
