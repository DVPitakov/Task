package ru.dvpitakov.experiment.yota.views;

import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.Inflater;

import ru.dvpitakov.experiment.yota.R;
import ru.dvpitakov.experiment.yota.intens.VideoIntentHelper;
import ru.dvpitakov.experiment.yota.models.Launch;
import ru.dvpitakov.experiment.yota.rest.ServiceHelper;

public class MainActivity extends AppCompatActivity implements ServiceHelper.ServiceHelperListener {

    private RocketAdapter launchArrayAdapter;
    private ListView lancesListView;
    private ArrayList<Launch> launches = new ArrayList<>();
    private SparseArray<ImageView> imageForUpdating = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lancesListView = findViewById(R.id.lauchs_list_view);
        if (launchArrayAdapter == null) {
            launchArrayAdapter = new RocketAdapter(R.layout.launch_item, launches, imageForUpdating);
        }
        lancesListView.setAdapter(launchArrayAdapter);
        lancesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoIntentHelper.doIntent(getBaseContext()
                        , launchArrayAdapter.getItem(position).videoLink);
            }
        });

        ServiceHelper.getInstance().setListener(this);
        ServiceHelper.getInstance().requestLaunchs(getBaseContext());

    }

    @Override
    public void onDestroy() {
        ServiceHelper.getInstance().removeListener();
        super.onDestroy();
    }

    @Override
    public void onServiceHelperResult(Bundle bundle) {
        String action = bundle.getString(ServiceHelper.ACTION, "");
        switch (action) {
            case ServiceHelper.ACTION_IMAGE_RECEIVED: {
                int key = bundle.getInt(ServiceHelper.DATA_IMAGE_ID);
                Bitmap bitmap = bundle.getParcelable(ServiceHelper.DATA_IMAGE);
                ImageView target = imageForUpdating.get(key);
                Log.d("9999", "GGG");
                if(target != null && bitmap != null) {
                    target.setImageBitmap(bitmap);
                    launchArrayAdapter.notifyDataSetChanged();
                }
                break;
            }
            case ServiceHelper.ACTION_LAUCHES_RECEIVED: {
                ArrayList parcelables = bundle.getParcelableArrayList(ServiceHelper.DATA_LAUNCHS);
                if (parcelables != null) {
                    this.launches.clear();
                    this.launches.addAll(parcelables);
                    launchArrayAdapter.notifyDataSetChanged();
                }
                break;
            }
        }
    }
}
