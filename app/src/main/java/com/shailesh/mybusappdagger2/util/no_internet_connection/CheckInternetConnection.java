package com.shailesh.mybusappdagger2.util.no_internet_connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class CheckInternetConnection
{
    private ConnectionChangeListener connectionChangeListener;

    private HandlerThread mHandlerThread;

    private Handler mConnectionCheckerHandler;

    private int mUpdateInterval = 3000;

    private boolean quite;

    private Interface_NoInternet interface_noInternet;

    public CheckInternetConnection() {

    }

    private void initHandler() {
        quite = false;
        mHandlerThread = new HandlerThread("MyHandlerThread");
        mHandlerThread.setPriority(3);
        mHandlerThread.start();
        Looper looper = mHandlerThread.getLooper();
        mConnectionCheckerHandler = new Handler(looper);
    }

    public int getUpdateInterval() {
        return mUpdateInterval;
    }

    public void setUpdateInterval(int updateIntervalInMillis) {
        this.mUpdateInterval = updateIntervalInMillis;
    }

    private void updateListenerInMainThread(final boolean connectionAvailability)   {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                connectionChangeListener.onConnectionChanged(connectionAvailability);
            }
        });
    }

    private void sleep(int timeInMilli) {

        try {
            Thread.sleep(timeInMilli);
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void addConnectionChangeListener(ConnectionChangeListener connectionChangeListener) {
        this.connectionChangeListener = connectionChangeListener;
        initHandler();
        mConnectionCheckerHandler.post(new ConnectionCheckRunnable());
    }

    public void removeConnectionChangeListener()    {
        quite = true;
        mHandlerThread.quit();
    }

    class ConnectionCheckRunnable implements Runnable  {

        @Override
        public void run() {

            sleep(1000);

            while (!quite)
            {
                try {
                    int timeoutMs = 1500;
                    Socket sock = new Socket();
                    SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

                    sock.connect(sockaddr, timeoutMs);
                    sock.close();

                    updateListenerInMainThread(true);

                    sleep(mUpdateInterval);

                } catch (IOException e) {

                    updateListenerInMainThread(false);

                    sleep(mUpdateInterval);
                }
            }
        }
    }

    public interface ConnectionChangeListener   {
        void onConnectionChanged(boolean isConnectionAvailable);
    }

    /* This method check specifically the Internet is available or not */
    public static boolean isConnectionToInternet(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}

