package ru.dvpitakov.experiment.yota.rest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

/**
 * Created by dmitry on 14.02.18.
 */

public class ServiceHelper implements RocketResultReceiver.Receiver {

    public static final String ACTION_GET_ROCETS = "ru.dvpitakov.experiment.yota.rest.action.GET_ROCETS";
    public static final String ACTION_GET_BITMAP = "ru.dvpitakov.experiment.yota.rest.action.ACTION_GET_BITMAP";

    public static final String ACTION = "ACTION";
    public static final String ACTION_IMAGE_RECEIVED = "ACTION_IMAGE_RECEIVED";
    public static final String ACTION_LAUCHES_RECEIVED = "ACTION_LAUCHES_RECEIVED";

    public static final String DATA_IMAGE_ID = "DATA_IMAGE_ID";
    public static final String DATA_IMAGE = "DATA_IMAGE";
    public static final String DATA_LAUNCHS = "DATA_LAUNCHS";
    public static final String IMAGE_URL = "IMAGE_URL";

    public static final String RECEIVER = "RECEIVER";

    public interface ServiceHelperListener {
        void onServiceHelperResult(Bundle bundle);
    }

    private static ServiceHelper instance;

    synchronized public static ServiceHelper getInstance() {
        if (instance == null) {
            instance = new ServiceHelper();

        }
        return instance;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle data) {
        if(resultCode == 200) {
            serviceHelperListener.onServiceHelperResult(data);
            Log.d("88888", "***");
        }
    }

    private RocketResultReceiver rocetResultReciever;
    private ServiceHelperListener serviceHelperListener;

    private ServiceHelper() {
        this.rocetResultReciever = new RocketResultReceiver(new Handler());
        this.rocetResultReciever.setReceiver(this);
    }

    public void setListener(ServiceHelperListener serviceHelperListener) {
        this.serviceHelperListener = serviceHelperListener;
    }

    public void removeListener() {
        this.serviceHelperListener = null;
    }

    private Intent getPreparedIntent(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), RocetIntentService.class);
        intent.putExtra(RECEIVER, rocetResultReciever);
        return intent;
    }

    public void requestLaunchs(Context context) {
        Intent intent = getPreparedIntent(context);
        intent.setAction(ACTION_GET_ROCETS);
        context.startService(intent);
    }

    public void requestImage(Context context, int pos, String url) {
        Intent intent = getPreparedIntent(context);
        intent.setAction(ACTION_GET_BITMAP);
        intent.putExtra(DATA_IMAGE_ID, pos);
        intent.putExtra(IMAGE_URL, url);
        context.startService(intent);
    }



}
