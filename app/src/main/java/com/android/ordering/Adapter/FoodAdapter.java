package com.android.ordering.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.ordering.Bean.FoodBean;
import com.android.ordering.FoodActivity;

import java.util.List;

import com.android.ordering.R;

public class FoodAdapter extends BaseAdapter {
    private final Context mContext;
    private List<FoodBean> foodBeanList;//菜单列表数据
    private final OnAddListener onAddListener;

    public FoodAdapter(Context context, OnAddListener onAddListener) {
        this.mContext = context;
        this.onAddListener = onAddListener;
    }

    /**
     * 设置数据更新界面
     */
    public void setData(List<FoodBean> fbl) {
        this.foodBeanList = fbl;
        notifyDataSetChanged();
    }

    /**
     * 获取Item的总数
     */
    @Override
    public int getCount() {
        return foodBeanList == null ? 0 : foodBeanList.size();
    }

    /**
     * 根据position得到对应Item的对象
     */
    @Override
    public FoodBean getItem(int position) {
        return foodBeanList == null ? null : foodBeanList.get(position);
    }

    /**
     * 根据position得到对应Item的id
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 得到相应position对应的Item视图，position是当前Item的位置，
     * convertView参数是滚出屏幕的Item的View
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        //复用convertView
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.recyclelist_item,
                    parent, false);
            vh.tv_food_name = convertView.findViewById(R.id.tv_food_name);
            vh.tv_price = convertView.findViewById(R.id.tv_price);
            vh.tv_count = convertView.findViewById(R.id.item_count);
            vh.rightList_add = convertView.findViewById(R.id.iv_add);
            vh.rightList_minus = convertView.findViewById(R.id.iv_minus);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //获取position对应的Item的数据对象
        final FoodBean bean = getItem(position);
        if (bean != null) {
            vh.tv_food_name.setText(bean.getName());
            vh.tv_sale_num.setText(bean.getSales());
            String price = "" + bean.getPrice();
            vh.tv_price.setText(price);

        }
        //每个Item的点击事件
        convertView.setOnClickListener(v -> {
            //跳转到菜品详情界面
            if (bean == null) return;
            Intent intent = new Intent(mContext, FoodActivity.class);
            //把菜品的详细信息传递到菜品详情界面
            intent.putExtra("food", bean);
            mContext.startActivity(intent);
        });
        vh.rightList_add.setOnClickListener(v -> {
            if (vh.tv_count.getText().toString().equals("0")) {
                onAddListener.onSelectAddCar(position);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        public TextView tv_food_name, tv_sale_num, tv_price, tv_count;
        public ImageView rightList_add, rightList_minus;
    }

    public interface OnAddListener {
        void onSelectAddCar(int position); //处理加入购物车按钮的方法
    }
}