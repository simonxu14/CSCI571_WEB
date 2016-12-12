package com.simon.hw9;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Simon on 11/30/16.
 */
public class Committee implements Serializable {
    private String committee_id;
    private String name;
    private String chamber;
    private String parent_committee_id;
    private String phone;
    private String office;

    public String getCommittee_id() {
        return committee_id;
    }
    public String getName() {
        return name;
    }
    public String getChamber() {
        return chamber;
    }
    public String getParent_committee_id() {
        return parent_committee_id;
    }
    public String getPhone() {
        return phone;
    }
    public String getOffice() {
        return office;
    }

    public static Committee fromJson(JSONObject jsonObject) {
        Committee committee = new Committee();
        try {
            committee.committee_id = jsonObject.has("committee_id") ? jsonObject.getString("committee_id") : "";
            committee.name = jsonObject.has("name") ? jsonObject.getString("name") : "";
            committee.chamber = jsonObject.has("chamber") ? jsonObject.getString("chamber") : "";
            committee.parent_committee_id = jsonObject.has("parent_committee_id") ? jsonObject.getString("parent_committee_id") : "N.A.";
            committee.phone = jsonObject.has("phone") ? jsonObject.getString("phone") : "N.A.";
            committee.office = jsonObject.has("office") ? jsonObject.getString("office") : "N.A.";
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return committee;
    }

    public static ArrayList<Committee> fromJson(JSONArray jsonArray) throws JSONException {
        ArrayList<Committee> committees = new ArrayList<Committee>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject committeeJson = null;
            try {
                committeeJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Committee committee = Committee.fromJson(committeeJson);
            if (committee != null) {
                committees.add(committee);
            }
        }
        return committees;
    }
}
