package com.android.ordering;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.ordering.Adapter.CarAdapter;
import com.android.ordering.Adapter.FoodAdapter;
import com.android.ordering.Bean.FoodBean;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.
        OnClickListener {
    private final List<FoodBean> foodList = new ArrayList<>();
    private TextView tv_settle_accounts;
    private TextView tv_count;
    private TextView tv_money;
    private TextView tv_distribution_cost;
    private TextView tv_not_enough;
    private ImageView iv_shop_car;
    public static final int MSG_COUNT_OK = 1;// 获取购物车中商品的数量
    private MHandler mHandler;
    private int totalCount = 0;
    private Double totalMoney;            //购物车中菜品的总价格
    private List<FoodBean> carFoodList;      //购物车中的菜品数据
    private FoodAdapter foodAdapter;
    private CarAdapter carAdapter;
    private RelativeLayout rl_car_list;
    private TextView tv_distribution;
    private ListView car_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new MHandler(Looper.myLooper());
        totalMoney = 0.0;//初始化变量totalMoney
        carFoodList = new ArrayList<>(); //初始化集合carFoodList
        initData();
        initView();     //初始化界面控件
        initAdapter(); //初始化adapter
    }

    /**
     * 初始化界面控件
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        TextView tv_back = findViewById(R.id.tv_back);
        //  ListView list_left = findViewById(R.id.listView_left);
        tv_settle_accounts = findViewById(R.id.tv_settle_accounts);
        tv_distribution_cost = findViewById(R.id.tv_distribution_cost);
        tv_count = findViewById(R.id.tv_count);
        iv_shop_car = findViewById(R.id.iv_shop_car);
        tv_money = findViewById(R.id.tv_money);
        tv_not_enough = findViewById(R.id.tv_not_enough);
        TextView tv_clear = findViewById(R.id.tv_clear);
        ListView list_right = findViewById(R.id.right_list);
        foodAdapter.setData(foodList);
        list_right.setAdapter(foodAdapter);

        car_list = findViewById(R.id.list_car);          //购物车列表
        ImageView recycle_item_add = findViewById(R.id.iv_add);
        recycle_item_add.setOnClickListener(this);
        ImageView recycle_item_minus = findViewById(R.id.iv_minus);
        recycle_item_minus.setOnClickListener(this);
        tv_distribution = findViewById(R.id.tv_distribution);
        rl_car_list = findViewById(R.id.rl_car_list);
        //点击购物车列表界面外的其他部分会隐藏购物车列表界面
        rl_car_list.setOnTouchListener((v, event) -> {
            if (rl_car_list.getVisibility() == View.VISIBLE) {
                rl_car_list.setVisibility(View.GONE);
            }
            return false;
        });
        //设置返回键、去结算按钮、购物车图片、清空购物车按钮的点击监听事件
        tv_back.setOnClickListener(this);
        tv_settle_accounts.setOnClickListener(this);
        iv_shop_car.setOnClickListener(this);
        tv_clear.setOnClickListener(this);
    }

    /**
     * 初始化adapter
     */
    private void initAdapter() {
        carAdapter = new CarAdapter(this, new CarAdapter.OnSelectListener() {
            @Override
            public void onSelectAdd(int position, TextView tv_food_count, TextView
                    tv_food_price) {
                //添加菜品到购物车中
                FoodBean bean = carFoodList.get(position);  //获取当前菜品对象
                String count = bean.getCount() + 1 + "";
                tv_food_count.setText(count); //设置该菜品在购物车中的数量
                String allPrice = "￥" + bean.getPrice() * (bean.getCount() + 1);
                tv_food_price.setText(allPrice);//菜品总价格
                bean.setCount(bean.getCount() + 1);//将当前菜品在购物车中的数量设置给菜品对象
                //遍历购物车中的数据
                //找到当前菜品
                //删除购物车中当前菜品的旧数据
                carFoodList.removeIf(food -> food.getId() == bean.getId());
                carFoodList.add(position, bean); //将当前菜品的最新数据添加到购物车数据集合中
                totalCount = totalCount + 1;      //购物车中菜品的总数量+1
                //购物车中菜品的总价格+当前菜品价格
                totalMoney = totalMoney + (bean.getPrice());
                carDataMsg();//将购物车中菜品的总数量和总价格通过Handler传递到主线程中
            }

            @Override
            public void onSelectMis(int position, TextView tv_food_count, TextView
                    tv_food_price) {
                FoodBean bean = carFoodList.get(position);
                String count = bean.getCount() - 1 + "";//获取当前菜品对象
                tv_food_count.setText(count);//设置当前菜品的数量
                String num = "￥" + bean.getPrice() * (bean.getCount() - 1);
                //设置当前菜品总价格，菜品价格=菜品单价*菜品数量
                tv_food_price.setText(num);
                minusCarData(bean, position);//删除购物车中的菜品
            }
        });
        foodAdapter = new FoodAdapter(this, position -> {
            FoodBean fb = foodList.get(position);
            fb.setCount(fb.getCount() + 1);
            carFoodList.removeIf(food -> food.getId() == fb.getId());
            carFoodList.add(fb);
            totalCount = totalCount + 1;
            totalMoney = totalMoney + fb.getPrice();
            carDataMsg();
        });

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:               //返回按钮的点击事件
                finish();
                break;
            case R.id.tv_settle_accounts: //去结算按钮的点击事件
                //跳转到订单界面
                if (totalCount > 0) {
                    Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                    intent.putExtra("carFoodList", (Serializable) carFoodList);
                    intent.putExtra("totalMoney", totalMoney + "");
                    startActivity(intent);
                }
                break;
            case R.id.iv_shop_car:          //购物车的点击事件
                if (totalCount <= 0) return;
                if (rl_car_list != null) {
                    if (rl_car_list.getVisibility() == View.VISIBLE) {
                        rl_car_list.setVisibility(View.GONE);
                    } else {
                        rl_car_list.setVisibility(View.VISIBLE);
                        //创建一个从底部滑出的动画
                        Animation animation = AnimationUtils.loadAnimation(
                                MainActivity.this, R.anim.slide_bottom_to_top);
                        rl_car_list.startAnimation(animation);//将动画加载到购物车列表界面
                    }
                }
                carAdapter.setData(carFoodList);
                car_list.setAdapter(carAdapter);
                break;
            case R.id.tv_clear://清空按钮的点击事件
                dialogClear(); //弹出确认清空购物车的对话框
                break;
            case R.id.iv_add:

        }
    }

    /**
     * 弹出清空购物车的对话框
     */
    private void dialogClear() {
        //创建一个对话框Dialog
        final Dialog dialog = new Dialog(MainActivity.this, R.style.Dialog_Style);
        dialog.setContentView(R.layout.dialog_clear); //将布局文件加载到对话框中
        dialog.show();                                     //显示对话框
        TextView tv_clear = dialog.findViewById(R.id.tv_clear);  //获取对话框清除按钮
        TextView tv_cancel = dialog.findViewById(R.id.tv_cancel);//获取对话框取消按钮
        tv_cancel.setOnClickListener(view -> {
            dialog.dismiss();//关闭对话框
        });
        tv_clear.setOnClickListener(view -> {
            if (carFoodList == null) return;
            for (FoodBean bean : carFoodList) {
                bean.setCount(0);//设置购物车中所有菜品的数量为0
            }
            carFoodList.clear();//清空购物车中的数据
            carAdapter.notifyDataSetChanged();    //更新界面
            totalCount = 0;      //购物车中菜品的数量设置为0
            totalMoney = 0.0;//总价格设置为0
            carDataMsg();        //通过Handler更新购物车中菜品的数量和总价格
            dialog.dismiss();   //关闭对话框
        });
    }

    /**
     * 将购物车中菜品的总数量和总价格通过Handler传递到主线程中
     */
    private void carDataMsg() {
        Message msg = Message.obtain();
        msg.what = MSG_COUNT_OK;
        Bundle bundle = new Bundle();//创建一个Bundler对象
        //将购物车中的菜品数量和价格放入Bundler对象中
        bundle.putString("totalCount", totalCount + "");
        bundle.putString("totalMoney", totalMoney + "");
        msg.setData(bundle);        //将Bundler对象放入Message对象
        mHandler.sendMessage(msg); //将Message对象传递到MHandler类
    }

    /*删除购物车中的数据*/
    private void minusCarData(FoodBean bean, int position) {
        int count = bean.getCount() - 1; //将该菜品的数量减1
        bean.setCount(count);
        //将减后的数量设置到菜品对象中
        //遍历购物车中的菜
        //找到购物车中当前菜的Id
        //删除存放的菜
        carFoodList.removeIf(food -> food.getId() == bean.getId());
        //如果当前菜品的数量减1之后大于0，则将当前菜品添加到购物车集合中
        if (count > 0) carFoodList.add(position, bean);
        else carAdapter.notifyDataSetChanged();
        totalCount = totalCount - 1; //购物车中菜品的数量减1
        //购物车中的总价钱=总价钱-当前菜品的价格
        totalMoney = totalMoney - (bean.getPrice());
        carDataMsg();                  //调用该方法更新购物车中的数据
    }

    private void initData() {
        foodList.add(new FoodBean("红烧肉", "月售200+ 好评率98%", 18.99, R.drawable.hongshaorou));
        foodList.add(new FoodBean("醉蟹", "月售80+ 好评率99%", 86.88, R.drawable.zuixie));
        foodList.add(new FoodBean("章鱼丸", "月售100+ 好评率98%", 15.55, R.drawable.zhangyuwang));
        foodList.add(new FoodBean("红烧狮子头", "月售240+ 好评率99%", 16.66, R.drawable.shizitou));
        foodList.add(new FoodBean("口水鸡", "月售150+ 好评率95%", 20.88, R.drawable.koushuiji));
        foodList.add(new FoodBean("毛血旺", "月售200+ 好评率98%", 38.86, R.drawable.maoxuewang));
        foodList.add(new FoodBean("干锅肥肠", "月售300+ 好评率100%", 40.99, R.drawable.feichang));
        foodList.add(new FoodBean("鱼香肉丝", "月售250+ 好评率96%", 14.66, R.drawable.yuxiang));
        foodList.add(new FoodBean("梅菜扣肉", "月售100+ 好评率97%", 13.89, R.drawable.meicairou));
        foodList.add(new FoodBean("麻辣小龙虾", "月售180+ 好评率99%", 99.68, R.drawable.longxia));
    }

    /*事件捕获*/
    class MHandler extends Handler {
        public MHandler(Looper myLooper) {
            super(myLooper);
        }

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            if (msg.what == MSG_COUNT_OK) {
                Bundle bundle = msg.getData();
                String count = bundle.getString("totalCount", "");
                String money = bundle.getString("totalMoney", "");
                if (Integer.parseInt(count) > 0) {//如果购物车中有菜品
                    iv_shop_car.setImageResource(R.drawable.car_selected);
                    tv_count.setVisibility(View.VISIBLE);
                    tv_distribution_cost.setVisibility(View.VISIBLE);
                    tv_money.setTextColor(Color.parseColor("#ffffff"));
                    tv_money.getPaint().setFakeBoldText(true);//加粗字体
                    String allMoney = "￥" + money;
                    tv_money.setText(allMoney);//设置购物车中菜品总价格
                    tv_count.setText(count);        //设置购物车中菜品总数量

                    double dis = Double.parseDouble(tv_distribution.getText().toString());
                    //总价格money与起送价格对比
                    double result = Double.parseDouble(money) - dis;
                    if (0.0 > result) { //总价格<起送价格
                        tv_settle_accounts.setVisibility(View.GONE);//隐藏去结算按钮
                        tv_not_enough.setVisibility(View.VISIBLE);   //显示差价文本
                        //差价=起送价格-总价格
                        String not_enough = dis - Double.parseDouble(money) + "";
                        tv_not_enough.setText(not_enough);
                    } else { //总价格>=起送价格
                        //显示去结算按钮
                        tv_settle_accounts.setVisibility(View.VISIBLE);
                        tv_not_enough.setVisibility(View.GONE); //隐藏差价文本
                    }
                } else { //如果购物车中没有菜品
                    if (rl_car_list.getVisibility() == View.VISIBLE) {
                        rl_car_list.setVisibility(View.GONE); //隐藏购物车列表
                    } else {
                        rl_car_list.setVisibility(View.VISIBLE);//显示购物车列表
                    }
                    iv_shop_car.setImageResource(R.drawable.car_empty);
                    tv_settle_accounts.setVisibility(View.GONE);//隐藏去结算按钮
                    tv_not_enough.setVisibility(View.VISIBLE);   //显示差价文本
                    String not = "￥" + tv_distribution.getText().toString() + "起送";
                    tv_not_enough.setText(not);
                    tv_count.setVisibility(View.GONE);//隐藏购物中的菜品数量控件
                    tv_distribution_cost.setVisibility(View.GONE);//隐藏配送费用
                    tv_money.setTextColor(getResources().getColor(R.color.
                            light_gray));
                    tv_money.setText("未选购商品");
                }
            }
        }
    }


}
