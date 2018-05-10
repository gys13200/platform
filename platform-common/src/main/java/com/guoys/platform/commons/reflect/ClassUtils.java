/*******************************************************************************
 * $Header: /cvslv/source/eos/java/ff-core/src/cn/chinaclear/sh/tp/core/util/ClassUtil.java,v 1.8 2013/05/17 02:43:41 supyuser Exp $
 * $Revision: 1.8 $
 * $Date: 2013/05/17 02:43:41 $
 *
 *==============================================================================
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License. 
 * 
 * Created on Sep 18, 2007
 *******************************************************************************/

package com.guoys.platform.commons.reflect;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 
 * TODO此处填写 class 信息
 * 
 * @author wengzr (mailto:zrweng@hotmail.com)
 */
public final class ClassUtils {

	private static final String ARRAY_SUFFIX = "[]";

	private static Map<String, Class> primitiveStringToClass;
	static {
		primitiveStringToClass = new HashMap<String, Class>();
		primitiveStringToClass.put(Boolean.TYPE.getName(), Boolean.class);
		primitiveStringToClass.put(Byte.TYPE.getName(), Byte.class);
		primitiveStringToClass.put(Character.TYPE.getName(), Character.class);
		primitiveStringToClass.put(Short.TYPE.getName(), Short.class);
		primitiveStringToClass.put(Integer.TYPE.getName(), Integer.class);
		primitiveStringToClass.put(Long.TYPE.getName(), Long.class);
		primitiveStringToClass.put(Double.TYPE.getName(), Double.class);
		primitiveStringToClass.put(Float.TYPE.getName(), Float.class);
	}

	/**
	 * Map with primitive wrapper type as key and corresponding primitive type
	 * as value, for example: Integer.class -> int.class.
	 */
	private static final Map<Class, Class> primitiveWrapperTypeMap = new HashMap<Class, Class>(8);

	/**
	 * Map with primitive type name as key and corresponding primitive type as
	 * value, for example: "int" -> "int.class".
	 */
	private static final Map<String, Class> primitiveTypeNameMap = new HashMap<String, Class>(8);

	static {
		primitiveWrapperTypeMap.put(Boolean.class, boolean.class);
		primitiveWrapperTypeMap.put(Byte.class, byte.class);
		primitiveWrapperTypeMap.put(Character.class, char.class);
		primitiveWrapperTypeMap.put(Double.class, double.class);
		primitiveWrapperTypeMap.put(Float.class, float.class);
		primitiveWrapperTypeMap.put(Integer.class, int.class);
		primitiveWrapperTypeMap.put(Long.class, long.class);
		primitiveWrapperTypeMap.put(Short.class, short.class);

		for (Iterator it = primitiveWrapperTypeMap.values().iterator(); it.hasNext();) {
			Class primitiveClass = (Class) it.next();
			primitiveTypeNameMap.put(primitiveClass.getName(), primitiveClass);
		}
	}

	private ClassUtils() {
		super();
	}

	public static Object newInstance(final Class r_Class, final Object[] r_Objects, final Class[] r_Classes) {
		if (null == r_Class) {
			return new IllegalArgumentException("the class name can't be null");
		}

		try {
			Constructor t_Constructor = r_Class.getDeclaredConstructor(r_Classes);
			t_Constructor.setAccessible(true);
			return t_Constructor.newInstance(r_Objects);
		} catch (Exception e) {
			throw new ReflectionAccessException(e);
		}
	}

	public static Object newInstance(final Class r_Class, final Object[] r_Objects) {
		if ((null == r_Objects) || (r_Objects.length == 0)) {
			return newInstance(r_Class, null, null);

		} else {
			final Class[] t_Classes = new Class[r_Objects.length];
			for (int i = 0; i < r_Objects.length; i++) {
				t_Classes[i] = r_Objects[i].getClass();
			}

			return newInstance(r_Class, r_Objects, t_Classes);
		}
	}

	/**
	 * 使用预先指定的类找到相应的构造函数<BR>
	 * 再使用传入的对象构造指定类的实例<BR>
	 * 如果传入的类名为空<BR>
	 * 抛出IllegalArgumentException异常<BR>
	 * 
	 */
	public static Object newInstance(final String r_ClassName, final Object[] r_Objects, final Class[] r_Classes) {
		if (StringUtils.isBlank(r_ClassName)) {
			return new IllegalArgumentException("the class name can't be null");
		}

		try {
			return newInstance(Class.forName(r_ClassName), r_Objects, r_Classes);
		} catch (ClassNotFoundException e) {
			throw new ReflectionAccessException(e);
		}
	}

