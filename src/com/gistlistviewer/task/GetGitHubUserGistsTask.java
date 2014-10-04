package com.gistlistviewer.task;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.gistlistviewer.activity.ViewGistListActivity;
import com.gistlistviewer.model.Gist;
import com.gistlistviewer.model.Gists;
import com.google.gson.Gson;

import android.os.AsyncTask;

public class GetGitHubUserGistsTask extends AsyncTask<String, String, String> {

    private String GIT_HUB_BASE_URI = "https://api.github.com/users/%s/gists";
    private ViewGistListActivity activity;
    
    public GetGitHubUserGistsTask(ViewGistListActivity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... user) {
        String uri = String.format(GIT_HUB_BASE_URI, user[0]);
        String result = new String();
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(uri);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity, "UTF-8");
        } catch (IOException x) {
            result = x.getLocalizedMessage();
            
        } 
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        final Gson gson = new Gson();
        final Gist[] rawList = gson.fromJson(result, Gist[].class);
        final Gists gists = new Gists();
        gists.setGists(rawList);
        activity.onTaskComplete(gists);
    }

}