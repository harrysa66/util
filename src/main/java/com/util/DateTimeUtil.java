package com.util;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

/**
 * @title DateTimeUtil
 * @author 
 * 
 */

public class DateTimeUtil {
	private final static String format = "yyyy-MM-dd hh24:mi:ss";
	private final static String showFormat = "yyyy-MM-dd hh24:mi:ss";
	private String datetime;

	public DateTimeUtil() {
		datetime = DateTimeUtil.getNow();
	}

	public DateTimeUtil(String datetime) {
		if (datetime == null || datetime.length() < 14)
			datetime = DateTimeUtil.getNow();
		else
			setDateTime(datetime);
	}

	public void setDateTime(String datetime) {
		this.datetime = datetime;
	}

	public String getDateTime() {
		return this.datetime;
	}

	public static String getCurMonth() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return String.valueOf(cal.get(Calendar.MONTH) + 1);
	}

	public String getAfterMonth(int month) {
		int i_year = Integer.parseInt(datetime.substring(0, 4));
		int i_month = Integer.parseInt(datetime.substring(4, 6));
		i_month += month;
		if (i_month < 0) {
			while (i_month < 0) {
				i_month += 12;
				i_year--;
			}
		}
		if ((i_month / 12) > 0)
			i_year += (i_month / 12);
		i_month = (i_month % 12);
		String s_month = "0" + i_month;
		s_month = s_month.substring(s_month.length() - 2, s_month.length());
		return String.valueOf(i_year) + s_month
				+ datetime.substring(6, datetime.length());
	}

	public String getAfterDay(int day) {
		int i_year = Integer.parseInt(datetime.substring(0, 4));
		int i_month = Integer.parseInt(datetime.substring(4, 6));
		int i_day = Integer.parseInt(datetime.substring(6, 8));
		Calendar c = Calendar.getInstance();
		c.set(i_year, (i_month - 1), i_day);
		c.add(Calendar.DATE, day);

		String s_year = String.valueOf(c.get(Calendar.YEAR));
		String s_month = "0" + String.valueOf((c.get(Calendar.MONTH) + 1));
		String s_day = "0" + String.valueOf(c.get(Calendar.DATE));
		s_month = s_month.substring(s_month.length() - 2, s_month.length());
		s_day = s_day.substring(s_day.length() - 2, s_day.length());
		return s_year + s_month + s_day
				+ datetime.substring(8, datetime.length());
	}

	public static Date toDateTime(String date) {
		try {
			SimpleDateFormat test = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date yy = test.parse(date);
			return yy;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date toDate(String date) {
		if(StringUtils.isBlank(date)) {
			return null;
		}
		try {
			SimpleDateFormat test = new SimpleDateFormat("yyyy-MM-dd");
			Date yy = test.parse(date);
			return yy;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date toDate(String date, String pattern) {
    	try {
    		//Date test = new SimpleDateFormat(("yyyy-MM-dd HH:mm:ss")).parse(date);   		    		   		
    		SimpleDateFormat test = new SimpleDateFormat(pattern);
    		Date yy = test.parse(date);
    		//System.out.print(yy.getTime());
    		//Date yy1 = new Date(yy);
    		System.out.print(test.format(yy));
			return yy;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
	
	public static Date parseDates(String s) {
		if(null == s || s.isEmpty()) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return format.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date parseDateTime(String s) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return format.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
		
	public static int getCurHour() {
		Date date = new Date(System.currentTimeMillis());
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	public static Date getCurDate(String pattern) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String dateString = format.format(date);
		try {
			Date returnDate = format.parse(dateString);
			return returnDate;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String getYearWeek(Date date) {

		DateTime in = new DateTime(date);
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMinimumIntegerDigits(2);
		String week = format.format(in.getWeekOfWeekyear());
		String year = String.valueOf(in.getWeekyear());
		;
		return year + week;
	}

	public static String getPointDate(String sdate, String edate) {
		try {
			String result = "";

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date dates = df.parse(sdate);
			Date datee = df.parse(edate);

			Calendar calendars = Calendar.getInstance();// 此时打印它获取的是系统当前时间
			calendars.setTime(dates);

			result = df.format(dates);

			for (; calendars.getTime().getTime() < datee.getTime();) {

				calendars.add(Calendar.DATE, 1);

				result += "," + df.format(calendars.getTime().getTime());

			}

			return result;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 标准java日期格式 [yyyy-MM-dd HH:mm:ss]HH表示用24小时制

	public static String getPointHH24(String sdate, int num) {
		try {
			String result = "";

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = df.parse(sdate);

			Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
			calendar.setTime(date);

			calendar.add(Calendar.DATE, num);
			date = calendar.getTime();

			String d = df.format(date);

			result = d + " 00";

			for (int i = 1; i < 24; i++) {
				if (i < 10) {
					result += "," + d + " 0" + i;
				} else {
					result += "," + d + " " + i;
				}

			}

			return result;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getPointmi10(Calendar calendar, int numhour, int addmi) {
		try {
			String result = "";

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			SimpleDateFormat dfLang = new SimpleDateFormat("yyyyMMddHHmm");

			// Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间

			String endm = df.format(calendar.getTime());
			long endmlang = Long.parseLong(dfLang.format(calendar.getTime()));

			calendar.add(Calendar.HOUR, numhour);

			String startm = df.format(calendar.getTime());
			long startmlang = Long.parseLong(dfLang.format(calendar.getTime()));

			// System.out.println(startm+"·"+endm);

			// 初始化字符串第一时间点
			startm = startm.substring(0, startm.length() - 1) + "0";
			result = startm;
			calendar.add(Calendar.MINUTE, addmi);
			startm = df.format(calendar.getTime());
			startmlang = Long.parseLong(dfLang.format(calendar.getTime()));

			for (; startmlang <= endmlang;) {
				// System.out.println(startmlang+"|"+endmlang);
				startm = startm.substring(0, startm.length() - 1) + "0";
				result += "," + startm;

				calendar.add(Calendar.MINUTE, addmi);

				startm = df.format(calendar.getTime());
				startmlang = Long.parseLong(dfLang.format(calendar.getTime()));
			}

			return result;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getDateShort(Date date) {
		try {

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			return df.format(date);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getDateBefor() {
		try {

			Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
			calendar.add(Calendar.DATE, -1); // 得到前一天
			String yestedayDate = new SimpleDateFormat("yyyy-MM-dd")
					.format(calendar.getTime());
			return yestedayDate;

			// calendar.add(Calendar.MONTH, -1); //得到前一个月
			// int year = calendar.get(Calendar.YEAR);
			// int month = calendar.get(Calendar.MONTH)+1; //输出前一月的时候要记得加1

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getDatebyint(int i) {
		try {

			Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
			calendar.add(Calendar.DATE, i); // 得到前一天
			String yestedayDate = new SimpleDateFormat("yyyy-MM-dd")
					.format(calendar.getTime());
			return yestedayDate;

			// calendar.add(Calendar.MONTH, -1); //得到前一个月
			// int year = calendar.get(Calendar.YEAR);
			// int month = calendar.get(Calendar.MONTH)+1; //输出前一月的时候要记得加1

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String parseDateToString(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static Date addDays(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, num);
		return cal.getTime();
	}

	// 当前日期 是星期几
	public static String getDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return String.valueOf(cal.get(Calendar.DAY_OF_WEEK) - 1);
	}

	public static java.sql.Date getSqlDateNow() {
		return new java.sql.Date(System.currentTimeMillis());
	}

	public static String getNow() {
		return new SimpleDateFormat(format).format(new Date());
	}

	public static String getShowFormat(String date) {
		return getShowFormat(date, false);
	}

	public static String getShowFormat(String date, boolean longFormat) {
		if (date == null || date.length() < 10)
			return new SimpleDateFormat(showFormat).format(new Date());
		StringBuffer sb = new StringBuffer(20);
		sb.append(date.substring(0, 4));
		sb.append("-");
		sb.append(date.substring(4, 6));
		sb.append("-");
		sb.append(date.substring(6, 8));
		if (longFormat) {
			sb.append(" ");
			sb.append(date.substring(8, 10));
			sb.append(":");
			sb.append(date.substring(10, 12));
		}
		return sb.toString();
	}

	public String getYear() {
		if (datetime.length() < 4)
			return "";
		return datetime.substring(0, 4);
	}

	public String getMonth() {
		if (datetime.length() < 6)
			return "";
		return datetime.substring(4, 6);
	}

	public String getDate() {
		if (datetime.length() < 8)
			return "";
		return datetime.substring(6, 8);
	}

	public String getHour() {
		if (datetime.length() < 10)
			return "";
		return datetime.substring(8, 10);
	}

	public String getMinute() {
		if (datetime.length() < 12)
			return "";
		return datetime.substring(10, 12);
	}

	public String getSecond() {
		if (datetime.length() < 14)
			return "";
		return datetime.substring(12, 14);
	}


	public static Timestamp getCurTimestamp() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return timestamp;
	}

	public static Timestamp date2Timestamp(Date date) {
		Timestamp timestamp = new Timestamp(date.getTime());
		return timestamp;
	}

	/**
	 * 获取当月第一天
	 * 
	 * @return
	 */
	public static String getThisMonthFirstDay() {
		Calendar cale = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		String firstDay = format.format(cale.getTime());
		return firstDay;
	}

	/**
	 * 获取当月最后一天
	 * 
	 * @return
	 */
	public static String getThisMonthLastDay() {
		Calendar cale = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		cale.set(Calendar.DAY_OF_MONTH,
				cale.getActualMaximum(Calendar.DAY_OF_MONTH));
		String lastday = format.format(cale.getTime());
		return lastday;
	}

	/**
	 * 获取前一个月第一天
	 * 
	 * @return
	 */
	public static String getBeforeMonthFirstDay() {
		Calendar cale = Calendar.getInstance();// 获取当前日期
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		cale.add(Calendar.MONTH, -1);
		cale.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		String firstDay = format.format(cale.getTime());
		return firstDay;
	}

	/**
	 * 获取前一个月最后一天
	 * 
	 * @return
	 */
	public static String getBeforeMonthLastDay() {
		Calendar cale = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		cale.set(Calendar.DAY_OF_MONTH, 0);// 设置为1号,当前日期既为本月第一天
		String lastDay = format.format(cale.getTime());
		return lastDay;
	}
	
	public static  int compareDay(String strdate1, String strdate2)throws  Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(strdate1==null||strdate2==null){
				return 0;
			}
			Date dt1 = df.parse(strdate1);
			Date dt2 = df.parse(strdate2);
			if (dt1.getTime() > dt2.getTime()) {
			//System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
			//System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			throw e;
		}
	}
	public  static String nextDay(String date) throws  Exception{
		if(date==null){
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(date);
//			dt1
			java.util.Calendar Cal=java.util.Calendar.getInstance(Locale.CHINA);
			Cal.setTime(dt1);
			Cal.add(Calendar.DAY_OF_YEAR, 1);
			dt1=Cal.getTime();
			return df.format(dt1);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static int div(Date beginDate, Date endDate) {
		return (int)((endDate.getTime()-beginDate.getTime())/(1000*3600*24));
	}
	
	public static void main(String[] args) {
		System.out.println(div(DateTimeUtil.toDate("2015-06-12"), DateTimeUtil.toDate("2015-07-31")));
	}
	
}
