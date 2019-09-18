package com.application.base.utils.common;

import com.application.base.utils.date.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * @desc 反射工具
 * @author 孤狼
 */
public class ReflectHelper {
	
	private static final String SETTER_PREFIX = "set";
	
	private static final String GETTER_PREFIX = "get";
	
	private static final String CGLIB_CLASS_SEPARATOR = "$$";
	
	private static Logger logger = LoggerFactory.getLogger(ReflectHelper.class);
	
	/**
	 * 调用Getter方法.
	 * 支持多级，如：对象名.对象名.方法
	 */
	@SuppressWarnings("unchecked")
	public static <E> E invokeGetter(Object obj, String propertyName)
	{
		Object object = obj;
		for (String name : StringUtils.split(propertyName, "."))
		{
			String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(name);
			object = invokeMethod(object, getterMethodName, new Class[] {}, new Object[] {});
		}
		return (E) object;
	}
	
	/**
	 * 调用Setter方法, 仅匹配方法名。
	 * 支持多级，如：对象名.对象名.方法
	 */
	public static <E> void invokeSetter(Object obj, String propertyName, E value)
	{
		Object object = obj;
		String[] names = StringUtils.split(propertyName, ".");
		for (int i = 0; i < names.length; i++)
		{
			if (i < names.length - 1)
			{
				String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(names[i]);
				object = invokeMethod(object, getterMethodName, new Class[] {}, new Object[] {});
			}
			else
			{
				String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(names[i]);
				invokeMethodByName(object, setterMethodName, new Object[] { value });
			}
		}
	}
	
