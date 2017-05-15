package com.weiwoju.kewuyou.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by Administrator on 2016/5/10.
 */
public class PinYin {
    private static HanyuPinyinOutputFormat format = null;// 拼音类

    /**
     * 拼音类使用到的泛型
     */
    private static enum Type {
        UPPERCASE, // 全部大写
        LOWERCASE, // 全部小写
        FIRSTUPPER // 首字母大写
    }

    /**
     * 获取全拼,全部大写
     */
    public static String getFirstLetterAllBig(String str) throws BadHanyuPinyinOutputFormatCombination {
        return toPinYin(str, "", Type.UPPERCASE);
    }

    /**
     * 获取全拼,全部小写
     */
    public static String getFirstLetterAllSmall(String str) throws BadHanyuPinyinOutputFormatCombination {
        return toPinYin(str, "", Type.LOWERCASE);
    }

    /**
     * 获取全拼,首字母大写
     */
    public static String getFirstLetterAllBigSmall(String str) throws BadHanyuPinyinOutputFormatCombination {
        return toPinYin(str, "", Type.FIRSTUPPER);
    }

    /**
     * 只获取中文首字母(不是全拼),例如:中文=ZW,大写
     */
    public static String getFirstLetterBig(String text) {
        return converterToFirstSpell(text, HanyuPinyinCaseType.UPPERCASE);
    }

    /**
     * 只获取中文首字母(不是全拼),例如:中文=zw,小写
     */
    public static String getFirstLetterSmall(String text) {
        return converterToFirstSpell(text, HanyuPinyinCaseType.LOWERCASE);
    }

    /**
     * 将str转换成拼音，如果不是汉字或者没有对应的拼音，则不作转换 如： 明天 转换成 MINGTIAN
     *
     * @param str
     * @param spera
     * @return
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    private static String toPinYin(String str, String spera, Type type) throws BadHanyuPinyinOutputFormatCombination {
        if (str == null || str.trim().length() == 0) {
            return "";
        }

        if (format == null) {// 对象为null就创建
            format = new HanyuPinyinOutputFormat();
        }

        if (type == Type.UPPERCASE) {
            format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        } else {
            format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        }
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        String py = "";
        String temp = "";
        String[] t;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ((int) c <= 128)
                py += c;
            else {
                t = PinyinHelper.toHanyuPinyinStringArray(c, format);
                if (t == null)
                    py += c;
                else {
                    temp = t[0];
                    if (type == Type.FIRSTUPPER)
                        temp = t[0].toUpperCase().charAt(0) + temp.substring(1);
                    py += temp + (i == str.length() - 1 ? "" : spera);
                }
            }
        }
        return py.trim();
    }

    /**
     * 只获取中文首字母(不是全拼),例如:中文=zw
     */
    private static String converterToFirstSpell(String text, HanyuPinyinCaseType caseType) {
        if (format == null) {
            format = new HanyuPinyinOutputFormat();
        }

        String pinyinName = "";
        char[] nameChar = text.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(caseType);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }

}
