package com.android.ordering.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.android.ordering.Bean.LeftBean;
import com.android.ordering.R;


import java.util.ArrayList;
import java.util.List;

public class LeftAdapter extends ArrayAdapter<LeftBean> {

    private List<LeftBean> mList = new ArrayList<>();

    public LeftAdapter(Context context, int resource, List<LeftBean> objects) {
        super(context, resource, objects);
        this.mList = objects;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item,
                    parent, false);
            holder.textView = convertView.findViewById(R.id.tv_listview);
            holder.imageView = convertView.findViewById(R.id.img_left);
            holder.leftLayout = convertView.findViewById(R.id.linear);
            holder.imageView.setImageResource(getItem(position).getImageId());
            holder.textView.setText(getItem(position).getName());
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public LinearLayout leftLayout;
    }
}
