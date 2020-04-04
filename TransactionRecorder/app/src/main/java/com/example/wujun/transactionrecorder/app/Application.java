package com.example.wujun.transactionrecorder.app;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.example.wujun.transactionrecorder.bean.Item;
import com.example.wujun.transactionrecorder.bean.Order;
import com.example.wujun.transactionrecorder.util.FileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Application extends android.app.Application{

    public static final String TAG = "Application";

    /**
     * 文件保存根路径
     */
    public static final String ROOT_DIR = Environment.getExternalStorageDirectory() + "/TransactionRecorder";


    public static List<Order> orders;
    public static List<Item> items;

    @Override
    public void onCreate() {
        super.onCreate();
        loadItems();
    }


    /**
     * 加载商品
     */
    private static void loadItems(){
        String filePath = ROOT_DIR + File.separator + "items.json";
        File itemFile = new File(filePath);
        if (!itemFile.exists()){
            items = new ArrayList<>();
            return;
        }
        try {
            String json = FileUtil.readAllText(filePath);
            items = JSONArray.parseArray(json, Item.class);
            Log.i(TAG, "load items...");
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
        Log.i(TAG, "load item...");
    }

    /**
     * 保存商品信息
     */
    public static void saveItems(){
        String filePath = ROOT_DIR + File.separator + "items.json";
        try {
            String json = JSONArray.toJSONString(items);
            FileUtil.writeAllText(filePath, json);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 显示信息
     * @param context
     * @param msg
     */
    public static void showMessage(Context context, String msg)
    {
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
}
