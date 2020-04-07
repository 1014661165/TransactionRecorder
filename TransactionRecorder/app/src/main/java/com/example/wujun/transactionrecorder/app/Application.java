package com.example.wujun.transactionrecorder.app;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.example.wujun.transactionrecorder.bean.Item;
import com.example.wujun.transactionrecorder.bean.Order;
import com.example.wujun.transactionrecorder.util.DateUtil;
import com.example.wujun.transactionrecorder.util.FileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Application extends android.app.Application{

    public static final String TAG = "Application";

    /**
     * 文件保存根路径
     */
    public static final String ROOT_DIR = Environment.getExternalStorageDirectory() + "/TransactionRecorder";

    public static final String ORDER_DIR = ROOT_DIR + "/Orders";

    public static Map<String, List<Order>> orders = new HashMap<>();
    public static List<Item> items;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    /**
     * 加载商品
     */
    public static void loadItems(){
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
     * 加载订单
     */
    public static void loadOrders(){
        String orderDirPath = ROOT_DIR + File.separator + "orders";
        File orderDir = new File(orderDirPath);
        orders = new HashMap<>();
        if (!orderDir.exists()){
            return;
        }
        File[] orderFiles = orderDir.listFiles();
        if (orderFiles == null || orderFiles.length == 0){
            return;
        }

        for (File f: orderFiles){
            try {
                String fileName = f.getName();
                String date = fileName.substring(0, fileName.lastIndexOf(".json"));

                String json = FileUtil.readAllText(f.getAbsolutePath());
                List<Order> currentOrders = JSONArray.parseArray(json, Order.class);
                orders.put(date, currentOrders);
            }catch (Exception e){
                e.printStackTrace();
                Log.e(TAG, e.toString());
            }
        }
    }

    /**
     * 保存订单
     */
    public static void saveOrders(){
        String date = DateUtil.format(new Date(), "yyyy-MM-dd");
        if (!orders.containsKey(date)){
            return;
        }
        List<Order> todayOrders = orders.get(date);
        try {
            String json = JSONArray.toJSONString(todayOrders);
            String path = String.format("%s/%s.json", ORDER_DIR, date);
            FileUtil.writeAllText(path, json);
            Log.i(TAG, "save orders...");
        }catch (IOException e){
            e.printStackTrace();
            Log.e(TAG, e.toString());
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
