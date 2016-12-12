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

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Simon on 12/1/16.
 */
public class AboutMeActivity extends AppCompatActivity {
    private ImageView photo;

    private TextView name;
    private TextView usc_id;
    private TextView email;
    private TextView phone;

    private Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        photo = (ImageView) findViewById(R.id.photo);
        name = (TextView) findViewById(R.id.name);
        usc_id = (TextView) findViewById(R.id.usc_id);
        email = (TextView) findViewById(R.id.email);
        phone = (TextView) findViewById(R.id.phone);

        this.setTitle("About Me");

        photo.setImageResource(R.drawable.simon);
        name.setText("Xinlong Xu");
        usc_id.setText("7860029645");
        email.setText("xinlongx@usc.edu");
        phone.setText("2137061352");

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.legislator_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
