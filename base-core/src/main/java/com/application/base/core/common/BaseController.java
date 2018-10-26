package com.application.base.core.common;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import com.application.base.utils.common.BaseEntity;
import com.application.base.utils.common.BaseStringUtil;
import com.application.base.utils.common.ExceptionInfo;
import com.application.base.utils.page.Common;
import com.application.base.utils.page.PageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.application.base.core.apisupport.BaseResultSupport;
import com.application.base.core.exception.BusinessException;
import com.application.base.core.exception.InfoEmptyException;
import com.application.base.core.exception.ValideErrorException;
import com.application.base.utils.common.Constants;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

/**
 * @desc 基础控制Controller
 * @author 孤狼
 */
public class BaseController extends BaseResultSupport {

    private Logger logger = LoggerFactory.getLogger(BaseController.class.getName());
    
    private static final int DEFAULT_PAGE_NO = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;
    
	protected static String PAGE_NO = "pageNo";
	protected static String PAGE_SIZE = "pageSize";
	
	/**
	 * 请求信息.
	 */
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	
	/**
	 * 初始化map大小
	 */
	static int MAP_SIZE = 16;
	
	/**
	 * 给请求赋值.
	 *
	 * @param request
	 * @param response
	 */
	@ModelAttribute
	protected void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}
	
	/**
     * 对外打印JSON数据
     *
     * @param data
     */
    protected void printJsonWriter(String data) {
		//设置响应头
		HttpServletResponse resultResponse = setBasicResponseHeader(response,"application/json");
        try {
            writeContext(data, resultResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * 对外打印JSON数据
	 *
	 * @param response
	 * @param data
	 */
	protected void printJsonWriter(HttpServletResponse response, String data) {
		//设置响应头
		HttpServletResponse resultResponse = setBasicResponseHeader(response,"application/json");
		try {
			writeContext(data, resultResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 对外打印 txt 数据
	 *
	 * @param data
	 */
	protected void printTxtWriter(String data) {
		//设置响应头
		HttpServletResponse resultResponse = setBasicResponseHeader(response,"text/plain");
		try {
			writeContext(data, resultResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 对外打印 txt 数据
	 *
	 * @param response
	 * @param data
	 */
	protected void printTxtWriter(HttpServletResponse response, String data) {
		//设置响应头
		HttpServletResponse resultResponse = setBasicResponseHeader(response,"text/plain");
		try {
			writeContext(data, resultResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 对外打印 html 数据
	 *
	 * @param data
	 */
	protected void printHtmlWriter(String data) {
		//设置响应头
		HttpServletResponse resultResponse = setBasicResponseHeader(response,"text/html");
		try {
			writeContext(data, resultResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 对外打印 html 数据
	 *
	 * @param response
	 * @param data
	 */
	protected void printHtmlWriter(HttpServletResponse response, String data) {
		//设置响应头
		HttpServletResponse resultResponse = setBasicResponseHeader(response,"text/html");
		try {
			writeContext(data, resultResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 对外打印 xml 数据
	 *
	 * @param data
	 */
	protected void printXmlWriter(String data) {
		//设置响应头
		HttpServletResponse resultResponse = setBasicResponseHeader(response,"text/xml");
		try {
			writeContext(data, resultResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 对外打印 xml 数据
	 *
	 * @param response
	 * @param data
	 */
	protected void printXmlWriter(HttpServletResponse response, String data) {
		//设置响应头
		HttpServletResponse resultResponse = setBasicResponseHeader(response,"text/xml");
		try {
			writeContext(data, resultResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    /**
     * 设置响应头
     *
     * @param currentResponse
     */
    protected HttpServletResponse setBasicResponseHeader(HttpServletResponse currentResponse,String type) {
		HttpServletResponse resultResponse=null;
    	if (currentResponse==null){
    		if (response != null){
				response.setContentType(type + ";charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Pragma", "No-cache");
				response.setDateHeader("Expires", 0);
				response.setCharacterEncoding(Constants.CharSet.UTF8);
				resultResponse = response;
			}
		}else {
			currentResponse.setContentType(type + ";charset=UTF-8");
			currentResponse.setHeader("Cache-Control", "no-cache");
			currentResponse.setHeader("Pragma", "No-cache");
			currentResponse.setDateHeader("Expires", 0);
			currentResponse.setCharacterEncoding("UTF-8");
			resultResponse = currentResponse;
		}
		return resultResponse;
    }

    /**
     * 数据打印功能
     *
     * @param data
     * @param resultResponse
     * @throws IOException
     */
    private void writeContext(String data, HttpServletResponse resultResponse) throws IOException {
		//设置响应头
		if (resultResponse!=null){
			PrintWriter printWriter = resultResponse.getWriter();
			printWriter.write(data);
			printWriter.flush();
			printWriter.close();
		}
    }
	
    /**
	 * 获取请求客户端的真实ip地址
	 * @param request 请求对象
	 * @return ip地址
	 */
	public String getIpAddr(HttpServletRequest request) {
		if (request==null){
			request = this.request;
		}
		String requestIp = request.getHeader("x-forwarded-for");
		String str1=" unknown ",str2="unknown";
		if (requestIp == null || requestIp.length() == 0 || str1.equalsIgnoreCase(requestIp)) {
			requestIp = request.getHeader("X-Real-IP");
		}
		if (requestIp == null || requestIp.length() == 0 || str2.equalsIgnoreCase(requestIp)) {
			requestIp = request.getHeader("Proxy-Client-IP");
		}
		if (requestIp == null || requestIp.length() == 0 || str2.equalsIgnoreCase(requestIp)) {
			requestIp = request.getHeader("WL-Proxy-Client-IP");
		}
		if (requestIp == null || requestIp.length() == 0 || str2.equalsIgnoreCase(requestIp)) {
			requestIp = request.getHeader("HTTP_CLIENT_IP");
		}
		if (requestIp == null || requestIp.length() == 0 || str2.equalsIgnoreCase(requestIp)) {
			requestIp = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (requestIp == null || requestIp.length() == 0 || str2.equalsIgnoreCase(requestIp)) {
			requestIp = request.getRemoteAddr();
		}
		return requestIp;
	}
	
	
	/**
	 * web应用绝对路径
	 * @param request 请求对象
	 * @return 绝对路径
	 */
	public static String getBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		return basePath;
	}
	
	/**
	 * 所有ActionMap 统一从这里获取
	 * @return
	 */
	public Map<String, Object> getRootPageMap(HttpServletRequest request) {
		return getRequestParams(request,true);
	}
	
	/**
	 * 所有ActionMap 统一从这里获取
	 * @return
	 */
	public Map<String, Object> getRootParamMap(HttpServletRequest request) {
		return getRequestParams(request,false);
	}
	
	public ModelAndView forword(String viewName, Map context) {
		return new ModelAndView(viewName, context);
	}
	
	public ModelAndView error(String errMsg) {
		return new ModelAndView("error");
	}
	
	/**
	 * 获得 request 请求的参数
	 * @return
	 */
	public Map<String, Object> getRequestParams(Map<String, Object> params,boolean isPage) {
		Map<String, Object> resultMap = new HashMap<>(MAP_SIZE);
		if (isPage){
			// 处理分页参数
			Object pageNoStr = params.get(Constants.SqlConstants.PAGE_NO);
			Object pageSizeStr = params.get(Constants.SqlConstants.PAGE_SIZE);
			// 起始页
			int pageNo = BaseStringUtil.intValue(pageNoStr) == 0 ? DEFAULT_PAGE_NO : BaseStringUtil.intValue(pageNoStr) ;
			// 每页显示的条数
			int pageSize = BaseStringUtil.intValue(pageSizeStr) == 0 ? DEFAULT_PAGE_SIZE : BaseStringUtil.intValue(pageSizeStr);
			resultMap.put(Constants.SqlConstants.PAGE_NO, pageNo);
			resultMap.put(Constants.SqlConstants.PAGE_SIZE, pageSize);
		}
		for (Map.Entry<String,Object>  entry : params.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (BaseStringUtil.isNotEmpty(value)){
				resultMap.put(key,value);
			}
		}
		return resultMap;
	}
	
	/**
	 * 获得 request 请求的参数
	 * @param request
	 * @return
	 */
    public Map<String, Object> getRequestParams(HttpServletRequest request,boolean isPage) {
    	if (request==null){
    		request = this.request;
		}
        Map<String, Object> paramsMap = new HashMap<String, Object>(MAP_SIZE);
        if (isPage){
			// 处理分页参数
			String pageNoStr = request.getParameter(Constants.SqlConstants.PAGE_NO);
			String pageSizeStr = request.getParameter(Constants.SqlConstants.PAGE_SIZE);
			// 起始页
			int pageNo = BaseStringUtil.intValue(pageNoStr) == 0 ? DEFAULT_PAGE_NO : BaseStringUtil.intValue(pageNoStr) ;
			// 每页显示的条数
			int pageSize = BaseStringUtil.intValue(pageSizeStr) == 0 ? DEFAULT_PAGE_SIZE : BaseStringUtil.intValue(pageSizeStr);
			paramsMap.put(Constants.SqlConstants.PAGE_NO, pageNo);
			paramsMap.put(Constants.SqlConstants.PAGE_SIZE, pageSize);
		}
        Enumeration<?> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = BaseStringUtil.stringValue(parameterNames.nextElement());
            try {
                String value = null;
                String[] values = request.getParameterValues(name);
                if (values != null && values.length == 1) {
                    value = values[0];
                    paramsMap.put(name, value.trim());
                }
            } catch (Exception e) {
                logger.error("获取页面参数时异常，参数名称【{}】异常信息【{}】", name, e);
            }
        }
        return paramsMap;
    }
	
	
	/**
	 * Controller 全局异常处理
	 * @param e
	 */
	@ExceptionHandler
	public void exception(Exception e) {
		// 添加自己的异常处理逻辑，如日志记录
		if (!(e instanceof BusinessException)) {
			e.printStackTrace();
			ExceptionInfo.exceptionInfo(e, logger);
			e = new BusinessException(e);
		}
		printJsonWriter(response, resultJSON((BusinessException) e));
	}
	
	/**
	 * 非空验证统一方法
	 * @param args
	 */
	protected Map<String ,Object> nullAbleValidation(Map<String, Object> params, String... args) {
		if (args == null || args.length == 0) {
			throw new InfoEmptyException();
		}
		for(String key:args){
			if(StringUtils.isEmpty(params.get(key))) {
				throw new InfoEmptyException();
			}
		}
		return params;
	}
	
	/**
     * 非空验证统一方法
     * @param request
     * @param args
     */
    protected Map<String ,Object> nullAbleValidation(HttpServletRequest request, String... args) {
        Map<String, Object> params = getRequestParams(request,true);
        if (args == null || args.length == 0) {
			throw new InfoEmptyException();
		}
        for(String key:args){
            if(StringUtils.isEmpty(params.get(key))) {
				throw new InfoEmptyException();
			}
        }
        return params;
    }

    protected Object validation(HttpServletRequest request, Validator validator) {
        Map<String, Object> param = getRequestParams(request,true);
        return validator.valid(param);
    }
	
	/**
	 * 校验器
	 */
	protected interface Validator {
		/**
		 * 校验 id 状态
		 * @param map
		 * @return
		 */
        Object valid(Map<String, Object> map);
    }

    /**
     * id校验
     *
     * @author rocky
     */
    public class IdValid implements Validator {
        @Override
		public Object valid(Map<String, Object> map) {
        	String idStr = "id";
            if (StringUtils.isEmpty(map.get(idStr))) {
				throw new InfoEmptyException();
			}
            int id;
            try {
                id = BaseStringUtil.intValue(map.get(idStr));
            } catch (Exception e) {
               throw new ValideErrorException();
            }
            return id;
        }

        public int getValidId(Map<String, Object> map) {
            return BaseStringUtil.intValue(valid(map));
        }
    }

    /**
     * uuid校验
     *
     * @author rocky
     */
    public class UuidValid implements Validator {
        @Override
		public Object valid(Map<String, Object> map) {
            if (StringUtils.isEmpty(map.get(BaseEntity.FIELD_UUID))) {
				throw new InfoEmptyException();
			}
            return BaseStringUtil.stringValue(map.get(BaseEntity.FIELD_UUID));
        }

        public String getValidUUID(Map<String, Object> map) {
            return (String) valid(map);
        }
    }

    /**
     * trim value
     * @param map
     * @param key
     * @return
     */
    public Object getMapVal(Map<String, Object> map, String key) {
        Object object = map.get(key);
        if (object instanceof String) {
            if (object == null || "".equals(object)) {
                return "";
            } else {
                return ((String) object).trim();
            }
        }
        return object;
    }
	
	/**
	 * 底层分页设置方法
	 * @param pageNo
	 * @param pageSize
	 */
	public PageView findByPage(String pageNo, String pageSize ){
		PageView pageView = null;
		//默认显示.
		if(Common.isEmpty(pageNo) && Common.isEmpty(pageSize)){
			pageView = new PageView(1);
			//输入有页数,默认展示条数.
		}else if(!Common.isEmpty(pageNo) && Common.isEmpty(pageSize) ){
			pageView = new PageView(Integer.parseInt(pageNo));
			//设置页数和每页显示的条数.
		}else if(Common.isEmpty(pageNo) && !Common.isEmpty(pageSize)) {
			pageView = new PageView(1,Integer.parseInt(pageSize));
			//设置页数和每页显示的条数.
		}else {
			pageView = new PageView(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
		}
		return pageView;
	}
	
	/**
	 * 
	 * @param jspTemplateURL 重定向JSP模板
	 * @param staticHtmlPath 目标静态化路径
	 */
	public void jsp2htmlBuilder(String jspTemplateURL, String staticHtmlPath) {
		FileOutputStream fos = null;
		try {
			RequestDispatcher rd = session.getServletContext().getRequestDispatcher(jspTemplateURL);
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			final ServletOutputStream stream = new ServletOutputStream() {
				@Override
				public void write(byte[] data, int offset, int length) {
					os.write(data, offset, length);
				}
				@Override
				public void write(int b) throws IOException {
					os.write(b);
				}
				@Override
				public boolean isReady() {
					return false;
				}
				@Override
				public void setWriteListener(WriteListener writeListener) {
					
				}
			};
			final PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
			HttpServletResponse rep = new HttpServletResponseWrapper(response) {
				@Override
				public ServletOutputStream getOutputStream() {
					return stream;
				}
				@Override
				public PrintWriter getWriter() {
					return pw;
				}
			};
			rd.include(request, rep);
			pw.flush();
			fos = new FileOutputStream(staticHtmlPath);
			os.writeTo(fos);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
