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
import com.simon.hw9.Committee;
import com.simon.hw9.CommitteeAdapter;
import com.simon.hw9.CommitteeDetailActivity;
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
public class CommitteeFragment extends Fragment {
    public static final String COMMITTEE_DETAIL_KEY = "committee";
    private ListView house_list;
    private ListView senate_list;
    private ListView joint_list;
    private CommitteeAdapter committeeAdapter_house;
    private CommitteeAdapter committeeAdapter_senate;
    private CommitteeAdapter committeeAdapter_joint;
    private InfoClient client;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    public CommitteeFragment() {
        // Required empty public constructor
    }

    public static CommitteeFragment newInstance(String param1, String param2) {
        CommitteeFragment fragment = new CommitteeFragment();
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
        View view = inflater.inflate(R.layout.fragment_committee, container, false);

        final TabHost tabHost = (TabHost) view.findViewById(android.R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec spec = tabHost.newTabSpec("House");
        spec.setContent(R.id.tab_house);
        spec.setIndicator("HOUSE");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Senate");
        spec.setContent(R.id.tab_senate);
        spec.setIndicator("SENATE");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Joint");
        spec.setContent(R.id.tab_joint);
        spec.setIndicator("JOINT");
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
                if("House".equals(tabId)) {
                    TextView textView = (TextView)tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
                    textView.setTextColor(Color.BLACK);
                    TextView textView2 = (TextView)tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
                    textView2.setTextColor(Color.GRAY);
                    TextView textView3 = (TextView)tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
                    textView3.setTextColor(Color.GRAY);
                }
                if("Senate".equals(tabId)) {
                    TextView textView = (TextView)tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
                    textView.setTextColor(Color.BLACK);
                    TextView textView2 = (TextView)tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
                    textView2.setTextColor(Color.GRAY);
                    TextView textView3 = (TextView)tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
                    textView3.setTextColor(Color.GRAY);
                }
                if("JOINT".equals(tabId)) {
                    TextView textView = (TextView)tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
                    textView.setTextColor(Color.BLACK);
                    TextView textView2 = (TextView)tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
                    textView2.setTextColor(Color.GRAY);
                    TextView textView3 = (TextView)tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
                    textView3.setTextColor(Color.GRAY);
                }
            }});

        house_list = (ListView) view.findViewById(R.id.house_list);
        ArrayList<Committee> aCommittees_house = new ArrayList<Committee>();
        committeeAdapter_house = new CommitteeAdapter(this.getContext(), aCommittees_house);
        house_list.setAdapter(committeeAdapter_house);
        setupCommitteeSelectedListener_house();
        fetchCommitteesHouse();

        senate_list = (ListView) view.findViewById(R.id.senate_list);
        ArrayList<Committee> aCommittees_senate = new ArrayList<Committee>();
        committeeAdapter_senate = new CommitteeAdapter(this.getContext(), aCommittees_senate);
        senate_list.setAdapter(committeeAdapter_senate);
        setupCommitteeSelectedListener_senate();
        fetchCommitteesSenate();

        joint_list = (ListView) view.findViewById(R.id.joint_list);
        ArrayList<Committee> aCommittees_joint = new ArrayList<Committee>();
        committeeAdapter_joint = new CommitteeAdapter(this.getContext(), aCommittees_joint);
        joint_list.setAdapter(committeeAdapter_joint);
        setupCommitteeSelectedListener_joint();
        fetchCommitteesJoint();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void setupCommitteeSelectedListener_house() {
        house_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CommitteeDetailActivity.class);
                intent.putExtra(COMMITTEE_DETAIL_KEY, committeeAdapter_house.getItem(position));
                startActivity(intent);
            }
        });
    }
    public void setupCommitteeSelectedListener_senate() {
        senate_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CommitteeDetailActivity.class);
                intent.putExtra(COMMITTEE_DETAIL_KEY, committeeAdapter_senate.getItem(position));
                startActivity(intent);
            }
        });
    }

    public void setupCommitteeSelectedListener_joint() {
        joint_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CommitteeDetailActivity.class);
                intent.putExtra(COMMITTEE_DETAIL_KEY, committeeAdapter_joint.getItem(position));
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

    private void fetchCommitteesHouse() {
        client = new InfoClient();
        client.getCommittees("", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray docs = null;
                    if(response != null) {
                        docs = response.getJSONArray("results");
                        final ArrayList<Committee> committees = Committee.fromJson(docs);
                        ArrayList<Committee> committees_house = new ArrayList<Committee>();
                        for (Committee committee : committees) {
                            String chamber = committee.getChamber();
                            if (chamber.equals("house"))
                                committees_house.add(committee);
                        }
                        committeeAdapter_house.clear();
                        for (Committee committee : committees_house) {
                            committeeAdapter_house.add(committee);
                        }
                        committeeAdapter_house.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void fetchCommitteesSenate() {
        client = new InfoClient();
        client.getCommittees("", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray docs = null;
                    if(response != null) {
                        docs = response.getJSONArray("results");
                        final ArrayList<Committee> committees = Committee.fromJson(docs);
                        ArrayList<Committee> committees_senate = new ArrayList<Committee>();
                        for (Committee committee : committees) {
                            String chamber = committee.getChamber();
                            if (chamber.equals("senate"))
                                committees_senate.add(committee);
                        }
                        committeeAdapter_senate.clear();
                        for (Committee committee : committees_senate) {
                            committeeAdapter_senate.add(committee);
                        }
                        committeeAdapter_senate.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void fetchCommitteesJoint() {
        client = new InfoClient();
        client.getCommittees("", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray docs = null;
                    if(response != null) {
                        docs = response.getJSONArray("results");
                        final ArrayList<Committee> committees = Committee.fromJson(docs);
                        ArrayList<Committee> committees_joint = new ArrayList<Committee>();
                        for (Committee committee : committees) {
                            String chamber = committee.getChamber();
                            if (chamber.equals("joint"))
                                committees_joint.add(committee);
                        }
                        committeeAdapter_joint.clear();
                        for (Committee committee : committees_joint) {
                            committeeAdapter_joint.add(committee);
                        }
                        committeeAdapter_joint.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
