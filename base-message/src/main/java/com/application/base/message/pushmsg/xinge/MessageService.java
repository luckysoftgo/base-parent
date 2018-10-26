package com.application.base.message.pushmsg.xinge;


import com.application.base.message.pushmsg.xinge.uitls.DeviceType;
import com.tencent.xinge.Message;
import com.tencent.xinge.MessageIOS;
import com.tencent.xinge.TagTokenPair;

import java.util.List;
import java.util.Map;

/**
 * @desc 信息发送
 * @author 孤狼
 */
public interface MessageService {
	
	/**
	 * 推送信息给一个指定的终端账户
	 * @param cellphone 账号
	 * @param title 标题
	 * @param content 内容
	 * @param deviceType 设备类型：Android or Ios
	 * @return
	 */
	Map<String,Object> pushToAccountUser(String cellphone, String title, String content, DeviceType deviceType);
	
	/**
	 * 推送信息给一个指定的终端账户
	 * @param cellphone 账号
	 * @param title 标题
	 * @param content 内容
	 * @param custom 設置
	 * @param deviceType 设备类型：Android or Ios
	 * @return
	 */
	Map<String,Object> pushToAccountUser(String cellphone, String title, String content, Map<String, Object> custom, DeviceType deviceType);
	
	/**
	 * 推送信息给一个指定的终端账户
	 * @param cellphone 账号
	 * @param message 消息主体
	 * @param messageIOS 消息主体
	 * @param deviceType 设备类型：Android or Ios
	 * @return
	 */
	Map<String,Object> pushToAccountUser(String cellphone, Message message, MessageIOS messageIOS, DeviceType deviceType);
	
	/**
	 * 推送信息给一个指定的终端账户
	 * @param token 设备的 token
	 * @param title 标题
	 * @param content 内容
	 * @param deviceType 设备类型：Android or Ios
	 * @return
	 */
	Map<String,Object> pushToDeviceUser(String token, String title, String content, DeviceType deviceType);
	
	/**
	 * 推送信息给一个指定的终端账户
	 * @param token 设备的 token
	 * @param title 标题
	 * @param content 内容
	 * @param custom 設置
	 * @param deviceType 设备类型：Android or Ios
	 * @return
	 */
	Map<String,Object> pushToDeviceUser(String token, String title, String content, Map<String, Object> custom, DeviceType deviceType);
	
	/**
	 * 推送信息给一个指定的终端账户
	 * @param token 设备的 token
	 * @param message 消息主体
	 * @param messageIOS 消息主体
	 * @param deviceType 设备类型：Android or Ios
	 * @return
	 */
	Map<String,Object> pushToDeviceUser(String token, Message message, MessageIOS messageIOS, DeviceType deviceType);
	
	/**
	 * 推送信息给一批指定的账号用户
	 * @param cellphones  账号
	 * @param title 标题
	 * @param content 内容
	 * @param deviceType 设备类型：Android or Ios
	 * @return
	 */
	Map<String,Object> pushToAccountUsers(List<String> cellphones, String title, String content, DeviceType deviceType);
	
	/**
	 * 推送信息给一批指定的账号用户
	 * @param cellphones  账号
	 * @param title 标题
	 * @param content 内容
	 * @param custom 設置
	 * @param deviceType 设备类型：Android or Ios
	 * @return
	 */
	Map<String,Object> pushToAccountUsers(List<String> cellphones, String title, String content, Map<String, Object> custom, DeviceType deviceType);
	
	/**
	 * 推送信息给一批指定的账号用户
	 * @param cellphones  账号
	 * @param message 消息主体
	 * @param messageIOS 消息主体
	 * @param deviceType 设备类型：Android or Ios
	 * @return
	 */
	Map<String,Object> pushToAccountUsers(List<String> cellphones, Message message, MessageIOS messageIOS, DeviceType deviceType);
	
	/**
	 * 推送信息给一批指定的终端用户
	 * @param tokens  账号
	 * @param title 标题
	 * @param content 内容
	 * @param deviceType 设备类型：Android or Ios
	 * @return
	 */
	Map<String,Object> pushToDeviceUsers(List<String> tokens, String title, String content, DeviceType deviceType);
	
	/**
	 * 推送信息给一批指定的终端用户
	 * @param tokens  账号
	 * @param title 标题
	 * @param content 内容
	 * @param custom 設置
	 * @param deviceType 设备类型：Android or Ios
	 * @return
	 */
	Map<String,Object> pushToDeviceUsers(List<String> tokens, String title, String content, Map<String, Object> custom, DeviceType deviceType);
	
