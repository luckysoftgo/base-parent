package com.application.base.message.pushmsg.xinge.uitls;

import com.tencent.xinge.XingeApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @desc 信鸽消息推送工具类.
 * @author 孤狼
 */
@Component
public class XinGeAppUtils {
	
	@Autowired
	private com.application.base.message.pushmsg.xinge.uitls.XinGeConfig config;
	
	String appOnline="2";
	
	/**
	 * 是不是线上环境
	 * @return
	 */
	public boolean getIsAppStore(){
		if (appOnline.equals(config.getIosConfigurationEnvironment())) {
			return false;
		}
		return false;
	}
	
	/**
	 * 获得 Android 的实例
	 * @return
	 */
	public  XingeApp getAndroidXinge(){
		return new XingeApp(config.getAndroidAccessId(), config.getAndroidSecretKey());
	}
	
	/**
	 * 获得 ios 的实例
	 * @return
	 */
	public  XingeApp getIosXinge(){
		return new XingeApp(config.getIosAccessId(), config.getIosSecretKey());
	}
	
	/**
	 * 获得 ios app store 的实例
	 * @return
	 */
	public  XingeApp getIosXingeAppStore(){
		return new XingeApp(config.getIosAccessIdAppStore(), config.getIosSecretKeyAppStore());
	}
	
}
