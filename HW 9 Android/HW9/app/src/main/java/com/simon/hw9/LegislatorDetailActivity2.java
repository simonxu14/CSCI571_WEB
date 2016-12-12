package com.simon.hw9;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
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
import com.simon.hw9.fragment.LegislatorFragment;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Simon on 11/28/16.
 */
public class LegislatorDetailActivity2 extends AppCompatActivity {
    private ImageView isFavorite;
    private ImageView facebook;
    private ImageView twitter;
    private ImageView website;
    private ImageView detail_image;
    private ImageView detail_party_image;

    private TextView detail_name;
    private TextView detail_email;
    private TextView detail_chamber;
    private TextView detail_contact;
    private TextView detail_party;
    private TextView detail_start_term;
    private TextView detail_end_term;
    private TextView detail_term;
    private TextView detail_office;
    private TextView detail_state;
    private TextView detail_fax;
    private TextView detail_birthday;
    private Toolbar toolbar;

    private InfoClient client;
    PhotoViewAttacher mAttacher;

    private MyProgress progressBar;
    private int progressStatus = 0;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legislator_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        isFavorite = (ImageView) findViewById(R.id.isFavorite);
        facebook = (ImageView) findViewById(R.id.facebook);
        twitter = (ImageView) findViewById(R.id.twitter);
        website = (ImageView) findViewById(R.id.website);
        detail_image = (ImageView) findViewById(R.id.detail_image);
        detail_party_image = (ImageView) findViewById(R.id.detail_party_image);
        detail_name = (TextView) findViewById(R.id.detail_name);
        detail_email = (TextView) findViewById(R.id.detail_email);
        detail_chamber = (TextView) findViewById(R.id.detail_chamber);
        detail_contact = (TextView) findViewById(R.id.detail_contact);
        detail_party = (TextView) findViewById(R.id.detail_party);
        detail_start_term = (TextView) findViewById(R.id.detail_start_term);
        detail_end_term = (TextView) findViewById(R.id.detail_end_term);
//        detail_term = (TextView) findViewById(R.id.detail_term);
        detail_office = (TextView) findViewById(R.id.detail_office);
        detail_state = (TextView) findViewById(R.id.detail_state);
        detail_fax = (TextView) findViewById(R.id.detail_fax);
        detail_birthday = (TextView) findViewById(R.id.detail_birthday);

        // Use the book to populate the data into our views
        Legislator legislator = (Legislator) getIntent().getSerializableExtra(LegislatorFragment.LEGISLATOR_DETAIL_KEY);

        loadLegislator(legislator);

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

