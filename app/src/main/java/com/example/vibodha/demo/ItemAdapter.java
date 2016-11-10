package com.example.vibodha.demo;

/**
 * Created by vibodha on 11/10/16.
 */

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public int count;

    public ItemAdapter(Context context, int layoutResourceId, Item[] data,int count) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.count = count;
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
            holder.relativeLayout = (RelativeLayout) row.findViewById(R.id.appointment);

            row.setTag(holder);
        }
        else
        {
            holder = (ItemHolder) row.getTag();
        }


        Item historyItem = data[position];
        holder.textTreatment.setText(historyItem.getTreatmentName());
        holder.textDate.setText(historyItem.getDate());
        holder.textStatus.setText(historyItem.getStatus());
        holder.image.setImageBitmap(historyItem.getImage());

        switch(historyItem.getStatus()){
            case "PENDING":
                holder.textStatus.setTextColor( ContextCompat.getColor(getContext(), R.color.myYellow));
                holder.relativeLayout.setBackgroundColor( ContextCompat.getColor(getContext(), R.color.myYellow));
                break;
            case "ACCEPTED":
                holder.textStatus.setTextColor( ContextCompat.getColor(getContext(), R.color.myGreen));
                holder.relativeLayout.setBackgroundColor( ContextCompat.getColor(getContext(), R.color.myGreen));
                break;
            case "REJECT":
                holder.textStatus.setTextColor( ContextCompat.getColor(getContext(), R.color.myRed));
                holder.relativeLayout.setBackgroundColor( ContextCompat.getColor(getContext(), R.color.myRed));
        }
        System.out.println(historyItem.getTreatmentName());
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
