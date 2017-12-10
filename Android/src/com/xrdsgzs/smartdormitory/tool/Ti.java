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
	 * long time to string ����ʱ���long���ݣ���ȡdateFormat��ʽ��String����ʱ������
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
	 * ����ʱ���long���ݣ���ȡyyyy-MM-dd��ʽ��String����ʱ������
	 * 
	 * @param timeInMillis
	 * @return
	 */
	public static String getTime(long timeInMillis) {
		return getTime(timeInMillis, DATE_FORMAT_DATE);
	}

	/**
	 * get current time in milliseconds ��ȡ��ǰʱ���long��������
	 * 
	 * @return
	 */
	public static long getCurrentTimeInLong() {
		return System.currentTimeMillis();
	}

	/**
	 * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
	 * ��ȡ��ǰʱ���yyyy-MM-dd HH:mm:ss��ʽ��String��������
	 * 
	 * @return
	 */
	public static String getCurrentTimeInString() {
		return getTime(getCurrentTimeInLong());
	}

	/**
	 * get current time in milliseconds ����dateFormat��ʽ��ȡ��ǰʱ������
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
			diff = (d1.getTime() - d2.getTime()) / (1000 * 3600 * 24);// �����õ��Ĳ�ֵ��΢�뼶��
			// L.i(diff+"");
		} catch (Exception e) {
		}
		return (int) diff;
	}

	/***
	 * �ж���ʱ��Ĵ�С
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
			diff = d1.getTime() - d2.getTime();// �����õ��Ĳ�ֵ��΢�뼶��
			// L.i(diff+"");
		} catch (Exception e) {
		}
		return diff;
	}
}
