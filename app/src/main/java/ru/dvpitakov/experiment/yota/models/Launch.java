package ru.dvpitakov.experiment.yota.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by dmitry on 14.02.18.
 */

public class Launch implements Parcelable {
    public int flightNumber;
    public long launchDateUnix;
    public String details;
    public String missionPatch;
    public String videoLink;
    public Rocket rocket;

    public Launch(JSONObject jsonLaunch) throws JSONException {
        flightNumber = jsonLaunch.getInt("flight_number");
        launchDateUnix = jsonLaunch.getInt("launch_date_unix");
        details = jsonLaunch.getString("details");
        rocket = new Rocket(jsonLaunch.getJSONObject("rocket"));
        missionPatch = jsonLaunch.getJSONObject("links").getString("mission_patch");
        videoLink = jsonLaunch.getJSONObject("links").getString("video_link");
    }

    protected Launch(Parcel in) {
        flightNumber = in.readInt();
        launchDateUnix = in.readLong();
        rocket = in.readParcelable(Rocket.class.getClassLoader());
        details = in.readString();
        missionPatch = in.readString();
        videoLink = in.readString();
    }

    public static final Creator<Launch> CREATOR = new Creator<Launch>() {
        @Override
        public Launch createFromParcel(Parcel in) {
            return new Launch(in);
        }

        @Override
        public Launch[] newArray(int size) {
            return new Launch[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(flightNumber);
        dest.writeLong(launchDateUnix);
        dest.writeParcelable(rocket, flags);
        dest.writeString(details);
        dest.writeString(missionPatch);
        dest.writeString(videoLink);
    }
}
