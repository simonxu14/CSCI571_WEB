package com.simon.hw9.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.simon.hw9.InfoClient;
import com.simon.hw9.Legislator;
import com.simon.hw9.LegislatorAdapter;
import com.simon.hw9.LegislatorDetailActivity;
import com.simon.hw9.R;
import com.simon.hw9.SortComparator_House;
import com.simon.hw9.SortComparator_Senate;
import com.simon.hw9.SortComparator_State;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Simon on 11/27/16.
 */
public class LegislatorFragment extends Fragment implements View.OnClickListener {
    public static final String LEGISLATOR_DETAIL_KEY = "legislator";
    private ListView state_list;
    private ListView house_list;
    private ListView senate_list;
    private LegislatorAdapter legislatorAdapter_state;
    private LegislatorAdapter legislatorAdapter_house;
    private LegislatorAdapter legislatorAdapter_senate;
    private InfoClient client;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    SharedPreferences sharedpreferences;

//    final Map<String, Integer> mapIndex = new LinkedHashMap<String, Integer>();
    Map<String, Integer> mapIndex;
    Map<String, Integer> mapIndex2;
    Map<String, Integer> mapIndex3;

    private OnFragmentInteractionListener mListener;
    public LegislatorFragment() {
        // Required empty public constructor
    }

