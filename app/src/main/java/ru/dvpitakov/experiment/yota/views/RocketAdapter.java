package ru.dvpitakov.experiment.yota.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ru.dvpitakov.experiment.yota.R;
import ru.dvpitakov.experiment.yota.models.Launch;
import ru.dvpitakov.experiment.yota.rest.ServiceHelper;

/**
 * Created by dmitry on 15.02.18.
 */

public class RocketAdapter extends BaseAdapter {
    private List<Launch> launchList;
    private int layout_ident;
    private SparseArray<ImageView> imageForUpdating;
    RocketAdapter(int layout_ident
            , List<Launch> launches
            , SparseArray<ImageView> imageForUpdating) {

        this.layout_ident = layout_ident;
        this.imageForUpdating = imageForUpdating;
        this.launchList = launches;
    }

    @Override
    public int getCount() {
        return launchList.size();
    }

    @Override
    public Launch getItem(int position) {
        return launchList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public @NonNull View getView(int position
            , @Nullable View convertView
            , @NonNull ViewGroup parent) {
        Launch launch = getItem(position);
        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(layout_ident, parent, false);
        }
        FrameLayout frameLayout = convertView.findViewById(R.id.image_container);
        frameLayout.removeAllViews();
        ImageView imageView = imageForUpdating.get(position);
        if (imageView == null) {
            Log.d("9999", "LLLL");
            imageView = new ImageView(frameLayout.getContext());
            imageView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_START);
            imageView.setImageResource(R.drawable.ic_android_black_24dp);
            imageForUpdating.put(position, imageView);
            ServiceHelper.getInstance().requestImage(parent.getContext()
                    , position
                    , launch.missionPatch
            );
            Log.d("999", "missionPatch: " + launch.missionPatch);
        }
        else {
            ViewGroup parento = ((ViewGroup)imageView.getParent());
            if(parento != null) parento.removeView(imageView);
        }
        frameLayout.addView(imageView);


        TextView rocketName = convertView.findViewById(R.id.rocket_name);
        rocketName.setText(launch.rocket.rocketName);
        TextView launchDate = convertView.findViewById(R.id.launch_date);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd\n  HH:mm:SS");
        launchDate.setText(fmt.format(new Date(launch.launchDateUnix * 1000)));
        TextView details = convertView.findViewById(R.id.details);
        details.setText('\t' + launch.details);
        return convertView;
    }
}
