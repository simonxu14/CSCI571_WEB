package com.simon.hw9;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Simon on 11/30/16.
 */
public class BillAdapter extends ArrayAdapter<Bill> {
    private static class ViewHolder {
        public TextView bill_id;
        public TextView official_title;
        public TextView introduced_on;
        public ImageView arrow;
    }
    public BillAdapter(Context context, ArrayList<Bill> aBills) {
        super(context, 0, aBills);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final Bill bill = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_bill, parent, false);
            viewHolder.bill_id = (TextView)convertView.findViewById(R.id.bill_id);
            viewHolder.official_title = (TextView)convertView.findViewById(R.id.official_title);
            viewHolder.introduced_on = (TextView)convertView.findViewById(R.id.introduced_on);
            viewHolder.arrow = (ImageView)convertView.findViewById(R.id.arrow);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.bill_id.setText(bill.getBill_id());
        viewHolder.official_title.setText(bill.getOfficial_title());
        TimeFormat tf = new TimeFormat();
        viewHolder.introduced_on.setText(tf.parseDateToMMMddy(bill.getIntroduced_on()));
        viewHolder.arrow.setImageResource(R.drawable.forward);
        return convertView;
    }
}
