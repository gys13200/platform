package com.guoys.platform.commons.lang.type;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;

/**
 * 自定日期时间类，用于转化表示日期时间串等
 * @author yangbo
 * @version 1.0 2013-12-6
 * @since 1.6
 */
public class BDateTime extends BDate implements Comparable<BDate>{
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 7280791980952901691L;
	
	/**日期格式化对象*/
	public static SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 自定义日期时间无参构造方法。
	 */
	public BDateTime() {
		super();
	}
		
	/**
	 * 通过毫秒数构造自定义日期时间对象
	 * (以从1970年1月1日0时0分0秒到现在的毫秒数来构造日期)
	 * @param m 毫秒数
	 */
	public BDateTime(long millis) {
		super(millis);
	}

	/**
	 * 通过日期对象构造自定义日期时间对象
	 * @param date 日期对象
	 */
	public BDateTime(Date date) {
		super(date);
	}
	
	/**
	 * 通过自定义日期对象构造自定义日期时间对象
	 * @param date 日期对象
	 */
	public BDateTime(BDate date) {
		super(date.getMillis());
	}
	
	/**
	 * 通过自定义日期对象和时间对象构造自定义日期时间对象
	 * @param date 日期对象
	 */
	public BDateTime(BDate date,BTime time) {
		super(date.getMillis()+time.getMillis());
	}
	
	/**
	 * 通过默认形式日期串构造自定义日期对象
	 * @param date 默认格式日期串
	 */
	public BDateTime(String date) {
		super(date,DEFAULT_FORMAT);
	}
	
	/**
	 * 通过日期串和指定的格式化串构造自定义日期时间对象
	 * @param date 符合格式串的日期时间对象
	 * @param format 日期格式化串
	 */
	public BDateTime(String date,String format) {
		super(date,format);
	}
	
	/**
	 * 通过日期串和指定的格式化对象构造自定义日期时间对象
	 * @param date 符合格式化对象的日期时间对象
	 * @param dateFormat 格式化对象
	 */
	public BDateTime(String date,DateFormat dateFormat){
		super(date,dateFormat);
	}
	

	
	/**
	 * 用日期对象初始化对象
	 */
	protected void init(){
		
	}
	
	/**
	 * 根据对象构建自定义日期时间对象
	 * @param obj 对象
	 * @return 自定义日期时间对象
	 */
	public static BDateTime parseBDateTime(Object obj){
		if(obj == null)
			return null;
		if(obj instanceof BDate)
			return new BDateTime(((BDate)obj).getMillis());

		if(obj instanceof Date)
			return new BDateTime((Date)obj);
		
		if(obj instanceof Number){
            return new BDateTime(((Number)obj).longValue());
        }
		if(NumberUtils.isNumber(obj.toString())){
			return new BDateTime(NumberUtils.toLong(obj.toString()));
		}
		return new BDateTime(obj.toString());

	}
	
	/**
	 * 取得自定义日期时间对象中的自定义日期对象
	 * @return 自定义日期对象
	 */
	public BDate getBDate() {
		return new BDate(getMillis());
	}

	/**
	 * 取得自定义日期时间对象中的自定义时间对象
	 * @return 自定义时间对象
	 */
	public BTime getBTime() {
		return new BTime(getMillis());
	}
	

	/* (non-Javadoc)
	 * @see com.bosssoft.frame.base.lang.BDate#getDateAfter(int)
	 */
	public BDateTime getDateAfter(int days) {
		return new BDateTime(getMillis() + MILLIS_PERDAY * days);
	}
	
	/* (non-Javadoc)
	 * @see com.bosssoft.frame.base.lang.BDate#getDateBefore(int)
	 */
	public BDateTime getDateBefore(int days) {
		return getDateAfter(-days);
	}

	/**
	 * 取得两个日期时间对象相差的小时数
	 * @param begin 开始日期时间
	 * @param end 结束日期时间
	 * @return 小时数
	 */
	public static int getHoursBetween(BDateTime begin, BDateTime end) {
		return (int) (getMilisBetween(begin, end) / BTime.MILLIS_PERHOUR);
	}

	/**
	 * 取得两个日期时间对象相差的分钟数
	 * @param begin 开始日期时间
	 * @param end 结束日期时间
	 * @return 分钟数
	 */
	public static int getMinutesBetween(BDateTime begin, BDateTime end) {
		return (int) (getMilisBetween(begin, end) / BTime.MILLIS_PERMINUTE);
	}

	/**
	 * 取得两个日期时间对象相差的秒数
	 * @param begin 开始日期时间
	 * @param end 结束日期时间
	 * @return 秒数
	 */
	public static int getSecondsBetween(BDateTime begin, BDateTime end) {
		return (int) (getMilisBetween(begin,end)/BTime.MILLIS_PERSECOND);
	}

	/**
	 * 取得两个日期时间对象相差的毫秒数
	 * @param begin 开始日期时间
	 * @param end 结束日期时间
	 * @return 毫秒数
	 */
	private static long getMilisBetween(BDateTime begin, BDateTime end) {
		if (begin == null || end == null)
			return 0;
		return end.getMillis() - begin.getMillis();
	}
	
	/**
	 * 取得当前日期时间对象在指定的日期时间对象之后多少毫秒
	 * @param time 要比较的自定义日期时间
	 * @return 当前时间在给定日期时间
	 */
	public long getMillisAfter(BDateTime dateTime) {
		return getMilisBetween(dateTime,this);
	}
	
	/**
	 * 取得小时数
	 * @return 小时数
	 */
	public int getHour(){
		return get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 取得小时数字符串
	 * @return 小时数字符串
	 */
	public String getHOUR(){
		return formatNumner(getHour());
	}
	
	/**
	 * 取得分钟数
	 * @return 分钟数
	 */
	public int getMinute(){
		return get(Calendar.MINUTE);
	}
	
	/**
	 * 取得分钟数字符串
	 * @return 分钟数字符串
	 */
	public String getMINUTE(){
		return formatNumner(getMinute());
	}
	
	/**
	 * 取得秒数
	 * @return 秒数
	 */
	public int getSecond(){
		return get(Calendar.SECOND);
	}
	
	/**
	 * 取得秒数字符串
	 * @return 秒数字符串
	 */
	public String getSECOND(){
		return formatNumner(getSecond());
	}
	
	/**
	 * 取得毫秒数
	 * @return 毫秒数
	 */
	public int getMilliSecond(){
		return get(Calendar.MILLISECOND);
	}
	
	/**
	 * 取得毫秒数串
	 * @return 毫秒数串
	 */
	public String getMILLISECOND(){
		int ms = getMilliSecond();
		return Integer.toString(ms + 1000).substring(1);
	}
	
	/* 实现比较接口方法
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(BDate bdate) {
		return calendar.compareTo(bdate.calendar);
	}
	
	/**
	 * 比较日期是否相等
	 * @param 比较的对象
	 * @return 布尔值
	 */
	public boolean equals(Object o) {
		if ((o != null) && (o instanceof BDate)) {
			return this.getMillis() == ((BDate) o).getMillis();
		}
		return false;
	}
	
	/* 用默认的格式化串转化日期时间
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return toString(DEFAULT_FORMAT);
	}
}
