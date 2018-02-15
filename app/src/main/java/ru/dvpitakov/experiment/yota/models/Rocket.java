package ru.dvpitakov.experiment.yota.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dmitry on 14.02.18.
 */

public class Rocket implements Parcelable {
    public String rocketId;
    public String rocketName;

    protected Rocket(Parcel in) {
        rocketId = in.readString();
        rocketName = in.readString();
    }

    public Rocket(JSONObject jsonRocket) throws JSONException {
        this.rocketId = jsonRocket.getString("rocket_id");
        this.rocketName = jsonRocket.getString("rocket_name");
    }

    public static final Creator<Rocket> CREATOR = new Creator<Rocket>() {
        @Override
        public Rocket createFromParcel(Parcel in) {
            return new Rocket(in);
        }

        @Override
        public Rocket[] newArray(int size) {
            return new Rocket[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rocketId);
        dest.writeString(rocketName);
    }
}
