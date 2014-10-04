package com.gistlistviewer.activity;

import com.gistlistviewer.R;
import com.gistlistviewer.util.GitHubUserManager;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;

public class SetGitUserActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_git_user);
        Bundle extras = getIntent().getExtras();
        String user = extras.getString("userName");
        final EditText et = (EditText) findViewById(R.id.editText1);
        if (user.equals(GitHubUserManager.NO_USER_VALUE)) {
            et.setText("");
        } else {
            et.setText(user);
        }
        final Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final EditText editText = (EditText) findViewById(R.id.editText1);
                final String user = editText.getText().toString();
                final GitHubUserManager manager = new GitHubUserManager(
                        getBaseContext());
                manager.setGitHubUser(user);
                Intent i = new Intent(SetGitUserActivity.this,
                        ViewGistListActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

}
