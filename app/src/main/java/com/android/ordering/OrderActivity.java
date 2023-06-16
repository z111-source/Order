package com.android.ordering;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Dialog;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.ordering.Adapter.OrderAdapter;
import com.android.ordering.Bean.FoodBean;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private ListView lv_order;
    private List<FoodBean> carFoodList;
    private TextView tv_distribution_cost;
    private TextView tv_total_cost;
    private TextView tv_cost;
    private String money,distributionCost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        //获取购物车中的数据
        carFoodList= (List<FoodBean>) getIntent().getSerializableExtra("carFoodList");
        //获取购物车中菜的总价格
        money=(getIntent().getStringExtra("totalMoney"));
        //获取店铺的配送费
        distributionCost=(getIntent().getStringExtra(
                "distributionCost"));
        initView();
        setData();
    }
    /**
     * 初始化界面控件
     */
    private void initView(){
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("订单");
        RelativeLayout rl_title_bar =  findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(getResources().getColor(R.color.back));
        TextView tv_back =  findViewById(R.id.tv_back);
        lv_order=  findViewById(R.id.lv_order);
        tv_distribution_cost =  findViewById(R.id.tv_distribution_cost);
        tv_total_cost = findViewById(R.id.tv_total_cost);
        tv_cost = findViewById(R.id.tv_cost);
        TextView tv_payment = findViewById(R.id.tv_payment);
        // 返回键的点击事件
        tv_back.setOnClickListener(v -> finish());
        tv_payment.setOnClickListener(view -> { //“去支付”按钮的点击事件
            Dialog dialog = new Dialog(OrderActivity.this, R.style.Dialog_Style);
            dialog.setContentView(R.layout.unfinshed_pay);
            dialog.show();
        });
    }
    /**
     * 设置界面数据
     */
    private void setData() {
        OrderAdapter adapter = new OrderAdapter(this);
        lv_order.setAdapter(adapter);
        adapter.setData(carFoodList);
        String cost = "￥"+money;
        tv_cost.setText(cost);
        String dis = "￥"+distributionCost;
        tv_distribution_cost.setText(dis);
        String total_cost = "￥"+(money+distributionCost);
        tv_total_cost.setText(total_cost);
    }
}