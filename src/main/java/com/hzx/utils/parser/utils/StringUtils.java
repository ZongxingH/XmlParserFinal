package com.hzx.utils.parser.utils;

/**
 * Created by ZongxingH on 2016/9/4.
 */
public class StringUtils {

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if (null == str || "".equals(str)) return true;
        return false;
    }
}
