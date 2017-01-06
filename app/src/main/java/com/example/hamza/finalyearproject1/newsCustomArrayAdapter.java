package com.example.hamza.finalyearproject1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hamza on 1/6/2017.
 */

public class newsCustomArrayAdapter extends ArrayAdapter<newsClass> {



    newsCustomArrayAdapter(Activity context, ArrayList<newsClass> newsClass)
    {
        super(context,0,newsClass);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View newslistItem = convertView;
        if(newslistItem==null)
        {
            newslistItem = LayoutInflater.from(getContext()).inflate(R.layout.news,parent,false);
        }

        newsClass currentPosition = getItem(position);

        ImageView image = (ImageView) newslistItem.findViewById(R.id.image);
        TextView headline = (TextView) newslistItem.findViewById(R.id.headline);
        TextView dateTime = (TextView) newslistItem.findViewById(R.id.time);

       // image.setImageBitmap(currentPosition.getImageId());

        headline.setText(currentPosition.getHeadline());
        dateTime.setText(currentPosition.getDate());

            image.setImageResource(currentPosition.getImageId());



        return newslistItem;

    }
}
