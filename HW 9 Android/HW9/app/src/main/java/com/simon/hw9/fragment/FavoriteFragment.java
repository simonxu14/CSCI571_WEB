package com.simon.hw9.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.simon.hw9.Bill;
import com.simon.hw9.BillAdapter;
import com.simon.hw9.BillDetailActivity;
import com.simon.hw9.Committee;
import com.simon.hw9.CommitteeAdapter;
import com.simon.hw9.CommitteeDetailActivity;
import com.simon.hw9.InfoClient;
import com.simon.hw9.Legislator;
import com.simon.hw9.LegislatorAdapter;
import com.simon.hw9.LegislatorDetailActivity;
import com.simon.hw9.LegislatorDetailActivity2;
import com.simon.hw9.R;
import com.simon.hw9.SortComparator_House;
import com.simon.hw9.SortComparator_Senate;
import com.simon.hw9.SortComparator_State;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Simon on 11/27/16.
 */
public class FavoriteFragment extends Fragment {
    public static final String LEGISLATOR_DETAIL_KEY = "legislator";
    public static final String BILL_DETAIL_KEY = "bill";
    public static final String COMMITTEE_DETAIL_KEY = "committee";
    private ListView legislator_list;
    private ListView bill_list;
    private ListView committee_list;
    private LegislatorAdapter legislatorAdapter;
    private BillAdapter billAdapter;
    private CommitteeAdapter committeeAdapter;
    private InfoClient client;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    SharedPreferences sharedpreferences;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        final TabHost tabHost = (TabHost) view.findViewById(android.R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec spec = tabHost.newTabSpec("Legislator");
        spec.setContent(R.id.tab_legislator);
        spec.setIndicator("LEGISLATOR");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Bill");
        spec.setContent(R.id.tab_bill);
        spec.setIndicator("BILL");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Committee");
        spec.setContent(R.id.tab_committee);
        spec.setIndicator("COMMITTEE");
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);

        TextView textView = (TextView)tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(15);
        TextView textView2 = (TextView)tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
        textView2.setTextColor(Color.GRAY);
        textView2.setTextSize(15);
        TextView textView3 = (TextView)tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
        textView3.setTextColor(Color.GRAY);
        textView3.setTextSize(15);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            @Override
            public void onTabChanged(String tabId) {
                if("Legislator".equals(tabId)) {
                    TextView textView = (TextView)tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
                    textView.setTextColor(Color.BLACK);
                    TextView textView2 = (TextView)tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
                    textView2.setTextColor(Color.GRAY);
                    TextView textView3 = (TextView)tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
                    textView3.setTextColor(Color.GRAY);
                }
                if("Bill".equals(tabId)) {
                    TextView textView = (TextView)tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
                    textView.setTextColor(Color.BLACK);
                    TextView textView2 = (TextView)tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
                    textView2.setTextColor(Color.GRAY);
                    TextView textView3 = (TextView)tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
                    textView3.setTextColor(Color.GRAY);
                }
                if("Committee".equals(tabId)) {
                    TextView textView = (TextView)tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
                    textView.setTextColor(Color.BLACK);
                    TextView textView2 = (TextView)tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
                    textView2.setTextColor(Color.GRAY);
                    TextView textView3 = (TextView)tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
                    textView3.setTextColor(Color.GRAY);
                }
            }});

        legislator_list = (ListView) view.findViewById(R.id.legislator_list);
        ArrayList<Legislator> aLegislators = new ArrayList<Legislator>();
        legislatorAdapter = new LegislatorAdapter(this.getContext(), aLegislators);
        legislator_list.setAdapter(legislatorAdapter);
        setupLegislatorSelectedListener();
        fetchLegislators();

        bill_list = (ListView) view.findViewById(R.id.bill_list);
        ArrayList<Bill> aBills = new ArrayList<Bill>();
        billAdapter = new BillAdapter(this.getContext(), aBills);
        bill_list.setAdapter(billAdapter);
        setupBillSelectedListener();
        fetchBills();

        committee_list = (ListView) view.findViewById(R.id.committee_list);
        ArrayList<Committee> aCommittees = new ArrayList<Committee>();
        committeeAdapter = new CommitteeAdapter(this.getContext(), aCommittees);
        committee_list.setAdapter(committeeAdapter);
        setupCommitteeSelectedListener();
        fetchCommittees();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void setupLegislatorSelectedListener() {
        legislator_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), LegislatorDetailActivity.class);
                intent.putExtra(LEGISLATOR_DETAIL_KEY, legislatorAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    public void setupBillSelectedListener() {
        bill_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), BillDetailActivity.class);
                intent.putExtra(BILL_DETAIL_KEY, billAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    public void setupCommitteeSelectedListener() {
        committee_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CommitteeDetailActivity.class);
                intent.putExtra(COMMITTEE_DETAIL_KEY, committeeAdapter.getItem(position));
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

    private void fetchLegislators() {
        sharedpreferences = getActivity().getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        String legislators = sharedpreferences.getString("legislator", null);
        Gson gson = new Gson();
        TypeToken<ArrayList<Legislator>> token = new TypeToken<ArrayList<Legislator>>() {};
        ArrayList<Legislator> legislators_list = gson.fromJson(legislators, token.getType());
        legislatorAdapter.clear();
        if (legislators_list != null) {
            Comparator comp = new SortComparator_State();
            Collections.sort(legislators_list, comp);
            for (Legislator legislator : legislators_list) {
                legislatorAdapter.add(legislator);
            }
            legislatorAdapter.notifyDataSetChanged();
        }
    }

    private void fetchBills() {
        sharedpreferences = getActivity().getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        String bills = sharedpreferences.getString("bill", null);
        Gson gson = new Gson();
        TypeToken<ArrayList<Bill>> token = new TypeToken<ArrayList<Bill>>() {};
        ArrayList<Bill> bills_list = gson.fromJson(bills, token.getType());
        billAdapter.clear();
        if (bills_list != null) {
            for (Bill bill : bills_list) {
                billAdapter.add(bill);
            }
            billAdapter.notifyDataSetChanged();
        }
    }

    private void fetchCommittees() {
        sharedpreferences = getActivity().getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        String committees = sharedpreferences.getString("committee", null);
        Gson gson = new Gson();
        TypeToken<ArrayList<Committee>> token = new TypeToken<ArrayList<Committee>>() {};
        ArrayList<Committee> committees_list = gson.fromJson(committees, token.getType());
        committeeAdapter.clear();
        if (committees_list != null) {
            for (Committee committee : committees_list) {
                committeeAdapter.add(committee);
            }
            committeeAdapter.notifyDataSetChanged();
        }
    }

    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        fetchLegislators();
        fetchCommittees();
        fetchBills();
    }
}