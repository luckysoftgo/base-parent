package com.application.base.core.datasource.param;

import com.application.base.core.constant.CoreConstants;
import com.application.base.core.utils.SqlUtil;

/**
 * @desc SQL生产器，根据类、查询类型或方法名称产生SQL
 * @ClassName:  SQLCretor
 * @author 孤狼
 */
public class SqlCreator {

	@SuppressWarnings("rawtypes")
	public static SqlEntity set(Class clazz, ESQL template) {
		return new DefaultSql(clazz.getSimpleName(), template.getSqlId());
	}


	@SuppressWarnings("rawtypes")
	public static SqlEntity set(Class clazz, Throwable able) {
		return new DefaultSql(clazz.getSimpleName(), getCurrentMethodName(able));
	}

    public static SqlEntity set(String className, Throwable able) {
		return new DefaultSql(className, getCurrentMethodName(able));
	}

	@SuppressWarnings("rawtypes")
	public static SqlEntity set(Class clazz, String elementId) {
		return new DefaultSql(clazz.getSimpleName(), elementId);
	}
    public static SqlEntity set(String className, String elementId) {
		return new DefaultSql(className, elementId);
	}

    /**
	 * 获得方法名称
	 * @return
	 */
	public static String getCurrentMethodName(Throwable throwable) {
		if (throwable == null) {
			return null;
		}
		return throwable.getStackTrace()[0].getMethodName();
	}

	public static CustomSql where() {
		return new DefaultCustomerSql();
	}

	@SuppressWarnings("rawtypes")
	public static SqlEntity set(Class clazz) {
		return new DefaultSql(clazz.getSimpleName(), getCurrentMethodName());
	}

	/**
	 * 获得方法名称
	 * @return
	 */
	private static String getCurrentMethodName() {
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		int thisMethodIndex = 0;
		for (StackTraceElement element : elements) {
			thisMethodIndex++;
			if (element != null && "set".equals(element.getMethodName())) {
				break;
			}
		}
		String methodName = elements[thisMethodIndex + 1].getMethodName();
		return methodName;
	}
}

/**
 * @desc DefaultSQL
 */
class DefaultSql implements SqlEntity {
	private String className;
	private String toClassName;
	private String sqlName;

	public DefaultSql(String className, String sqlName) {
		this.className = className;
		this.sqlName = sqlName;
	}

	public DefaultSql(String className, String toClassNme, String sqlName) {
		this.className = className;
		this.toClassName = toClassNme;
		this.sqlName = sqlName;
	}

	public DefaultSql(Class<?> clazz, Throwable able) {
		this.className = clazz.getName();
		this.sqlName = SqlCreator.getCurrentMethodName(able);
	}

	@Override
	public String get() {
		//return new StringBuffer(this.className.substring(0,1).toLowerCase() + this.className.substring(1)).append(CoreConstants.Separator.DOT).append(sqlName).toString();
		return new StringBuffer(className).append(CoreConstants.Separator.DOT).append(sqlName).toString();
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSqlName() {
		return sqlName;
	}

	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}

	public String getToClassName() {
		return toClassName;
	}

	public void setToClassName(String toClassName) {
		this.toClassName = toClassName;
	}

}

class DefaultCustomerSql implements CustomSql {
	StringBuilder builder = new StringBuilder();

	
	@Override
	public CustomSql cloumn(String column) {
		if (builder == null) {
			builder = new StringBuilder();
		}
		if (!column.contains(CoreConstants.Separator.UPDERLINE)) {
			column = SqlUtil.formateToColumn(column);
		}
		builder.append(CoreConstants.Separator.BLANK).append(column).append(CoreConstants.Separator.BLANK);
		return this;
	}

	
	@Override
	public CustomSql operator(ESQLOperator operator) {
		if (builder == null) {
			throw new IllegalArgumentException("PrefixQuery can't be in the first!");
		}
		builder.append(operator);
		return this;
	}

	
	@Override
	public CustomSql value(Object value) {
		if (builder == null) {
			throw new IllegalArgumentException("Value can't be in the first!");
		}
		builder.append(CoreConstants.Separator.BLANK).append(SqlUtil.get(value)).append(CoreConstants.Separator.BLANK);
		return this;
	}

	
	@Override
	public String toString() {
		if (builder == null) {
			throw new IllegalArgumentException("Where sql doesn't init!");
		}
		String result = builder.toString();
		return result;
	}


}
