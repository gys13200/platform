package com.guoys.platform.commons.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author wengzr
 *
 */
public class AnnotationUtils {


    
    /**
     * 判断方法是否以指定的Annotation注释
     * @param method
     * @param annotation
     * @return
     */
    public static boolean isAnnotated(Method method, Class annotation) {
        return method.isAnnotationPresent(annotation);
    }

    public static Constructor[] getAnnotatedConstructors(Class clazz, Class annotation) {
        List<Constructor> constructorList = new ArrayList<Constructor>();
        Constructor constructors[] = clazz.getConstructors();
        Constructor arr[] = constructors;
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            Constructor constructor = arr[i];
            if (constructor.isAnnotationPresent(annotation))
                constructorList.add(constructor);
        }

        return (Constructor[])constructorList.toArray(new Constructor[constructorList.size()]);
    }

    /**
     * 获取指定annotation的成员属性数组
     * @param clazz 指定的类类型
     * @param annotation 指定的Annotation类型
     * @return
     */
    public static Field[] getAnnotatedFields(Class clazz, Class annotation) {
        List<Field> fieldList = new ArrayList<Field>();
        Field fields[] = ClassUtils.getAllFields(clazz,0);
        Field arr[] = fields;
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            Field field = arr[i];
            if (field.isAnnotationPresent(annotation))
                fieldList.add(field);
        }

        return (Field[])fieldList.toArray(new Field[fieldList.size()]);
    }

    /**
     * 获取有注释的方法数组
     * 
     * @param clz
     * @param annotation
     * @return
     */
    public static Method[] getAnnotatedMethods(Class clz, Class annotation) {
        List<Method> methods = new ArrayList<Method>();
        Method allMethods[] = clz.getMethods();
        for (int i = 0; i < allMethods.length; i++){
            if (isAnnotated(allMethods[i], annotation))
                methods.add(allMethods[i]);
        }

        return (Method[])methods.toArray(new Method[methods.size()]);
    }

    
    public static Method[] getAnnotatedDeclaredMethods(Class clz, Class annotation) {
        List<Method> methods = new ArrayList<Method>();
        Method allMethods[] = clz.getDeclaredMethods();
        for (int i = 0; i < allMethods.length; i++)
            if (isAnnotated(allMethods[i], annotation))
                methods.add(allMethods[i]);

        return (Method[])methods.toArray(new Method[methods.size()]);
    }

    public static Method[] getAnnotatedSetMethods(Class clz, Class annotation) {
        List<Method> methods = new ArrayList<Method>();
        Method allMethods[] = clz.getMethods();
        for (int i = 0; i < allMethods.length; i++)
            if (MethodUtils.isSetMethod(allMethods[i]) && isAnnotated(allMethods[i], annotation))
                methods.add(allMethods[i]);

        return (Method[])methods.toArray(new Method[methods.size()]);
    }

    public static Method[] getAnnotatedGetMethods(Class clz, Class annotation) {
        List<Method> methods = new ArrayList<Method>();
        Method allMethods[] = clz.getMethods();
        for (int i = 0; i < allMethods.length; i++)
            if (MethodUtils.isGetMethod(allMethods[i]) && isAnnotated(allMethods[i], annotation))
                methods.add(allMethods[i]);

        return (Method[])methods.toArray(new Method[methods.size()]);
    }

    public static boolean hasAnnotatedMethod(Class clz, Class annotation) {
        Method allMethods[] = clz.getMethods();
        for (int i = 0; i < allMethods.length; i++)
            if (isAnnotated(allMethods[i], annotation))
                return true;

        return false;
    }

    public static boolean isAnnotated(Field field, Class annotation) {
        return field.isAnnotationPresent(annotation);
    }
}
