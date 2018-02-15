package ru.dvpitakov.experiment.yota.rest;

import android.graphics.Bitmap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import ru.dvpitakov.experiment.yota.models.Launch;

/**
 * Created by dmitry on 15.02.18.
 */

public class RocketProcessor {
    private RocketProcessor() {};

    private static RocketProcessor instance = null;
    synchronized public static RocketProcessor getInstance() {
        if(instance == null) {
            instance = new RocketProcessor();
        }
        return instance;
    }

    private HashMap<String, Bitmap> imgsInMemory = new HashMap<>();
    public Bitmap getBitmap(String uri) {
        Bitmap result = imgsInMemory.get(uri);
        if(result == null) {
            try {
                result = RocketREST.getInstance().getBitmap(uri);
                imgsInMemory.put(uri, result);
            } catch (IOException e) {
                result = null;
            }
        }
        return result;

    }

    public ArrayList<Launch> getLauchs() {
        try {
            return RocketREST.getInstance().getLauchs();
        } catch (IOException e) {
            return null;
        }
    }


}
