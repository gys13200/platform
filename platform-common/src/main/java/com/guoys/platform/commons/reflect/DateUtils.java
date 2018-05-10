package com.guoys.platform.commons.reflect;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 * @author wengzr
 *
 */
public class DateUtils {
	static final Map<String,Locale> DATE_FORMAT_PATTERN=new LinkedHashMap<String,Locale>();//LinkedHashMap保证有序
	static{
		DATE_FORMAT_PATTERN.put("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		DATE_FORMAT_PATTERN.put("yyyy-MM-dd HH:mm", Locale.SIMPLIFIED_CHINESE);
		DATE_FORMAT_PATTERN.put("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
		DATE_FORMAT_PATTERN.put("yyyy/MM/dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		DATE_FORMAT_PATTERN.put("yyyy/MM/dd HH:mm", Locale.SIMPLIFIED_CHINESE);
		DATE_FORMAT_PATTERN.put("yyyy/MM/dd", Locale.SIMPLIFIED_CHINESE);
		//DATE_FORMAT_PATTERN.put("yyyy-MM-dd HH:mm a", Locale.SIMPLIFIED_CHINESE);
		DATE_FORMAT_PATTERN.put("yyyyMMddHHmmss", Locale.SIMPLIFIED_CHINESE);
		DATE_FORMAT_PATTERN.put("yyyyMMddHHmm", Locale.SIMPLIFIED_CHINESE);
		DATE_FORMAT_PATTERN.put("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
		DATE_FORMAT_PATTERN.put("E MMM dd HH:mm:ss z yyyy", Locale.US);
		
	}
	/**
	 * 解析日期字符串格式,转换为java.util.Date对象<BR>
	 * 支持格式：
	 * <li>yyyy-MM-dd HH:mm:ss</li>
	 * <li>yyyy-MM-dd HH:mm</li>
	 * <li>yyyy-MM-dd</li>
	 * <li>yyyy/MM/dd HH:mm:ss</li>
	 * <li>yyyy/MM/dd HH:mm</li>
	 * <li>yyyy/MM/dd</li>
	 * <li>yyyyMMddHHmmss</li>
	 * <li>yyyyMMddHHmm</li>
	 * <li>yyyyMMdd</li>
	 * <li>E MMM dd HH:mm:ss z yyyy (Locale.US)</li>
	 * @param _dateStr 日期字符串
	 * @return
	 */
	public static Date parseDate(String _dateStr)  {
		if (_dateStr == null || _dateStr.trim().equals("")) {
			return null;
		}
		
		Date date = null;
		for(Map.Entry<String, Locale> entry:DATE_FORMAT_PATTERN.entrySet()){
			String pattern=entry.getKey();
			Locale locale=entry.getValue();
			SimpleDateFormat format = new SimpleDateFormat(pattern,locale);  
			try {
				//如果Pattern不带格式,日期也只有不带格式才可以正确使用parse解析，如yyyyMMddHHmmss
				if(isCompactFormat(pattern)){
					if(!isCompactFormat(_dateStr))
						continue;
				}
				date = format.parse(_dateStr);
				break;
			} catch (java.text.ParseException pe) {
				//ingore
			}
		}
		
		if(date==null)
			throw new IllegalArgumentException("parse date error:[" + _dateStr + "]!");
		return date;
	}
	
	private static boolean isCompactFormat(String dateStr){
		return dateStr.indexOf("-")==-1 && 
				dateStr.indexOf("\\")==-1 && 
				dateStr.indexOf("/")==-1 &&
				dateStr.indexOf(":")==-1;
	}
	
	/**
	 * 解析日期字符串格式,转换为java.util.Date对象
	 * @param dateString 日期字符串
	 * @param pattern 日期格式
	 * @param locale 国际化区域
	 * @return
	 */
	public static Date parseDate(String dateString, String pattern,Locale locale) {
		if(dateString == null || dateString.trim().equals("")) {
			return null;
		}
		Date d = null;
		SimpleDateFormat df = new SimpleDateFormat(pattern,locale);
		try {
			d = df.parse(dateString);
		} catch(Exception e) {
			throw new IllegalArgumentException("parse date error:[" + dateString + "]!");
		}
		
		return d;
	}
	
	/**
	 * 解析日期字符串格式,转换为java.util.Date对象,默认为中文支持格式
	 * @param dateString
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String dateString, String pattern) {
		return parseDate(dateString,pattern,Locale.getDefault());
	}
	
	/**
	 * 解析字符串日期，转换为java.sql.Date对象
	 * @param _dateStr 日期字符串
	 * @param _pattern 日期格式
	 * @return
	 */
	public static java.sql.Date parseSqlDate(String _dateStr,String _pattern)  {
		return toSqlDate(parseDate(_dateStr, _pattern));
	}
	
	
	/**
	 * 将java.util.Date类型转换为java.sql.Date类型
	 * @param date
	 * @return
	 */
	public static java.sql.Date toSqlDate(Date date){
		if (date == null) {
			return null;
		}
		return new java.sql.Date(date.getTime());
	}

	/**
	 * 将java.util.Date类型转换为java.sql.Timestatmp类型
	 * @param date
	 * @return
	 */
	public static Timestamp toTimestamp(Date date){
		if (date == null) {
			return null;
		}
		return new Timestamp(date.getTime());
	}
	
	/**
	 * 格式化日期对象，输出为指定格式的字符串
	 * @param date 日期对象
	 * @param pattern 格式字符串
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		if(date == null || pattern == null) {
			return new String("");
		}
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}
	
	/**
	 * 格式化日期对象，输出为指定格式的字符串(默认格式yyyy-MM-dd HH:mm:ss)
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return formatDate(date,"yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 获取当前时间戳
	 * @return
	 */
	public static Timestamp getCurrentTime(){
		return new Timestamp( System.currentTimeMillis() );
	}
	
	/**
	 * 获得以“年”的日期字符串
	 * @param i 整形数数值,0：表示取得当年日期；
	 *                    +1：表示取得下一年的日期;
	 *                     -1：表示取得前一年月的日期
	 * 以此类推..
	 * @param pattern 日期格式：yyyyMMdd/yyyy-MM-dd
	 * @return 返回日期字符串
	 */
	public static String getYear(int i,String pattern){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,i);
		return formatDate(cal.getTime(),pattern);
		
	}
	
	/**
	 * 获得以“月”的日期字符串
	 * @param i 整形数数值,0：表示取得当月日期；
	 *                    +1：表示取得下个月的日期;
	 *                     -1：表示取得前个月的日期
	 * 以此类推..
	 * @param pattern 日期格式：yyyyMMdd/yyyy-MM-dd
	 * @return 返回日期字符串 
	 */
	public static String getMonth(int i,String pattern){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH,i);
		return formatDate(cal.getTime(),pattern);
	}
	

	/**
	 * 获得以“天”的日期字符串
	 * @param i 整形数数值,0：表示取得当日日期；
	 *                    +1：表示取得下一天的日期;
	 *                     -1：表示取得前一天的日期
	 * 以此类推..
	 * @param pattern 日期格式：yyyyMMdd/yyyy-MM-dd
	 * @return 返回日期字符串 
	 */
	public static String getDay(int i,String pattern){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE,i);
		return formatDate(cal.getTime(),pattern);
	}
    
	
	 /**  
	  *比较日期
	  * @param beginDate 开始日期需要正确的日期格式 (yyyy-MM或yyyy-MM-dd) 
	  * @param endDate 结束日期  为空(null)则为当前时间  
	  * @param stype 返回值类型   0为多少天，1为多少个月，2为多少年  
	  * @return  
     */ 
    public static int compareDate(String beginDate,String endDate,int stype){  
        int n = 0;  
          
        String formatStyle = stype==1?"yyyy-MM":"yyyy-MM-dd";  
          
        endDate = endDate==null?getCurrentDate():endDate;  
          
        DateFormat df = new SimpleDateFormat(formatStyle);  
        Calendar c1 = Calendar.getInstance();  
        Calendar c2 = Calendar.getInstance();  
        try {  
            c1.setTime(df.parse(beginDate));  
            c2.setTime(df.parse(endDate));  
        } catch (Exception e3) {  
            System.out.println("wrong occured");  
        }  
        //List list = new ArrayList();  
        while (!c1.after(c2)) {                     // 循环对比，直到相等，n 就是所要的结果  
            //list.add(df.format(c1.getTime()));    // 这里可以把间隔的日期存到数组中 打印出来  
            n++;  
            if(stype==1){  
                c1.add(Calendar.MONTH, 1);          // 比较月份，月份+1  
            }  
            else{  
                c1.add(Calendar.DATE, 1);           // 比较天数，日期+1  
            }  
        }  
          
        n = n-1;  
          
        if(stype==2){  
            n = (int)n/365;  
        }     
                
        return n;  
    }  
      
    
    /**  
     * 得到当前日期  
     * @return  
     */ 
    public static String getCurrentDate() {  
        Calendar c = Calendar.getInstance();  
        Date date = c.getTime();  
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");  
        return simple.format(date);  
    }  
    
    
    public static void main(String[] args)throws Exception{
    	System.out.println(DateUtils.getCurrentDate());
    	System.out.println(DateUtils.compareDate("2015-09-27", "2015-08-31", 0));
    	
    }


}
