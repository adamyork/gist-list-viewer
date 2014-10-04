package com.gistlistviewer.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

public class GitHubUserManager extends ContextWrapper {

    public static final String PREFS_NAME = "GistListViewerPreferences";
    public static final String USER_KEY = "GistListViewerUserName";
    public static final String NO_USER_VALUE = "NoUser";

    public GitHubUserManager(Context base) {
        super(base);
    }

    public String getGitHubUser() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
        final String result = prefs.getString(USER_KEY, NO_USER_VALUE);
        return result;
    }

    public void setGitHubUser(String user) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_KEY, user);
        editor.commit();
    }
}
