package com.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FormatField {

        public static  String  formatString(String s){
                
                if(null != s  && !"".equals(s.trim())){
                        return s.trim();
                }else{
                        return "";
                }
        }
        
        public static   String formatEncoding(String s) throws Exception {
                if(null != s && !"".equals(s.trim())){
                        return new String( s.trim().getBytes("ISO-8859-1") , "UTF-8");
                }else{
                        return "";
                }
        }
        
        //将汉字转换为%E4%BD%A0形式
        public static String toUtf8String(String s) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c >= 0 && c <= 255) {
                    sb.append(c);
                } else {
                    byte[] b;
                    try {
                        b = String.valueOf(c).getBytes("utf-8");
                    } catch (Exception ex) {
                        System.out.println(ex);
                        b = new byte[0];
                    }
                    for (int j = 0; j < b.length; j++) {
                        int k = b[j];
                        if (k < 0)
                            k += 256;
                        sb.append("%" + Integer.toHexString(k).toUpperCase());
                    }
                }
            }
            return sb.toString();
        }
        
        //将%E4%BD%A0转换为汉字 
        public static String unescape(String s) {
            StringBuffer sbuf = new StringBuffer();
            int l = s.length();
            int ch = -1;
            int b, sumb = 0;
            for (int i = 0, more = -1; i < l; i++) {
                /* Get next byte b from URL segment s */
                switch (ch = s.charAt(i)) {
                case '%':
                    ch = s.charAt(++i);
                    int hb = (Character.isDigit((char) ch) ? ch - '0'
                            : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
                    ch = s.charAt(++i);
                    int lb = (Character.isDigit((char) ch) ? ch - '0'
                            : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
                    b = (hb << 4) | lb;
                    break;
                case '+':
                    b = ' ';
                    break;
                default:
                    b = ch;
                }
                /* Decode byte b as UTF-8, sumb collects incomplete chars */
                if ((b & 0xc0) == 0x80) { // 10xxxxxx (continuation byte)   
                    sumb = (sumb << 6) | (b & 0x3f); // Add 6 bits to sumb   
                    if (--more == 0)
                        sbuf.append((char) sumb); // Add char to sbuf   
                } else if ((b & 0x80) == 0x00) { // 0xxxxxxx (yields 7 bits)   
                    sbuf.append((char) b); // Store in sbuf   
                } else if ((b & 0xe0) == 0xc0) { // 110xxxxx (yields 5 bits)   
                    sumb = b & 0x1f;
                    more = 1; // Expect 1 more byte   
                } else if ((b & 0xf0) == 0xe0) { // 1110xxxx (yields 4 bits)   
                    sumb = b & 0x0f;
                    more = 2; // Expect 2 more bytes   
                } else if ((b & 0xf8) == 0xf0) { // 11110xxx (yields 3 bits)   
                    sumb = b & 0x07;
                    more = 3; // Expect 3 more bytes   
                } else if ((b & 0xfc) == 0xf8) { // 111110xx (yields 2 bits)   
                    sumb = b & 0x03;
                    more = 4; // Expect 4 more bytes   
                } else /*if ((b & 0xfe) == 0xfc)*/{ // 1111110x (yields 1 bit)   
                    sumb = b & 0x01;
                    more = 5; // Expect 5 more bytes   
                }
                /* We don't test if the UTF-8 encoding is well-formed */
            }
            return sbuf.toString();
        }
        
        
        
        
        public static  String[] formatStrings(String[] s){
                
                if(null != s && s.length >0 && !"".equals(s)){
                        return s;
                }else{
                        return null;
                }
        }
        
        
        public static int[] stringToInt(String[] s){
                if(null != s && s.length >0){
                     String[] ss = s[0].split(",");
                    int[] an=new int[ss.length];
                    for(int i=0;i<ss.length;i++){
                            if(null != ss[i]&& !"".equals(ss[i])){
                                if(ss[i].length()>=10){
                                    an[i]=01;
                                }else{                                    
                                    an[i]=Integer.valueOf(ss[i]);
                                }
                            }
                    }
                    return an;
                }else{
                        return null;
                }
        }
        
        public static int[] StirngsToInts(String[] s){
            if(null != s && s.length > 0){
                int[] ss = new int[s.length];
                for(int i=0;i< s.length; i++){
                    ss[i] = Integer.valueOf(s[i]);
                }
                return ss;
            }else{
                return null;
            }
        }
             
        public static String stringsToString(String[] s){
                if(null != s && s.length >0){
                    
                    String an= "";
                    for(int i=0;i<s.length;i++){
                         an +=s[i]+",";   
                    }
                    an = an.substring(0, an.length()-1);
                    return an;
                }else{
                        return null;
                }
        }
        
        public static String getCharAndNumr(int length)     
        {     
            String val = "";     
                     
            Random random = new Random();     
            for(int i = 0; i < length; i++)     
            {     
                String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字     
                         
                if("char".equalsIgnoreCase(charOrNum)) // 字符串     
                {     
                    int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母     
                    val += (char) (choice + random.nextInt(26));     
                }     
                else if("num".equalsIgnoreCase(charOrNum)) // 数字     
                {     
                    val += String.valueOf(random.nextInt(10));     
                }     
            }     
                     
            return val;     
        }   
        
        
        public static String lastDay(String date)  throws Exception{
            if(date==null){
                return null;
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date dt1 = df.parse(date);
//              dt1
                java.util.Calendar Cal=java.util.Calendar.getInstance();
                Cal.setTime(dt1);
                Cal.add(Calendar.DAY_OF_YEAR, -1);
                dt1=Cal.getTime();
                return df.format(dt1);
            } catch (Exception exception) {
                throw new Exception("nexDay时出错:"+date);
            }
        }
        
       /**
        * 根据参数找到前几天的日期
        * @param dateString  当前日期
        * @param beforeDays  提前几天
        * @return
        * @throws Exception
        */
        public static Date beforeDay(String dateString,int beforeDays) throws Exception{
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date inputDate = dateFormat.parse(dateString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(inputDate);
            
            int inputDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
            cal.set(Calendar.DAY_OF_YEAR , inputDayOfYear-beforeDays );
            
            return cal.getTime();
        }
        
        
        
        public static String advState(Integer state){
            if(state == 0){
                return "未完成";
            }else if(state == 1){
                return "待审批";
            }else if(state ==2){
                return "正常";
            }else if(state == 3){
                return "手工暂停";
            }else if(state ==4){
                return "删除";
            }else if(state == 5){
                return "未通过";
            }else if(state == 6){
                return "结束";
            }else if(state == 7){
                return "冻结";
            }else if(state == 8){
                return "余额不足";
            }else if(state == 9){
                return "未到启动时间";
            }else if(state == 10){
                return "测试";
            }else if(state == 11){
                return "待定";
            }else if(state == 14){
                return "流量预测";
            }else if(state == 15){
                return "测试通过";
            }else {
                return "";
            }
        }
        
        
        public static String getAdvAtid(int atid){
            if(atid == 2){
                return "Android";
            }else if (atid == 3) {
                return "IPhone";
            }else if (atid == 6) {
                return "IPad";
            }else if (atid == 5) {
                return "Windows Phone";
            }else {                
                return ""; 
            }
        }
        
        public static String getPayMethod(int paymetod){
            if(paymetod == 0){
                return "CPM";
            }else if (paymetod == 1) {
                return "CPC";
            }else if (paymetod == 2) {
                return "CPM";
            }else {
                return "";
            }
        }
        
        
        public static String getShowType(int _v){
            if(_v == 1){return "手机网络";}else
            if(_v == 3){return "程序下载";}else
            if(_v == 5){return "点击通话";}else
            if(_v == 9){return "地图定位";}else
            if(_v == 10){return "内容搜索";}else
            if(_v == 11){return "iTunes";}else
            if(_v == 12){return "发送短信";}else
            if(_v == 13){return "发送邮件";}else
            if(_v == 14){return "播放音频";}else
            if(_v == 15){return "播放视频";}else
            {return "";}
        }
        
        /**
         * 两个日期相差几天
         * @param strdate1
         * @param strdate2
         * @return
         */
        public static long getDays(String strdate1,String strdate2){
                long day=0;
                try {
                    if(strdate1==null||strdate2==null){
                        return 0;
                    }
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date date1 = df.parse(strdate1);
                    java.util.Date date2=df.parse(strdate2);
                    long l=date1.getTime()-date2.getTime();
                    day=l/(24*60*60*1000)+1;
                } catch (Exception e) {
                }
                return day;
                
            }
        
        
        public static List compareArr(String[] a,String[] b){
            List commonlist=new ArrayList();
            if(a.length<b.length){
                for(int i=0;i<a.length;i++){
                    if(a[i].equals(b[i]))
                        commonlist.add(a[i]);
                }
            }
            return commonlist;
        }
        
        public static List toArrayList(String[] temp) {
            List templist=new ArrayList();
            for (int i = 0; i < temp.length; i++) {
                templist.add(temp[i]);
            }
            return templist;
        }

        public static List romove(List lista,List listb){
            lista.removeAll(listb);
            return lista;
            //System.out.println(lista);
        }
}
