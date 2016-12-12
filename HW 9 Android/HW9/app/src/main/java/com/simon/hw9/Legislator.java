package com.simon.hw9;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Simon on 11/27/16.
 */
public class Legislator implements Serializable {
    private String bioguide_id;
    private String first_name;
    private String last_name;
    private String district;
    private String party;
    private String state_name;
    private String state;
    private String chamber;
    private String title;
    private String oc_email;
    private String phone;
    private String term_start;
    private String term_end;
    private String office;
    private String fax;
    private String birthday;
    private String twitter_id;
    private String facebook_id;
    private String website;


    public String getBioguide_id() {
        return bioguide_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getDistrict() {
        return district;
    }

    public String getState() {
        return state;
    }

    public String getState_name() {
        return state_name;
    }

    public String getParty() {
        return party;
    }

    public String getChamber() {
        return chamber;
    }

    public String getTitle() {
        return title;
    }

    public String getOc_email() {
        return oc_email;
    }

    public String getPhone() {
        return phone;
    }
    public String getTerm_start() {
        return term_start;
    }
    public String getTerm_end() {
        return term_end;
    }
    public String getOffice() {
        return office;
    }
    public String getFax() {
        return fax;
    }
    public String getBirthday() {
        return birthday;
    }
    public String getTwitter_id() {
        return twitter_id;
    }
    public String getFacebook_id() {
        return facebook_id;
    }
    public String getWebsite() {
        return website;
    }

    public static Legislator fromJson(JSONObject jsonObject) {
        Legislator legislator = new Legislator();
        try {
            legislator.bioguide_id = jsonObject.has("bioguide_id") ? jsonObject.getString("bioguide_id") : "";
            legislator.first_name = jsonObject.has("first_name") ? jsonObject.getString("first_name") : "";
            legislator.last_name = jsonObject.has("last_name") ? jsonObject.getString("last_name") : "";
            legislator.district = jsonObject.has("district") ? jsonObject.getString("district") : "";
            if (legislator.district == "null") {
                legislator.district = "0";
            }
            legislator.party = jsonObject.has("party") ? jsonObject.getString("party") : "";
            legislator.state_name = jsonObject.has("state_name") ? jsonObject.getString("state_name") : "";
            legislator.state = jsonObject.has("state") ? jsonObject.getString("state") : "";
            legislator.chamber = jsonObject.has("chamber") ? jsonObject.getString("chamber") : "";
            legislator.title = jsonObject.has("title") ? jsonObject.getString("title") : "";
            legislator.oc_email = jsonObject.has("oc_email") ? jsonObject.getString("oc_email") : "N.A.";
            legislator.phone = jsonObject.has("phone") ? jsonObject.getString("phone") : "N.A.";
            legislator.term_start = jsonObject.has("term_start") ? jsonObject.getString("term_start") : "";
            legislator.term_end = jsonObject.has("term_end") ? jsonObject.getString("term_end") : "";
            legislator.office = jsonObject.has("office") ? jsonObject.getString("office") : "N.A.";
            legislator.fax = jsonObject.has("fax") ? jsonObject.getString("fax") : "N.A.";
            legislator.birthday = jsonObject.has("birthday") ? jsonObject.getString("birthday") : "";
            legislator.twitter_id = jsonObject.has("twitter_id") ? jsonObject.getString("twitter_id") : "";
            legislator.facebook_id = jsonObject.has("facebook_id") ? jsonObject.getString("facebook_id") : "";
            legislator.website = jsonObject.has("website") ? jsonObject.getString("website") : "";
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return legislator;
    }

    public static ArrayList<Legislator> fromJson(JSONArray jsonArray) throws JSONException {
        ArrayList<Legislator> legislators = new ArrayList<Legislator>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject legislatorJson = null;
            try {
                legislatorJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Legislator legislator = Legislator.fromJson(legislatorJson);
            if (legislator != null) {
                legislators.add(legislator);
            }
        }
        return legislators;
    }
    public String getImageUrl() {
        return "https://theunitedstates.io/images/congress/225x275/" + bioguide_id + ".jpg";
    }

    public String getName() {
        return last_name + ", " + first_name;
    }

    public String getFullName() {
        return title + ". " + last_name + ", " + first_name;
    }

    public String getBasic_info() {
        return "(" + party + ")" + state_name + " - District " + district;
    }

}
