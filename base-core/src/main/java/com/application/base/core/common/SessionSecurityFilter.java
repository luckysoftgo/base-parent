package com.application.base.core.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.application.base.core.result.ResultDataVO;
import com.application.base.core.datasource.api.CacheReadAndWriteDataSessionFactory;
import com.application.base.core.utils.SpringContextHolder;
import com.application.base.utils.common.BaseStringUtil;
import com.application.base.utils.common.ExceptionInfo;
import com.application.base.utils.json.JsonConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.application.base.core.exception.CommonException;
import com.application.base.utils.common.Constants;

/**
 * @desc 登陆过滤器操作.
 * @author 孤狼
 */
public class SessionSecurityFilter extends OncePerRequestFilter {

	private static final String AJAX_HEADER = "XMLHttpRequest";
	private static Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
	private static Properties properties = new Properties();
	private static final String TOKEN = "token";

	CacheReadAndWriteDataSessionFactory factory = SpringContextHolder.getBean("readAndWriteDataSessionFactory",CacheReadAndWriteDataSessionFactory.class);

	static {
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("properties/config.properties"));
		}
		catch (IOException e) {
			ExceptionInfo.exceptionInfo(e, logger);
			e.printStackTrace();
		}
	}

	boolean isValid(String uri) {
		boolean doFilter = true;
		String noFilterStr = properties.getProperty("noValidate");
		if (!StringUtils.isEmpty(noFilterStr)) {
			String[] notFilter = noFilterStr.split(Constants.Separator.COMMA);
			for (String s : notFilter) {
				if (uri.indexOf(s) != -1) {
					doFilter = !doFilter;
					break;
				}
			}
		}

		return doFilter;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding(Constants.CharSet.UTF8);
		PrintWriter out;
		String uri = request.getRequestURI();
		//打印参数
		printRequestParams(request);
		logger.debug("<=======requestURI:{}=======>", uri);
		boolean doFilter = isValid(uri);
		if (doFilter) {
			String sessionId = request.getParameter(TOKEN);
			// 如果没有获取sessionId,返回
			if (StringUtils.isEmpty(sessionId) && StringUtils.isEmpty(sessionId)) {
				out = response.getWriter();
				out.print(JsonConvertUtils.toJson(new ResultDataVO("10204","登陆超时")));
				return;
			}
			StringBuilder builder = new StringBuilder("");
			sessionId = builder.append(sessionId).toString();
			String json = factory.getRedisSessionFactory().getRedisSession().getData(sessionId);
			// 获取前台用户登录信息
			Integer userId = 0;
			try {
				//userId = LoginSessionUtils.getUserId(sessionId);
			}
			catch (CommonException e) {
					throw e;
			}
			// 如果根据sessionkey未能获取数据则认为没有登录或登录超时
			if (StringUtils.isEmpty(json) && userId == null) {
				out = response.getWriter();
				out.print(JsonConvertUtils.toJson(new ResultDataVO("10204","登陆超时")));
				return;
			}
			else {
				// 如果获取到了则返回
				//SysUser user = JsonConvertUtils.fromJson(json, SysUser.class);
				Object user = null;
				if (user == null && userId == 0) {
					out = response.getWriter();
					out.print(JsonConvertUtils.toJson(new ResultDataVO("10204","登陆超时")));
					return;
				}
				else {
					filterChain.doFilter(request, response);
				}
			}
		}
		else {
			filterChain.doFilter(request, response);
		}
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		if (header != null && AJAX_HEADER.equals(header)) {
			return true;
		}
		return false;
	}
	
   /**
	 * 请求参数的打印.
	 * @param request
	 */
	private void printRequestParams(HttpServletRequest request) {
		Map<String, Object> paramsMap = new HashMap<String, Object>(16);
		Enumeration<?> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String name = BaseStringUtil.stringValue(parameterNames.nextElement());
			try {
				String value = null;
				String[] values = request.getParameterValues(name);
				if (values != null && values.length == 1) {
					value = values[0];
					paramsMap.put(name, value);
				}
			} catch (Exception e) {
				logger.error("获取页面参数时异常，参数名称【{}】异常信息【{}】", name, e);
			}
		}
		logger.debug("\r\n— — — 请求url：【" + request.getRequestURL() + "】 \r\n— — — 参数：【" + JsonConvertUtils.toJson(paramsMap) + "】");
	}
			
}
