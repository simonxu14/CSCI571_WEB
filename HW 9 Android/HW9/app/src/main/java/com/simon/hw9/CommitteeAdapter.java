package com.simon.hw9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Simon on 11/30/16.
 */
public class CommitteeAdapter extends ArrayAdapter<Committee> {
    private static class ViewHolder {
        public TextView committee_id;
        public TextView name;
        public TextView chamber;
        public ImageView arrow;
    }
    public CommitteeAdapter(Context context, ArrayList<Committee> aCommittees) {
        super(context, 0, aCommittees);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final Committee committee = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_committee, parent, false);
            viewHolder.committee_id = (TextView)convertView.findViewById(R.id.committee_id);
            viewHolder.name = (TextView)convertView.findViewById(R.id.name);
            viewHolder.chamber = (TextView)convertView.findViewById(R.id.chamber);
            viewHolder.arrow = (ImageView)convertView.findViewById(R.id.arrow);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.committee_id.setText(committee.getCommittee_id());
        viewHolder.name.setText(committee.getName());
        if (committee.getChamber().equals("house")) {
            viewHolder.chamber.setText("House");
        }
        else if (committee.getChamber().equals("senate")) {
            viewHolder.chamber.setText("Senate");
        }
        else if (committee.getChamber().equals("joint")) {
            viewHolder.chamber.setText("Joint");
        }
        else {
            viewHolder.chamber.setText("N.A.");
        }

        viewHolder.arrow.setImageResource(R.drawable.forward);
        return convertView;
    }

}
