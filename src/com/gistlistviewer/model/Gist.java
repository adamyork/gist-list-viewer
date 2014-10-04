package com.gistlistviewer.model;

import com.google.gson.internal.LinkedTreeMap;

public class Gist {
    
    private String url;
    private String html_url;
    private String description;
    private String id;
    private LinkedTreeMap<String, LinkedTreeMap<String, String>> files;
    private String raw_url;
    
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getHtml_url() {
        return html_url;
    }
    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public LinkedTreeMap<String, LinkedTreeMap<String, String>> getFiles() {
        return files;
    }
    public void setFiles(LinkedTreeMap<String, LinkedTreeMap<String, String>> files) {
        this.files = files;
    }
    public void setRaw_url(String raw_url) {
        this.raw_url = raw_url;
    }
    
    public String getRaw_url() {
        return raw_url;
    }

}
