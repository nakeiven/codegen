package com.nakeiven.codegen;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.nakeiven.codegen.bean.Pair;

/**
 * 日期格式化工具类
 * 
 */
public class DateFormatUtils {
    
    public static Integer DAY_MSEC_NUM = 1000 * 60 * 60 * 24;
    
    /**
     * 周天工具
     * 
     * @author zhangyz created on 2015-6-23
     */
    public static class WdUtil {

        private java.util.Calendar cale = Calendar.getInstance();
        private Date startDate;

        public WdUtil(Date startDate) {
            super();
            cale.setFirstDayOfWeek(Calendar.MONDAY);//MONDAY周一值是2      
            this.startDate = startDate;
            cale.setTime(startDate);
        }
        
        /**
         * 获取指定日期是第几天，周一是1，周日是7
         * @return
         * @author zhangyz created on 2015-6-23
         */
        public int getCurWeekDay() {   
            int nowDayOfWeek = cale.get(Calendar.DAY_OF_WEEK) - 1;//本周第几天,MONDAY是2，我们认为是1
            if (nowDayOfWeek == 0)
                nowDayOfWeek = 7;//相当于周日
            return nowDayOfWeek;
        }
        
        /**
         * 移到下一天
         * 
         * @author zhangyz created on 2015-6-23
         */
        public Date next() {
            startDate.setTime(startDate.getTime() + DAY_MSEC_NUM);
            cale.setTime(startDate);
            return startDate;
        }
    }
    
    /**
     * 年月日结合体
     * 
     * @author zhangyz created on 2015-5-27
     */
    public static class Ymw {
        int year = 0;
        int month = 0;//年中的月，第一月是1
        int week = 0;//年中的周，第一周是1
                
        public Ymw() {
            super();
        }

        public Ymw(int year, int month, int week) {
            super();
            this.year = year;
            this.month = month;
            this.week = week;
        }

        public int getYear() {
            return year;
        }
        
        public void setYear(int year) {
            this.year = year;
        }
        
        public int getMonth() {
            return month;
        }
        
        public void setMonth(int month) {
            this.month = month;
        }
        
        public int getWeek() {
            return week;
        }
        
        public void setWeek(int week) {
            this.week = week;
        }
        
        /**
         * 一年最大周数
         * @param year
         * @return
         * @author zhangyz created on 2015-5-27
         */
        private static int getWeeks(int year) 
        {
            int week = 0;
            int days = 365;
            @SuppressWarnings("unused")
            int day = 0;
            if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) 
            {//判断是否闰年，闰年366天
                days = 366;
            }
            //得到一年所有天数然后除以7
            day = days % 7;//得到余下几天
            week = days / 7;//得到多少周
            return week;
        }
        
