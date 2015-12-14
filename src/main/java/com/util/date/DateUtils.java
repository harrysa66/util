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
     * ������/��/��(�������ڸ�ʽ,�����ַ���)
     * @param date ���ڸ�ʽ
     * @param type YEAR/MONTH/DAY
     * @param n ����
     * @return �ַ�������  yyyy-MM-dd
     */
    public final static String addDateReStr(Date date,String type,int n){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        if(type.equals("YEAR")){
            cd.add(Calendar.YEAR, n);//����һ��
        }else if(type.equals("MONTH")){
            cd.add(Calendar.MONTH, n);//����һ��
        }else if(type.equals("DAY")){
            cd.add(Calendar.DATE, n);//����һ��
        }else{
            cd.add(Calendar.MONTH, n);//����һ��
        }
        return sdf.format(cd.getTime());
    }

    /**
     * ������/��/��(�������ڸ�ʽ,�������ڸ�ʽ)
     * @param date ���ڸ�ʽ
     * @param type YEAR/MONTH/DAY
     * @param n ����
     * @return ���ڸ�ʽ
     */
    public final static Date addDateReDate(Date date,String type,int n){
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        if(type.equals("YEAR")){
            cd.add(Calendar.YEAR, n);//����һ��
        }else if(type.equals("MONTH")){
            cd.add(Calendar.MONTH, n);//����һ��
        }else if(type.equals("DAY")){
            cd.add(Calendar.DATE, n);//����һ��
        }else{
            cd.add(Calendar.MONTH, n);//����һ��
        }
        return cd.getTime();
    }

    /**
     * ������/��/��(�����ַ���,�����ַ���)
     * @param date �ַ�������  yyyy-MM-dd
     * @param type YEAR/MONTH/DAY
     * @param n ����
     * @return �ַ�������   yyyy-MM-dd
     * @throws ParseException
     */
    public final static String addDateStrReStr(String date,String type,int n) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cd = Calendar.getInstance();
        cd.setTime(sdf.parse(date));
        if(type.equals("YEAR")){
            cd.add(Calendar.YEAR, n);//����һ��
        }else if(type.equals("MONTH")){
            cd.add(Calendar.MONTH, n);//����һ��
        }else if(type.equals("DAY")){
            cd.add(Calendar.DATE, n);//����һ��
        }else{
            cd.add(Calendar.MONTH, n);//����һ��
        }
        return sdf.format(cd.getTime());
    }

    /**
     * ������/��/��(�������ڸ�ʽ,��������)
     * @param date �ַ�������  yyyy-MM-dd
     * @param type YEAR/MONTH/DAY
     * @param n ����
     * @return ���ڸ�ʽ
     * @throws ParseException
     */
    public final static Date addDateStrReDate(String date,String type,int n) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cd = Calendar.getInstance();
        cd.setTime(sdf.parse(date));
        if(type.equals("YEAR")){
            cd.add(Calendar.YEAR, n);//����һ��
        }else if(type.equals("MONTH")){
            cd.add(Calendar.MONTH, n);//����һ��
        }else if(type.equals("DAY")){
            cd.add(Calendar.DATE, n);//����һ��
        }else{
            cd.add(Calendar.MONTH, n);//����һ��
        }
        return cd.getTime();
    }



    /**
     * @param date1 ��Ҫ�Ƚϵ�ʱ�� ����Ϊ��(null),��Ҫ��ȷ�����ڸ�ʽ
     * @param date2 ���Ƚϵ�ʱ��  Ϊ��(null)��Ϊ��ǰʱ��
     * @param stype ����ֵ����   0Ϊ�����죬1Ϊ���ٸ��£�2Ϊ������
     * @return
     */
    public static int compareDate(String date1,String date2,int stype){
        int n = 0;
        String[] u = {"��","��","��"};
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
        while (!c1.after(c2)) {                     // ѭ���Աȣ�ֱ����ȣ�n ������Ҫ�Ľ��
            //list.add(df.format(c1.getTime()));    // ������԰Ѽ�������ڴ浽������ ��ӡ����
            n++;
            if(stype==1){
                c1.add(Calendar.MONTH, 1);          // �Ƚ��·ݣ��·�+1
            }
            else{
                c1.add(Calendar.DATE, 1);           // �Ƚ�����������+1
            }
        }
        n = n-1;
        if(stype==2){
            n = (int)n/365;
        }
        System.out.println(date1+" -- "+date2+" ������"+u[stype]+":"+n);
        return n;
    }

    /**
     * �õ���ǰ����
     * @return
     */
    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        return simple.format(date);
    }

    /**
     * ��ȡ��ǰʱ��(����ʱ��)
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
