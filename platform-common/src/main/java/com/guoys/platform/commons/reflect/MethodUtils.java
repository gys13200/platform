/*******************************************************************************
 * $Header: /cvslv/source/eos/java/ff-core/src/cn/chinaclear/sh/tp/core/util/MethodUtils.java,v 1.8 2013/06/05 07:32:15 supyuser Exp $
 * $Revision: 1.8 $
 * $Date: 2013/06/05 07:32:15 $
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
 * Created on Dec 30, 2007
 *******************************************************************************/


package com.guoys.platform.commons.reflect;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import com.guoys.platform.commons.reflect.asm.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;


public class MethodUtils {
	

    public static boolean isGetMethod(Method method) {
        return (method.getName().startsWith("get") && !method.getName().equals("get") || method.getName().startsWith("is") && !method.getName().equals("is")) && method.getReturnType() != Void.TYPE && method.getParameterTypes().length == 0;
    }

    public static boolean isSetMethod(Method method) {
        return method.getName().startsWith("set") && !method.getName().equals("set") && method.getReturnType() == Void.TYPE && method.getParameterTypes().length == 1;
    }
    
    
	public static String convertName(String aName, boolean isColumn) {
		String name = WordUtils.capitalizeFully(aName, new char[] {'_'});
		//属性的首字母小写,实体首字母大写
		if(isColumn)
			name = StringUtils.uncapitalize(name);//首字母变小写
		return StringUtils.remove(name, '_');
	}
	
	/**
	 * 首字母大写
	 * @param name
	 * @return
	 */
	public static String uppercaseFirstWord(String name){
		return WordUtils.capitalize(name);
	}
	
	/**
	 * 首字母小写
	 * @param name
	 * @return
	 */
	public static String lowercaseFirstWord(String name)
	{
		return WordUtils.uncapitalize(name);
	}
	/**
	 * 除去泛型
	 * @param classType
	 * @return
	 */
	public static String removeGenerate(String classType)
	{
		if(classType.indexOf("<")!=-1)
		{
			return StringUtils.substringBefore(classType, "<");
		}else{
			return classType;
		}
	}
	/**
	 * 获得除法的结果
	 * @param divisior
	 * @param dividend
	 * @return
	 */
	public static String getDivisionResult(int divisior,int dividend)
	{
		int result=divisior/dividend;
		
		return String.valueOf(result);
	}
	/**
	 * 获得除法结果后+1
	 * @param divisior
	 * @param dividend
	 * @return
	 */
	public static String getDivisionResultAndAddOne(int divisior,int dividend)
	{
		int result=divisior/dividend;
		
		return String.valueOf(result);
	}
	/**
	 * 获得模运算的结果
	 * @param first
	 * @param second
	 * @return
	 */
	public static String getModularResult(int first,int second)
	{
		int result=first%second;
		
		return String.valueOf(result);
	}
	/**
	 * 取模运算后+1
	 * @param first
	 * @param second
	 * @return
	 */
	public static String getModularResultAndAddOne(int first,int second)
	{
		int result=first%second+1;
		
		return String.valueOf(result);
	}
	/**
	 * 获得加运算的结果
	 * @param first
	 * @param second
	 * @return
	 */
	public static String getAddResult(int first,int second)
	{
		int result=first+second;
		
		return String.valueOf(result);
	}
	/**
	 * 获得减法运算的结果
	 * @param first
	 * @param second
	 * @return
	 */
	public static String getDecreaseResult(int first,int second)
	{
		int result=first-second;
		
		return String.valueOf(result);
	}
	
	private static boolean sameType(Type[] types, Class<?>[] clazzes) {
		// 个数不同
		if (types.length != clazzes.length) {
			return false;
		}
		for (int i = 0; i < types.length; i++) {
			if (!Type.getType(clazzes[i]).equals(types[i])) {
				return false;
			}
		}
		return true;
	}
	
