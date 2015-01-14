package org.mo.taskmanager.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static String getDatePeriod(String date, int model) {
		String result = null;
		switch (model) {
		case 0:
			result = date + ";" + date;
			break;
		case 1:
			result = getWeekPeriod(date);
			break;
		case 2:
			result = getMothPeriod(date);
			break;
		case 3:
			result = getYearPeriod(date);
			break;

		default:
			break;
		}

		return result;
	}

	private static String getYearPeriod(String date) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		try {

			calendar.setTime(dateFormat.parse(date));
			int year = calendar.get(Calendar.YEAR);
			calendar.clear();
			calendar.set(Calendar.YEAR, year);
			String startDate = dateFormat.format(calendar.getTime());
			calendar.roll(Calendar.DAY_OF_YEAR, -1);
			String endDate = dateFormat.format(calendar.getTime());
			String result = startDate + ";" + endDate;
			return result;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private static String getMothPeriod(String date) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(dateFormat.parse(date));
//			int year = calendar.get(Calendar.YEAR);
//			int month = calendar.get(Calendar.MONTH) + 1;
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			String startDate =  dateFormat.format(calendar.getTime());
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			String endDate = dateFormat.format(calendar.getTime());
			String result = startDate + ";" + endDate;
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static String getWeekPeriod(String date) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(dateFormat.parse(date));
			int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 2;
			calendar.add(Calendar.DATE, -day_of_week);
			String startDate = dateFormat.format(calendar.getTime());
			calendar.add(Calendar.DATE, 6);
			String endDate = dateFormat.format(calendar.getTime());
			String result = startDate + ";" + endDate;
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}
	public static String getWeekPeriods(String date) {
		  try {
			Calendar calendar = Calendar.getInstance();
			  calendar.setFirstDayOfWeek(Calendar.MONDAY);
			  calendar.setTime(dateFormat.parse(date));
			  System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));
			  
		} catch (ParseException e) {
			e.printStackTrace();
		}

		
		return null;
	}

	public static String getDateAndWeek(String date) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(dateFormat.parse(date));
			int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);// 1为锟斤拷锟斤拷锟斤拷

			String dat = dateFormat.format(calendar.getTime());
			String week="";
			switch (day_of_week) {
			case 1:
				week="星期日";
				break;
			case 2:
				week="星期一";
				break;

			case 3:
				week="星期二";
				break;

			case 4:
				week="星期三";
				break;

			case 5:
				week="星期四";
				break;

			case 6:
				week="星期五";
				break;

			case 7:
				week="星期六";
				break;

			default:
				break;
			}
			return dat+" "+week;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static String getYearWeekEndDay(int yearNum, int weekNum) {
		if (yearNum < 1900 || yearNum > 9999) {
			throw new NullPointerException("");
		}
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY); 
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		cal.setMinimalDaysInFirstWeek(7); // 锟斤拷锟斤拷每锟斤拷锟斤拷锟斤拷为7锟斤拷
		cal.set(Calendar.YEAR, yearNum);
		cal.set(Calendar.WEEK_OF_YEAR, weekNum);
		return dateFormat.format(cal.getTime());
	}

	public static String calculateEndDate(String date, int days) {
		Calendar sCalendar = Calendar.getInstance();
		try {
			sCalendar.setTime(dateFormat.parse(date));
			sCalendar.add(Calendar.DATE, days);
			return dateFormat.format(sCalendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	 public static int getWeekOfYear(String date) {
		  Calendar calendar = Calendar.getInstance();
		  try {
		   calendar.setTime(dateFormat.parse(date));
		  int week=calendar.get(Calendar.WEEK_OF_YEAR);
		   return week;
		  } catch (ParseException e) {
		   e.printStackTrace();
		  }
		  return 0;
	 }

		public static String getDateFormate(String date, boolean needYear) {
			String[] foString = date.split("-");
			String dateString;
			if (needYear) {

				dateString = foString[0] + "年" + foString[1] + "月" + foString[2]
						+ "日";
			} else {

				dateString = foString[1] + "月" + foString[2] + "日";
			}
			return dateString;
		}
}