	/**
	 * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
	 */
	@SuppressWarnings("unchecked")
	public static <E> E getFieldValue(final Object obj, final String fieldName)
	{
		Field field = getAccessibleField(obj, fieldName);
		if (field == null)
		{
			logger.debug("在 [" + obj.getClass() + "] 中，没有找到 [" + fieldName + "] 字段 ");
			return null;
		}
		E result = null;
		try
		{
			result = (E) field.get(obj);
		}
		catch (IllegalAccessException e)
		{
			logger.error("不可能抛出的异常{}", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
	 */
	public static <E> void setFieldValue(final Object obj, final String fieldName, final E value)
	{
		Field field = getAccessibleField(obj, fieldName);
		if (field == null)
		{
			// throw new IllegalArgumentException("在 [" + obj.getClass() + "] 中，没有找到 [" + fieldName + "] 字段 ");
			logger.debug("在 [" + obj.getClass() + "] 中，没有找到 [" + fieldName + "] 字段 ");
			return;
		}
		try
		{
			field.set(obj, value);
		}
		catch (IllegalAccessException e)
		{
			logger.error("不可能抛出的异常: {}", e.getMessage());
		}
	}
	
	/**
	 * 直接调用对象方法, 无视private/protected修饰符.
	 * 用于一次性调用的情况，否则应使用getAccessibleMethod()函数获得Method后反复调用.
	 * 同时匹配方法名+参数类型，
	 */
	@SuppressWarnings("unchecked")
	public static <E> E invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
	                                 final Object[] args)
	{
		if (obj == null || methodName == null)
		{
			return null;
		}
		Method method = getAccessibleMethod(obj, methodName, parameterTypes);
		if (method == null)
		{
			logger.debug("在 [" + obj.getClass() + "] 中，没有找到 [" + methodName + "] 方法 ");
			return null;
		}
		try
		{
			return (E) method.invoke(obj, args);
		}
		catch (Exception e)
		{
			String msg = "method: " + method + ", obj: " + obj + ", args: " + args + "";
			throw convertReflectionExceptionToUnchecked(msg, e);
		}
	}
	
	/**
	 * 直接调用对象方法, 无视private/protected修饰符，
	 * 用于一次性调用的情况，否则应使用getAccessibleMethodByName()函数获得Method后反复调用.
	 * 只匹配函数名，如果有多个同名函数调用第一个。
	 */
	@SuppressWarnings("unchecked")
	public static <E> E invokeMethodByName(final Object obj, final String methodName, final Object[] args)
	{
		Method method = getAccessibleMethodByName(obj, methodName, args.length);
		if (method == null)
		{
			// 如果为空不报错，直接返回空。
			logger.debug("在 [" + obj.getClass() + "] 中，没有找到 [" + methodName + "] 方法 ");
			return null;
		}
		try
		{
			// 类型转换（将参数数据类型转换为目标方法参数类型）
			Class<?>[] cs = method.getParameterTypes();
			for (int i = 0; i < cs.length; i++)
			{
				if (args[i] != null && !args[i].getClass().equals(cs[i]))
				{
					if (cs[i] == String.class)
					{
						args[i] = ConvertUitl.toStr(args[i]);
						if (StringUtils.endsWith((String) args[i], ".0"))
						{
							args[i] = StringUtils.substringBefore((String) args[i], ".0");
						}
					}
					else if (cs[i] == Integer.class)
					{
						args[i] = ConvertUitl.toInt(args[i]);
					}
					else if (cs[i] == Long.class)
					{
						args[i] = ConvertUitl.toLong(args[i]);
					}
					else if (cs[i] == Double.class)
					{
						args[i] = ConvertUitl.toDouble(args[i]);
					}
					else if (cs[i] == Float.class)
					{
						args[i] = ConvertUitl.toFloat(args[i]);
					}
					else if (cs[i] == Date.class)
					{
						if (args[i] instanceof String)
						{
							args[i] = DateUtils.parseDate(args[i]);
						}
						else
						{
							args[i] = DateUtil.getJavaDate((Double) args[i]);
						}
					}
				}
			}
			return (E) method.invoke(obj, args);
		}
		catch (Exception e)
		{
			String msg = "method: " + method + ", obj: " + obj + ", args: " + args + "";
			throw convertReflectionExceptionToUnchecked(msg, e);
		}
	}
	
	/**
	 * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	public static Field getAccessibleField(final Object obj, final String fieldName)
	{
		// 为空不报错。直接返回 null
		if (obj == null)
		{
			return null;
		}
		Validate.notBlank(fieldName, "fieldName can't be blank");
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass())
		{
			try
			{
				Field field = superClass.getDeclaredField(fieldName);
				makeAccessible(field);
				return field;
			}
			catch (NoSuchFieldException e)
			{
				continue;
			}
		}
		return null;
	}
	
	/**
	 * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.
	 * 如向上转型到Object仍无法找到, 返回null.
	 * 匹配函数名+参数类型。
	 * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
	 */
	public static Method getAccessibleMethod(final Object obj, final String methodName,
	                                         final Class<?>... parameterTypes)
	{
		// 为空不报错。直接返回 null
		if (obj == null)
		{
			return null;
		}
		Validate.notBlank(methodName, "methodName can't be blank");
		for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass())
		{
			try
			{
				Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
				makeAccessible(method);
				return method;
			}
			catch (NoSuchMethodException e)
			{
				continue;
			}
		}
		return null;
	}
	
	/**
	 * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.
	 * 如向上转型到Object仍无法找到, 返回null.
	 * 只匹配函数名。
	 * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
	 */
	public static Method getAccessibleMethodByName(final Object obj, final String methodName, int argsNum)
	{
		// 为空不报错。直接返回 null
		if (obj == null)
		{
			return null;
		}
		Validate.notBlank(methodName, "methodName can't be blank");
		for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass())
		{
			Method[] methods = searchType.getDeclaredMethods();
			for (Method method : methods)
			{
				if (method.getName().equals(methodName) && method.getParameterTypes().length == argsNum)
				{
					makeAccessible(method);
					return method;
				}
			}
		}
		return null;
	}
	
