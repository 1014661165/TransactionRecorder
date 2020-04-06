package com.example.wujun.transactionrecorder.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {


    /**
     * 格式化日期对象
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern){
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }
}
