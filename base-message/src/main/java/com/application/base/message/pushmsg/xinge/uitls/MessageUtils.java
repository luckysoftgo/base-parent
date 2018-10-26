package com.application.base.message.pushmsg.xinge.uitls;

import com.tencent.xinge.*;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * @desc 消息构建
 * @author 孤狼
 */
public class MessageUtils {
	
	/**
	 * 允许推送的时间闭区间
	 * @return
	 */
	public static TimeInterval getDefaultTimeInterval(){
		return new TimeInterval(0, 0, 23, 59);
	}
	
	/**
	 * 允许推送的时间闭区间
	 * @param startHour
	 * @param startMin
	 * @param endHour
	 * @param endMin
	 * @return
	 */
	public static TimeInterval getTimeInterval(Integer startHour,Integer startMin,Integer endHour,Integer endMin){
		if (null==startHour){
			startHour = 0;
		}
		if (null==startMin){
			startMin = 0;
		}
		if (null==endHour){
			endHour = 0;
		}
		if (null==endMin){
			endMin = 0;
		}
		return new TimeInterval(startHour, startMin, endHour, endMin);
	}
	
	/**
	 * 通知消息被点击的事件;使用时候注意看 API
	 * @param actionType
	 * @param url
	 * @param confirmOnUrl
	 * @param activity
	 * @param atyAttrIntentFlag
	 * @param atyAttrPendingIntentFlag
	 * @param intent
	 * @return
	 */
	public static ClickAction getClickAction(Integer actionType,String url,Integer confirmOnUrl,String activity,Integer atyAttrIntentFlag,Integer atyAttrPendingIntentFlag,String intent){
		ClickAction  action = new ClickAction();
		if (null!=actionType){
			action.setActionType(actionType);
		}
		if (StringUtils.isNotBlank(url)){
			action.setUrl(url);
		}
		if (null!=confirmOnUrl){
			action.setConfirmOnUrl(confirmOnUrl);
		}
		if (StringUtils.isNotBlank(activity)){
			action.setActivity(activity);
		}
		if (null!=atyAttrIntentFlag){
			action.setAtyAttrIntentFlag(atyAttrIntentFlag);
		}
		if (null!=atyAttrPendingIntentFlag){
			action.setAtyAttrPendingIntentFlag(atyAttrPendingIntentFlag);
		}
		if (StringUtils.isNotBlank(intent)){
			action.setIntent(intent);
		}
		return action;
	}
	
	/**
	 * 默认消息展现
	 * @return
	 */
	public static Style getDefaultStyle(){
		return new Style(3,1,1,1,0);
	}
	
	
	/**
	 * 默认消息展现
	 * @return
	 */
	public static Style getStyle(String iconRes,String ringRaw,String smallIcon){
		Style style = getDefaultStyle();
		if (StringUtils.isBlank(iconRes)){
			style.setIconRes("xg.png");
		}else {
			style.setIconRes(iconRes);
		}
		if (StringUtils.isBlank(ringRaw)){
			style.setRingRaw("ring.mp3");
		}else {
			style.setRingRaw(ringRaw);
		}
		if (StringUtils.isBlank(smallIcon)){
			style.setSmallIcon("xg.png");
		}else {
			style.setSmallIcon(smallIcon);
		}
		return style;
	}
	
	
	/**
	 * 获得 Message 的消息实例
	 * @param title
	 * @param context
	 * @return
	 */
	public static Message getMessage(String title, String context){
		return getMessage(title, context,null,null,null,null,null);
	}
	
	/**
	 * 获得 Message 的消息实例
	 * @param title
	 * @param context
	 * @param custom
	 * @return
	 */
	public static Message getMessage(String title, String context,Map<String,Object> custom){
		return getMessage(title, context,null,null,null,null,custom);
	}
	
	/**
	 * 获得 Message 的消息实例
	 * @param title
	 * @param context
	 * @param interval
	 * @return
	 */
	public static Message getMessage(String title, String context,TimeInterval interval){
		return getMessage(title, context,interval,null,null,null,null);
	}
	
