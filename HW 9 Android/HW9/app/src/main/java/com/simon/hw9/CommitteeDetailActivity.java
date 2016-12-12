package com.simon.hw9;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.simon.hw9.fragment.CommitteeFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Simon on 11/30/16.
 */
public class CommitteeDetailActivity extends AppCompatActivity {
    private ImageView isFavorite;

    private TextView committee_id;
    private TextView name;
    private TextView chamber;
    private TextView parent_committee_id;
    private TextView phone;
    private TextView office;
    private Toolbar toolbar;
    private InfoClient client;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        isFavorite = (ImageView) findViewById(R.id.isFavorite);
        committee_id = (TextView) findViewById(R.id.committee_id);
        name = (TextView) findViewById(R.id.name);
        chamber = (TextView) findViewById(R.id.chamber);
        parent_committee_id = (TextView) findViewById(R.id.parent_committee_id);
        phone = (TextView) findViewById(R.id.phone);
        office = (TextView) findViewById(R.id.office);

        Committee committee = (Committee) getIntent().getSerializableExtra(CommitteeFragment.COMMITTEE_DETAIL_KEY);
        loadCommittee(committee);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.legislator_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super. onResume();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadCommittee(final Committee committee) {
        this.setTitle("Committee Info");
        Gson gson = new Gson();
        sharedpreferences = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String committees = sharedpreferences.getString("committee", null);
        TypeToken<ArrayList<Committee>> token = new TypeToken<ArrayList<Committee>>() {};
        ArrayList<Committee> committees_list = gson.fromJson(committees, token.getType());
        Log.v("TEST", "AAA");
        if (committees_list == null) {
            isFavorite.setImageResource(R.drawable.star);
        }
        else {
            boolean flag = false;
            int index = -1;
            for (int i=0; i<committees_list.size(); i++) {
                if (committees_list.get(i).getCommittee_id().equals(committee.getCommittee_id())) {
                    flag = true;
                    index = i;
                }

            }
            if (flag) {
                isFavorite.setImageResource(R.drawable.star_filled);
            }
            else {
                isFavorite.setImageResource(R.drawable.star);
            }
        }
        committee_id.setText(committee.getCommittee_id());
        name.setText(committee.getName());
        if (committee.getChamber().equals("house")) {
            chamber.setText("House");
        }
        else if (committee.getChamber().equals("senate")) {
            chamber.setText("Senate");
        }
        else if (committee.getChamber().equals("joint")) {
            chamber.setText("Joint");
        }
        else {
            chamber.setText("N.A.");
        }
        parent_committee_id.setText(committee.getParent_committee_id());
        phone.setText(committee.getPhone());
        office.setText(committee.getOffice());

        isFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                sharedpreferences = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                String committees = sharedpreferences.getString("committee", null);
                TypeToken<ArrayList<Committee>> token = new TypeToken<ArrayList<Committee>>() {};
                ArrayList<Committee> committees_list = gson.fromJson(committees, token.getType());
                Log.v("TEST", "AAA");
                if (committees_list == null) {
                    committees_list = new ArrayList<Committee>();
                    committees_list.add(committee);
                    editor.putString("committee", gson.toJson(committees_list));
                    Log.i("TEST", gson.toJson(committees_list));
                    editor.commit();
                    isFavorite.setImageResource(R.drawable.star_filled);
                }
                else {
                    boolean flag = false;
                    int index = -1;
                    for (int i=0; i<committees_list.size(); i++) {
                        if (committees_list.get(i).getCommittee_id().equals(committee.getCommittee_id())) {
                            flag = true;
                            index = i;
                        }

                    }
                    if (flag) {
                        committees_list.remove(index);
                        editor.putString("committee", gson.toJson(committees_list));
                        Log.i("TEST", gson.toJson(committees_list));
                        editor.commit();
                        isFavorite.setImageResource(R.drawable.star);
                    }
                    else {
                        committees_list.add(committee);
                        editor.putString("committee", gson.toJson(committees_list));
                        Log.i("TEST", gson.toJson(committees_list));
                        editor.commit();
                        isFavorite.setImageResource(R.drawable.star_filled);
                    }
                }


            }
        });
    }
}