	public static String[] getMethodParamNames(ClassLoader classLoader,final Method method){
		final String[] paramNames = new String[method.getParameterTypes().length];
		ClassReader cr = null;
		InputStream stream=null;
		try {
			
			stream=classLoader.getResourceAsStream(method.getDeclaringClass().getName().replace(".", "/")+".class");
			cr = new ClassReader(stream);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			IOUtils.closeQuietly(stream);
		}
		
		cr.accept(new ClassVisitor() {
			public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
				final Type[] args = Type.getArgumentTypes(desc);
				// 方法名相同并且参数个数相同
				if (!name.equals(method.getName())|| !sameType(args, method.getParameterTypes())) {
					return null;
				}
				return new MethodVisitor() {
					public void visitLocalVariable(String name, String desc,
                                                   String signature, Label start, Label end, int index) {
						int i = index - 1;
						// 如果是静态方法，则第一就是参数
						// 如果不是静态方法，则第一个是"this"，然后才是方法的参数
						//if (Modifier.isStatic(m.getModifiers())) {
						//	i = index;
						//}
						if (i >= 0 && i < paramNames.length) {
							paramNames[i] = name;
						}
					
					}

					public AnnotationVisitor visitAnnotation(String arg0, boolean arg1) {
						// TODO 自动生成方法存根
						return null;
					}

					public AnnotationVisitor visitAnnotationDefault() {
						// TODO 自动生成方法存根
						return null;
					}

					public void visitAttribute(Attribute arg0) {
						// TODO 自动生成方法存根
						
					}

					public void visitCode() {
						// TODO 自动生成方法存根
						
					}

					public void visitEnd() {
						// TODO 自动生成方法存根
						
					}

					public void visitFieldInsn(int arg0, String arg1, String arg2, String arg3) {
						// TODO 自动生成方法存根
						
					}

					public void visitFrame(int arg0, int arg1, Object[] arg2, int arg3, Object[] arg4) {
						// TODO 自动生成方法存根
						
					}

					public void visitIincInsn(int arg0, int arg1) {
						// TODO 自动生成方法存根
						
					}

					public void visitInsn(int arg0) {
						// TODO 自动生成方法存根
						
					}

					public void visitIntInsn(int arg0, int arg1) {
						// TODO 自动生成方法存根
						
					}

					public void visitJumpInsn(int arg0, Label arg1) {
						// TODO 自动生成方法存根
						
					}

					public void visitLabel(Label arg0) {
						// TODO 自动生成方法存根
						
					}

					public void visitLdcInsn(Object arg0) {
						// TODO 自动生成方法存根
						
					}

					public void visitLineNumber(int arg0, Label arg1) {
						// TODO 自动生成方法存根
						
					}

					public void visitLookupSwitchInsn(Label arg0, int[] arg1, Label[] arg2) {
						// TODO 自动生成方法存根
						
					}

					public void visitMaxs(int arg0, int arg1) {
						// TODO 自动生成方法存根
						
					}

					public void visitMethodInsn(int arg0, String arg1, String arg2, String arg3) {
						// TODO 自动生成方法存根
						
					}

					public void visitMultiANewArrayInsn(String arg0, int arg1) {
						
					}

					public AnnotationVisitor visitParameterAnnotation(int arg0, String arg1, boolean arg2) {
						return null;
					}

					public void visitTableSwitchInsn(int arg0, int arg1, Label arg2, Label[] arg3) {
						
					}

					public void visitTryCatchBlock(Label arg0, Label arg1, Label arg2, String arg3) {
						
					}

					public void visitTypeInsn(int arg0, String arg1) {
						
					}

					public void visitVarInsn(int arg0, int arg1) {
						
					}

				};
			}

			public void visit(int arg0, int arg1, String arg2, String arg3, String arg4, String[] arg5) {
				
			}

			public AnnotationVisitor visitAnnotation(String arg0, boolean arg1) {
				return null;
			}

			public void visitAttribute(Attribute arg0) {
				
			}

			public void visitEnd() {
				
			}

			public FieldVisitor visitField(int arg0, String arg1, String arg2, String arg3, Object arg4) {
				return null;
			}

			public void visitInnerClass(String arg0, String arg1, String arg2, int arg3) {
				
			}

			public void visitOuterClass(String arg0, String arg1, String arg2) {
				
			}

			public void visitSource(String arg0, String arg1) {
				
			}
		}, 0);
		return paramNames;
	}
	
	/**
	 * 获取方法参数名称
	 * @param classLoader 类加载器
	 * @param className 类名
	 * @param methodName 方法名
	 * @param parameterTypes 参数类型
	 * @return
	 */
	public static String[] getMethodParamNames(ClassLoader classLoader,
			final String className,final String methodName,final Class[] parameterTypes) {
		
		final String[] paramNames = new String[parameterTypes.length];
		ClassReader cr = null;
		InputStream stream=null;
		try {
			stream=classLoader.getResourceAsStream(className.replace(".", "/")+".class");
			cr = new ClassReader(stream);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			IOUtils.closeQuietly(stream);
		}
		
		cr.accept(new ClassVisitor() {
			public MethodVisitor visitMethod(int access,String name, String desc,String signature, String[] exceptions) {
				final Type[] args = Type.getArgumentTypes(desc);
				// 方法名相同并且参数个数相同
				if (!name.equals(methodName)|| !sameType(args, parameterTypes)) {
					return null;
				}
				return new MethodVisitor() {
					public void visitLocalVariable(String name, String desc,
							String signature, Label start, Label end, int index) {
						int i = index - 1;
						// 如果是静态方法，则第一就是参数
						// 如果不是静态方法，则第一个是"this"，然后才是方法的参数
						//if (Modifier.isStatic(m.getModifiers())) {
						//	i = index;
						//}
						if (i >= 0 && i < paramNames.length) {
							paramNames[i] = name;
						}
					
					}

					public AnnotationVisitor visitAnnotation(String arg0, boolean arg1) {
						// TODO 自动生成方法存根
						return null;
					}

					public AnnotationVisitor visitAnnotationDefault() {
						// TODO 自动生成方法存根
						return null;
					}

					public void visitAttribute(Attribute arg0) {
						// TODO 自动生成方法存根
						
					}

					public void visitCode() {
						// TODO 自动生成方法存根
						
					}

					public void visitEnd() {
						// TODO 自动生成方法存根
						
					}

					public void visitFieldInsn(int arg0, String arg1, String arg2, String arg3) {
						// TODO 自动生成方法存根
						
					}

					public void visitFrame(int arg0, int arg1, Object[] arg2, int arg3, Object[] arg4) {
						// TODO 自动生成方法存根
						
					}

					public void visitIincInsn(int arg0, int arg1) {
						// TODO 自动生成方法存根
						
					}

					public void visitInsn(int arg0) {
						// TODO 自动生成方法存根
						
					}

					public void visitIntInsn(int arg0, int arg1) {
						// TODO 自动生成方法存根
						
					}

					public void visitJumpInsn(int arg0, Label arg1) {
						// TODO 自动生成方法存根
						
					}

					public void visitLabel(Label arg0) {
						// TODO 自动生成方法存根
						
					}

					public void visitLdcInsn(Object arg0) {
						// TODO 自动生成方法存根
						
					}

					public void visitLineNumber(int arg0, Label arg1) {
						// TODO 自动生成方法存根
						
					}

					public void visitLookupSwitchInsn(Label arg0, int[] arg1, Label[] arg2) {
						// TODO 自动生成方法存根
						
					}

					public void visitMaxs(int arg0, int arg1) {
						// TODO 自动生成方法存根
						
					}

					public void visitMethodInsn(int arg0, String arg1, String arg2, String arg3) {
						// TODO 自动生成方法存根
						
					}

					public void visitMultiANewArrayInsn(String arg0, int arg1) {
						
					}

					public AnnotationVisitor visitParameterAnnotation(int arg0, String arg1, boolean arg2) {
						return null;
					}

					public void visitTableSwitchInsn(int arg0, int arg1, Label arg2, Label[] arg3) {
						
					}

					public void visitTryCatchBlock(Label arg0, Label arg1, Label arg2, String arg3) {
						
					}

					public void visitTypeInsn(int arg0, String arg1) {
						
					}

					public void visitVarInsn(int arg0, int arg1) {
						
					}

				};
			}

			public void visit(int arg0, int arg1, String arg2, String arg3, String arg4, String[] arg5) {
				
			}

			public AnnotationVisitor visitAnnotation(String arg0, boolean arg1) {
				return null;
			}

			public void visitAttribute(Attribute arg0) {
				
			}

			public void visitEnd() {
				
			}

			public FieldVisitor visitField(int arg0, String arg1, String arg2, String arg3, Object arg4) {
				return null;
			}

			public void visitInnerClass(String arg0, String arg1, String arg2, int arg3) {
				
			}

			public void visitOuterClass(String arg0, String arg1, String arg2) {
				
			}

			public void visitSource(String arg0, String arg1) {
				
			}
		}, 0);
		return paramNames;
	}
	
	
}
