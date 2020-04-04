package com.example.wujun.transactionrecorder.app;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class Application extends android.app.Application{

    /**
     * 文件保存根路径
     */
    public static final String ROOT_DIR = Environment.getExternalStorageDirectory() + "/TransactionRecorder";

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
