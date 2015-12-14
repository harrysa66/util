package com.util.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * <p>
 * <h2>SysDate���������ڴ����ݿ�ȡ��ϵͳʱ�䡣</h2>
 * </p>
 *
 * <p>
 * SysDate��������Խ�ȡ�õ�ϵͳʱ�仺������������ÿ�ζ�ȥ���ݿ�ȡ��ϵͳʱ�䣬 �����������ݿ���ʴ�����ʵ����ֻ������һ�Σ������Ч�ʡ�
 * </p>
 *
 * <p>
 * ��Oracle���ݿ�ȡ��ʱ��ʱ��ʹ��������SysConfig.getSqlDate()�������ص�SQL�� �����ڣ�<br>
 *
 * <pre>
 * select sysdate from dual;
 * </pre>
 *
 * </p>
 *
 * <p>
 * SysDate�����໹֧�����ڵ��ַ����������ַ��������ڵĸ�ʽת���������Ҫ֧��������
 * �������㣨�磺��ȡ���ڲ��֣�ʹʱ��Ϊ00:00:00�����뽫��ط��������ͨ�ù��߷����� ���뵽�ù������С�
 * </p>
 *
 */
public final class SysDate {

    private static SysDate sysDate = new SysDate();

    private static String sMaxDate = "2038-12-31";

    private static Date maxDate;

    // ���ݿ�ʱ����WEB������ʱ���
    private static long diff = 0;

    private static int dateLength = 12;

    // ���ڸ�ʽ: yyyyMMdd
    private static DateFormat dateFormatSimple;

    // ���ڸ�ʽ: yyyyMM
    private static DateFormat dateFormatYearMonth;

    // ���ڸ�ʽ
    private static DateFormat dateFormatEn;

    // ����ʱ���ʽ
    private static DateFormat dateTimeFormatEn;

    /**
     * ȥ��
     * @param object
     * @return
     */
    public static String trim(Object object) {
        if (object == null || object.equals("null")) {
            return "";
        } else {
            return object.toString();
        }
    }

    // ������ʵ����SysDate����
    private SysDate() {
    }

    /** ��ȡ�������ʱ�� */
    public static Date getMaxDate() {
        if (maxDate == null) {
            maxDate = SysDate.getDate(sMaxDate);
        }
        return maxDate;
    }

    /** ��ȡ�������ʱ�� */
    public static Date addMonth(Date date, int month) {
        GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
        cal.setTime(date);
        // cal.set (date.getYear(), date.getMonth(),);
        // cal.roll(Calendar.MONTH,-month);
        cal.roll(Calendar.MONTH, month);
        return cal.getTime();
    }

    /** ��ȡϵͳ����ʱ�� */
    public static Date getSysDate() {
        Date date = Calendar.getInstance().getTime();
        return new Timestamp(date.getTime() - diff);
    }

    // ��ָ�����ڶ����ʽ����ָ����ʽ�������ַ���
    private static String getDate(Date date, DateFormat formator) {
        return formator.format(date);
    }

    // ��ָ���������ַ�������ָ���ĸ�ʽ���������ڶ���
    private static Date getDate(String date, DateFormat formator) {
        if (date == null || date.length() <= 0) {
            return null;
        }
        Date d = null;
        try {
            d = formator.parse(date);
        } catch (Exception e) {
            try {
                if (date.length() <= dateLength) {
                    d = SysDate.getDateFormat().parse(date);
                } else {
                    d = SysDate.getDateTimeFormat().parse(date);
                }
            } catch (ParseException e1) {
                // String format1 = SysConfig.getFormatDate();
                // String format2 = SysConfig.getFormatDateTime();
                // throw new ExceptionSys("���ڸ�ʽ������ȷ��ʽӦ���ǣ�" , e);
            }
        }
        if (d != null) {
            return getTimestamp(d);
        }
        return null;
    }

    /**
     * �����������ڸ�ʽ�ַ���ת��ΪDateFormat
     *
     * @param format
     * @return
     */
    private static DateFormat getDateFormat(String format) {
        if (format == null)
            format = "YYYY-MM-DD";
        DateFormat dateType = new SimpleDateFormat(format);
        return dateType;
    }

    /**
     * �������ַ���תΪָ����������
     *
     * @param date
     * @param format
     * @return
     */
    public static Date getDate(String date, String format) {
        if (date == null)
            return null;
        DateFormat dateType = getDateFormat(format);
        return getDate(date, dateType);
    }

    /**
     * ����������ת��Ϊָ����ʽ�������ַ���
     *
     * @param date
     * @param format
     * @return
     */
    public static String getDate(Date date, String format) {
        if (date == null)
            return null;
        DateFormat dateType = getDateFormat(format);
        return getDate(date, dateType);
    }

    private static DateFormat getFormatYearMonth() {
        if (dateFormatYearMonth == null) {
            String format = "yyyyMM";
            dateFormatYearMonth = new SimpleDateFormat(format);
        }
        return dateFormatYearMonth;
    }

