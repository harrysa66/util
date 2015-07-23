package com.util;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberFormatUtil {
	/**
	 * 百分号形式
	 * 
	 * @param number
	 *            0.1234
	 * @return 12.34%
	 */
	public static String formatPercent(Double number) {
		if (number == null) {
			return "0.00%";
		}
		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
		format.setMinimumFractionDigits(2);// 设置小数位
		return format.format(number);
	}

	/**
	 * 千分号形式,保留两位小数，四舍五入
	 * 
	 * @param number
	 *            12345.678
	 * @return 12,345.68
	 */
	public static String formatDecimal(Double number) {
		if (number == null)
			return "0.00";
		DecimalFormat format = new DecimalFormat("##,###,###.00");
		return format.format(number);
	}

	/**
	 * 千分号分割整形
	 * 
	 * @param number
	 *            123456789
	 * @return 123,456,789
	 */
	public static String formatInteger(Integer number) {
		if (number == null) {
			return "0";
		}
		DecimalFormat format = new DecimalFormat("##,###,###");
		return format.format(number);
	}
}
