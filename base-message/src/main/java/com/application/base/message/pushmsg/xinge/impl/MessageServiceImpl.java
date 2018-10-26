package com.application.base.message.pushmsg.xinge.impl;

import com.application.base.message.pushmsg.xinge.MessageService;
import com.application.base.message.pushmsg.xinge.uitls.MessageUtils;
import com.application.base.message.pushmsg.xinge.uitls.XinGeAppUtils;
import com.application.base.message.pushmsg.xinge.uitls.DeviceType;
import com.application.base.utils.json.JsonConvertUtils;
import com.tencent.xinge.Message;
import com.tencent.xinge.MessageIOS;
import com.tencent.xinge.TagTokenPair;
import com.tencent.xinge.XingeApp;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @desc 消息的实现
 * @author 孤狼
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {
	
	static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class.getName());
	
	@Autowired
	private XinGeAppUtils appUtils;
	/**
	 * 示例.
	 */
	private XingeApp androidXinge;
	private XingeApp iosXinge;
	private XingeApp iosXingeAppStore;
	/**
	 * 是否是 ios 的线上环境.
	 */
	private boolean isStore = false;
	/**
	 * 定义方式.
	 */
	String android = "android",ios = "ios";
	String pushId = "push_id",deviceNum = "device_num";
	String retCode = "ret_code",errMsg="err_msg";
	String result = "result",tags="tags",total="total",tokens="tokens";
	
	@PostConstruct
	private void initXinGe(){
		if (null==androidXinge){
			androidXinge = appUtils.getAndroidXinge();
		}
		if (null==iosXinge){
			iosXinge = appUtils.getIosXinge();
		}
		if (null==iosXingeAppStore){
			iosXingeAppStore = appUtils.getIosXingeAppStore();
		}
		isStore = appUtils.getIsAppStore();
	}
	
	/**
	 * 获得消息推送结果.
	 * @return
	 */
	private Map<String, Object> getSimpleResult(JSONObject object){
		Map<String, Object> resultMap = new HashMap<>(16);
		try {
			if (null != object){
				int code = object.getInt(retCode);
				resultMap.put(retCode,code);
				if (code!=0){
					resultMap.put(errMsg,object.getString(errMsg));
				}
				return resultMap;
			}
		}catch (Exception ex){
			logger.error("解析返回的字符串异常,字符串是:{}",object.toString());
		}
		return resultMap;
	}
	
	/**
	 *获得返回的结果
	 * @param object
	 * @param key
	 * @return
	 */
	private Map<String,Object> getObjectResult(JSONObject object,String key){
		Map<String, Object> resultMap = new HashMap<>(16);
		try {
			if (null != object){
				int code = object.getInt(retCode);
				resultMap.put(retCode,code);
				if (code!=0){
					resultMap.put(errMsg,object.getString(errMsg));
				}else {
					object = object.getJSONObject(result);
					if (key.equalsIgnoreCase(pushId)){
						resultMap.put(pushId,object.getString(pushId));
					}
					if (key.equalsIgnoreCase(deviceNum)){
						resultMap.put(deviceNum,object.getInt(deviceNum));
					}
				}
				return resultMap;
			}
		}catch (Exception ex){
			logger.error("解析返回的字符串异常,字符串是:{}",object.toString());
		}
		return resultMap;
	}
	
	/**
	 *获取结果
	 * @param object
	 * @param isTotal
	 * @param operateTag
	 * @return
	 */
	private Map<String,Object> getArrayResult(JSONObject object,boolean isTotal,String operateTag){
		Map<String, Object> resultMap = new HashMap<>(16);
		try {
			if (null != object){
				int code = object.getInt(retCode);
				resultMap.put(retCode,code);
				if (code!=0){
					resultMap.put(errMsg,object.getString(errMsg));
				}else {
					object = object.getJSONObject(result);
					if (isTotal){
						resultMap.put(total,object.getInt(total));
					}
					if (operateTag.equalsIgnoreCase(tags)){
						resultMap.put(tags,getArrayValues(object,tags));
					}
					if (operateTag.equalsIgnoreCase(tokens)){
						resultMap.put(tokens,getArrayValues(object,tokens));
					}
				}
				return resultMap;
			}
		}catch (Exception ex){
			logger.error("解析返回的字符串异常,字符串是:{}",object.toString());
		}
		return resultMap;
	}
	
	private List<String> getArrayValues(JSONObject object,String operateTag){
		List<String> valueList = new ArrayList<>();
		JSONArray array = object.getJSONArray(operateTag);
		for(int i=0;i<array.length();i++){
			Object value = array.get(i);
			valueList.add(Objects.toString(value));
		}
		return valueList;
	}
	
	
	/**
	 * 推送 android 信息
	 * @param cellphone
	 * @param message
	 * @return
	 */
	public JSONObject pushAndroidAccountMsg(String cellphone, Message message){
		logger.debug("pushToAccountUser:" + cellphone + "msg:" + message.toJson());
		JSONObject object = androidXinge.pushSingleAccount(0, cellphone, message);
		logger.debug("pushToAccountUser:" + cellphone + "result:" + JsonConvertUtils.toJson(object));
		return object;
	}
	
	/**
	 * 推送 ios 信息
	 * @param cellphone
	 * @param messageIos
	 * @return
	 */
	public JSONObject pushIosAccountMsg(String cellphone, MessageIOS messageIos){
		JSONObject object = null;
		if (isStore){
			logger.debug("pushToAccountUser:" + cellphone + "msg:" + messageIos.toJson());
			object = iosXingeAppStore.pushSingleAccount(0, cellphone, messageIos, XingeApp.IOSENV_PROD);
			logger.debug("pushToAccountUser:" + cellphone + "result:" + JsonConvertUtils.toJson(object));
		}else {
			logger.info("pushToAccountUser:" + cellphone + "msg:" + messageIos.toJson());
			object = iosXinge.pushSingleAccount(0, cellphone, messageIos,XingeApp.IOSENV_DEV);
			logger.info("pushToAccountUser:" + cellphone + "result:" + JsonConvertUtils.toJson(object));
		}
		return object;
	}
	
	
	@Override
	public Map<String, Object> pushToAccountUser(String cellphone, String title, String content, DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			Message message = MessageUtils.getMessage(title,content);
			object = pushAndroidAccountMsg(cellphone,message);
		}
		if (ios.equals(deviceType.getName())){
			MessageIOS messageIos = MessageUtils.getMessageIOS(content);
			object = pushIosAccountMsg(cellphone,messageIos);
		}
		return getSimpleResult(object);
	}
	
	@Override
	public Map<String, Object> pushToAccountUser(String cellphone, String title, String content, Map<String, Object> custom, DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			Message message = MessageUtils.getMessage(title,content,custom);
			object = pushAndroidAccountMsg(cellphone,message);
		}
		if (ios.equals(deviceType.getName())){
			MessageIOS messageIos = MessageUtils.getMessageIOS(content,custom);
			object = pushIosAccountMsg(cellphone,messageIos);
		}
		return getSimpleResult(object);
	}
	
	@Override
	public Map<String, Object> pushToAccountUser(String cellphone, Message message,MessageIOS messageIOS, DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			object = pushAndroidAccountMsg(cellphone,message);
		}
		if (ios.equals(deviceType.getName())){
			object = pushIosAccountMsg(cellphone,messageIOS);
		}
		return getSimpleResult(object);
	}
	
	/**
	 * 推送 android 信息
	 * @param token
	 * @param message
	 * @return
	 */
	public JSONObject pushAndroidDeviceMsg(String token, Message message){
		logger.debug("pushToDeviceUser:" + token + "msg:" + message.toJson());
		JSONObject object = androidXinge.pushSingleDevice(token, message);
		logger.debug("pushToDeviceUser:" + token + "result:" + JsonConvertUtils.toJson(object));
		return object;
	}
	
	/**
	 * 推送 ios 信息
	 * @param token
	 * @param messageIos
	 * @return
	 */
	public JSONObject pushIosDeviceMsg(String token, MessageIOS messageIos){
		JSONObject object = null;
		if (isStore){
			logger.debug("pushToDeviceUser:" + token + "msg:" + messageIos.toJson());
			object = iosXingeAppStore.pushSingleDevice(token, messageIos, XingeApp.IOSENV_PROD);
			logger.debug("pushToDeviceUser:" + token + "result:" + JsonConvertUtils.toJson(object));
		}else {
			logger.info("pushToDeviceUser:" + token + "msg:" + messageIos.toJson());
			object = iosXinge.pushSingleDevice(token, messageIos,XingeApp.IOSENV_DEV);
			logger.info("pushToDeviceUser:" + token + "result:" + JsonConvertUtils.toJson(object));
		}
		return object;
	}
	
	@Override
	public Map<String, Object> pushToDeviceUser(String token, String title, String content, DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			Message message = MessageUtils.getMessage(title,content);
			object = pushAndroidDeviceMsg(token,message);
		}
		if (ios.equals(deviceType.getName())){
			MessageIOS messageIos = MessageUtils.getMessageIOS(content);
			object = pushIosDeviceMsg(token,messageIos);
		}
		return getSimpleResult(object);
	}
	
	@Override
	public Map<String, Object> pushToDeviceUser(String token, String title, String content, Map<String, Object> custom, DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			Message message = MessageUtils.getMessage(title,content,custom);
			object = pushAndroidDeviceMsg(token,message);
		}
		if (ios.equals(deviceType.getName())){
			MessageIOS messageIos = MessageUtils.getMessageIOS(content,custom);
			object = pushIosDeviceMsg(token,messageIos);
		}
		return getSimpleResult(object);
	}
	
	@Override
	public Map<String, Object> pushToDeviceUser(String token, Message message, MessageIOS messageIOS,DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			object = pushAndroidDeviceMsg(token,message);
		}
		if (ios.equals(deviceType.getName())){
			object = pushIosDeviceMsg(token,messageIOS);
		}
		return getSimpleResult(object);
	}
	
	/**
	 * 推送 android 信息
	 * @param cellphones
	 * @param message
	 * @return
	 */
	public JSONObject pushAndroidAccountListMsg(List<String> cellphones, Message message){
		logger.debug("pushToAccountUsers:" + cellphones.toString() + "msg:" + message.toJson());
		JSONObject object = androidXinge.pushAccountList(0, cellphones, message);
		logger.debug("pushToAccountUsers:" + cellphones.toString() + "result:" + JsonConvertUtils.toJson(object));
		return object;
	}
	
	/**
	 * 推送 ios 信息
	 * @param cellphones
	 * @param messageIos
	 * @return
	 */
	public JSONObject pushIosAccountListMsg(List<String> cellphones, MessageIOS messageIos){
		JSONObject object = null;
		if (isStore){
			logger.debug("pushToAccountUsers:" + cellphones.toString() + "msg:" + messageIos.toJson());
			object = iosXingeAppStore.pushAccountList(0, cellphones, messageIos, XingeApp.IOSENV_PROD);
			logger.debug("pushToAccountUsers:" + cellphones.toString() + "result:" + JsonConvertUtils.toJson(object));
		}else {
			logger.info("pushToAccountUsers:" + cellphones.toString() + "msg:" + messageIos.toJson());
			object = iosXinge.pushAccountList(0, cellphones, messageIos,XingeApp.IOSENV_DEV);
			logger.info("pushToAccountUsers:" + cellphones.toString() + "result:" + JsonConvertUtils.toJson(object));
		}
		return object;
	}
	
	@Override
	public Map<String, Object> pushToAccountUsers(List<String> cellphones, String title, String content, DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			Message message = MessageUtils.getMessage(title,content);
			object = pushAndroidAccountListMsg(cellphones,message);
		}
		if (ios.equals(deviceType.getName())){
			MessageIOS messageIos = MessageUtils.getMessageIOS(content);
			object = pushIosAccountListMsg(cellphones,messageIos);
		}
		return getSimpleResult(object);
	}
	
	@Override
	public Map<String, Object> pushToAccountUsers(List<String> cellphones, String title, String content, Map<String, Object> custom, DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			Message message = MessageUtils.getMessage(title,content,custom);
			object = pushAndroidAccountListMsg(cellphones,message);
		}
		if (ios.equals(deviceType.getName())){
			MessageIOS messageIos = MessageUtils.getMessageIOS(content,custom);
			object = pushIosAccountListMsg(cellphones,messageIos);
		}
		return getSimpleResult(object);
	}
	
	@Override
	public Map<String, Object> pushToAccountUsers(List<String> cellphones, Message message,MessageIOS messageIOS, DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			object = pushAndroidAccountListMsg(cellphones,message);
		}
		if (ios.equals(deviceType.getName())){
			object = pushIosAccountListMsg(cellphones,messageIOS);
		}
		return getSimpleResult(object);
	}
	
	/**
	 * 推送 android 信息
	 * @param tokens
	 * @param message
	 * @return
	 */
	public JSONObject pushAndroidDeviceLMsg(List<String> tokens, Message message){
		logger.debug("pushToDeviceUsers:" + tokens.toString() + "msg:" + message.toJson());
		JSONObject object = androidXinge.pushAccountList(0, tokens, message);
		logger.debug("pushToDeviceUsers:" + tokens.toString() + "result:" + JsonConvertUtils.toJson(object));
		return object;
	}
	
	/**
	 * 推送 ios 信息
	 * @param tokens
	 * @param messageIos
	 * @return
	 */
	public JSONObject pushIosDeviceListMsg(List<String> tokens, MessageIOS messageIos){
		JSONObject object = null;
		if (isStore){
			logger.debug("pushToDeviceUsers:" + tokens.toString() + "msg:" + messageIos.toJson());
			object = iosXingeAppStore.pushAccountList(0, tokens, messageIos, XingeApp.IOSENV_PROD);
			logger.debug("pushToDeviceUsers:" + tokens.toString() + "result:" + JsonConvertUtils.toJson(object));
		}else {
			logger.info("pushToDeviceUsers:" + tokens.toString() + "msg:" + messageIos.toJson());
			object = iosXinge.pushAccountList(0, tokens, messageIos,XingeApp.IOSENV_DEV);
			logger.info("pushToDeviceUsers:" + tokens.toString() + "result:" + JsonConvertUtils.toJson(object));
		}
		return object;
	}
	
	@Override
	public Map<String, Object> pushToDeviceUsers(List<String> tokens, String title, String content, DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			Message message = MessageUtils.getMessage(title,content);
			object = pushAndroidDeviceLMsg(tokens,message);
		}
		if (ios.equals(deviceType.getName())){
			MessageIOS messageIos = MessageUtils.getMessageIOS(content);
			object = pushIosDeviceListMsg(tokens,messageIos);
		}
		return getSimpleResult(object);
	}
	
	@Override
	public Map<String, Object> pushToDeviceUsers(List<String> tokens, String title, String content, Map<String, Object> custom, DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			Message message = MessageUtils.getMessage(title,content,custom);
			object = pushAndroidDeviceLMsg(tokens,message);
		}
		if (ios.equals(deviceType.getName())){
			MessageIOS messageIos = MessageUtils.getMessageIOS(content,custom);
			object = pushIosDeviceListMsg(tokens,messageIos);
		}
		return getSimpleResult(object);
	}
	
	@Override
	public Map<String, Object> pushToDeviceUsers(List<String> tokens, Message message,MessageIOS messageIOS, DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			object = pushAndroidDeviceLMsg(tokens,message);
		}
		if (ios.equals(deviceType.getName())){
			object = pushIosDeviceListMsg(tokens,messageIOS);
		}
		return getSimpleResult(object);
	}
	
	@Override
	public Map<String, Object> createMultipush(Message message, MessageIOS messageIOS, DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			logger.debug("pushAllDevice:" + "msg:" + message.toJson());
			object = androidXinge.createMultipush(message);
			logger.debug("pushAllDevice:" + "result:" + JsonConvertUtils.toJson(object));
		}
		if (ios.equals(deviceType.getName())){
			if (isStore){
				logger.debug("pushAllDevice:" + "msg:" + messageIOS.toJson());
				object = iosXingeAppStore.createMultipush(messageIOS, XingeApp.IOSENV_PROD);
				logger.debug("pushAllDevice:" + "result:" + JsonConvertUtils.toJson(object));
			}else {
				logger.info("pushAllDevice:"  + "msg:" + messageIOS.toJson());
				object = iosXinge.createMultipush(messageIOS,XingeApp.IOSENV_DEV);
				logger.info("pushAllDevice:"  + "result:" + JsonConvertUtils.toJson(object));
			}
		}
		return getObjectResult(object,pushId);
	}
	
	@Override
	public Map<String, Object> pushAccountListMultiple(Integer pushId, List<String> accountList,DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			object = androidXinge.pushAccountListMultiple(pushId,accountList);
			logger.debug("pushAccountListMultiple:" + "result:" + JsonConvertUtils.toJson(object));
		}
		if (ios.equals(deviceType.getName())){
			if (isStore){
				object = iosXingeAppStore.pushAccountListMultiple(pushId,accountList);
				logger.debug("pushAccountListMultiple:" + "result:" + JsonConvertUtils.toJson(object));
			}else {
				object = iosXinge.pushAccountListMultiple(pushId,accountList);
				logger.info("pushAccountListMultiple:"  + "result:" + JsonConvertUtils.toJson(object));
			}
		}
		return getSimpleResult(object);
	}
	
	@Override
	public Map<String, Object> pushDeviceListMultiple(Integer pushId, List<String> deviceList,DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			object = androidXinge.pushDeviceListMultiple(pushId,deviceList);
			logger.debug("pushDeviceListMultiple:" + "result:" + JsonConvertUtils.toJson(object));
		}
		if (ios.equals(deviceType.getName())){
			if (isStore){
				object = iosXingeAppStore.pushDeviceListMultiple(pushId,deviceList);
				logger.debug("pushDeviceListMultiple:" + "result:" + JsonConvertUtils.toJson(object));
			}else {
				object = iosXinge.pushDeviceListMultiple(pushId,deviceList);
				logger.info("pushDeviceListMultiple:"  + "result:" + JsonConvertUtils.toJson(object));
			}
		}
		return getSimpleResult(object);
	}
	
	@Override
	public Map<String, Object> pushMessageToTags(List<String> tagList, String tagOps, Message message, MessageIOS messageIOS, DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			object = androidXinge.pushTags(0,tagList,tagOps,message);
			logger.debug("pushMessageToTags:" + "result:" + JsonConvertUtils.toJson(object));
		}
		if (ios.equals(deviceType.getName())){
			if (isStore){
				object = iosXingeAppStore.pushTags(0,tagList,tagOps,messageIOS, XingeApp.IOSENV_PROD);
				logger.debug("pushMessageToTags:" + "result:" + JsonConvertUtils.toJson(object));
			}else {
				object = iosXinge.pushTags(0,tagList,tagOps,messageIOS,XingeApp.IOSENV_DEV);
				logger.info("pushMessageToTags:"  + "result:" + JsonConvertUtils.toJson(object));
			}
		}
		return getObjectResult(object,pushId);
	}
	
	@Override
	public Map<String, Object> queryDeviceCount(DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			object = androidXinge.queryDeviceCount();
			logger.debug("queryDeviceCount:" + "result:" + JsonConvertUtils.toJson(object));
		}
		if (ios.equals(deviceType.getName())){
			if (isStore){
				object = iosXingeAppStore.queryDeviceCount();
				logger.debug("queryDeviceCount:" + "result:" + JsonConvertUtils.toJson(object));
			}else {
				object = iosXinge.queryDeviceCount();
				logger.info("queryDeviceCount:"  + "result:" + JsonConvertUtils.toJson(object));
			}
		}
		return getObjectResult(object,deviceNum);
	}
	
	@Override
	public Map<String, Object> queryDeviceTags(DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			object = androidXinge.queryTags();
			logger.debug("queryDeviceTags:" + "result:" + JsonConvertUtils.toJson(object));
		}
		if (ios.equals(deviceType.getName())){
			if (isStore){
				object = iosXingeAppStore.queryTags();
				logger.debug("queryDeviceTags:" + "result:" + JsonConvertUtils.toJson(object));
			}else {
				object = iosXinge.queryTags();
				logger.info("queryDeviceTags:"  + "result:" + JsonConvertUtils.toJson(object));
			}
		}
		return getArrayResult(object,true,tags);
	}
	
	
	
	@Override
	public Map<String, Object> batchSetDeviceTag(List<TagTokenPair> pairList, Message message, MessageIOS messageIOS, DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			object = androidXinge.BatchSetTag(pairList);
			logger.debug("batchSetDeviceTag:" + "result:" + JsonConvertUtils.toJson(object));
		}
		if (ios.equals(deviceType.getName())){
			if (isStore){
				object = iosXingeAppStore.BatchSetTag(pairList);
				logger.debug("batchSetDeviceTag:" + "result:" + JsonConvertUtils.toJson(object));
			}else {
				object = iosXinge.BatchSetTag(pairList);
				logger.info("batchSetDeviceTag:"  + "result:" + JsonConvertUtils.toJson(object));
			}
		}
		return getSimpleResult(object);
	}
	
	@Override
	public Map<String, Object> batchDelDeviceTag(List<TagTokenPair> pairList, Message message, MessageIOS messageIOS, DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			object = androidXinge.BatchDelTag(pairList);
			logger.debug("batchDelDeviceTag:" + "result:" + JsonConvertUtils.toJson(object));
		}
		if (ios.equals(deviceType.getName())){
			if (isStore){
				object = iosXingeAppStore.BatchDelTag(pairList);
				logger.debug("batchDelDeviceTag:" + "result:" + JsonConvertUtils.toJson(object));
			}else {
				object = iosXinge.BatchDelTag(pairList);
				logger.info("batchDelDeviceTag:"  + "result:" + JsonConvertUtils.toJson(object));
			}
		}
		return getSimpleResult(object);
	}
	
	@Override
	public Map<String, Object> queryDeviceTokenTags(String deviceToken, Message message, MessageIOS messageIOS, DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			object = androidXinge.queryTokenTags(deviceToken);
			logger.debug("queryDeviceTokenTags:" + "result:" + JsonConvertUtils.toJson(object));
		}
		if (ios.equals(deviceType.getName())){
			if (isStore){
				object = iosXingeAppStore.queryTokenTags(deviceToken);
				logger.debug("queryDeviceTokenTags:" + "result:" + JsonConvertUtils.toJson(object));
			}else {
				object = iosXinge.queryTokenTags(deviceToken);
				logger.info("queryDeviceTokenTags:"  + "result:" + JsonConvertUtils.toJson(object));
			}
		}
		return getArrayResult(object,false,tags);
	}
	
	@Override
	public Map<String, Object> queryTagTokenNum(String tag, Message message, MessageIOS messageIOS, DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			object = androidXinge.queryTagTokenNum(tag);
			logger.debug("queryTagTokenNum:" + "result:" + JsonConvertUtils.toJson(object));
		}
		if (ios.equals(deviceType.getName())){
			if (isStore){
				object = iosXingeAppStore.queryTagTokenNum(tag);
				logger.debug("queryTagTokenNum:" + "result:" + JsonConvertUtils.toJson(object));
			}else {
				object = iosXinge.queryTagTokenNum(tag);
				logger.info("queryTagTokenNum:"  + "result:" + JsonConvertUtils.toJson(object));
			}
		}
		return getObjectResult(object,deviceNum);
	}
	
	@Override
	public Map<String, Object> queryTokensOfAccount(String account, Message message, MessageIOS messageIOS, DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			object = androidXinge.queryTokensOfAccount(account);
			logger.debug("queryTokensOfAccount:" + "result:" + JsonConvertUtils.toJson(object));
		}
		if (ios.equals(deviceType.getName())){
			if (isStore){
				object = iosXingeAppStore.queryTokensOfAccount(account);
				logger.debug("queryTokensOfAccount:" + "result:" + JsonConvertUtils.toJson(object));
			}else {
				object = iosXinge.queryTokensOfAccount(account);
				logger.info("queryTokensOfAccount:"  + "result:" + JsonConvertUtils.toJson(object));
			}
		}
		return getArrayResult(object,false,tokens);
	}
	
	@Override
	public Map<String, Object> deleteTokenOfAccount(String account, String deviceToken, Message message, MessageIOS messageIOS, DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			object = androidXinge.deleteTokenOfAccount(account,deviceToken);
			logger.debug("deleteTokenOfAccount:" + "result:" + JsonConvertUtils.toJson(object));
		}
		if (ios.equals(deviceType.getName())){
			if (isStore){
				object = iosXingeAppStore.deleteTokenOfAccount(account,deviceToken);
				logger.debug("deleteTokenOfAccount:" + "result:" + JsonConvertUtils.toJson(object));
			}else {
				object = iosXinge.deleteTokenOfAccount(account,deviceToken);
				logger.info("deleteTokenOfAccount:"  + "result:" + JsonConvertUtils.toJson(object));
			}
		}
		return getArrayResult(object,false,tokens);
	}
	
	@Override
	public Map<String, Object> deleteAllTokensOfAccount(String account, Message message, MessageIOS messageIOS, DeviceType deviceType) {
		JSONObject object = null;
		if (android.equals(deviceType.getName())){
			object = androidXinge.deleteAllTokensOfAccount(account);
			logger.debug("deleteAllTokensOfAccount:" + "result:" + JsonConvertUtils.toJson(object));
		}
		if (ios.equals(deviceType.getName())){
			if (isStore){
				object = iosXingeAppStore.deleteAllTokensOfAccount(account);
				logger.debug("deleteAllTokensOfAccount:" + "result:" + JsonConvertUtils.toJson(object));
			}else {
				object = iosXinge.deleteAllTokensOfAccount(account);
				logger.info("deleteAllTokensOfAccount:"  + "result:" + JsonConvertUtils.toJson(object));
			}
		}
		return getSimpleResult(object);
	}
	
}
