package com.best.phonemanager.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author fanshuo
 * @since 2013-4-3
 */
@SuppressWarnings("deprecation")
public class DateUtils
{
	static SimpleDateFormat sSimpleDateFormat;
	public static String formatDate(final Date pDate)
	{
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return dateFormat.format(pDate);
	}
	
	public static String formatDate(final Date date, DateFormat dateFormat) {
		return dateFormat.format(date);
	}

	public static String formatDateL(final Date pDate)
	{
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(pDate);
	}
	
	public static String formatDateTime(Date date){
		String retStr = "";
		retStr = date.getHours() +":"+date.getMinutes();
		return retStr;
	}
	
	public static String formatDateCN(Date pDate){
		final DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		return dateFormat.format(pDate);
	}
	
	public static String formatDateDifference(final Date pDate)
	{
		//X秒前
		final long differenceTime = (System.currentTimeMillis() - pDate.getTime()) / 1000;
		//X分钟前
		final long minute = differenceTime / 60;
		//X小时前
		final long hour = minute / 60;
		//X天前
		final long day = hour / 24;

		if(differenceTime < 60){
			return "1分钟前";
		}
		else if(minute < 60){
			return minute + "分钟前";
		}
		else if(hour < 24){
			return hour + "小时前";
		}
		else {
			return day + "天前";
		}
		
		
//		if (day > 0)
//		{
//			return day + "天前";
//		}
//		else if(hour > 0)
//		{
//			return hour + "小时前";
//		}
//		else
//		{
//			return minute + "分钟前";
//		}
	}
	
	public static Date parseDate(final String pDateString)
	{
		if (sSimpleDateFormat == null)
		{
			sSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		}
		try
		{
			return sSimpleDateFormat.parse(pDateString);
		}
		catch (final ParseException e)
		{
			return new Date();
		}
	}
}