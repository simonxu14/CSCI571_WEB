package com.simon.hw9.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.simon.hw9.Bill;
import com.simon.hw9.BillAdapter;
import com.simon.hw9.BillDetailActivity;
import com.simon.hw9.InfoClient;
import com.simon.hw9.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Simon on 11/27/16.
 */
public class BillFragment extends Fragment {
    public static final String BILL_DETAIL_KEY = "bill";
    private ListView active_list;
    private ListView new_list;
    private BillAdapter billAdapter_active;
    private BillAdapter billAdapter_new;
    private InfoClient client;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    public BillFragment() {
        // Required empty public constructor
    }

    public static BillFragment newInstance(String param1, String param2) {
        BillFragment fragment = new BillFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);

        final TabHost tabHost = (TabHost) view.findViewById(android.R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec spec = tabHost.newTabSpec("Active");
        spec.setContent(R.id.tab_active);
        spec.setIndicator("ACTIVE BILLS");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("New");
        spec.setContent(R.id.tab_new);
        spec.setIndicator("NEW BILLS");
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);


        TextView textView = (TextView)tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(15);
        TextView textView2 = (TextView)tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
        textView2.setTextColor(Color.GRAY);
        textView2.setTextSize(15);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            @Override
            public void onTabChanged(String tabId) {
                if("Active".equals(tabId)) {
                    TextView textView = (TextView)tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
                    textView.setTextColor(Color.BLACK);
                    TextView textView2 = (TextView)tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
                    textView2.setTextColor(Color.GRAY);
                }
                if("New".equals(tabId)) {
                    TextView textView = (TextView)tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
                    textView.setTextColor(Color.BLACK);
                    TextView textView2 = (TextView)tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
                    textView2.setTextColor(Color.GRAY);
                }
            }});

        active_list = (ListView) view.findViewById(R.id.active_list);
        ArrayList<Bill> aBills_active = new ArrayList<Bill>();
        billAdapter_active = new BillAdapter(this.getContext(), aBills_active);
        active_list.setAdapter(billAdapter_active);
        setupBillSelectedListener_active();
        fetchBillsActive();

        new_list = (ListView) view.findViewById(R.id.new_list);
        ArrayList<Bill> aBills_new = new ArrayList<Bill>();
        billAdapter_new = new BillAdapter(this.getContext(), aBills_new);
        new_list.setAdapter(billAdapter_new);
        setupBillSelectedListener_new();
        fetchBillsNew();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void setupBillSelectedListener_active() {
        active_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), BillDetailActivity.class);
                intent.putExtra(BILL_DETAIL_KEY, billAdapter_active.getItem(position));
                startActivity(intent);
            }
        });
    }
    public void setupBillSelectedListener_new() {
        new_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), BillDetailActivity.class);
                intent.putExtra(BILL_DETAIL_KEY, billAdapter_new.getItem(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void fetchBillsActive() {
        client = new InfoClient();
        client.getBillsActive("", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray docs = null;
                    if(response != null) {
                        docs = response.getJSONArray("results");
                        final ArrayList<Bill> bills = Bill.fromJson(docs);
                        billAdapter_active.clear();
                        for (Bill bill : bills) {
                            billAdapter_active.add(bill);
                        }
                        billAdapter_active.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void fetchBillsNew() {
        client = new InfoClient();
        client.getBillsNew("", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray docs = null;
                    if(response != null) {
                        docs = response.getJSONArray("results");
                        final ArrayList<Bill> bills = Bill.fromJson(docs);
                        billAdapter_new.clear();
                        for (Bill bill : bills) {
                            billAdapter_new.add(bill);
                        }
                        billAdapter_new.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
