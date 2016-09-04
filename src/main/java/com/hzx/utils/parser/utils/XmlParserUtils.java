package com.hzx.utils.parser.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ZongxingH on 2016/9/4.
 */
public class XmlParserUtils {
    /**
     * 根据标签返回报文中标签之间的内容，包括标签自身
     *
     * @param tagName 报文中的标签，非空，不包含"<"，">"，"</"，">"
     * @param xml     报文内容
     * @return
     */
    public static String getXmlByTag(String tagName, String xml) {
        //此处校验接收到的报文是否为空值。整个报文head和body
        if (StringUtils.isEmpty(xml)) {
            throw new NullPointerException("The Message of XML is empty, please check it");
        }
        if (StringUtils.isEmpty(tagName)) {
            throw new NullPointerException("The Message of TagName is empty, please check it");
        }
        String staKey = "<" + tagName + ">";
        String endKey = "</" + tagName + ">";
        //int indexOf(int ch) 返回指定字符在此字符串中第一次出现处的索引。
        int staIndex = xml.indexOf(staKey);
        if (-1 == staIndex) {
            return null;
        }
        int staLenth = staKey.length();
        //str＝str.substring(int beginIndex);截取掉str从首字母起长度为beginIndex的字符串，将剩余字符串赋值给str；
        xml = xml.substring(staIndex + staLenth);
        int endIndex = xml.indexOf(endKey);
        xml = xml.substring(0, endIndex);
        return xml;
    }

    /**
     * 根据标签返回报文中标签的值
     *
     * @param tagName 报文中的标签，非空，不包含"<"，">"，"</"，">"
     * @param xml     报文内容
     * @return
     */
    public static String getElementsByTag(String tagName, String xml) {
        //此处校验接收到的报文是否为空值。整个报文head和body
        if (StringUtils.isEmpty(xml)) {
            throw new NullPointerException("The Message of XML is empty, please check it");
        }
        if (StringUtils.isEmpty(tagName)) {
            throw new NullPointerException("The Message of TagName is empty, please check it");
        }
        Pattern pattern = Pattern.compile("<(" + tagName + ")[^>]*?>((.*?)</" + tagName + ">|(/>))");
        Matcher matcher = pattern.matcher(xml);
        ArrayList<String> al = new ArrayList<String>();
        while(matcher.find()) {
            al.add(matcher.group());
        }
        String[] arr = al.toArray(new String[al.size()]);
        al.clear();
        String tagValue = arr[0];
        int firIndex = tagValue.indexOf(">");
        int endIndex = tagValue.lastIndexOf("<");
        tagValue = tagValue.substring(firIndex + 1,endIndex);
        return tagValue;

    }

    /**
     * 根据标签返回报文中标签的属性名和属性值
     *
     * @param tagName 报文中的标签，非空，不包含"<"，">"，"</"，">"
     * @param xml     报文内容
     * @return
     */
    public static Map<String, String> getElementAttributes(String tagName, String xml) {
        //此处校验接收到的报文是否为空值。整个报文head和body
        if (StringUtils.isEmpty(xml)) {
            throw new NullPointerException("The Message of XML is empty, please check it");
        }
        if (StringUtils.isEmpty(tagName)) {
            throw new NullPointerException("The Message of TagName is empty, please check it");
        }
        Pattern pattern = Pattern.compile("<(" + tagName + ")[^>]*?>(([\\w\\W.]*?)</" + tagName + ">|(/>))");
        Matcher matcher = pattern.matcher(xml);
        ArrayList<String> al = new ArrayList<String>();
        while(matcher.find()) {
            al.add(matcher.group());
        }
        String[] arr = al.toArray(new String[al.size()]);
        al.clear();
        String tagValue = arr[0];

        pattern = Pattern.compile("(\\w+)\\s*=\\s*\\\"([\\w-\\./]*)\\\"");
        matcher = pattern.matcher(tagValue);
        Map<String, String> attrs = new HashMap<String, String>();
        while(matcher.find()){
            attrs.put(matcher.group(1).trim(),matcher.group(2).trim());
        }
        return attrs;
    }

}
