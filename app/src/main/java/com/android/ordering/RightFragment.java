package com.android.ordering;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.ordering.Adapter.FoodAdapter;
import com.android.ordering.Bean.FoodBean;

import java.util.List;


public class RightFragment extends Fragment {
    private List<FoodBean> foodList;
    private FoodAdapter foodAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void refreshRecommend() {
        foodList.clear();
        foodList.add(new FoodBean("红烧肉", "月售200+ 好评率98%", 18.99, R.drawable.hongshaorou));
        foodList.add(new FoodBean("醉蟹", "月售80+ 好评率99%", 86.88, R.drawable.zuixie));
        foodList.add(new FoodBean("红烧狮子头", "月售240+ 好评率99%", 16.66, R.drawable.shizitou));
        foodList.add(new FoodBean("口水鸡", "月售150+ 好评率95%", 20.88, R.drawable.koushuiji));
        foodList.add(new FoodBean("毛血旺", "月售200+ 好评率98%", 38.86, R.drawable.maoxuewang));
        foodList.add(new FoodBean("干锅肥肠", "月售300+ 好评率100%", 40.99, R.drawable.feichang));
        foodList.add(new FoodBean("鱼香肉丝", "月售250+ 好评率96%", 14.66, R.drawable.yuxiang));
        foodList.add(new FoodBean("麻辣小龙虾", "月售180+ 好评率99%", 99.68, R.drawable.longxia));
        foodAdapter.notifyAll();
    }

    public void refreshFire() {
        foodList.clear();
        foodList.add(new FoodBean("醉蟹", "月售80+ 好评率99%", 86.88, R.drawable.zuixie));
        foodList.add(new FoodBean("章鱼丸", "月售100+ 好评率98%", 15.55, R.drawable.zhangyuwang));
        foodList.add(new FoodBean("毛血旺", "月售200+ 好评率98%", 38.86, R.drawable.maoxuewang));
        foodList.add(new FoodBean("红烧狮子头", "月售240+ 好评率99%", 16.66, R.drawable.shizitou));
        foodList.add(new FoodBean("干锅肥肠", "月售300+ 好评率100%", 40.99, R.drawable.feichang));
        foodList.add(new FoodBean("鱼香肉丝", "月售250+ 好评率96%", 14.66, R.drawable.yuxiang));
        foodAdapter.notifyAll();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
