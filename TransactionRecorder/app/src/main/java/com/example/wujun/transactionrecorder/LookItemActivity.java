package com.example.wujun.transactionrecorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.wujun.transactionrecorder.app.Application;
import com.example.wujun.transactionrecorder.bean.Item;

public class LookItemActivity extends AppCompatActivity {

    public static final String TAG = "LookItemActivity";

    private ListView itemListView;
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
                view = getLayoutInflater().inflate(R.layout.activity_look_item_item_list_layout, null);
            }else{
                view = convertView;
            }
            Item item = Application.items.get(i);

            TextView itemNameView = view.findViewById(R.id.item_name_view);
            itemNameView.setText(item.getName());

            TextView itemPriceView = view.findViewById(R.id.item_price_view);
            itemPriceView.setText(String.valueOf(item.getPrice()));

            ImageView deleteItemView = view.findViewById(R.id.delete_item_view);
            deleteItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Application.items.remove(item);
                    adapter.notifyDataSetChanged();
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LookItemActivity.this, CreateItemActivity.class);
                    intent.putExtra("item", item);
                    startActivity(intent);
                }
            });

            return view;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_item);
        initUI();
    }

    private void initUI(){
        itemListView = findViewById(R.id.item_list_view);
        itemListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_look_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()){
            case R.id.create_new_item_option:
                intent = new Intent(LookItemActivity.this, CreateItemActivity.class);
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
        Application.saveItems();
    }
}
