package com.weiwoju.kewuyou.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by zhangguobing on 2017/5/3.
 */
public class FileUtil {

    public static String readRawJsonFile(Context context, String fileName) {
        StringBuilder builder = new StringBuilder();
        int id = context.getResources().getIdentifier(fileName, "raw", context.getPackageName());
        InputStream inputStream = context.getResources().openRawResource(id);
        InputStreamReader inputStreamReader;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String content;
            while ((content = bufferedReader.readLine()) != null) {
                builder.append(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