    /** ��ȡ��ǰϵͳ���ڣ���ת�����ַ�������ʽ��yyyyMM */
    public static String getDateYearMonth() {
        return getDate(getSysDate(), getFormatYearMonth());
    }

    /** ��ȡ��ǰϵͳ���ڣ���ת�����ַ�������ʽ��yyyyMM */
    public static Long getDateMonth() {
        return new Long(getDate(getSysDate(), "MM"));
    }

    /** ��ȡָ�����ڵ��ַ�������ʽ��yyyyMM */
    public static String getDateYearMonth(Date date) {
        return getDate(date, getFormatYearMonth());
    }

    private static DateFormat getFormatSimple() {
        if (dateFormatSimple == null) {
            String format = "yyyyMMdd";
            dateFormatSimple = new SimpleDateFormat(format);
        }
        return dateFormatSimple;
    }

    /** ��ȡ��ǰϵͳ���ڣ���ת�����ַ�������ʽ��yyyyMMdd */
    public static String getDateSimple() {
        return getDate(getSysDate(), getFormatSimple());
    }

    /** ��ȡָ�����ڵ��ַ�������ʽ��yyyyMMdd */
    public static String getDateSimple(Date date) {
        return getDate(date, getFormatSimple());
    }

    /**
     * ȡ�����ڸ�ʽ����SysConfig.getFormatDate()���ã� �磺yyyy-MM-dd
     */
    public static DateFormat getDateFormat() {
        if (dateFormatEn == null) {
            String format = "yyyy-MM-dd";
            dateFormatEn = new SimpleDateFormat(format);
        }
        return dateFormatEn;
    }

    /**
     * ȡ������ʱ���ʽ����SysConfig.getFormatDateTime()���ã� �磺yyyy-MM-dd HH:mm:ss
     */
    public static DateFormat getDateTimeFormat() {
        if (dateTimeFormatEn == null) {
            String format = "yyyy-MM-dd HH:mm:ss";
            dateTimeFormatEn = new SimpleDateFormat(format);
        }
        return dateTimeFormatEn;
    }

    /**
     * ��ȡ��ǰϵͳ���ڣ���ת�����ַ�������ʽ��SysConfig.getFormatDate()���ã� �磺yyyy-MM-dd
     */
    public static String getDate() {
        return getDate(getSysDate(), getDateFormat());
    }

    /**
     * ��ȡ��ǰϵͳ����ʱ�䣬��ת�����ַ�������ʽ��SysConfig.getFormatDateTime()���ã� �磺yyyy-MM-dd
     * HH:mm:ss
     */
    public static String getDateTime() {
        return getDate(getSysDate(), getDateTimeFormat());
    }

    /**
     * ��ָ�����ڶ���ת�����ַ�������ʽ��SysConfig.getFormatDate()���ã� �磺yyyy-MM-dd
     */
    public static String getDate(Date date) {
        return getDate(date, getDateFormat());
    }

    /**
     * ��ָ�����ڶ���ת�����ַ�������ʽ��SysConfig.getFormatDateTime()���ã� �磺yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTime(Date date) {
        return getDate(date, getDateTimeFormat());
    }

    /**
     * ���ַ���ת�������ڶ��󣬸�ʽ��SysConfig.getFormatDate()���ã� �磺yyyy-MM-dd
     */
    public static Date getDate(String date) {
        return getDate(date, getDateFormat());
    }

    /**
     * ���ַ���ת�������ڶ��󣬸�ʽ��SysConfig.getFormatDateTime()���ã� �磺yyyy-MM-dd HH:mm:ss
     */
    public static Date getDateTime(String date) {
        return getDate(date, getDateTimeFormat());
    }

    /** ȡ��ָ�����������·ݵĵ�һ�죬���δָ�����ڣ���Ĭ��Ϊ��ǰ���� */
    public static Date getMonthDateFirst(Date date) {
        Date nowDate = date;
        if (nowDate == null) {
            nowDate = SysDate.getSysDate();
            if (nowDate == null) {
                return null;
            }
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.DATE, 1);

        return calendar.getTime();
    }

    /** ȡ��ָ�����������·ݵ����һ�죬���δָ�����ڣ���Ĭ��Ϊ��ǰ���� */
    public static Date getMonthDateLast(Date date) {
        Date nowDate = date;
        if (nowDate == null) {
            nowDate = SysDate.getSysDate();
            if (nowDate == null) {
                return null;
            }
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR_OF_DAY, 23);

        int lastDay = calendar.getActualMaximum(Calendar.DATE);
        calendar.set(Calendar.DATE, lastDay);

        return calendar.getTime();
    }

    private static Date getTimestamp(Date date) {
        if (date == null) {
            return null;
        }
        if (date instanceof Timestamp) {
            return date;
        }
        return new Timestamp(date.getTime());
    }

    public static SysDate getInstance() {
        return sysDate;
    }
}
