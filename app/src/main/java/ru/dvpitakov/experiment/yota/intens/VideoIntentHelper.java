package ru.dvpitakov.experiment.yota.intens;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by dmitry on 15.02.18.
 */

public class VideoIntentHelper {
    public static void doIntent(Context context, String uri) {
        Uri address = Uri.parse(uri);
        Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
        context.startActivity(openlinkIntent);
    }

}