	/**
	 * 获得 Message 的消息实例
	 * @param title
	 * @param context
	 * @param interval
	 * @param style
	 * @return
	 */
	public static Message getMessage(String title, String context,TimeInterval interval,Style style){
		return getMessage(title, context,interval,null,style,null,null);
	}
	
	/**
	 * 获得 Message 的消息实例
	 * @param title
	 * @param context
	 * @param interval
	 * @param style
	 * @param action
	 * @return
	 */
	public static Message getMessage(String title, String context,TimeInterval interval,Style style,ClickAction action){
		return getMessage(title, context,interval,null,style,action,null);
	}
	
	/**
	 * 获得 Message 的消息实例
	 * @param title
	 * @param context
	 * @param interval
	 * @param type
	 * @param style
	 * @param action
	 * @param custom
	 * @return
	 */
	public static Message getMessage(String title, String context,TimeInterval interval,Integer type,Style style,ClickAction action,Map<String,Object> custom){
		Message message = new Message();
		message.setTitle(title);
		message.setContent(context);
		if (null!=interval){
			message.addAcceptTime(interval);
		}else {
			message.addAcceptTime(getDefaultTimeInterval());
		}
		if (null!=type){
			message.setType(type);
		}else {
			message.setType(Message.TYPE_NOTIFICATION);
		}
		if (null!=style){
			message.setStyle(style);
		}else {
			message.setStyle(getDefaultStyle());
		}
		if (null!=action){
			message.setAction(action);
		}
		if (null!=custom){
			message.setCustom(custom);
		}
		return message;
	}
	
	/**
	 * IOS message 的实例构建
	 * @param alert alert 信息
	 * @return
	 */
	public static MessageIOS getMessageIOS(String alert){
		return getMessageIOS(null,alert,null,null,null);
	}
	
	/**
	 * IOS message 的实例构建
	 * @param interval 执行区间
	 * @param alert alert 信息
	 * @return
	 */
	public static MessageIOS getMessageIOS(TimeInterval interval,String alert){
		return getMessageIOS(interval,alert,null,null,null);
	}
	
	/**
	 * IOS message 的实例构建
	 * @param alert alert 信息
	 * @param custom 参数
	 * @return
	 */
	public static MessageIOS getMessageIOS(String alert,Map<String,Object> custom){
		return getMessageIOS(null,alert,null,null,custom);
	}
	
	/**
	 * IOS message 的实例构建
	 * @param interval 执行区间
	 * @param alert alert 信息
	 * @param badge 角标
	 * @return
	 */
	public static MessageIOS getMessageIOS(TimeInterval interval,String alert,Integer badge){
		return getMessageIOS(interval,alert,badge,null,null);
	}
	
	/**
	 * IOS message 的实例构建
	 * @param interval 执行区间
	 * @param alert alert 信息
	 * @param sound s声音
	 * @param custom
	 * @return
	 */
	public static MessageIOS getMessageIOS(TimeInterval interval,String alert,String sound,Map<String,Object> custom){
		return getMessageIOS(interval,null,null,sound,custom);
	}
	
	/**
	 * IOS message 的实例构建
	 * @param interval 执行区间
	 * @param alert
	 * @param badge 角标
	 * @param sound s声音
	 * @param custom
	 * @return
	 */
	public static MessageIOS getMessageIOS(TimeInterval interval,String alert,Integer badge,String sound,Map<String,Object> custom){
		MessageIOS messageIOS = new MessageIOS();
		if (null!=interval){
			messageIOS.addAcceptTime(interval);
		}else {
			messageIOS.addAcceptTime(getDefaultTimeInterval());
		}
		if (StringUtils.isNotBlank(alert)){
			messageIOS.setAlert(alert);
		}
		if (null !=badge){
			messageIOS.setBadge(badge);
		}
		if (StringUtils.isNotBlank(sound)){
			messageIOS.setSound(sound);
		}
		if (null !=custom && custom.size()>0){
			messageIOS.setCustom(custom);
		}
		return messageIOS;
	}
	
}
