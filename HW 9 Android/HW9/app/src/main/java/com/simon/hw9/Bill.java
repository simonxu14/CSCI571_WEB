package com.simon.hw9;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Simon on 11/30/16.
 */
public class Bill implements Serializable {
    private String bill_id;
    private String bill_type;
    private String official_title;
    private String sponsor_title;
    private String sponsor_first_name;
    private String sponsor_last_name;
    private String chamber;
    private String history_active;
    private String introduced_on;
    private String urls_congress;
    private String last_version_version_name;
    private String bill_url;

    public String getBill_id() {
        return bill_id;
    }
    public String getBill_type() {
        return bill_type;
    }
    public String getOfficial_title() {
        return official_title;
    }
    public String getSponsor_name() {
        return sponsor_title + ". " + sponsor_last_name + ", " + sponsor_first_name;
    }
    public String getChamber() {
        return chamber;
    }
    public String getHistory_active() {
        return history_active;
    }
    public String getIntroduced_on() {
        return introduced_on;
    }
    public String getUrls_congress() {
        return urls_congress;
    }
    public String getLast_version_version_name() {
        return last_version_version_name;
    }
    public String getBill_url() {
        return bill_url;
    }

    public static Bill fromJson(JSONObject jsonObject) {
        Bill bill = new Bill();
        try {
            bill.bill_id = jsonObject.has("bill_id") ? jsonObject.getString("bill_id") : "";
            bill.bill_type = jsonObject.has("bill_type") ? jsonObject.getString("bill_type") : "";
            bill.official_title = jsonObject.has("official_title") ? jsonObject.getString("official_title") : "";
            bill.sponsor_title = jsonObject.getJSONObject("sponsor").has("title") ? jsonObject.getJSONObject("sponsor").getString("title") : "";
            bill.sponsor_first_name = jsonObject.getJSONObject("sponsor").has("first_name") ? jsonObject.getJSONObject("sponsor").getString("first_name") : "";
            bill.sponsor_last_name = jsonObject.getJSONObject("sponsor").has("last_name") ? jsonObject.getJSONObject("sponsor").getString("last_name") : "";
            bill.chamber = jsonObject.has("chamber") ? jsonObject.getString("chamber") : "";
            bill.history_active = jsonObject.getJSONObject("history").has("active") ? jsonObject.getJSONObject("history").getString("active") : "";
            bill.introduced_on = jsonObject.has("introduced_on") ? jsonObject.getString("introduced_on") : "";
            bill.urls_congress = jsonObject.getJSONObject("urls").has("congress") ? jsonObject.getJSONObject("urls").getString("congress") : "N.A.";
            bill.last_version_version_name = jsonObject.getJSONObject("last_version").has("version_name") ? jsonObject.getJSONObject("last_version").getString("version_name") : "N.A.";
            bill.bill_url = jsonObject.getJSONObject("urls").has("pdf") ? jsonObject.getJSONObject("urls").getString("pdf") : "N.A.";
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return bill;
    }

    public static ArrayList<Bill> fromJson(JSONArray jsonArray) throws JSONException {
        ArrayList<Bill> bills = new ArrayList<Bill>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject billJson = null;
            try {
                billJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Bill bill = Bill.fromJson(billJson);
            if (bill != null) {
                bills.add(bill);
            }
        }
        return bills;
    }


}
