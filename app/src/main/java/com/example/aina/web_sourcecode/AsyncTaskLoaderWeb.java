package com.example.aina.web_sourcecode;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Aina on 21/10/2017.
 */

public class AsyncTaskLoaderWeb  extends AsyncTaskLoader<String> {

    String Result, url;
    boolean cancel =false;

    public AsyncTaskLoaderWeb(Context context, String Url) {
        super(context);
        url = Url;
    }

    @Override
    public String loadInBackground() {
        InputStream input = null;
        HttpURLConnection connection = null;

        try {
            URL Url = new URL(url);
            connection = (HttpURLConnection)Url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(20000);
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode()==HttpURLConnection.HTTP_OK){

                input = connection.getInputStream();
                if (input!=null)
                {
                    BufferedReader buffered = new BufferedReader(new InputStreamReader(input));
                    StringBuilder builder = new StringBuilder();
                    String line = "";

                    while ((line = buffered.readLine())!=null)
                    {
                        builder.append(line+" \n");
                    }
                    return builder.toString();
                }
            }
            else
            {
                return "Eror Respone Code"+connection.getResponseCode();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "Unknown Eror";
        }
        finally {
            if (input!=null)
            {
                try {
                    input.close();
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            connection.disconnect();
        }
        return null;
    }

    @Override
    protected void onStartLoading() {
        if (Result!=null || cancel)
        {
            deliverResult(Result);
        }
        else
        {
            forceLoad();
        }
    }

    @Override
    public void deliverResult(String data) {
        super.deliverResult(data);
        Result = data;
    }

    @Override
    public void onCanceled(String data) {
        super.onCanceled(data);
        cancel = true;
    }
}
