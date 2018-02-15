package ru.dvpitakov.experiment.yota.rest;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import java.util.ArrayList;

import ru.dvpitakov.experiment.yota.models.Launch;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class RocetIntentService extends IntentService {
    public RocetIntentService() {
        super("RocetIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final ResultReceiver receiver = intent.getParcelableExtra(ServiceHelper.RECEIVER);
            final String action = intent.getAction();
            final Bundle bundle = new Bundle();
            final RocketProcessor rocketPocessor = RocketProcessor.getInstance();
            if (ServiceHelper.ACTION_GET_ROCETS.equals(action)) {
                ArrayList<Launch> launchList = rocketPocessor.getLauchs();
                bundle.putParcelableArrayList(ServiceHelper.DATA_LAUNCHS, launchList);
                bundle.putString(ServiceHelper.ACTION, ServiceHelper.ACTION_LAUCHES_RECEIVED);
            }
            else if (ServiceHelper.ACTION_GET_BITMAP.equals(action)) {
                Bitmap bitmap = rocketPocessor.getBitmap(intent.getStringExtra(ServiceHelper.IMAGE_URL));
                bundle.putParcelable(ServiceHelper.DATA_IMAGE, bitmap);
                bundle.putString(ServiceHelper.ACTION, ServiceHelper.ACTION_IMAGE_RECEIVED);
                bundle.putInt(ServiceHelper.DATA_IMAGE_ID
                        , intent.getIntExtra(ServiceHelper.DATA_IMAGE_ID, -1));
            }
            receiver.send(200, bundle);
        }
    }
}
