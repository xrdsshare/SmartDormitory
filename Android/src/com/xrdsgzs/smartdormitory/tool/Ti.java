package com.xrdsgzs.smartdormitory.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TimeUtils
 * 
 * @author XRDSGZS 20160509
 */
public class Ti {

	public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat(
			"yyyy-MM-dd");

	private Ti() {
		throw new AssertionError();
	}

	/**
	 * long time to string 根据时间的long数据，获取dateFormat格式的String类型时间数据
	 * 
	 * @param timeInMillis
	 * @param dateFormat
	 * @return
	 */
	public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
		return dateFormat.format(new Date(timeInMillis));
	}

	/**
	 * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
	 * 根据时间的long数据，获取yyyy-MM-dd格式的String类型时间数据
	 * 
	 * @param timeInMillis
	 * @return
	 */
	public static String getTime(long timeInMillis) {
		return getTime(timeInMillis, DATE_FORMAT_DATE);
	}

	/**
	 * get current time in milliseconds 获取当前时间的long类型数据
	 * 
	 * @return
	 */
	public static long getCurrentTimeInLong() {
		return System.currentTimeMillis();
	}

	/**
	 * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
	 * 获取当前时间的yyyy-MM-dd HH:mm:ss格式的String类型数据
	 * 
	 * @return
	 */
	public static String getCurrentTimeInString() {
		return getTime(getCurrentTimeInLong());
	}

	/**
	 * get current time in milliseconds 根据dateFormat格式获取当前时间数据
	 * 
	 * @return
	 */
	public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
		return getTime(getCurrentTimeInLong(), dateFormat);
	}

	public static int getDayCount(String beginTime, String _endTime) {
		long  diff = 0;
		// L.i(bt);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d1 = df.parse(_endTime);
			Date d2 = df.parse(beginTime);
			diff = (d1.getTime() - d2.getTime()) / (1000 * 3600 * 24);// 这样得到的差值是微秒级别
			// L.i(diff+"");
		} catch (Exception e) {
		}
		return (int) diff;
	}

	/***
	 * 判断两时间的大小
	 * @param beginTime
	 * @param _endTime
	 * @return
	 */
	public static long getTimeCount(String beginTime, String _endTime) {
		String bt = "2016-01-01 " + beginTime;
		String et = "2016-01-01 " + _endTime;
		long  diff = 0;
		// L.i(bt);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date d1 = df.parse(et);
			Date d2 = df.parse(bt);
			diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
			// L.i(diff+"");
		} catch (Exception e) {
		}
		return diff;
	}
}