	/**
	 * 使用预先指定的对象找到相应的构造函数<BR>
	 * 再使用传入的对象构造指定类的实例<BR>
	 * 如果传入的类名为空<BR>
	 * 抛出IllegalArgumentException异常<BR>
	 * 
	 */
	public static Object newInstance(final String r_ClassName, final Object[] r_Objects) {
		if (StringUtils.isBlank(r_ClassName)) {
			return new IllegalArgumentException("the class name can't be null");
		}

		try {
			return newInstance(Class.forName(r_ClassName), r_Objects);
		} catch (Exception e) {
			throw new ReflectionAccessException(e);
		}
	}

	/**
	 * 返回指定名称和参数的方法。<BR>
	 * 
	 * Return a method with the specified name and parameters.<BR>
	 * 
	 * @param r_Class
	 * @param r_Name
	 * @param parameterTypes
	 * @return
	 */
	public static Method getMethod(Class r_Class, String r_Name, Class[] parameterTypes) {
		Method[] t_Methods = r_Class.getMethods();
		for (int i = 0; i < t_Methods.length; i++) {
			Method t_Method = t_Methods[i];
			if (t_Method.getName().equals(r_Name)) {
				if (null != parameterTypes) {
					if (Arrays.equals(parameterTypes, t_Method.getParameterTypes())) {
						return t_Method;
					}
				} else {
					if (null == t_Method.getParameterTypes() || t_Method.getParameterTypes().length == 0) {
						return t_Method;
					}
				}
			}
		}
		t_Methods = r_Class.getDeclaredMethods();
		for (int i = 0; i < t_Methods.length; i++) {
			Method t_Method = t_Methods[i];
			if (t_Method.getName().equals(r_Name)) {
				if (null != parameterTypes) {
					if (Arrays.equals(parameterTypes, t_Method.getParameterTypes())) {
						return t_Method;
					}
				} else {
					if (null == t_Method.getParameterTypes() || t_Method.getParameterTypes().length == 0) {
						return t_Method;
					}
				}
			}
		}
		return null;
	}

	private static Method getAllSuperMethod(Class r_Class, String r_Name, Class[] parameterTypes) {
		Class superClass = r_Class;
		while (true) {
			if (superClass == null) {
				break;
			}
			Method method = getMethod(superClass, r_Name, parameterTypes);
			if (method != null) {
				return method;
			}
			superClass = superClass.getSuperclass();
		}
		return null;
	}

	public static Object invokeMethod(Object obj, String r_Name, Class[] parameterTypes, Object[] args) throws Exception {
		if (obj == null) {
			return null;
		}
		Class r_Class = obj.getClass();
		Method method = getAllSuperMethod(r_Class, r_Name, parameterTypes);
		if (method == null) {
			return null;
		} else if (!method.isAccessible()) {
			method.setAccessible(true);
		}
		return method.invoke(obj, args);
	}

	/**
	 * Replacement for <code>Class.forName()</code> that also returns Class
	 * instances for primitives (like "int") and array class names (like
	 * "String[]").
	 */
	public static Class forName(String name) throws ClassNotFoundException, LinkageError {
		return forName(name, getDefaultClassLoader());
	}

	/**
	 * Replacement for <code>Class.forName()</code> that also returns Class
	 * instances for primitives (like "int") and array class names (like
	 * "String[]").
	 */
	public static Class forName(String name, ClassLoader classLoader) throws ClassNotFoundException, LinkageError {
		Class clazz = resolvePrimitiveClassName(name);
		if (clazz != null) {
			return clazz;
		}
		if (name.endsWith(ARRAY_SUFFIX)) {
			// special handling for array class names
			String elementClassName = name.substring(0, name.length() - ARRAY_SUFFIX.length());
			Class elementClass = forName(elementClassName, classLoader);
			return Array.newInstance(elementClass, 0).getClass();
		}
		ClassLoader classLoaderToUse = classLoader;
		if (classLoaderToUse == null) {
			classLoaderToUse = getDefaultClassLoader();
		}
		return classLoaderToUse.loadClass(name);
	}

