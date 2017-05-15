package com.weiwoju.kewuyou.util;

import com.weiwoju.kewuyou.context.AppContext;

public class StringFetcher {

    public static String getString(int id) {
        return AppContext.getContext().getString(id);
    }

    public static String getString(int id, Object... format) {
        return AppContext.getContext().getString(id, format);
    }

}