package com.example.wujun.transactionrecorder;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wujun.transactionrecorder.app.Application;
import com.example.wujun.transactionrecorder.bean.Item;
import com.example.wujun.transactionrecorder.bean.Order;
import com.example.wujun.transactionrecorder.util.DateUtil;
import com.example.wujun.transactionrecorder.util.FileUtil;
import com.example.wujun.transactionrecorder.util.PermissionUtil;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    //UI
    private EditText dateInput;
    private Button searchBtn;
    private ListView orderListView;

    //ListView  Adapter
    private BaseAdapter adapter = new BaseAdapter() {

        @Override
        public int getCount() {
            String date = DateUtil.format(new Date(), "yyyy-MM-dd");
            if (dateInput != null && !dateInput.getText().toString().isEmpty()){
                date = dateInput.getText().toString().trim();
            }
            if (Application.orders.containsKey(date)){
                return Application.orders.get(date).size();
            }
            return 0;
        }

        @Override
        public Object getItem(int i) {
            String date = DateUtil.format(new Date(), "yyyy-MM-dd");
            if (dateInput != null && !dateInput.getText().toString().isEmpty()){
                date = dateInput.getText().toString().trim();
            }
            if (Application.orders.containsKey(date)){
                return Application.orders.get(date).get(i);
            }
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            View view = null;
            if (convertView == null){
                view = getLayoutInflater().inflate(R.layout.activity_main_order_list_view_item, null);
            }else{
                view = convertView;
            }
            String date = DateUtil.format(new Date(), "yyyy-MM-dd");
            if (dateInput != null && !dateInput.getText().toString().isEmpty()){
                date = dateInput.getText().toString().trim();
            }
            if (!Application.orders.containsKey(date)){
                return view;
            }
            List<Order> orders = Application.orders.get(date);
            Order order = orders.get(i);

            TextView orderCreateTimeView = view.findViewById(R.id.order_create_time_view);
            orderCreateTimeView.setText(order.getCreateTime());

            TextView orderPriceView = view.findViewById(R.id.order_price_view);
            orderPriceView.setText(String.valueOf(order.getTotalPrice()));

            ImageView removeOrderView = view.findViewById(R.id.remove_order_view);
            removeOrderView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orders.remove(order);
                    adapter.notifyDataSetChanged();
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StringBuilder messageBuilder = new StringBuilder();
                    Map<String, Integer> itemMap = new HashMap<>();
                    for (Item item: order.getItems()){
                        if (!itemMap.containsKey(item.getName())){
                            itemMap.put(item.getName(), 1);
                            continue;
                        }
                        int cnt = itemMap.get(item.getName());
                        itemMap.put(item.getName(), cnt + 1);
                    }
                    for (String itemName: itemMap.keySet()){
                        messageBuilder.append(String.format("%s * %d\n", itemName, itemMap.get(itemName)));
                    }
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("订单商品")
                            .setMessage(messageBuilder.toString())
                            .create();
                    alertDialog.show();
                }
            });

            return view;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        PermissionUtil.requestPermissions(this);
        createRootDir();
        Application.loadItems();
        Application.loadOrders();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_main, menu);
        return true;
    }

    private void initUI(){
        dateInput = findViewById(R.id.date);
        searchBtn = findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.notifyDataSetChanged();
            }
        });
        orderListView = findViewById(R.id.order_list_view);
        orderListView.setAdapter(adapter);
    }

    private void createRootDir(){
        try {
            FileUtil.mkdir(Application.ROOT_DIR);
            Log.i(TAG, "create root dir...");
            FileUtil.mkdir(Application.ORDER_DIR);
            Log.i(TAG, "create orders dir...");
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.create_new_order_option:
                intent = new Intent(MainActivity.this, CreateOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.look_item_option:
                intent = new Intent(MainActivity.this, LookItemActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Application.saveOrders();
    }
}
