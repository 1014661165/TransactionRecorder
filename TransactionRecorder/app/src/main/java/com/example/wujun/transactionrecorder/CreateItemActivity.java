package com.example.wujun.transactionrecorder;

import android.content.Intent;
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
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);
        Intent intent = getIntent();
        if (intent.hasExtra("item")){
            item = (Item)intent.getSerializableExtra("item");
        }else{
            item = new Item(-1, "", 0f);
        }
        initUI();
    }

    private void initUI(){
        itemNameInput = findViewById(R.id.item_name_input);
        itemNameInput.setText(item.getName());

        itemPriceInput = findViewById(R.id.item_price_input);
        itemPriceInput.setText(String.valueOf(item.getPrice()));

        addItemBtn = findViewById(R.id.add_item_btn);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = itemNameInput.getText().toString();
                String itemPrice = itemPriceInput.getText().toString();
                boolean exist = Application.items.stream().anyMatch(i->i.getName().equals(itemName));
                if (exist) {
                    Item item = Application.items.stream().filter(i->i.getName().equals(itemName)).findFirst().get();
                    item.setPrice(Float.parseFloat(itemPrice));
                    Application.showMessage(CreateItemActivity.this, String.format("商品\"%s\"修改成功!", itemName));
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
