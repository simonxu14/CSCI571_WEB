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
 * Created by Simon on 11/27/16.
 */
public class LegislatorAdapter extends ArrayAdapter<Legislator> {
    private static class ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView basic_info;
        public ImageView arrow;
    }
    public LegislatorAdapter(Context context, ArrayList<Legislator> aLegislators) {
        super(context, 0, aLegislators);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final Legislator legislator = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_legislator, parent, false);
            viewHolder.image = (ImageView)convertView.findViewById(R.id.image);
            viewHolder.name = (TextView)convertView.findViewById(R.id.name);
            viewHolder.basic_info = (TextView)convertView.findViewById(R.id.basic_info);
            viewHolder.arrow = (ImageView)convertView.findViewById(R.id.arrow);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(getContext()).load(Uri.parse(legislator.getImageUrl())).into(viewHolder.image);
        viewHolder.name.setText(legislator.getName());
        viewHolder.basic_info.setText(legislator.getBasic_info());
        viewHolder.arrow.setImageResource(R.drawable.forward);
        return convertView;
    }
}
