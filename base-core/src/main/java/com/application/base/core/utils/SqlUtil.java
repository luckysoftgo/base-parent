package com.application.base.core.utils;

import com.application.base.utils.date.DateUtils;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.application.base.core.constant.CoreConstants;
import com.application.base.core.datasource.param.ESQLOperator;
import com.application.base.core.datasource.param.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *@desc SQL语句工具类
 *@author 孤狼
 */
public class SqlUtil {

    private static Logger logger = LoggerFactory.getLogger(SqlUtil.class.getName());
	public static final int FROUNT = 1;
	public static final int BACK = 2;
	public static final int ALL = 3;
	
	/**
	 * 根据参数返回相应SQL语句中拼写的值
	 * @param value
	 * @return
	 * String
	 */
	@SuppressWarnings("unchecked")
	public static String get(Object value) {
		StringBuilder builder = new StringBuilder();
		if (value instanceof String) {
			builder.delete(0, builder.length());
			builder.append(CoreConstants.Separator.SINGLE_QUOTES).append(String.valueOf(value)).append(CoreConstants.Separator.SINGLE_QUOTES);
			return builder.toString();
		} else if (value instanceof Date) {
			builder.delete(0, builder.length());
			String dateStr = DateUtils.formatDateStr((Date) value);
			if (StringUtils.isEmpty(dateStr)) {
				throw new IllegalArgumentException("Date value can't be null or date value can't be formated!");
			}
			builder.append(CoreConstants.Separator.BLANK).append(CoreConstants.Separator.SINGLE_QUOTES).append(dateStr).append(CoreConstants.Separator.SINGLE_QUOTES).append(CoreConstants.Separator.BLANK);
			return builder.toString();
		} else if (value instanceof Number) {
			builder.delete(0, builder.length());
			builder.append(CoreConstants.Separator.BLANK).append(value).append(CoreConstants.Separator.BLANK);
			return builder.toString();
		} else if (value instanceof String[]) {
			builder.delete(0, builder.length());
			builder.append(CoreConstants.Separator.BLANK).append(CoreConstants.Separator.LBRACKET);
			String[] valueArr = (String[]) value;
			for (int i = 0; i < valueArr.length; i++) {
				String vStr = valueArr[i];
				builder = appendStringValue(builder, vStr);
				if (i != valueArr.length - 1) {
					builder.append(CoreConstants.Separator.COMMA);
				}
			}
			builder.append(CoreConstants.Separator.RBRACKET).append(CoreConstants.Separator.BLANK);
			return builder.toString();
		}else if (value instanceof Number[]) {
			builder.delete(0, builder.length());
			builder.append(CoreConstants.Separator.BLANK).append(CoreConstants.Separator.LBRACKET);
			Integer[] valueArr = (Integer[]) value;
			for (int i = 0; i < valueArr.length; i++) {
				Integer v = valueArr[i];
				if (v!=null) {
					builder.append(v);
					if (i != valueArr.length - 1) {
						builder.append(CoreConstants.Separator.COMMA);
					}
				}
			}
			builder.append(CoreConstants.Separator.RBRACKET).append(CoreConstants.Separator.BLANK);
			return builder.toString();
		} else if (value instanceof List) {
			builder.delete(0, builder.length());
			builder.append(CoreConstants.Separator.BLANK).append(CoreConstants.Separator.LBRACKET);
			List<Object> valueArr = List.class.cast(value);
			for (int i = 0; i < valueArr.size(); i++) {
				Object o = valueArr.get(i);
				if(!(o instanceof String) && !(o instanceof Number)) {
					throw new IllegalArgumentException();
				}
				if (o!=null) {
					if(o instanceof String) {
						builder = appendStringValue(builder, o.toString());
					}
					if(o instanceof Number) {
						builder.append(o);
					}
				}
				if (i != valueArr.size() - 1) {
					builder.append(CoreConstants.Separator.COMMA);
				}
			}
			builder.append(CoreConstants.Separator.RBRACKET).append(CoreConstants.Separator.BLANK);
			return builder.toString();
		}else {
			builder.delete(0, builder.length());
			builder.append(CoreConstants.Separator.BLANK).append(String.valueOf(value)).append(CoreConstants.Separator.BLANK);
			return builder.toString();
		}
	}

	private static StringBuilder appendStringValue(StringBuilder builder, String vStr) {
		builder.append(CoreConstants.Separator.SINGLE_QUOTES).append(String.valueOf(vStr))
		.append(CoreConstants.Separator.SINGLE_QUOTES);
		return builder;
	}

	/**
	 * 生成 LIKE值
	 * @param value
	 * @param type FROUNT,BACK,默认ALL
	 * @return
	 * String
	 */
	public static String likeValue(String value, int type) {
		StringBuilder builder = new StringBuilder();
		switch (type) {
		case FROUNT:
			return builder.append(CoreConstants.Separator.PERCENT).append(value).toString();
		case BACK:
			return builder.append(value).append(CoreConstants.Separator.PERCENT).toString();
		default:
			return builder.append(CoreConstants.Separator.PERCENT).append(value).append(CoreConstants.Separator.PERCENT).toString();
		}
	}

	/**
	 * 生成更新语句
	 * @param param
	 * @return
	 * String
	 *
	 */
	public static String updateSql(Param param) {
		StringBuilder builder = new StringBuilder();
		Map<String, Object> map = param.get();
		if (map == null) {
			throw new RuntimeException("update param is null");
		}
		Set<String> keys = map.keySet();
        builder.append(CoreConstants.Separator.BLANK).append(ESQLOperator.SET);
		for (String column : keys) {
            builder.append(CoreConstants.Separator.BLANK).append(formateToColumn(column)).append(ESQLOperator.EQ)
					.append(SqlUtil.get(map.get(column))).append(CoreConstants.Separator.COMMA);
		}
		String result = builder.substring(CoreConstants.WrapperExtend.ZERO, builder.length() - 1).toString();
		return result;
	}
	
    /**
     * 打印sql语句
     *
     * @param sqlId
     * @param map
     */
    public static  String getSql(SqlSession sqlSession, String sqlId, Map<String, Object> map) {
        Configuration configuration = sqlSession.getConfiguration();
        String sql = MyBatisSqlUtils.getSql(configuration, configuration.getMappedStatement(sqlId).getBoundSql(map));
        logger.debug("printlnSQL [{}]", sql);
        return sql;
    }

    public static String formateToColumn(String columnName) {
        char[] arr = columnName.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char c : arr) {
            if (!Character.isUpperCase(c)) {
				builder.append(c);
			} else {
				builder.append(CoreConstants.Separator.UPDERLINE).append(c);
			}

        }
        return builder.toString().toUpperCase();
    }

}
