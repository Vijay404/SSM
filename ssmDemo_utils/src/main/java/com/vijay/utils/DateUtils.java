package com.vijay.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 将日期格式与字符串格式相互转换
 */
public class DateUtils {
    //日期转字符串
    public static String date2String(Date date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(date);
        return s;
    }

    //字符串转日期
    public static Date string2Date(String date,String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date parse = sdf.parse(date);
        return parse;
    }

}
