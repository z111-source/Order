package com.android.ordering;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.ordering.Bean.FoodBean;

import java.io.Serializable;
import java.util.List;

public class CarFragment extends Fragment {
    private List<FoodBean> foodBeanList ;
    private TextView toPay;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_car,container,false);
        toPay = view.findViewById(R.id.tv_settle_accounts);
        toPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),OrderActivity.class);
                intent.putExtra("fbl",(Serializable) foodBeanList);
                startActivity(intent);
            }
        });
        return view;
    }
}
