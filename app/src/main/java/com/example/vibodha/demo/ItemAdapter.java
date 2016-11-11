package com.example.vibodha.demo;

/**
 * Created by vibodha on 11/10/16.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

/**
 * Created by vibodha on 9/26/16.
 */
public class  ItemAdapter extends ArrayAdapter<Item> {

    Context context;
    int layoutResourceId;
    Item[] data = null;

    public ItemAdapter(Context context, int layoutResourceId, Item[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ItemHolder();
            holder.image = (ImageView)row.findViewById(R.id.image);
            holder.textTreatment = (TextView)row.findViewById(R.id.textTreatment);
            holder.textDate = (TextView)row.findViewById(R.id.textDate);
            holder.textStatus = (TextView)row.findViewById(R.id.textStatus);
            holder.relativeLayout = (RelativeLayout) row.findViewById(R.id.item);

            row.setTag(holder);
        }
        else
        {
            holder = (ItemHolder) row.getTag();
        }


        Item item = data[position];
        holder.textTreatment.setText(item.getName());
        holder.textDate.setText(item.getDate());
        holder.textStatus.setText(item.getStatus());
        holder.relativeLayout.setTag(item.get_id());
        Picasso.with(getContext()).load(item.getImage()).placeholder(R.drawable.default_placeholder).into(holder.image);
        return row;

    }

    static class ItemHolder
    {
        ImageView image;
        TextView textTreatment;
        TextView textDate;
        TextView textStatus;
        RelativeLayout relativeLayout;
    }
}
