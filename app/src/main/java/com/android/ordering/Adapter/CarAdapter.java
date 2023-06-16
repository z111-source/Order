package com.android.ordering.Adapter;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.ordering.Bean.FoodBean;
import com.android.ordering.R;
import java.util.List;

public class CarAdapter extends BaseAdapter {
    private final Context mContext;
    private List<FoodBean> fbl;
    private final OnSelectListener onSelectListener;
    public CarAdapter(Context context, OnSelectListener onSelectListener) {
        this.mContext = context;
        this.onSelectListener = onSelectListener;
    }
    public void setData(List<FoodBean> fbl) {
        this.fbl = fbl;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return fbl == null ? 0 : fbl.size();    }

    @Override
    public Object getItem(int position) {
        return fbl == null ? null : fbl.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        //复用convertView
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.car_item,
                    parent,false);
            vh.tv_food_name =  convertView.findViewById(R.id.tv_food_name);
            vh.tv_food_count =  convertView.findViewById(R.id.tv_food_count);
            vh.tv_food_price =  convertView.findViewById(R.id.tv_food_price);
            vh.iv_add =  convertView.findViewById(R.id.iv_add);
            vh.iv_minus =  convertView.findViewById(R.id.iv_minus);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //获取position对应的Item的数据对象
        final FoodBean bean = (FoodBean) getItem(position);
        if (bean != null) {
            vh.tv_food_name.setText(bean.getName());
            vh.tv_food_count.setText(bean.getCount());
            String price = "￥"+(bean.getPrice())*(bean.getCount());
            vh.tv_food_price.setText(price);
        }
        vh.iv_add.setOnClickListener(view -> onSelectListener.onSelectAdd(position,vh.tv_food_count,vh.
                tv_food_price));
        vh.iv_minus.setOnClickListener(view -> onSelectListener.onSelectMis(position,vh.tv_food_count,vh.
                tv_food_price));
        return convertView;
    }
    static class ViewHolder {
        public TextView tv_food_name, tv_food_count,tv_food_price;
        public ImageView iv_add,iv_minus;
    }
    public interface OnSelectListener {
        void onSelectAdd(int position,TextView tv_food_price,TextView tv_food_count);
        void onSelectMis(int position, TextView tv_food_price, TextView tv_food_count);
    }
}
