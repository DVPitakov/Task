package ru.dvpitakov.experiment.yota.rest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import ru.dvpitakov.experiment.yota.models.Launch;

/**
 * Created by dmitry on 15.02.18.
 */

public class RocketREST {
    private final static String SCHEME = "https";
    private final static String HOST = "api.spacexdata.com";
    private final static String ROOT = "v2/launches";
    private final static String QUERY = "launch_year=2017";
    private static RocketREST instance;

    private RocketREST(){};

    synchronized static RocketREST getInstance() {
        if (instance == null) {
            instance = new RocketREST();
        }
        return instance;
    }

    @Nullable Bitmap getBitmap(String uri) throws IOException {
        InputStream is = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(uri).openConnection();
            conn.setReadTimeout(20000);
            conn.setConnectTimeout(25000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Accept", "image/*");
            conn.connect();
            int responseCode = conn.getResponseCode();
            is = conn.getInputStream();
            Bitmap bitmap = null;
            if (responseCode == 200) {
                bitmap = BitmapFactory.decodeStream(is);
            }
            return bitmap;
        }
        finally {
            if(is != null) {
                is.close();
            }
        }
    }

    ArrayList<Launch> getLauchs() throws IOException {
        InputStream is = null;
        ArrayList<Launch> result = null;
        try {
            final String uri = Uri.parse(SCHEME + "://" + HOST + "/" + ROOT + "?" + QUERY)
                    .buildUpon()
                    .build()
                    .toString();
            HttpURLConnection conn = (HttpURLConnection) new URL(uri).openConnection();
            conn.setReadTimeout(20000);
            conn.setConnectTimeout(25000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();

            int responseCode = conn.getResponseCode();
            is = conn.getInputStream();
            if (responseCode == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line);
                }
                String res = stringBuilder.toString();
                JSONArray jsonArray = new JSONArray(res);
                result = new ArrayList<>();
                for(int i = 0; i < jsonArray.length(); i++) {
                    Launch launch = new Launch(jsonArray.getJSONObject(i));
                    result.add(launch);
                }
            }
        } catch (JSONException e) {
            return null;
        } finally {
            if(is != null) {
                is.close();
            }
        }
        return result;
    }

}
