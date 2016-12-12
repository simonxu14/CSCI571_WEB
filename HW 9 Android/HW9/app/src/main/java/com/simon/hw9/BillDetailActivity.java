package com.simon.hw9;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.simon.hw9.fragment.BillFragment;
import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by Simon on 11/30/16.
 */
public class BillDetailActivity extends AppCompatActivity {
    private ImageView isFavorite;

    private TextView bill_id;
    private TextView official_title;
    private TextView bill_type;
    private TextView sponsor_name;
    private TextView chamber;
    private TextView history_active;
    private TextView introduced_on;
    private TextView urls_congress;
    private TextView last_version_version_name;
    private TextView bill_url;

    private Toolbar toolbar;

    private InfoClient client;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        isFavorite = (ImageView) findViewById(R.id.isFavorite);
        bill_id = (TextView) findViewById(R.id.bill_id);
        official_title = (TextView) findViewById(R.id.official_title);
        bill_type = (TextView) findViewById(R.id.bill_type);
        sponsor_name = (TextView) findViewById(R.id.sponsor_name);
        chamber = (TextView) findViewById(R.id.chamber);
        history_active = (TextView) findViewById(R.id.history_active);
        introduced_on = (TextView) findViewById(R.id.introduced_on);
        urls_congress = (TextView) findViewById(R.id.urls_congress);
        last_version_version_name = (TextView) findViewById(R.id.last_version_version_name);
        bill_url = (TextView) findViewById(R.id.bill_url);

        // Use the book to populate the data into our views
        Bill bill = (Bill) getIntent().getSerializableExtra(BillFragment.BILL_DETAIL_KEY);
        loadBill(bill);
    }

    @Override
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

    private void loadBill(final Bill bill) {
        this.setTitle("Bill Info");
        Gson gson = new Gson();
        sharedpreferences = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String bills = sharedpreferences.getString("bill", null);
        TypeToken<ArrayList<Bill>> token = new TypeToken<ArrayList<Bill>>() {};
        ArrayList<Bill> bills_list = gson.fromJson(bills, token.getType());
        Log.v("TEST", "AAA");
        if (bills_list == null) {
            isFavorite.setImageResource(R.drawable.star);
        }
        else {
            boolean flag = false;
            int index = -1;
            for (int i=0; i<bills_list.size(); i++) {
                if (bills_list.get(i).getBill_id().equals(bill.getBill_id())) {
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
        bill_id.setText(bill.getBill_id());
        official_title.setText(bill.getOfficial_title());
        bill_type.setText(bill.getBill_type().toUpperCase());
        sponsor_name.setText(bill.getSponsor_name());
        if (bill.getChamber().equals("house")) {
            chamber.setText("House");
        }
        else if (bill.getChamber().equals("senate")) {
            chamber.setText("Senate");
        }
        else {
            chamber.setText(bill.getChamber());
        }

        if (bill.getHistory_active().equals("true")) {
            history_active.setText("Active");
        }
        else if (bill.getHistory_active().equals("false")) {
            history_active.setText("New");
        }
        else {
            history_active.setText("N.A.");
        }
        TimeFormat tf = new TimeFormat();
        introduced_on.setText(tf.parseDateToMMMddy(bill.getIntroduced_on()));
        urls_congress.setText(bill.getUrls_congress());
        last_version_version_name.setText(bill.getLast_version_version_name());
        bill_url.setText(bill.getBill_url());

        isFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                sharedpreferences = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                String bills = sharedpreferences.getString("bill", null);
                TypeToken<ArrayList<Bill>> token = new TypeToken<ArrayList<Bill>>() {};
                ArrayList<Bill> bills_list = gson.fromJson(bills, token.getType());
                Log.v("TEST", "AAA");
                if (bills_list == null) {
                    bills_list = new ArrayList<Bill>();
                    bills_list.add(bill);
                    editor.putString("bill", gson.toJson(bills_list));
                    Log.i("TEST", gson.toJson(bills_list));
                    editor.commit();
                    isFavorite.setImageResource(R.drawable.star_filled);
                }
                else {
                    boolean flag = false;
                    int index = -1;
                    for (int i=0; i<bills_list.size(); i++) {
                        if (bills_list.get(i).getBill_id().equals(bill.getBill_id())) {
                            flag = true;
                            index = i;
                        }

                    }
                    if (flag) {
                        bills_list.remove(index);
                        editor.putString("bill", gson.toJson(bills_list));
                        Log.i("TEST", gson.toJson(bills_list));
                        editor.commit();
                        isFavorite.setImageResource(R.drawable.star);
                    }
                    else {
                        bills_list.add(bill);
                        editor.putString("bill", gson.toJson(bills_list));
                        Log.i("TEST", gson.toJson(bills_list));
                        editor.commit();
                        isFavorite.setImageResource(R.drawable.star_filled);
                    }
                }


            }
        });
    }
}
