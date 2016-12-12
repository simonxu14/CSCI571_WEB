package com.simon.hw9;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Simon on 11/27/16.
 */
public class InfoClient {
    private static final String API_BASE_URL = "http://app1-simon-env.us-west-1.elasticbeanstalk.com/index.php?";
    private AsyncHttpClient client;

    public InfoClient() {
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    public void getLegislators(final String query, JsonHttpResponseHandler handler) {
        String url = getApiUrl("operation=legislators");
        Log.v("TEST", url);
        client.get(url, handler);
    }

    public void getBillsActive(final String query, JsonHttpResponseHandler handler) {
        String url = getApiUrl("operation=bills&active=true");
        Log.v("TEST", url);
        client.get(url, handler);
    }

    public void getBillsNew(final String query, JsonHttpResponseHandler handler) {
        String url = getApiUrl("operation=bills&active=false");
        Log.v("TEST", url);
        client.get(url, handler);
    }

    public void getCommittees(final String query, JsonHttpResponseHandler handler) {
        String url = getApiUrl("operation=committees");
        Log.v("TEST", url);
        client.get(url, handler);
    }

    public void getFavoriteLegislators(final String query, JsonHttpResponseHandler handler) {
        String url = getApiUrl("operation=legislators");
        Log.v("TEST", url);
        client.get(url, handler);
    }

    public void getFavoriteBills(final String query, JsonHttpResponseHandler handler) {
        String url = getApiUrl("operation=bills&active=false");
        Log.v("TEST", url);
        client.get(url, handler);
    }

    public void getFavoriteCommittees(final String query, JsonHttpResponseHandler handler) {
        String url = getApiUrl("operation=committees");
        Log.v("TEST", url);
        client.get(url, handler);
    }
}
