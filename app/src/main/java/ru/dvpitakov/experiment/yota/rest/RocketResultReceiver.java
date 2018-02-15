package ru.dvpitakov.experiment.yota.rest;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

/**
 * Created by dmitry on 14.02.18.
 */

public class RocketResultReceiver extends ResultReceiver {

    public interface Receiver {
        void onReceiveResult(int resultCode, Bundle data);

    }

    private Receiver receiver;

    public RocketResultReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;

    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (receiver != null) {
            receiver.onReceiveResult(resultCode, resultData);
            Log.d("88888", "???");
        }

    }
}
