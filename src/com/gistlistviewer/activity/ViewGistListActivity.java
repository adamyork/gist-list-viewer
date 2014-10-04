package com.gistlistviewer.activity;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.gistlistviewer.R;
import com.gistlistviewer.model.Gist;
import com.gistlistviewer.model.Gists;
import com.gistlistviewer.task.GetGitHubUserGistsTask;
import com.gistlistviewer.util.GitHubUserManager;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ViewGistListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String user = new GitHubUserManager(getBaseContext())
                .getGitHubUser();
        if (user.equals(GitHubUserManager.NO_USER_VALUE)) {
            startSetGitUserActivity(user);
        } else {
            setContentView(R.layout.activity_view_gist_list);
            final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
            progressBar.setVisibility(View.VISIBLE);
            final TextView progressBarLabel = (TextView) findViewById(R.id.progeressBarLabel);
            progressBarLabel.setVisibility(View.VISIBLE);
            final TextView userNameLabel = (TextView) findViewById(R.id.textViewUserName);
            userNameLabel.setText(user);
            final Button button = (Button) findViewById(R.id.buttonChangeUser);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startSetGitUserActivity(user);
                }
            });
            getGitHubUserGists(user);
        }
    }
    
    @SuppressLint({"SetJavaScriptEnabled","NewApi"})
    public void onTaskComplete(final Gists gists) {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.INVISIBLE);
        final TextView progressBarLabel = (TextView) findViewById(R.id.progeressBarLabel);
        progressBarLabel.setVisibility(View.INVISIBLE);
        final WebView webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLayoutAlgorithm(android.webkit.WebSettings.LayoutAlgorithm.NORMAL);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.setWebChromeClient(new WebChromeClient(){
            public boolean onConsoleMessage(ConsoleMessage cm) {
                Log.d("js message ", cm.message() + " -- From line "
                                     + cm.lineNumber() + " of "
                                     + cm.sourceId() );
                return true;
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url){
                for(Gist gist : gists.getGists()) {
                    final Set<Entry<String, LinkedTreeMap<String, String>>> entrySet = gist.getFiles().entrySet();
                    final Set<Entry<String, String>> nestEntrySet = entrySet.iterator().next().getValue().entrySet();
                    Iterator<Entry<String, String>> itr = nestEntrySet.iterator();
                    while(itr.hasNext()) {
                        final Entry<String,String> next = itr.next();
                        final String key = next.getKey();
                        final String value = next.getValue();
                        if(key.equals("raw_url")){
                            gist.setRaw_url(value);
                        }
                     }
                    final Gson gson = new Gson();
                    final String gisStr = gson.toJson(gist);
                    String jsTemplate = "javascript:addAGist(%s)";
                    final String output = String.format(jsTemplate, gisStr);
                    webView.loadUrl(output);
                }
            }
        });
        webView.loadUrl("file:///android_asset/gists.html");
    }

    private void getGitHubUserGists(String user) {
        final GetGitHubUserGistsTask task = new GetGitHubUserGistsTask(this);
        task.execute(user);
    }

    private void startSetGitUserActivity(String user) {
        Intent i = new Intent(ViewGistListActivity.this,
                SetGitUserActivity.class);
        i.putExtra("userName", user);
        startActivity(i);
        finish();
    }
}