        /**
         * 获取本周的上一周
         * @return
         * @author zhangyz created on 2015-5-27
         */
        public Ymw getPreWeek() {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, this.year);
            calendar.set(Calendar.MONTH, this.month - 1);
            calendar.set(Calendar.WEEK_OF_YEAR, this.week);
            return preWeek(calendar.getTime());
        }
        
        /**
         * 获取本周的开始和结束一天
         * @return
         * @author zhangyz created on 2015-5-28
         */
        public Pair<String, String> getStartEndDate() {
            Pair<String, String> ret = new Pair<String, String> ();
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);//周一作为一周开始
            
            calendar.set(Calendar.YEAR, this.year);
            calendar.set(Calendar.MONTH, this.month);
            calendar.set(Calendar.WEEK_OF_YEAR, this.week);
            
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            ret.setV1(formatDate(calendar.getTime(), YYYY_MM_DD));
            
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            ret.setV2(formatDate(calendar.getTime(), YYYY_MM_DD));
            
            return ret;
        }
        
        /**
         * 获取上一周
         * @return
         * @author zhangyz created on 2015-5-27
         */
        public static Ymw preWeek(Date curDate) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(curDate);            
            calendar.setFirstDayOfWeek(Calendar.MONDAY);//周一作为一周开始
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//移到本周的第一天
            
            int preYear, preMonth, preWeek;
            int fd = calendar.get(Calendar.DAY_OF_YEAR);
            if (fd == 1) {//本年第一天
                calendar.roll(Calendar.YEAR, false);
                preYear = calendar.get(Calendar.YEAR);
                preMonth = 12;
                preWeek = getWeeks(preYear);
            }
            else {
                //直接上移一天，就到上周
                calendar.roll(Calendar.DAY_OF_YEAR, false);
                preYear = calendar.get(Calendar.YEAR);
                preMonth = calendar.get(Calendar.MONTH) + 1;
                preWeek = calendar.get(Calendar.WEEK_OF_YEAR);
            }
            
            Ymw pre = new Ymw(preYear, preMonth, preWeek);            
            return pre;
        }
    }
    
	public static final String MMDDYYYY = "MM/dd/yyyy";
	public static final String YMD = "yyyyMMdd";
	public static final String YYYYMMDD = "yyyy/MM/dd";
	public static final String MM_DD_YYYY = "MM-dd-yyyy";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmssSSS";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String HH_MM_SS = "HH:mm:ss";
	public static final String HH_MM = " HH:mm";
	public static final String MM_DD_CHINA = "MM月dd日";
	public static final String MM_DD_HH_MMCHINA = "MM月dd日 HH:mm";
	public static final String YYYY_MM_DD_HH_MMCHINA = "YYYY年MM月dd日 HH:mm";
	public static final String YYMMDDHHMMSS = "yyMMddHHmmss";
	public static final Integer SHORT = 3;
	/**
	 * 用输入的模板参数，格式化输入的日期参数，并返回格式化后的字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        return sdf.format(date);
    }

	/**
	 * 用输入的模板参数，格式化当前日期
	 * 
	 * @param pattern
	 * @return
	 */
	public static String formatCurrentDate(String pattern) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	/**
	 * 用输入的模板参数，格式化前一天日期
	 * 
	 * @param pattern
	 * @return
	 */
	public static String formatYestaryDate(String pattern) {
		return formatNumberDate(pattern, 1);
	}
	
	/**
	 * 格式化当前时间减去number天数的时间
	 * @param pattern 时间格式模板
 	 * @param number 天数 如：0=今天， 1=昨天， 2=前天，3=今天减去3天
	 * @return
	 * @author wdb  2014-4-21 上午11:09:48
	 */
	public static String formatNumberDate(String pattern,int number) {
		Date d = new Date();
		Calendar calendar = new GregorianCalendar(); 
	    calendar.setTime(d); 
	    calendar.add(Calendar.DATE,-number);//把日期往后增加一天.整数往后推,负数往前移动 
	    Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 指定某一天的前一天, 解析错误默认为昨天
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatNextDate(String date, String pattern){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		try {
			Date date1 = simpleDateFormat.parse(date);
			Date date2 = new Date(date1.getTime() - 24 * 60 * 60 * 1000L);
			return simpleDateFormat.format(date2);
		} catch (ParseException e) {
			return formatYestaryDate(pattern);
		}
	}
	
	/**
	 * 格式化date减去number天数的时间
	 * 
	 * @param date 时间
	 * @param pattern 时间格式末班
	 * @param number 天数 如：0=今天， 1=昨天， 2=前天，3=今天减去3天
	 * @return
	 * @Author:zhanggd created on 2015-3-5
	 */
	public static String formatNumberDate(String dateStr, String pattern,int number) {
        Date d = convertStringToDate(dateStr, pattern);
        Calendar calendar=new GregorianCalendar(); 
        calendar.setTime(d); 
        calendar.add(Calendar.DATE,-number);//把日期往后增加一天.整数往后推,负数往前移动 
        Date date=calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
	
	/**
	 * 用输入的模板参数，格式化时间为当天时间
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static Date formatTimeToCurrentDate(String time,String pattern) {
		Date date = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat(pattern);
		try {
			return sdf2.parse(sdf1.format(date)+" "+time);
		} catch (ParseException e) {
			return new Date();
		}
	}
	/**
	 * 指定模板参数，格式化当前月第一天
	 * 
	 * @param pattern
	 * @return
	 */
	public static String formatFirstDayOfCurrentMonth(String pattern) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(calendar.getTime());
	}
	/**
	 * 指定模板参数，格式化当前月最后一天
	 * @param pattern
	 * @return
	 */
	public static String formatLastDayOfCurrentMonth(String pattern) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);  
	    calendar.set(Calendar.DAY_OF_MONTH, 0);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(calendar.getTime());
	}
	/**
	 * 在指定的日期增加或减少分钟
	 * 
	 * @param date
	 * @param minutes
	 * @param pattern
	 * @return
	 */
	public static String addMinutes(Date date, int minutes, String pattern) {
		Date newDate = DateUtils.addMinutes(date, minutes);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(newDate);
	}
	/**
	 * 在指定的日期增加或减少几天，并返回格式化后的日期
	 * 
	 * @param date
	 * @param days
	 * @param pattern
	 * @return
	 */
	public static String addDays(Date date, int days, String pattern) {
		Date newDate = DateUtils.addDays(date, days);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(newDate);
	}

	/**
	 * 在指定的日期增加或减少几月，并返回格式化后的日期
	 * 
	 * @param date
	 * @param months
	 * @param pattern
	 * @return
	 */
	public static String addMonths(Date date, int months, String pattern) {
		Date newDate = DateUtils.addMonths(date, months);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(newDate);
	}

	/**
	 * 在指定的日期增加或减少几年，并返回格式化后的日期
	 * 
	 * @param date
	 * @param years
	 * @param pattern
	 * @return
	 */
	public static String addYears(Date date, int years, String pattern) {
		Date newDate = DateUtils.addYears(date, years);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(newDate);
	}

	/**
	 * 将string对象转换成date对象
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date convertStringToDate(String date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			return new Date();
		}
	}

	/**
	 * 以一天为单位，计算两个date对象(start - end)相差多少天
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long computeByDays(Date start, Date end) {
		Date date1 = start;
		Date date2 = end;
		if (date1 == null) {
			date1 = new Date();
		}
		if (date2 == null) {
			date2 = new Date();
		}
		return Math.abs((date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000));
	}
	
	/**
	 * 与今天凌晨0点比较，相差的分钟数
	 * @return result<0:早于今天凌晨；result>0:晚于今天凌晨
	 */
	public static long computeTodayZeroClockByMinute(Date date) {
		String cDateStr = formatCurrentDate(YYYY_MM_DD);
		Date today = convertStringToDate(cDateStr+" "+ "00:00:00",YYYY_MM_DD_HH_MM_SS);
		return (date.getTime()-today.getTime()) / (60 * 1000);
	}
	
	/**
	 * 以分钟为单位 比较两个时间相差多少分钟
	 * @return result<0:早于昨天凌晨；result>0:晚于昨天凌晨
	 */
	public static long computeByYestaryMinute(Date date) {
		String cDateStr = formatYestaryDate(YYYY_MM_DD);
		Date yestaryDate = convertStringToDate(cDateStr+" "+"00:00:00",YYYY_MM_DD_HH_MM_SS);
		return (date.getTime()-yestaryDate.getTime()) / (60 * 1000);
	}
	/**
	 * 时间跟过去时间的凌晨相差的分钟数
	 * @param date 需要比较的时间
	 * @param number 往前第几天，如：0=跟今天凌晨比较，1=跟昨天凌晨比较，2=跟前天凌晨比较
	 * @return
	 * @author wdb  2014-4-21 上午11:04:34
	 */
	public static long compareToZeroClockByMinute(Date date,int number) {
		String cDateStr = formatNumberDate(YYYY_MM_DD, number);
		Date dayDate = convertStringToDate(cDateStr+" "+"00:00:00",YYYY_MM_DD_HH_MM_SS);
		return (date.getTime()-dayDate.getTime()) / (60 * 1000);
	}
	/**
	 * 以分钟为单位，比较两个日期相隔多少分钟数
	 * 
	 * @param start
	 * @param end
	 * @return 
	 * 		result<0:start早于end(result)分钟;
	 * 		result>0:start晚于end(result)分钟
	 */
	public static long computeByMinute(Date start,Date end) {
		return (start.getTime()-end.getTime()) / (60 * 1000);
	}
	/**
	 * 以月份为单位 比较两个日期相隔多少个月
	 * @param start
	 * @param end
	 * @return
	 */
	public static long computeByMonths(Date start, Date end){
		long result = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(start);
		c2.setTime(end);
		result = c2.get(Calendar.MONDAY) - c1.get(Calendar.MONTH);
		return Math.abs(result);
	}
	/**
	 * 获得一年中每个月份的日期区间
	 * 
	 * @return
	 */
	public static Map<Integer, String> getMonthMap() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "01/01:01/31");
		map.put(2, "02/01:02/28");
		map.put(3, "03/01:03/31");
		map.put(4, "04/01:04/30");
		map.put(5, "05/01:05/31");
		map.put(6, "06/01:06/30");
		map.put(7, "07/01:07/31");
		map.put(8, "08/01:08/31");
		map.put(9, "09/01:09/30");
		map.put(10, "10/01:10/31");
		map.put(11, "11/01:11/30");
		map.put(12, "12/01:12/31");
		return map;
	}

	/**
	 * 获取月份标签
	 * 
	 * @return
	 */
	public static Map<Integer, String> getMonthLabel() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "一月");
		map.put(2, "二月");
		map.put(3, "三月");
		map.put(4, "四月");
		map.put(5, "五月");
		map.put(6, "六月");
		map.put(7, "七月");
		map.put(8, "八月");
		map.put(9, "九月");
		map.put(10, "十月");
		map.put(11, "十一月");
		map.put(12, "十二月");
		return map;
	}

	/**
	 * 检查时间是否有效
	 * 
	 * @param time
	 * @param durInHours
	 * @return
	 */
	public static boolean checkValidity(long time, long durInHours) {
		if (durInHours <= 0)
			return false;
		long now = new Date().getTime();
		long interv = (now - time) / (1000 * 60 * 60);
		return durInHours >= interv;
	}
	/**
	 * 格式化时间戳
	 * 
	 * @param timestamp
	 * @return
	 */
	public static Date formatTimeStamp(String timestamp){
		Long timestampL = Long.valueOf(timestamp);
		return new Date(timestampL * 1000);
	}

	/**
	 *获取日期所在周的星期一的日期
     *     若date不为空，则取date所在周星期一的日期；为空则取当前日期所在周的星期一的日期
     *     
     * @param date     
	 * @return Date
	 */
	public static Date genCurrentMonday(Date date) {
	    Calendar cal =Calendar.getInstance();
	    if (date != null)
            cal.setTime(date);
	    
	    int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
	    if (day_of_week == 0)
	        day_of_week = 7;
	    
	    cal.add(Calendar.DATE, -day_of_week + 1);
        //cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
        return cal.getTime();
	}
	
	/**
	 * 获取当前日期的星期一的日期
	 * 
	 * @return String 2015-03-09 00:00:00
	 */
	public static String genCurrentMondayStr(Date date) {
        return formatDate(genCurrentMonday(date), YYYY_MM_DD) + " 00:00:00";
    }
	
	/**
	 * 获取日期的上一个星期一的日期
	 *     若date不为空，则取date的上一个星期一的日期；为空则取当前日期的上一个星期一的日期
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date genPreviousMonday(Date date) {
        Calendar cal =Calendar.getInstance();
        if (date != null)
            cal.setTime(date);
        
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        
        cal.add(Calendar.DATE, -(day_of_week + 7) + 1);
        return cal.getTime();
    }
	
	/**
	 * 获取当前日期的上一个星期一的日期
	 * 
	 * @return String 2015-03-01 00:00:00
	 */
	public static String genPreviousMondayStr(Date date) {
        return formatDate(genPreviousMonday(date), YYYY_MM_DD) + " 00:00:00";
    }
	
	 /**
	  * 获得日期的下一个星期一的日期 
	  *
	  * @param date
	  * @return
	  */
	public static Date genNextMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		do{
			cal.add(Calendar.DATE, 1);
		}while(cal.get(Calendar.DAY_OF_WEEK)!=2);
		return cal.getTime();
	}
	/**
	 * 以周为单位，计算两个日期相隔的周数
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long computeByWeeks(Date start, Date end){
		Date nextM_start = genNextMonday(start);//开始日期的下一个礼拜一日期
		Date nextM_end = genNextMonday(end);//结束日期的下一个礼拜一日期
		long day = computeByDays(nextM_end,nextM_start);
		long week = day/7;
		return week;
	}

    /**
     * 格式化时间
     * 格式规则：
     * 1、10分钟之内：刚刚  
     * 2、10m<时间<60m：1小时前
     * 3、今天、昨天、这周、这月、
     * 4、超过这个月的显示年月日时分
     * @param msgList
     * @return
     */
	public static String setSpecialFormat(Date date){
        if (date == null)
            return null;
        long tmin = DateFormatUtils.computeTodayZeroClockByMinute(date);// 跟今天凌晨0点比较
        long ymin = DateFormatUtils.computeByYestaryMinute(date);// 跟昨天凌晨0点比较
        long qmin = DateFormatUtils.compareToZeroClockByMinute(date, 2);// 跟前天凌晨0点比较

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        if (tmin >= 0) {// 今天
            return "今天" + sdf.format(date);
        }
        else if (ymin >= 0) {// 昨天
            return "昨天" + sdf.format(date);
        }
        else {// 昨天以前(包括前天，前天以前)
            if (qmin >= 0) {
                return "前天" + sdf.format(date);
            }
            return DateFormat.getDateInstance(DateFormat.SHORT).format(date)
                    + DateFormatUtils.formatDate(date, DateFormatUtils.HH_MM);
        }
	}
	
	/**
	 * 返回11位的日期(12月20日转换为在这一年中的第几天)
	 * @param date
	 * @return 
	 * @Author:zhanggd created on 2014-11-20
	 */
	public static String reduceStrDate() {
	    String dateStr = formatCurrentDate(YYMMDDHHMMSS);
        Calendar caleTemp = Calendar.getInstance();
        caleTemp.setTime(new Date());
        int year = caleTemp.get(Calendar.YEAR);
        String ye = String.valueOf(year).substring(2);
        int day = caleTemp.get(Calendar.DAY_OF_YEAR);
        String dayStr = formatToZeroBefore(day, 3); 
        String ret = ye + dayStr + dateStr.substring(6);
        return ret;
    }
	
	public static final String formatToZeroBefore(int num, int maxbit) {
        StringBuffer buf = new StringBuffer(maxbit);
        String strNum = "" + num;
        if (strNum.length() < maxbit) {
            for (int i = 0; i < (maxbit - strNum.length()); i++)
                buf.append('0');
            buf.append(strNum);
        }
        else
            buf.append(strNum.substring(strNum.length() - maxbit, strNum.length()));
        return new String(buf);
    }
	
	/**
     * 某个日期上个月的开始时间
     *  取date所在日期上个月的开始时间；否则，取上个月的开始时间
     * 
     * @param date
	 * @return Date 如2015-02-01 00:00:00
	 */
	public static Date genLastMonthStartTime(Date date) {
	    String startTime = genLastMonthStartTimeStr(new Date());
        return convertStringToDate (startTime, YYYY_MM_DD_HH_MM_SS);  
    } 
	
	/**
     * 某个日期上个月的开始时间
     *  取date所在日期上个月的开始时间；否则，取当前时间上个月的开始时间
     * 
     * @param date
     * @return String 如2015-02-01 00:00:00
     */
	public static String genLastMonthStartTimeStr(Date date) {
	    Calendar cal = Calendar.getInstance();  
	    if (date != null)
	        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);  
        int month = cal.get(Calendar.MONTH) + 1;  
        cal.set(Calendar.DAY_OF_MONTH, 1);  
        cal.add(Calendar.DAY_OF_MONTH, -1);  
        String months = "";  
        if (month > 1) {  
            month--;  
        } else {  
            year--;  
            month = 12;  
        }  
        if (!(String.valueOf(month).length() > 1)) {  
            months = "0" + month;  
        } else {  
            months = String.valueOf(month);  
        }  
        return year + "-" + months + "-01 00:00:00";  
	}
        
	/**
	 * 某个日期上个月的结束时间
     *    取date所在日期上个月的结束时间；否则，取上个月的结束时间
	 *    如2015-02-28 23:59:59
	 *    
	 * @param  date
	 * @return Date
	 */
	public static Date genLastMonthEndTime(Date date) {
	    String lastDay = genLastMonthEndTimeStr(date);
        return convertStringToDate (lastDay, YYYY_MM_DD_HH_MM_SS);  
	}
	
    /**
     * 某个日期上个月的结束时间
     *    取date所在日期上个月的结束时间；否则，取当前时间上个月的结束时间
     *    如2015-02-28 23:59:59
     *    
     * @param  date
     * @return String
     */
    public static String genLastMonthEndTimeStr(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null)
            cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String months = "";
        String days = "";
        if (month > 1) 
            month--;
        else {
            year--;
            month = 12;
        }
        if (!(String.valueOf(month).length() > 1)) 
            months = "0" + month;
        else
            months = String.valueOf(month);
        if (!(String.valueOf(day).length() > 1)) 
            days = "0" + day;
        else 
            days = String.valueOf(day);
        return year + "-" + months + "-" + days + " 23:59:59";
    }
    
	public static void main(String args[]){

        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);//周一作为一周开始
        calendar.set(Calendar.YEAR, 2014);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.WEEK_OF_YEAR, 53);  
        System.out.println(formatDate(calendar.getTime(), YYYY_MM_DD));
        
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date start = calendar.getTime();
        System.out.println(formatDate(start, YYYY_MM_DD));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Date end = calendar.getTime();
        System.out.println(formatDate(end, YYYY_MM_DD));
        
	    Date date = convertStringToDate("2015-03-01 09:31:56", YYYY_MM_DD_HH_MM_SS);
        System.out.println(formatDate(genCurrentMonday(date), YYYY_MM_DD));
        System.out.println(formatDate(genPreviousMonday(date), YYYY_MM_DD));
        Date date1 = convertStringToDate("2015-03-02 09:31:56", YYYY_MM_DD_HH_MM_SS);
        System.out.println(formatDate(genCurrentMonday(date1), YYYY_MM_DD));
        System.out.println(formatDate(genPreviousMonday(date1), YYYY_MM_DD));
        Date date2 = convertStringToDate("2015-03-03 09:31:56", YYYY_MM_DD_HH_MM_SS);
        System.out.println(formatDate(genCurrentMonday(date2), YYYY_MM_DD));
        System.out.println(formatDate(genPreviousMonday(date2), YYYY_MM_DD));

        
        Date date3 = convertStringToDate("2015-03-06 09:31:56", YYYY_MM_DD_HH_MM_SS);
        System.out.println(formatDate(genCurrentMonday(date3), YYYY_MM_DD));
        System.out.println(formatDate(genPreviousMonday(date3), YYYY_MM_DD));
        System.out.println("=================");
        /*
        Date date1 = convertStringToDate("2015-03-02 09:31:56", YYYY_MM_DD_HH_MM_SS);
        System.out.println(formatDate(genCurrentMonday(date1), YYYY_MM_DD));
        System.out.println(formatDate(genPreviousMonday(date1), YYYY_MM_DD));*/
        
        System.out.println(getPreYearAndWeekNumber(date));
        System.out.println(getPreYearAndWeekNumber(date2));
        Date date6 = convertStringToDate("2015-01-01 00:00:00", YYYY_MM_DD_HH_MM_SS);
        System.out.println(getPreYearAndWeekNumber(date6));
        
        Date date7 = convertStringToDate("2015-01-05 00:00:00", YYYY_MM_DD_HH_MM_SS);
        System.out.println(getPreYearAndWeekNumber(date7));
        Pair<Integer, Integer> pair = getPreYearAndWeekNumber(date7);
        System.out.println(pair.getV1() + "--" + pair.getV2());
        
        Date date8 = convertStringToDate("2014-12-29 00:00:00", YYYY_MM_DD_HH_MM_SS);
        Pair<Integer, Integer> pair2 = getPreYearAndWeekNumber(date8);
        System.out.println(pair2.getV1() + "--" + pair2.getV2());
        String str = "2015-01-05 00:00:00";
        System.out.println(str.substring(0, 4));
        System.out.println(str.substring(5, 7));
        
        Date date9 = convertStringToDate("2015-01-05", YYYY_MM_DD);
        System.out.println(date9);
        
	}
	
	public static Pair<Integer, Integer> getPreYearAndWeekNumber(Date date){ 
        Pair<Integer, Integer> ret = new Pair<Integer, Integer>();
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        ret.setFirst(new Integer(year));
        ret.setSecond(new Integer(week));
        return ret;
    }

	/**
	 * 获取date所在年的周次
	 * 
	 * @param date
	 * @return pair v1:年份 v2:周次
	 */
	public static Pair<Integer, Integer> getYearAndWeekNumber(Date date){ 
	    Pair<Integer, Integer> ret = new Pair<Integer, Integer>();
	    Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
       
        /*
         * 周次跨年问题解决方法：
         *  根据7天前的周数来做一下对比，如果7天前的周数小于当日周数，则表示为正常周数和 正常年份，如果7天前的周数大于当日周数，
         *  表示当日在年尾，并且当日周数被计算在下一年，此时要在得到的年份是+1
         */
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        int preWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        if (week < preWeek)
            year += 1;
        
        ret.setFirst(new Integer(year));
        ret.setSecond(new Integer(week));
        return ret;
	}
	
    /**
     * 根据字符串格式的日期获取年
     * 
     * @param aggDate
     * @return
     * @Author:zhanggd created on 2015-3-4
     */
    public static int getYearByStrDate(String aggDate) {
        Date date = convertStringToDate(aggDate, YYYY_MM_DD);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return year;
    }
	
    /**
     * 根据字符串格式的日期获取月
     * 
     * @param aggDate
     * @return
     * @Author:zhanggd created on 2015-3-4
     */
    public static int getMonthByStrDate(String aggDate) {
        Date date = convertStringToDate(aggDate, YYYY_MM_DD);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return month + 1;
    }
    
    /**
     * 根据字符串格式的日期获取周
     * 
     * @param aggDate
     * @return
     * @Author:zhanggd created on 2015-3-4
     */
    public static int getWeekOfYearByStrDate(String aggDate) {
        Date date = convertStringToDate(aggDate, YYYY_MM_DD);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
        return weekOfYear;
    } 
    
    /**
     * 把指定的日期变成没有时分秒，以便于数据库中的date类型匹配，否则可能不行
     * @param sendDate
     * @return
     * @author zhangyz created on 2015-6-12
     */
    public static Date clearNoTime(Date sendDate) {
        java.util.Calendar calc = Calendar.getInstance();
        calc.setTime(sendDate);
        calc.set(Calendar.HOUR_OF_DAY, 0);
        calc.set(Calendar.MINUTE, 0);
        calc.set(Calendar.SECOND, 0);
        calc.set(Calendar.MILLISECOND, 0);      
        return calc.getTime();
    }
}
