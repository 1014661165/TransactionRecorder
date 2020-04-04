package com.example.wujun.transactionrecorder.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionUtil {
    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 请求所有权限
     */
    public static void requestPermissions(Activity activity){
        int result = PackageManager.PERMISSION_DENIED;
        for (String permission: PERMISSIONS){
            result = ContextCompat.checkSelfPermission(activity, permission);
            if (result == PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(activity, PERMISSIONS, 0);
            }
        }
    }
}