    private void loadLegislator(final Legislator legislator) {


        this.setTitle("Legislator Info");
        Gson gson = new Gson();
        sharedpreferences = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String legislators = sharedpreferences.getString("legislator", null);
        TypeToken<ArrayList<Legislator>> token = new TypeToken<ArrayList<Legislator>>() {};
        ArrayList<Legislator> legislators_list = gson.fromJson(legislators, token.getType());
        if (legislators_list == null) {
            isFavorite.setImageResource(R.drawable.star);
        }
        else {
            boolean flag = false;
            int index = -1;
            for (int i=0; i<legislators_list.size(); i++) {
                if (legislators_list.get(i).getBioguide_id().equals(legislator.getBioguide_id())) {
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
//        Drawable bitmap = getResources().getDrawable(R.drawable.f);
//        facebook.setImageDrawable(bitmap);
//        mAttacher = new PhotoViewAttacher(facebook);
        Picasso.with(this).load(R.drawable.f).into(facebook);
//        Drawable bitmap2 = getResources().getDrawable(R.drawable.t);
//        twitter.setImageDrawable(bitmap2);
//        mAttacher = new PhotoViewAttacher(twitter);
//        Drawable bitmap3 = getResources().getDrawable(R.drawable.w);
//        website.setImageDrawable(bitmap3);
//        mAttacher = new PhotoViewAttacher(website);
        Picasso.with(this).load(R.drawable.t).into(twitter);
        Picasso.with(this).load(R.drawable.w).into(website);
        Picasso.with(this).load(Uri.parse(legislator.getImageUrl())).into(detail_image);
        if (legislator.getParty().equals("R")) {
//            Drawable bitmap4 = getResources().getDrawable(R.drawable.r);
//            detail_party_image.setImageDrawable(bitmap4);
//            mAttacher = new PhotoViewAttacher(detail_party_image);
            Picasso.with(this).load(R.drawable.r).into(detail_party_image);
            detail_party.setText("Republican");
        }
        else if (legislator.getParty().equals("D")) {
//            Drawable bitmap5 = getResources().getDrawable(R.drawable.d);
//            detail_party_image.setImageDrawable(bitmap5);
//            mAttacher = new PhotoViewAttacher(detail_party_image);
            Picasso.with(this).load(R.drawable.d).into(detail_party_image);
            detail_party.setText("Democrat");
        }
        detail_name.setText(legislator.getFullName());
        if (legislator.getChamber().equals("house")) {
            detail_chamber.setText("House");
        }
        else if (legislator.getChamber().equals("senate")) {
            detail_chamber.setText("Senate");
        }
        else {
            detail_chamber.setText("N.A.");
        }
        detail_contact.setText(legislator.getPhone());
        detail_email.setText(legislator.getOc_email());
        TimeFormat tf = new TimeFormat();
        detail_start_term.setText(tf.parseDateToMMMddy(legislator.getTerm_start()));
        detail_end_term.setText(tf.parseDateToMMMddy(legislator.getTerm_end()));

        Date date_current = new Date();
        double percent = 0;
        try {

            String inputPattern = "yyyy-MM-dd";
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            Date date_start = inputFormat.parse(legislator.getTerm_start());
            Date date_end = inputFormat.parse(legislator.getTerm_end());

            Log.e("TEST" , date_start.getTime() + "");
            Log.e("TEST" , date_end.getTime() + "");
            Log.e("TEST" , date_current.getTime() + "");

            Long num1 = date_current.getTime() - date_start.getTime();
            Long num2 = date_end.getTime() - date_start.getTime();
            percent = (double)num1 / num2;
            DecimalFormat format = new DecimalFormat("0.00");
            String result = format.format(percent);
            float temp = Float.valueOf(result);

            progressBar = (MyProgress) findViewById(R.id.progressBar1);
            progressStatus = Math.round(temp*100);
            progressBar.setProgress(progressStatus);

        } catch (ParseException e) {
            e.printStackTrace();
        }

//        detail_term.setText(String.valueOf(percent));
        detail_office.setText(legislator.getOffice());
        detail_state.setText(legislator.getState());
        detail_fax.setText(legislator.getFax());
        detail_birthday.setText(tf.parseDateToMMMddy(legislator.getBirthday()));

        if (!facebook.equals("")) {
            facebook.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Uri content_url = Uri.parse("http://www.facebook.com/" + legislator.getFacebook_id());
                    Intent intent = new Intent(Intent.ACTION_VIEW, content_url);
                    startActivity(intent);
                }
            });
        }

        if (!twitter.equals("")) {
            twitter.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Uri content_url = Uri.parse("http://www.twitter.com/" + legislator.getTwitter_id());
                    Intent intent = new Intent(Intent.ACTION_VIEW, content_url);
                    startActivity(intent);
                }
            });
        }

        if (!website.equals("")) {
            website.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Uri content_url = Uri.parse(legislator.getWebsite());
                    Intent intent = new Intent(Intent.ACTION_VIEW, content_url);
                    startActivity(intent);
                }
            });
        }

        isFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                sharedpreferences = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                String legislators = sharedpreferences.getString("legislator", null);
                TypeToken<ArrayList<Legislator>> token = new TypeToken<ArrayList<Legislator>>() {};
                ArrayList<Legislator> legislators_list = gson.fromJson(legislators, token.getType());
                Log.v("TEST", "AAA");
                if (legislators_list == null) {
                    legislators_list = new ArrayList<Legislator>();
                    legislators_list.add(legislator);
                    editor.putString("legislator", gson.toJson(legislators_list));
                    Log.i("TEST", gson.toJson(legislators_list));
                    editor.commit();
                    isFavorite.setImageResource(R.drawable.star_filled);
                }
                else {
                    boolean flag = false;
                    int index = -1;
                    for (int i=0; i<legislators_list.size(); i++) {
                        if (legislators_list.get(i).getBioguide_id().equals(legislator.getBioguide_id())) {
                            flag = true;
                            index = i;
                        }

                    }
                    if (flag) {
                        legislators_list.remove(index);
                        editor.putString("legislator", gson.toJson(legislators_list));
                        Log.i("TEST", gson.toJson(legislators_list));
                        editor.commit();
                        isFavorite.setImageResource(R.drawable.star);
                    }
                    else {
                        legislators_list.add(legislator);
                        editor.putString("legislator", gson.toJson(legislators_list));
                        Log.i("TEST", gson.toJson(legislators_list));
                        editor.commit();
                        isFavorite.setImageResource(R.drawable.star_filled);
                    }
                }
            }
        });
    }
}
