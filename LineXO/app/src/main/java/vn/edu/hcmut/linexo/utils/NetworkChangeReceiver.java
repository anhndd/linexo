package vn.edu.hcmut.linexo.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private Context                 context;
    private NetworkChangeListener   listener;
    private boolean                 lastNetworkState;

    /**
     * Provide a context to register this {@code NetworkChangeReceiver} and a listener
     * @param context
     * @param listener
     */
    public void initReceiver(Context context, NetworkChangeListener listener) {
        this.listener = listener;
        this.context  = context;
        this.context.registerReceiver(
                NetworkChangeReceiver.this,
                new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        );
        checkNetwork(true);
    }

    public void endReceiver() {
        context.unregisterReceiver(NetworkChangeReceiver.this);
    }

    private void checkNetwork(boolean firstCheck) {
        Boolean currentNetworkState = isNetworkAvailable(context);
        if (firstCheck || currentNetworkState != lastNetworkState) {
            listener.onNetworkChange(currentNetworkState);
            lastNetworkState = currentNetworkState;
        }
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        checkNetwork(false);
    }

    public interface NetworkChangeListener {
        void onNetworkChange(boolean networkState);
    }
}
