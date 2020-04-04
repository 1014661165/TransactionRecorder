package com.example.wujun.transactionrecorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wujun.transactionrecorder.app.Application;
import com.example.wujun.transactionrecorder.util.FileUtil;
import com.example.wujun.transactionrecorder.util.PermissionUtil;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private EditText dateInput;
    private Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        PermissionUtil.requestPermissions(this);
        createRootDir();
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
                Application.showMessage(MainActivity.this, dateInput.getText().toString());
            }
        });
    }

    private void createRootDir(){
        try {
            FileUtil.mkdir(Application.ROOT_DIR);
            Log.i(TAG, "create root dir...");
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
}
