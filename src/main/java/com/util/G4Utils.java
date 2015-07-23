package com.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * 常见的辅助类
 * @author   Li,Bingwei
 * @date  Aug 3, 20125:17:47 PM
 * 	adwoadmin
 *  G4Utils
 *
 */
public class G4Utils {
	
	private Log log = LogFactory.getLog(G4Utils.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	
	/**
	 * 判断对象是否为NotEmpty(!null或元素>0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 * 
	 * @param pObj
	 *            待检查对象
	 * @return boolean 返回的布尔值
	 */
	public static boolean isNotEmpty(Object pObj) {
		if (pObj == null)
			return false;
		if (pObj == "")
			return false;
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return false;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return false;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return false;
			}
		}else if(pObj.equals(" ")){
            return false;
        }
		return true;
	}
	
	/**
     * 判断对象是否Empty(null或元素为0)<br>
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     * 
     * @param pObj
     *            待检查对象
     * @return boolean 返回的布尔值
     */
    public static boolean isEmpty(Object pObj) {
        if (pObj == null)
            return true;
        if (pObj == "")
            return true;
        if (pObj instanceof String) {
            if (((String) pObj).length() == 0) {
                return true;
            }
        } else if (pObj instanceof Collection) {
            if (((Collection) pObj).size() == 0) {
                return true;
            }
        } else if (pObj instanceof Map) {
            if (((Map) pObj).size() == 0) {
                return true;
            }
        }
        return false;
    }
    
    
    public static String[] getWeekStartAndEnd(String nowDateStr) {  
            String[] a = null;  
            try {  
                Date nowDate = sdf.parse(nowDateStr);  
                  
                Calendar nowCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));  
//              nowCalendar.setFirstDayOfWeek(Calendar.MONDAY);  
                nowCalendar.setTime(nowDate);  
                  
                //将开始日历和结束日历都初始化为当前选择日期  
                Calendar startCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));  
//              startCalendar.setFirstDayOfWeek(Calendar.MONDAY);  
                startCalendar.setTime(nowDate);  
                  
                Calendar endCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));  
//              endCalendar.setFirstDayOfWeek(Calendar.MONDAY);  
                endCalendar.setTime(nowDate);  
                  
                int sourceDayOfWeek = nowCalendar.get(Calendar.DAY_OF_WEEK);  
                if(sourceDayOfWeek == Calendar.MONDAY) {//星期一(开始日历不变，结束日历加6天)  
                      
                    endCalendar.add(Calendar.DAY_OF_MONTH, 6);  
                      
                } else if(sourceDayOfWeek == Calendar.TUESDAY) {//星期二(开始日历减1天，结束日历加5天)  
                      
                    startCalendar.add(Calendar.DAY_OF_MONTH, -1);  
                    endCalendar.add(Calendar.DAY_OF_MONTH, 5);  
                      
                } else if(sourceDayOfWeek == Calendar.WEDNESDAY) {//星期三(开始日历减2天，结束日历加4天)  
                      
                    startCalendar.add(Calendar.DAY_OF_MONTH, -2);  
                    endCalendar.add(Calendar.DAY_OF_MONTH, 4);  
                      
                } else if(sourceDayOfWeek == Calendar.THURSDAY) {//星期四(开始日历减3天，结束日历加3天)  
                      
                    startCalendar.add(Calendar.DAY_OF_MONTH, -3);  
                    endCalendar.add(Calendar.DAY_OF_MONTH, 3);  
                      
                } else if(sourceDayOfWeek == Calendar.FRIDAY) {//星期五(开始日历减4天，结束日历加2天)  
                      
                    startCalendar.add(Calendar.DAY_OF_MONTH, -4);  
                    endCalendar.add(Calendar.DAY_OF_MONTH, 2);  
                      
                } else if(sourceDayOfWeek == Calendar.SATURDAY) {//星期六(开始日历减5天,结束日历加1天)  
                      
                    startCalendar.add(Calendar.DAY_OF_MONTH, -5);  
                    endCalendar.add(Calendar.DAY_OF_MONTH, 1);  
                      
                } else if(sourceDayOfWeek == Calendar.SUNDAY) {//星期天(开始日历减6天,结束日历不变)  
                      
                    startCalendar.add(Calendar.DAY_OF_MONTH, -6);  
                      
                } else {  
                    throw new RuntimeException("The date out of a week...");  
                }  
                  
                a = new String[2];  
                //System.out.println(startCalendar.getTime());  
                a[0] = sdf.format(startCalendar.getTime());  
                a[1] = sdf.format(endCalendar.getTime());  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
            return a;  
        }  

    
    
    
    public static void main(String[] args) {
               getWeekStartAndEnd("2012-09-14");
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
