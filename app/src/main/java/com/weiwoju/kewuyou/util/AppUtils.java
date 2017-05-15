/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.weiwoju.kewuyou.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

public class AppUtils {

    public static PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            // Should not happen.
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取版本名
     * @param context
     * @return
     */
    public static String getVersionName(Context context){
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            return pi.versionName;
        }catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return "1.0";
    }

    /**
     * 获取版本号
     * @param context
     * @return
     */
    public static int getVersionCode(Context context){
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            return pi.versionCode;
        }catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return 1;
    }

    public static void openAppInMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "你手机中没有安装应用市场！", Toast.LENGTH_SHORT).show();
        }
    }

}
