package com.example.hp.upnews;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<customadapt> {
    Loader<customadapt> x;
    private static final String TAG = "Main-Activity";
    ListView mview;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=b7cfee89073b4c429b5150ab1ff21ba7";
        mview = findViewById(R.id.lay);
        x =getLoaderManager().initLoader(1,null,this);
        Log.e(TAG, "onCreate: " + x.toString());

    }

     @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: "+x.toString());

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: "+x.toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: "+x.toString());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: "+x.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: "+x.toString());
    }


    @Override
    public Loader<customadapt> onCreateLoader(int i, Bundle bundle) {
        Log.e(TAG, "onCreateLoader: " );
        return new newstask(MainActivity.this,url);

    }

    @Override
    public void onLoadFinished(Loader<customadapt> loader, customadapt adapter) {
        Log.e(TAG, "onLoadFinished: " );

        mview.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<customadapt> loader) {
        Log.e(TAG, "onLoaderReset: " );
        mview.setAdapter(new customadapt(this,new ArrayList<Event>()));
    }
}