	/**
	 * 改变private/protected的方法为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
	 */
	public static void makeAccessible(Method method)
	{
		if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
				&& !method.isAccessible())
		{
			method.setAccessible(true);
		}
	}
	
	/**
	 * 改变private/protected的成员变量为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
	 */
	public static void makeAccessible(Field field)
	{
		if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
				|| Modifier.isFinal(field.getModifiers())) && !field.isAccessible())
		{
			field.setAccessible(true);
		}
	}
	
	/**
	 * 通过反射, 获得Class定义中声明的泛型参数的类型, 注意泛型必须定义在父类处
	 * 如无法找到, 返回Object.class.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getClassGenricType(final Class clazz)
	{
		return getClassGenricType(clazz, 0);
	}
	
	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
	 * 如无法找到, 返回Object.class.
	 */
	public static Class getClassGenricType(final Class clazz, final int index)
	{
		Type genType = clazz.getGenericSuperclass();
		
		if (!(genType instanceof ParameterizedType))
		{
			logger.debug(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}
		
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		
		if (index >= params.length || index < 0)
		{
			logger.debug("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class))
		{
			logger.debug(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}
		
		return (Class) params[index];
	}
	
	public static Class<?> getUserClass(Object instance)
	{
		if (instance == null)
		{
			throw new RuntimeException("Instance must not be null");
		}
		Class clazz = instance.getClass();
		if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR))
		{
			Class<?> superClass = clazz.getSuperclass();
			if (superClass != null && !Object.class.equals(superClass))
			{
				return superClass;
			}
		}
		return clazz;
		
	}
	
	/**
	 * 将反射时的checked exception转换为unchecked exception.
	 */
	public static RuntimeException convertReflectionExceptionToUnchecked(String msg, Exception e)
	{
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException)
		{
			return new IllegalArgumentException(msg, e);
		}
		else if (e instanceof InvocationTargetException)
		{
			return new RuntimeException(msg, ((InvocationTargetException) e).getTargetException());
		}
		return new RuntimeException(msg, e);
	}
	
	/**
	 * 获取obj对象fieldName的Field
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Field getFieldByFieldName(Object obj, String fieldName) {
		if (obj == null || fieldName == null) {
			return null;
		}
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * 获取obj对象fieldName的属性值
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Object getValueByFieldName(Object obj, String fieldName) {
		Object value = null;
		try {
			Field field = getFieldByFieldName(obj, fieldName);
			if (field != null) {
				if (field.isAccessible()) {
					value = field.get(obj);
				} else {
					field.setAccessible(true);
					value = field.get(obj);
					field.setAccessible(false);
				}
			}
		} catch (Exception e) {
		}
		return value;
	}

	/**
	 * 获取obj对象fieldName的属性值
	 * @param obj
	 * @param fieldType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValueByFieldType(Object obj, Class<T> fieldType) {
		Object value = null;
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field[] fields = superClass.getDeclaredFields();
				for (Field f : fields) {
					if (f.getType() == fieldType) {
						if (f.isAccessible()) {
							value = f.get(obj);
							break;
						} else {
							f.setAccessible(true);
							value = f.get(obj);
							f.setAccessible(false);
							break;
						}
					}
				}
				if (value != null) {
					break;
				}
			} catch (Exception e) {
			}
		}
		return (T) value;
	}

	/**
	 * 设置obj对象fieldName的属性值
	 * @param obj
	 * @param fieldName
	 * @param value
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static boolean setValueByFieldName(Object obj, String fieldName,
			Object value) {
		try {
			//java.lang.Class.getDeclaredField()方法用法实例教程 - 方法返回一个Field对象，它反映此Class对象所表示的类或接口的指定已声明字段。
			//此方法返回这个类中的指定字段的Field对象
			Field field = obj.getClass().getDeclaredField(fieldName);
		  /**
			* public void setAccessible(boolean flag)
            *       throws SecurityException将此对象的 accessible 标志设置为指示的布尔值。值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false 则指示反射的对象应该实施 Java 语言访问检查。 
			* 	首先，如果存在安全管理器，则在 ReflectPermission("suppressAccessChecks") 权限下调用 checkPermission 方法。 
			* 	如果 flag 为 true，并且不能更改此对象的可访问性（例如，如果此元素对象是 Class 类的 Constructor 对象），则会引发 SecurityException。 
			* 	如果此对象是 java.lang.Class 类的 Constructor 对象，并且 flag 为 true，则会引发 SecurityException。 
			* 	参数：
			* 	flag - accessible 标志的新值 
 			* 	抛出： 
			* 	SecurityException - 如果请求被拒绝。
			*/
			//获取此对象的 accessible 标志的值。
			if (field.isAccessible()) {
				//将指定对象变量上此 Field 对象表示的字段设置为指定的新值
				field.set(obj, value);
			} else {
				field.setAccessible(true);
				field.set(obj, value);
				field.setAccessible(false);
			}
			return true;
		} catch (Exception e) {
		}
		return false;
	}
}
