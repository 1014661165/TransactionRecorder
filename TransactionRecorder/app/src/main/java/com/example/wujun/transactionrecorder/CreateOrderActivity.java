package com.example.wujun.transactionrecorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.wujun.transactionrecorder.app.Application;
import com.example.wujun.transactionrecorder.bean.Item;
import com.example.wujun.transactionrecorder.bean.Order;
import com.example.wujun.transactionrecorder.util.DateUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class CreateOrderActivity extends AppCompatActivity {

    public static final String TAG = "CreateOrderActivity";

    //order对象
    private Order order = new Order();

    //UI
    private RadioGroup takeOutRadioGroup;
    private ListView createOrderItemListView;
    private Button addOrderBtn;

    //ListView adapter
    private BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return Application.items.size();
        }

        @Override
        public Object getItem(int i) {
            return Application.items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            View view = null;
            if (convertView == null) {
                view = getLayoutInflater().inflate(R.layout.activity_create_order_item_list_view_layout, null);
            }else{
                view = convertView;
            }
            Item item = Application.items.get(i);

            TextView itemNameView = view.findViewById(R.id.item_name_view);
            itemNameView.setText(item.getName());

            TextView itemCountView  = view.findViewById(R.id.item_count_view);
            itemCountView.setText("0");

            ImageView addItemImageView = view.findViewById(R.id.add_item_image_view);
            addItemImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String number = itemCountView.getText().toString();
                    itemCountView.setText(String.valueOf(Integer.parseInt(number) + 1));

                    order.getItems().add(item.clone());
                }
            });

            ImageView removeItemImageView = view.findViewById(R.id.remove_item_image_view);
            removeItemImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String number = itemCountView.getText().toString();
                    if  (number.equals("0")){
                        return;
                    }
                    itemCountView.setText(String.valueOf(Integer.parseInt(number) - 1));

                    Item target = order.getItems().stream().filter(i->i.getName().equals(item.getName())).findFirst().get();
                    order.getItems().remove(target);
                }
            });

            return view;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        initUI();
    }

    private void initUI(){
        takeOutRadioGroup = findViewById(R.id.take_out_radio_group);
        takeOutRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.i(TAG, "radio"+i);
                switch (i){
                    case R.id.eat_in_radio:
                        order.setTakeOut(false);
                        break;
                    case R.id.take_out_radio:
                        order.setTakeOut(true);
                        break;
                }
            }
        });

        createOrderItemListView = findViewById(R.id.create_order_item_list_view);
        createOrderItemListView.setAdapter(adapter);

        addOrderBtn = findViewById(R.id.add_order_btn);
        addOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (order.getItems().size() == 0){
                    Application.showMessage(CreateOrderActivity.this, "请至少选择一种商品!");
                    return;
                }

                String createTime = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
                String date = DateUtil.format(new Date(), "yyyy-MM-dd");
                float totalPrice = (float) order.getItems().stream().mapToDouble(i->i.getPrice()).sum();
                totalPrice = Float.parseFloat(new DecimalFormat("#.0").format(totalPrice));

                order.setCreateTime(createTime);
                order.setTotalPrice(totalPrice);
                if (!Application.orders.containsKey(date)){
                    Application.orders.put(date, new ArrayList<>());
                }
                Application.orders.get(date).add(0, order);
                Application.showMessage(CreateOrderActivity.this, "订单添加成功");
                finish();
            }
        });

    }
}
