package com.example.wujun.transactionrecorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wujun.transactionrecorder.app.Application;
import com.example.wujun.transactionrecorder.bean.Item;

public class CreateItemActivity extends AppCompatActivity {

    public static final String TAG = "CreateItemActivity";

    private EditText itemNameInput;
    private EditText itemPriceInput;
    private Button addItemBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);
        initUI();
    }

    private void initUI(){
        itemNameInput = findViewById(R.id.item_name_input);
        itemPriceInput = findViewById(R.id.item_price_input);
        addItemBtn = findViewById(R.id.add_item_btn);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String itemName = itemNameInput.getText().toString();
                String itemPrice = itemPriceInput.getText().toString();
                boolean exist = Application.items.stream().anyMatch(i->i.getName().equals(itemName));
                if (exist) {
                    Application.showMessage(CreateItemActivity.this, String.format("商品\"%s\"已存在!", itemName));
                    return;
                }
                try {
                    float price = Float.parseFloat(itemPrice);
                    int maxId = 0;
                    if (Application.items.size() != 0){
                        maxId = Application.items.stream().mapToInt(i->i.getId()).max().getAsInt();
                    }
                    Item item = new Item(++maxId, itemName, price);
                    Application.items.add(item);
                    Application.showMessage(CreateItemActivity.this, String.format("商品\"%s\"添加成功!", itemName));
                }catch (Exception e){
                    e.printStackTrace();
                    Log.e(TAG, e.toString());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Application.saveItems();
    }
}
