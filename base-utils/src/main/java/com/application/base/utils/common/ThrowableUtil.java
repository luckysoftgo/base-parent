package com.application.base.utils.common;

import com.application.base.utils.json.JsonConvertUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 孤狼
 * @NAME: ThrowableUtil
 * @DESC: ThrowableUtil类设计
 **/
public class ThrowableUtil {
	
	/**
	 * 测试.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
		  int a	=1/0;
		}catch (Exception e){
			List<ThrowableInfo> list = getThrowInfos(e);
			System.out.println(JsonConvertUtils.toJson(list));
		}
	}
	
	
	/**
	 * 获得异常信息.
	 * @param throwable
	 * @return
	 */
	public static List<ThrowableInfo> getThrowInfos(Throwable throwable){
		List<ThrowableInfo> infoList = new ArrayList<>();
		if (throwable!=null){
			StackTraceElement[] elements = throwable.getStackTrace();
			for (StackTraceElement element : elements) {
				String className = element.getClassName();
				String fileName = element.getFileName();
				Integer lineNumber= element.getLineNumber();
				String methodName = element.getMethodName();
				Boolean nativeMethod = element.isNativeMethod();
				String throwInfo = element.toString();
				ThrowableInfo throwableInfo = new ThrowableInfo();
				throwableInfo.setClassName(className);
				throwableInfo.setFileName(fileName);
				throwableInfo.setLineNumber(lineNumber);
				throwableInfo.setMethodName(methodName);
				throwableInfo.setNativeMethod(nativeMethod);
				throwableInfo.setThrowInfo(throwInfo);
				throwableInfo.setStackTrace(element);
				infoList.add(throwableInfo);
			}
		}
		return infoList;
	}
	
	/**
	 * 异常信息.
	 */
	final static class ThrowableInfo{
		/**
		 * 异常的类名
		 */
		private String className;
		/**
		 * 异常的文件名
		 */
		private String fileName ;
		/**
		 * 异常所在类的行数
		 */
		private Integer lineNumber;
		/**
		 * 异常发生的方法名
		 */
		private String methodName;
		/**
		 * 是否是nativeMethod
		 */
		private Boolean nativeMethod;
		/**
		 * 异常信息
		 */
		private String throwInfo;
		/**
		 * 堆栈本身信息
		 */
		private StackTraceElement stackTrace;
		
		public String getClassName() {
			return className;
		}
		
		public void setClassName(String className) {
			this.className = className;
		}
		
		public String getFileName() {
			return fileName;
		}
		
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		
		public Integer getLineNumber() {
			return lineNumber;
		}
		
		public void setLineNumber(Integer lineNumber) {
			this.lineNumber = lineNumber;
		}
		
		public String getMethodName() {
			return methodName;
		}
		
		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}
		
		public Boolean getNativeMethod() {
			return nativeMethod;
		}
		
		public void setNativeMethod(Boolean nativeMethod) {
			this.nativeMethod = nativeMethod;
		}
		
		public String getThrowInfo() {
			return throwInfo;
		}
		
		public void setThrowInfo(String throwInfo) {
			this.throwInfo = throwInfo;
		}
		
		public StackTraceElement getStackTrace() {
			return stackTrace;
		}
		
		public void setStackTrace(StackTraceElement stackTrace) {
			this.stackTrace = stackTrace;
		}
	}
}
