package com.example.wujun.transactionrecorder.util;

import android.os.Environment;
import java.io.File;

/**
 * 文件工具类
 */
public class FileUtil {

    /**
     * 创建目录
     * @param path
     */
    public static void mkdir(String path){
        //外部存储是否存在
        boolean externalStorageExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if(!externalStorageExist) {
            return;
        }
        File rootDir = new File(path);
        if (!rootDir.exists()){
            rootDir.mkdir();
        }
    }
}