	/**
	 * 推送信息给一批指定的终端用户
	 * @param tokens  账号
	 * @param message 消息主体
	 * @param messageIOS 消息主体
	 * @param deviceType 设备类型：Android or Ios
	 * @return
	 */
	Map<String,Object> pushToDeviceUsers(List<String> tokens, Message message, MessageIOS messageIOS, DeviceType deviceType);
	
	
	/**
	 * 创建大批量推送消息.拿到 push_id 供推送使用.
	 * @param message
	 * @param messageIOS
	 * @param deviceType
	 * @return
	 */
	Map<String,Object> createMultipush(Message message, MessageIOS messageIOS, DeviceType deviceType);
	
	/**
	 *  推送消息给大批量账户(可多次),用 createMultipush 接口,拿到 push_id 供推送使用.
	 * @param pushId createMultipush接口返回的 push_id
	 * @param accountList 账号集合
	 * @param deviceType
	 * @return
	 */
	Map<String,Object> pushAccountListMultiple(Integer pushId, List<String> accountList, DeviceType deviceType);
	
	
	/**
	 *  推送消息给大批量设备(可多次),用 createMultipush 接口,拿到 push_id 供推送使用.
	 * @param pushId createMultipush接口返回的 push_id
	 * @param deviceList 设备集合
	 * @param deviceType
	 * @return
	 */
	Map<String,Object> pushDeviceListMultiple(Integer pushId, List<String> deviceList, DeviceType deviceType);
	
	
	/**
	 *推送消息给指定的 tag .
	 * @param tagList tag 集合
	 * @param tagOps  AND or OR
	 * @param message
	 * @param messageIOS
	 * @param deviceType Android or IOS
	 * @return
	 */
	Map<String,Object> pushMessageToTags(List<String> tagList, String tagOps, Message message, MessageIOS messageIOS, DeviceType deviceType);
	
	/**
	 * 查询应用的覆盖的设备数 .
	 * @param deviceType Android or IOS
	 * @return
	 */
	Map<String,Object> queryDeviceCount(DeviceType deviceType);
	
	/**
	 * 查询应用所有的 tags
	 * @param deviceType Android or IOS
	 * @return
	 */
	Map<String,Object> queryDeviceTags(DeviceType deviceType);
	
	/**
	 *批量为 token 设置标签
	 * @param pairList
	 * @param message
	 * @param messageIOS
	 * @param deviceType
	 * @return
	 */
	Map<String,Object> batchSetDeviceTag(List<TagTokenPair> pairList, Message message, MessageIOS messageIOS, DeviceType deviceType);
	
	/**
	 *批量为 token 删除标签
	 * @param pairList
	 * @param message
	 * @param messageIOS
	 * @param deviceType
	 * @return
	 */
	Map<String,Object> batchDelDeviceTag(List<TagTokenPair> pairList, Message message, MessageIOS messageIOS, DeviceType deviceType);
	
	/**
	 * 查询设备 token 下的标签列表.
	 * @param deviceToken
	 * @param message
	 * @param messageIOS
	 * @param deviceType
	 * @return
	 */
	Map<String,Object> queryDeviceTokenTags(String deviceToken, Message message, MessageIOS messageIOS, DeviceType deviceType);
	
	/**
	 * 查询 tag 下 token 的数目
	 * @param tag
	 * @param message
	 * @param messageIOS
	 * @param deviceType
	 * @return
	 */
	Map<String,Object> queryTagTokenNum(String tag, Message message, MessageIOS messageIOS, DeviceType deviceType);
	
	/**
	 *查询一个账号下的所有 token
	 * @param account
	 * @param message
	 * @param messageIOS
	 * @param deviceType
	 * @return
	 */
	Map<String,Object> queryTokensOfAccount(String account, Message message, MessageIOS messageIOS, DeviceType deviceType);
	
	/**
	 * 删除一个账号的某一个 token
	 * @param account
	 * @param deviceToken
	 * @param message
	 * @param messageIOS
	 * @param deviceType
	 * @return
	 */
	Map<String,Object> deleteTokenOfAccount(String account, String deviceToken, Message message, MessageIOS messageIOS, DeviceType deviceType);
	
	/**
	 * 删除一个账号下的所有 token
	 * @param account
	 * @param message
	 * @param messageIOS
	 * @param deviceType
	 * @return
	 */
	Map<String,Object> deleteAllTokensOfAccount(String account, Message message, MessageIOS messageIOS, DeviceType deviceType);
	
	
}