    public static LegislatorFragment newInstance(String param1, String param2) {
        LegislatorFragment fragment = new LegislatorFragment();
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
        final View view = inflater.inflate(R.layout.fragment_legislator, container, false);

        final TabHost tabHost = (TabHost) view.findViewById(android.R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec spec = tabHost.newTabSpec("State");
        spec.setContent(R.id.tab_state);
        spec.setIndicator("BY STATES");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("House");
        spec.setContent(R.id.tab_house);
        spec.setIndicator("HOUSE");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Senate");
        spec.setContent(R.id.tab_senate);
        spec.setIndicator("SENATE");
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
                if("State".equals(tabId)) {
                    view.setTag(1);
                    TextView textView = (TextView)tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
                    textView.setTextColor(Color.BLACK);
                    TextView textView2 = (TextView)tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
                    textView2.setTextColor(Color.GRAY);
                    TextView textView3 = (TextView)tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
                    textView3.setTextColor(Color.GRAY);
                }
                if("House".equals(tabId)) {
                    TextView textView = (TextView)tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
                    textView.setTextColor(Color.BLACK);
                    TextView textView2 = (TextView)tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
                    textView2.setTextColor(Color.GRAY);
                    TextView textView3 = (TextView)tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
                    textView3.setTextColor(Color.GRAY);
                }
                if("Senate".equals(tabId)) {
                    TextView textView = (TextView)tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
                    textView.setTextColor(Color.BLACK);
                    TextView textView2 = (TextView)tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
                    textView2.setTextColor(Color.GRAY);
                    TextView textView3 = (TextView)tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
                    textView3.setTextColor(Color.GRAY);
                }
            }});

        state_list = (ListView) view.findViewById(R.id.state_list);
        ArrayList<Legislator> aLegislators_state = new ArrayList<Legislator>();
        legislatorAdapter_state = new LegislatorAdapter(this.getContext(), aLegislators_state);
        state_list.setAdapter(legislatorAdapter_state);
        setupLegislatorSelectedListener_state();
        fetchLegislatorsState(view, inflater, this);

        house_list = (ListView) view.findViewById(R.id.house_list);
        ArrayList<Legislator> aLegislators_house = new ArrayList<Legislator>();
        legislatorAdapter_house = new LegislatorAdapter(this.getContext(), aLegislators_house);
        house_list.setAdapter(legislatorAdapter_house);
        setupLegislatorSelectedListener_house();
        fetchLegislatorsHouse(view, inflater, this);

        senate_list = (ListView) view.findViewById(R.id.senate_list);
        ArrayList<Legislator> aLegislators_senate = new ArrayList<Legislator>();
        legislatorAdapter_senate = new LegislatorAdapter(this.getContext(), aLegislators_senate);
        senate_list.setAdapter(legislatorAdapter_senate);
        setupLegislatorSelectedListener_senate();
        fetchLegislatorsSenate(view, inflater, this);


        return view;
    }



    public void onClick(View view) {
        TextView selectedIndex = (TextView) view;
        Log.i("TTT", selectedIndex.getTag().toString());
        if (selectedIndex.getTag().equals("0"))
            state_list.setSelection(mapIndex.get(selectedIndex.getText()));
        if (selectedIndex.getTag().equals("1"))
            house_list.setSelection(mapIndex2.get(selectedIndex.getText()));
        if (selectedIndex.getTag().equals("2"))
            senate_list.setSelection(mapIndex3.get(selectedIndex.getText()));
    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    public void setupLegislatorSelectedListener_state() {
        state_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), LegislatorDetailActivity.class);
                intent.putExtra(LEGISLATOR_DETAIL_KEY, legislatorAdapter_state.getItem(position));
                startActivity(intent);
            }
        });
    }
    public void setupLegislatorSelectedListener_house() {
        house_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), LegislatorDetailActivity.class);
                intent.putExtra(LEGISLATOR_DETAIL_KEY, legislatorAdapter_house.getItem(position));
                startActivity(intent);
            }
        });
    }

    public void setupLegislatorSelectedListener_senate() {
        senate_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), LegislatorDetailActivity.class);
                intent.putExtra(LEGISLATOR_DETAIL_KEY, legislatorAdapter_senate.getItem(position));
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

    private void fetchLegislatorsState(final View view, final LayoutInflater inflater, final View.OnClickListener activity) {
        client = new InfoClient();
        client.getLegislators("", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray docs = null;
                    if(response != null) {
                        docs = response.getJSONArray("results");
                        ArrayList<Legislator> legislators = Legislator.fromJson(docs);
                        Comparator comp = new SortComparator_State();
                        Collections.sort(legislators,comp);

                        legislatorAdapter_state.clear();
                        for (Legislator legislator : legislators) {
                            legislatorAdapter_state.add(legislator);
                        }
                        legislatorAdapter_state.notifyDataSetChanged();

                        mapIndex = new LinkedHashMap<String, Integer>();
                        for (int i = 0; i < legislators.size(); i++) {
                            Legislator legislator = legislators.get(i);
                            String index = legislator.getState().substring(0, 1);
                            if (mapIndex.get(index) == null)
                                mapIndex.put(index, i);
                        }

                        LinearLayout indexLayout = (LinearLayout) view.findViewById(R.id.side_index);
                        TextView textView;
                        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
                        for (String index : indexList) {
                            textView = (TextView) inflater.inflate(R.layout.side_index_item, null);
                            textView.setText(index);
                            textView.setTag("0");
                            textView.setOnClickListener(activity);
                            indexLayout.addView(textView);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void fetchLegislatorsHouse(final View view, final LayoutInflater inflater, final View.OnClickListener activity) {
        client = new InfoClient();
        client.getLegislators("", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray docs = null;
                    if(response != null) {
                        docs = response.getJSONArray("results");
                        final ArrayList<Legislator> legislators = Legislator.fromJson(docs);
                        Comparator comp = new SortComparator_House();
                        ArrayList<Legislator> legislators_house = new ArrayList<Legislator>();
                        for (Legislator legislator : legislators) {
                            String chamber = legislator.getChamber();
                            if (chamber.equals("house"))
                                legislators_house.add(legislator);
                        }
                        Collections.sort(legislators_house,comp);
                        legislatorAdapter_house.clear();
                        for (Legislator legislator : legislators_house) {
                            legislatorAdapter_house.add(legislator);
                        }
                        legislatorAdapter_house.notifyDataSetChanged();
                        mapIndex2 = new LinkedHashMap<String, Integer>();
                        for (int i = 0; i < legislators_house.size(); i++) {
                            Legislator legislator = legislators_house.get(i);
                            String index = legislator.getLast_name().substring(0, 1);
                            if (mapIndex2.get(index) == null)
                                mapIndex2.put(index, i);
                        }

                        LinearLayout indexLayout = (LinearLayout) view.findViewById(R.id.side_index2);
                        TextView textView;
                        List<String> indexList = new ArrayList<String>(mapIndex2.keySet());
                        for (String index : indexList) {
                            textView = (TextView) inflater.inflate(R.layout.side_index_item2, null);
                            textView.setText(index);
                            textView.setTag("1");
                            textView.setOnClickListener(activity);
                            indexLayout.addView(textView);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void fetchLegislatorsSenate(final View view, final LayoutInflater inflater, final View.OnClickListener activity) {
        client = new InfoClient();
        client.getLegislators("", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray docs = null;
                    if(response != null) {
                        docs = response.getJSONArray("results");
                        final ArrayList<Legislator> legislators = Legislator.fromJson(docs);
                        Comparator comp = new SortComparator_Senate();
                        ArrayList<Legislator> legislators_senate = new ArrayList<Legislator>();
                        for (Legislator legislator : legislators) {
                            String chamber = legislator.getChamber();
                            if (chamber.equals("senate"))
                                legislators_senate.add(legislator);
                        }
                        Collections.sort(legislators_senate,comp);
                        legislatorAdapter_senate.clear();
                        for (Legislator legislator : legislators_senate) {
                            legislatorAdapter_senate.add(legislator);
                        }
                        legislatorAdapter_senate.notifyDataSetChanged();

                        mapIndex3 = new LinkedHashMap<String, Integer>();
                        for (int i = 0; i < legislators_senate.size(); i++) {
                            Legislator legislator = legislators_senate.get(i);
                            String index = legislator.getLast_name().substring(0, 1);
                            if (mapIndex3.get(index) == null)
                                mapIndex3.put(index, i);
                        }

                        LinearLayout indexLayout = (LinearLayout) view.findViewById(R.id.side_index3);
                        TextView textView;
                        List<String> indexList = new ArrayList<String>(mapIndex3.keySet());
                        for (String index : indexList) {
                            textView = (TextView) inflater.inflate(R.layout.side_index_item3, null);
                            textView.setText(index);
                            textView.setTag("2");
                            textView.setOnClickListener(activity);
                            indexLayout.addView(textView);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
