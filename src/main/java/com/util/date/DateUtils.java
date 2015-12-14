package com.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by harrysa66 on 2015/12/14.
 */
public class DateUtils {

    public final static String TYPE_YEAR = "YEAR";
    public final static String TYPE_MONTH = "MONTH";
    public final static String TYPE_DAY = "DAY";

    /**
     * 增加年/月/日(输入日期格式,返回字符串)
     * @param date 日期格式
     * @param type YEAR/MONTH/DAY
     * @param n 数量
     * @return 字符串日期  yyyy-MM-dd
     */
    public final static String addDateReStr(Date date,String type,int n){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        if(type.equals("YEAR")){
            cd.add(Calendar.YEAR, n);//增加一年
        }else if(type.equals("MONTH")){
            cd.add(Calendar.MONTH, n);//增加一月
        }else if(type.equals("DAY")){
            cd.add(Calendar.DATE, n);//增加一日
        }else{
            cd.add(Calendar.MONTH, n);//增加一月
        }
        return sdf.format(cd.getTime());
    }

    /**
     * 增加年/月/日(输入日期格式,返回日期格式)
     * @param date 日期格式
     * @param type YEAR/MONTH/DAY
     * @param n 月数
     * @return 日期格式
     */
    public final static Date addDateReDate(Date date,String type,int n){
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        if(type.equals("YEAR")){
            cd.add(Calendar.YEAR, n);//增加一年
        }else if(type.equals("MONTH")){
            cd.add(Calendar.MONTH, n);//增加一月
        }else if(type.equals("DAY")){
            cd.add(Calendar.DATE, n);//增加一日
        }else{
            cd.add(Calendar.MONTH, n);//增加一月
        }
        return cd.getTime();
    }

    /**
     * 增加年/月/日(输入字符串,返回字符串)
     * @param date 字符串日期  yyyy-MM-dd
     * @param type YEAR/MONTH/DAY
     * @param n 月数
     * @return 字符串日期   yyyy-MM-dd
     * @throws ParseException
     */
    public final static String addDateStrReStr(String date,String type,int n) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cd = Calendar.getInstance();
        cd.setTime(sdf.parse(date));
        if(type.equals("YEAR")){
            cd.add(Calendar.YEAR, n);//增加一年
        }else if(type.equals("MONTH")){
            cd.add(Calendar.MONTH, n);//增加一月
        }else if(type.equals("DAY")){
            cd.add(Calendar.DATE, n);//增加一日
        }else{
            cd.add(Calendar.MONTH, n);//增加一月
        }
        return sdf.format(cd.getTime());
    }

    /**
     * 增加年/月/日(输入日期格式,返回日期)
     * @param date 字符串日期  yyyy-MM-dd
     * @param type YEAR/MONTH/DAY
     * @param n 月数
     * @return 日期格式
     * @throws ParseException
     */
    public final static Date addDateStrReDate(String date,String type,int n) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cd = Calendar.getInstance();
        cd.setTime(sdf.parse(date));
        if(type.equals("YEAR")){
            cd.add(Calendar.YEAR, n);//增加一年
        }else if(type.equals("MONTH")){
            cd.add(Calendar.MONTH, n);//增加一月
        }else if(type.equals("DAY")){
            cd.add(Calendar.DATE, n);//增加一日
        }else{
            cd.add(Calendar.MONTH, n);//增加一月
        }
        return cd.getTime();
    }



    /**
     * @param date1 需要比较的时间 不能为空(null),需要正确的日期格式
     * @param date2 被比较的时间  为空(null)则为当前时间
     * @param stype 返回值类型   0为多少天，1为多少个月，2为多少年
     * @return
     */
    public static int compareDate(String date1,String date2,int stype){
        int n = 0;
        String[] u = {"天","月","年"};
        String formatStyle = stype==1?"yyyy-MM":"yyyy-MM-dd";
        date2 = date2==null?getCurrentDate():date2;
        DateFormat df = new SimpleDateFormat(formatStyle);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(date1));
            c2.setTime(df.parse(date2));
        } catch (Exception e3) {
            System.out.println("wrong occured");
        }
        //List list = new ArrayList();
        while (!c1.after(c2)) {                     // 循环对比，直到相等，n 就是所要的结果
            //list.add(df.format(c1.getTime()));    // 这里可以把间隔的日期存到数组中 打印出来
            n++;
            if(stype==1){
                c1.add(Calendar.MONTH, 1);          // 比较月份，月份+1
            }
            else{
                c1.add(Calendar.DATE, 1);           // 比较天数，日期+1
            }
        }
        n = n-1;
        if(stype==2){
            n = (int)n/365;
        }
        System.out.println(date1+" -- "+date2+" 相差多少"+u[stype]+":"+n);
        return n;
    }

    /**
     * 得到当前日期
     * @return
     */
    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        return simple.format(date);
    }

    /**
     * 获取当前时间(北京时间)
     * @return
     */
    public static Date getCurrentBJDate(){
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        return date;
    }

    public static String getLastTime(Date date){
        String result = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar todayEnd = Calendar.getInstance();
        if(date != null){
            todayEnd.setTime(date);
        }
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        result = formatter.format(todayEnd.getTime());
        return result;
    }
}
