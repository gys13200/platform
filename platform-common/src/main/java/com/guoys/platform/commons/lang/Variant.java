package com.guoys.platform.commons.lang;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.guoys.platform.commons.lang.type.BBoolean;
import com.guoys.platform.commons.lang.type.BDate;
import com.guoys.platform.commons.lang.type.BDateTime;
import com.guoys.platform.commons.lang.type.BTime;
import org.apache.commons.lang.ObjectUtils;



/**
 * 数据变量对象，存储类型化值,用于多类型数据
 * @author yangbo
 * @version 1.0 2013-12-6
 * @since 1.6
 */
public class Variant implements Serializable, Cloneable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 1035719937225603367L;
	/**数据类型*/
	private DataType dataType = DataType.UNKNOWN;
	/**值对象*/
	private Object value = null;
	/**
	 * 无参数构造方法
	 */
	public Variant(){
		dataType = DataType.UNKNOWN;
	}
	
	/**
	 * 带类型的构造方法
	 * @param dataType 变量数据类型
	 */
	public Variant(DataType dataType){
		this.dataType = dataType;
	}

	/**
	 * 取得变量数据类型
	 * @return 数据类型
	 */
	public DataType getDataType(){
		return dataType;
	}

    /**
     * 设置变量数据类型
     * @param dataType 数据类型
     */
    public void setDataType(DataType dataType){
    	if(this.dataType == dataType)
    		return;
    	value = TypeConverter.translate(dataType,value);
    	this.dataType = dataType;
    }

    /**
     * 通过类型名称设置变量类型
     * @param dataType 变量类型名
     */
    public void setDataType(String dataType){
        setDataType(DataType.parse(dataType));
    }

    /**
     * 取得变量值对象
     * @return 变量值对象
     */
    public Object getValue(){
        return value;
    }

    /**
     * 设置变量值对象
     * @param value 变量值对象
     */
    public void setValue(Object value){
    	this.value = TypeConverter.translate(dataType,value);
    }
     
    /**
     * 取得变量对象的布尔型值
     * @return 布尔型值
     */
    public boolean getBoolean(){
        return TypeConverter.parseBoolean(value);
    }
    
    /**
     * 取得变量对象的自定义布尔型值
     * @return 自定义布尔型值
     */
    public BBoolean getBBoolean(){
        return TypeConverter.parseBBoolean(value);
    }
    
    /**
     * 取得变量对象的字节值
     * @return 字节值
     */
    public byte getByte(){
        return TypeConverter.parseByte(value);
    }
    /**
     * 取得变量对象的短整数值
     * @return 短整数值
     */
    public short getShort(){
        return TypeConverter.parseShort(value);
    }
    /**
     * 取得变量对象的整数值
     * @return 整数值
     */
    public int getInt(){
        return TypeConverter.parseInt(value);
    }
    /**
     * 取得变量对象的长整数值
     * @return 长整数值
     */
    public long getLong(){
        return TypeConverter.parseLong(value);
    }
    /**
     * 取得变量对象的浮点型值
     * @return 浮点型值
     */
    public float getFloat(){
        return TypeConverter.parseFloat(value);
    }
    /**
     * 取得变量对象的双浮点型值
     * @return 双浮点型值
     */
    public double getDouble(){
        return TypeConverter.parseDouble(value);
    }
    /**
     * 取得变量对象的小数型值
     * @return 小数型值
     */
    public BigDecimal getBigDecimal(){
        return TypeConverter.parseBigDecimal(value);
    }
    /**
     * 取得变量的字符串值
     * @return 变量的字符串值
     */
    public String getString(){
        return TypeConverter.parseString(value);
    }
    
    /**
     * 取得变量对象的java日期型值
     * @return java日期型值
     */
    public Date getDate(){
        return TypeConverter.parseDate(value);
    }
    
    /**
     * 取得变量对象的自定义日期型值
     * @return 自定义日期型值
     */
    public BDate getBDate(){
        return TypeConverter.parseBDate(value);
    }
    /**
     * 取得变量对象的自定义时间型值
     * @return 自定义时间型值
     */
    public BTime getBTime(){
        return TypeConverter.parseBTime(value);
    }
    /**
     * 取得变量对象的自定义日期时间型值
     * @return 自定义日期时间型值
     */
    public BDateTime getBDateTime(){
        return TypeConverter.parseBDateTime(value);
    }
    /**
     * 用布尔型值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为布尔型
     * @param value 布尔型值
     */
    public void setBoolean(boolean value){
        if(dataType == DataType.UNKNOWN){
            dataType = DataType.BOOLEAN;
        }
        if(dataType == DataType.BOOLEAN){
            this.value = value;
        }else{
            this.value = TypeConverter.translate(dataType,value);
        }
    }
    
    /**
     * 用自定义布尔型值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为自定义布尔型
     * @param value 自定义布尔型值
     */
    public void setBBoolean(BBoolean value){
        if(dataType == DataType.UNKNOWN){
            dataType = DataType.BBOOLEAN;
        }
        if(dataType == DataType.BBOOLEAN){
            this.value = value;
        } else{
            this.value = TypeConverter.translate(dataType,value);
        }
    }
    
    /**
     * 用字节值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为字节型
     * @param value 字节值
     */
    public void setByte(byte value){
        if(dataType == DataType.UNKNOWN){
            dataType = DataType.BYTE;
        }
        if(dataType == DataType.BYTE){
            this.value = value;
        }else{
            this.value = TypeConverter.translate(dataType,value);
        }
    }
    /**
     * 用短整数值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为短整数类型
     * @param value 短整数
     */
    public void setShort(short value){
        if(dataType == DataType.UNKNOWN){
            dataType = DataType.SHORT;
        }
        if(dataType == DataType.SHORT){
            this.value = value;
        }else{
            this.value = TypeConverter.translate(dataType,value);
        }
    }
    
    /**
     * 用整型值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为整型
     * @param value 整型值
     */
    public void setInt(int value){
        if(dataType == DataType.UNKNOWN){
            dataType = DataType.INT;
        }
        if(dataType == DataType.INT){
            this.value = value;
        }else{
            this.value = TypeConverter.translate(dataType,value);
        }
    }
    
    /**
     * 用长整型值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为长整型
     * @param value 长整型值
     */
    public void setLong(long value){
        if(dataType == DataType.UNKNOWN){
            dataType = DataType.LONG;
        }
        if(dataType == DataType.LONG){
            this.value = value;
        }else{
            this.value = TypeConverter.translate(dataType,value);
        }
    }
    
    /**
     * 用浮点型值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为浮点型
     * @param value 浮点型值
     */
    public void setFloat(float value){
        if(dataType == DataType.UNKNOWN){
            dataType = DataType.FLOAT;
        }
        if(dataType == DataType.FLOAT){
            this.value = value;
        }else{
            this.value = TypeConverter.translate(dataType,value);
        }
    }
    
    /**
     * 用双浮点型值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为双浮点型
     * @param value 双浮点型值
     */
    public void setDouble(double value){
        if(dataType == DataType.UNKNOWN){
            dataType = DataType.DOUBLE;
        }
        if(dataType == DataType.DOUBLE){
            this.value = value;
        }else{
            this.value = TypeConverter.translate(dataType,value);
        }
    }
    
    /**
     * 用小数型值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为小数型
     * @param value 小数型值
     */
    public void setBigDecimal(BigDecimal value){
        if(dataType == DataType.UNKNOWN){
            dataType = DataType.BIGDECIMAL;
        }
        if(dataType == DataType.BIGDECIMAL){
            this.value = value;
        }else{
            this.value = TypeConverter.translate(dataType,value);
        }
    }
    
    /**
     * 用字符串设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为字符型
     * @param value 字符串值
     */
    public void setString(String value){
        if(dataType == DataType.UNKNOWN){
            dataType = DataType.STRING;
        }
        if(dataType == DataType.STRING){
        	this.value = value;
        }else{
            this.value = TypeConverter.translate(dataType,value);
        }
    }
    
    /**
     * 用java日期型值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为java日期型
     * @param value java日期型值
     */
    public void setDate(Date value){
        if(dataType == DataType.UNKNOWN){
            dataType = DataType.DATE;
        }
        if(dataType == DataType.DATE){
            this.value = value;
        }else{
            this.value = TypeConverter.translate(dataType,value);
        }
    }
    
    /**
     * 用自定义日期型值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为自定义日期型
     * @param value 自定义日期型值
     */
    public void setBDate(BDate value){
        if(dataType == DataType.UNKNOWN){
            dataType = DataType.BDATE;
        }
        if(dataType == DataType.BDATE){
            this.value = value;
        }else{
            this.value = TypeConverter.translate(dataType,value);
        }
    }
    
    /**
     * 用自定义时间型值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为自定义时间型
     * @param value 自定义时间型值
     */
    public void setBTime(BTime value){
        if(dataType == DataType.UNKNOWN){
            dataType = DataType.BTIME;
        }
        if(dataType == DataType.BTIME){
            this.value = value;
        }else{
            this.value = TypeConverter.translate(dataType,value);
        }
    }
    
    /**
     * 用自定义日期时间型值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为自定义日期时间型
     * @param value 自定义日期时间型值
     */
    public void setBDateTime(BDateTime value){
        if(dataType == DataType.UNKNOWN){
            dataType = DataType.BDATETIME;
        }
        if(dataType == DataType.BDATETIME){
            this.value = value;
        }else{
            this.value = TypeConverter.translate(dataType,value);
        }
    }
    
    /**
     * 判断当前值是否为空
     * @return 布尔值
     */
    public boolean isNull(){
        return value == null;
    }

    /**
     * 设置当前变量对象值为空
     */
    public void setNull(){
        value = null;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj){
        if(obj instanceof Variant){
            return ObjectUtils.equals(value,((Variant)obj).getValue());
        }else{
            return false;
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode(){
        if(value != null){
            return value.hashCode();
        }else{
            return 0;
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    protected Variant clone() throws CloneNotSupportedException{
        Variant o = (Variant)super.clone();
        o.value = value;
        return o;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString(){
        return value != null ? value.toString() : "Variant {null}";
    }
}
