package com.example.wujun.transactionrecorder.util;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 读取文件所有行
     * @return
     */
    public static List<String> readLines(String path)throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(path));
        List<String> lines = new ArrayList<>();
        String line = null;
        while ((line = reader.readLine()) != null){
            lines.add(line);
        }
        reader.close();
        return lines;
    }

    /**
     * 读取文件所有内容
     * @param path
     * @return
     * @throws IOException
     */
    public static String readAllText(String path) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder content = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null){
            content.append(line);
        }
        reader.close();
        return content.toString();
    }

    /**
     * 向文件中写入所有内容
     * @param path
     * @param content
     * @throws IOException
     */
    public static void writeAllText(String path, String content) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(content);
        writer.flush();
        writer.close();
    }
}
