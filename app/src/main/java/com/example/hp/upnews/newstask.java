package com.example.hp.upnews;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by hp on 14-07-2018.
 */

public class newstask extends AsyncTaskLoader<customadapt>{
    Context mContext;
    String urls;
    customadapt mdata;
    MyObserver observer;

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    public newstask(Context baseContext, String urls) {
        super(baseContext);
        mContext = baseContext;
        this.urls = urls;
        observer = new MyObserver(null);
        getContext().getContentResolver().registerContentObserver(Uri.parse(urls),true,observer);

    }
    private static final String TAG = "Newstask-Activity";

    @Override
    protected void onStartLoading() {
        Log.e(TAG, "onStartLoading: " );
        super.onStartLoading();
        if(mdata==null) {
            forceLoad();
        }
        else
        {
            deliverResult(mdata);
        }
    }

    @Override
    public void deliverResult(customadapt data) {
        mdata = data;
        super.deliverResult(mdata);
    }

    @Override
    public customadapt loadInBackground() {
        Log.e(TAG, "loadInBackground: " );

        URL url = null;
        try {
            url = new URL(urls);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String jsonres = "";

        jsonres = makeHTTpRequest(url);
        if(jsonres=="")
        {
            Log.e(TAG, "jsonres is empty !" );
        }
        return new customadapt(mContext,  extractFeatureFromJson(jsonres));

    }





    private String makeHTTpRequest(URL url)
    {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(15000);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);

            }
        catch (IOException e)
        {
            Log.e(TAG, "no net: " );
            e.printStackTrace();
        }
        finally {
            if(urlConnection!=null)
            {
                urlConnection.disconnect();
            }
            if (inputStream!=null)
            {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonResponse;
    }

    private String readFromStream(InputStream in)
    {
        Log.e(TAG, "readFromStream: " );
        StringBuilder output = new StringBuilder();
        if(in!=null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(in, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            try {
                String line = bufferedReader.readLine();
                while(line!=null)
                {
                    output.append(line);
                    line = bufferedReader.readLine();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return output.toString();
    }

    private ArrayList<Event> extractFeatureFromJson(String jsonresp)
    {
        ArrayList<Event> NEWS = new ArrayList<Event>();
        try {
            JSONObject baseJSONresp = new JSONObject(jsonresp);
            JSONArray ArticleArr = baseJSONresp.getJSONArray("articles");
            int i =0;

            while(i<ArticleArr.length())
            {
                JSONObject news = ArticleArr.getJSONObject(i);
                String title = news.getString("title");
                String url = news.getString("url");
                String publishedAt =news.getString("publishedAt");
                NEWS.add(new Event(title,url,publishedAt));
                i++;

            }

        } catch (JSONException e) {
            Log.e(TAG, "yee yaah" );
            e.printStackTrace();
        }
        return NEWS;

    }


}