	/**
	 * Resolve the given class name into a Class instance. Supports primitives
	 * (like "int") and array class names (like "String[]").
	 * <p>
	 */
	public static Class resolveClassName(String className, ClassLoader classLoader) throws IllegalArgumentException {
		try {
			return forName(className, classLoader);
		} catch (ClassNotFoundException ex) {
			throw new IllegalArgumentException("Cannot find class [" + className + "]. Root cause: " + ex);
		} catch (LinkageError ex) {
			throw new IllegalArgumentException("Error loading class [" + className + "]: problem with class file or dependent class. Root cause: " + ex);
		}
	}

	/**
	 * Resolve the given class name as primitive class, if appropriate.
	 * 
	 */
	public static Class resolvePrimitiveClassName(String name) {
		Class result = null;
		// Most class names will be quite long, considering that they
		// SHOULD sit in a package, so a length check is worthwhile.
		if (name != null && name.length() <= 8) {
			// Could be a primitive - likely.
			result = (Class) primitiveTypeNameMap.get(name);
		}
		return result;
	}

	public static Class toPrimitiveClass(Class wrapperClass) {

		return (Class) primitiveWrapperTypeMap.get(wrapperClass);

	}

	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) {
			System.out.println("Cannot access thread context ClassLoader - falling back to system class loader");
		}
		if (cl == null) {
			// No thread context class loader -> use class loader of this class.
			cl = ClassUtils.class.getClassLoader();
		}
		return cl;
	}

	public static Class toPrimitiveWrapperClass(Class primitiveClass) {
		return primitiveStringToClass.get(primitiveClass.getName());
	}

	public static boolean isPrimitiveClass(String primitiveClassName) {
		return primitiveStringToClass.containsKey(primitiveClassName);
	}

	public static boolean isPrimitiveClass(Class primitiveClass) {
		return primitiveStringToClass.containsKey(primitiveClass.getName());
	}

	public static boolean isPrimitiveWrapperClass(String primitiveWrapperClass) {
		for (Class clazz : primitiveStringToClass.values()) {
			if (clazz.getName().equals(primitiveWrapperClass)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isPrimitiveWrapperClass(Class primitiveWrapperClass) {
		return primitiveStringToClass.values().contains(primitiveWrapperClass);
	}

	public static boolean isPrimitiveOrWrapperClass(String primitiveClass) {
		if (isPrimitiveClass(primitiveClass) || isPrimitiveWrapperClass(primitiveClass)) {
			return true;
		}
		return false;
	}

	/**
	 * 在指定类中查询所有符合过滤条件的字段。<BR>
	 * 过滤条件是(field.getModifiers() & r_Filer) == 0。<BR>
	 * 
	 * Get all the fields in the specified class by the filter condition.<BR>
	 * The filter condition is "(field.getModifiers() & r_Filer) == 0".<BR>
	 * 
	 */
	public static Field[] getFields(Class r_Class, int r_Filer) {
		List t_List = new ArrayList();

		Field[] t_Fields = r_Class.getDeclaredFields();
		for (int i = 0; i < t_Fields.length; i++) {
			Field t_Field = t_Fields[i];

			if ((t_Field.getModifiers() & r_Filer) == 0) {
				t_List.add(t_Field);
			}
		}

		Field[] t_ResultFields = new Field[t_List.size()];
		t_List.toArray(t_ResultFields);

		return t_ResultFields;
	}

	/**
	 * 设置类属性值
	 * 
	 */
	public static void setFieldValue(Object instance, String fieldName, Object value) throws IllegalArgumentException, IllegalAccessException {
		Field field = getField(instance.getClass(), fieldName);
		if (field != null) {
			field.setAccessible(true);
			field.set(instance, value);

		}

	}

	public static Field getSuperField(Class clazz, String fieldName) {
		Field field = null;
		Class superClass = null;

		while (true) {
			superClass = clazz.getSuperclass();
			if (superClass == null || superClass == Object.class)
				return null;

			try {
				field = superClass.getDeclaredField(fieldName);
				if (field != null)
					break;
			} catch (NoSuchFieldException noSuchFieldException1) {
				superClass = superClass.getSuperclass();
			}
		}
		return field;
	}

	/**
	 * 返回指定名称的字段。<BR>
	 * 
	 * @return
	 */
	public static Field getField(Class r_Class, String r_Name) {

		Field[] t_Fields = r_Class.getFields();
		for (int i = 0; i < t_Fields.length; i++) {
			Field t_Field = t_Fields[i];
			if (t_Field.getName().equals(r_Name)) {
				return t_Field;
			}
		}
		t_Fields = r_Class.getDeclaredFields();
		for (int i = 0; i < t_Fields.length; i++) {
			Field t_Field = t_Fields[i];
			if (t_Field.getName().equals(r_Name)) {
				return t_Field;
			}
		}

		return getSuperField(r_Class, r_Name);

	}

	/**
	 * 在指定类及其父类中查询所有符合过滤条件的字段。<BR>
	 * 过滤条件是(field.getModifiers() & r_Filer) == 0。<BR>
	 * 
	 */
	public static Field[] getAllFields(Class r_Class, int r_Filer) {
		List t_List = new ArrayList();

		Class t_Class = r_Class;
		while (null != t_Class) {
			addAll(t_List, getFields(t_Class, r_Filer));
			t_Class = t_Class.getSuperclass();
		}

		Field[] t_ResultFields = new Field[t_List.size()];
		t_List.toArray(t_ResultFields);

		return t_ResultFields;
	}

	/**
	 * 将数组元素全部加到一个集合中。<BR>
	 */
	private static void addAll(final Collection r_Collection, final Object[] r_Objects) {
		if (null == r_Objects) {
			return;
		}

		for (int i = 0; i < r_Objects.length; i++) {
			if (null != r_Objects[i]) {
				r_Collection.add(r_Objects[i]);
			}
		}
	}

	/**
	 * 取得指定对象中某个字段的值。<BR>
	 * 
	 * @return
	 */
	public static Object getFieldValue(Object r_Object, String r_FieldName) {

		Field t_Field;
		try {
			if (null == r_Object) {
				return null;
			}

			t_Field = r_Object.getClass().getDeclaredField(r_FieldName);

			if (null == t_Field) {
				t_Field = r_Object.getClass().getField(r_FieldName);
			}

			if (null != t_Field) {
				t_Field.setAccessible(true);
				return t_Field.get(r_Object);
			} else {
				return null;
			}

		} catch (Exception e) {
			if (e instanceof NoSuchFieldException) {
				try {
					return getNotAccessibleFieldValue(r_Object, r_FieldName);
				} catch (Exception ex1) {
					e.printStackTrace();
					return null;
				}
			}
			e.printStackTrace();
			// Nothing to do
		}

		return null;

	}

	private static Object getNotAccessibleFieldValue(Object r_Object, String r_FieldName) throws IllegalArgumentException, IllegalAccessException {
		Field t_Field = null;
		Class superClass = null;
		while (true) {
			superClass = r_Object.getClass().getSuperclass();
			if (superClass == null) {
				return null;
			}
			try {
				t_Field = superClass.getDeclaredField(r_FieldName);
				break;
			} catch (NoSuchFieldException noSuchFieldException1) {
				superClass = superClass.getSuperclass();
			}
		}
		if (null != t_Field) {
			t_Field.setAccessible(true);
			return t_Field.get(r_Object);
		} else {
			return null;
		}
	}

	
    public static Object newInstance2(Class<?> clazz, String value) throws Exception {
        Object obj;
        if (clazz.isArray()) {
            Class<?> elementClass = clazz.getComponentType();
            String[] arr=value.split(",");
            Object array = Array.newInstance(elementClass, arr.length);
            
            for (int i = 0; i<arr.length;i++) {
                Object param = newInstance2(elementClass, arr[i]);
                Array.set(array, i, param);
            }
            obj = array;
        } else {
            Class<?> clz = clazz;
            if (clazz.isPrimitive()){//将原生类型转换为对象类型
                clz = (Class<?>)primitiveStringToClass.get(clazz.getName());
            }
            
            if (String.class.isAssignableFrom(clz)){
            	obj = value;
            }
            else if(java.sql.Date.class.isAssignableFrom(clz)){
            	java.util.Date date=DateUtils.parseDate(value);
            	obj=DateUtils.toSqlDate(date);
            }
            else if(java.sql.Timestamp.class.isAssignableFrom(clz)){
            	java.util.Date date=DateUtils.parseDate(value);
            	obj=DateUtils.toTimestamp(date);
            }
            else if (java.util.Date.class.isAssignableFrom(clz)){
            	obj=DateUtils.parseDate(value);
            }
            else{
                obj = clz.getConstructor(new Class[] {String.class}).newInstance(new Object[] {value });
            }
        }
        return obj;
    }
    


}