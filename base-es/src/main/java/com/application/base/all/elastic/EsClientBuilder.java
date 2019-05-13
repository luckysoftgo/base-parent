package com.application.base.all.elastic;

import com.application.base.utils.common.BaseStringUtil;
import com.application.base.utils.common.PropStringUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * ES(5.5.0) 客户端建立.
 * @author 孤狼
 */
public class EsClientBuilder {

	static Logger logger = LoggerFactory.getLogger(EsClientBuilder.class.getName());
	
	/**
	 * 客户端：配置文件获得.
	 */
	private TransportClient settingClient;
	
	/**
	 * 客户端：参数获得.
	 */
	private TransportClient paramClient;
	
	/**
	 * 集群名.
	 */
	private String clusterName="elasticsearch";
	/**
	 * node IP 地址.
	 */
	private String serverIPs="127.0.0.1:9300";
	/**
	 * 是否自动加载集群的机器到列表中：true.是;false.否.
	 */
	private boolean isAppend=true;
	
	/**
	 *配置文件信息记录
	 */
	private String infoPath="/es.properties";
	
	/**
	 * 读取文件初始化.
     * ES TransPortClient 客户端连接
     * 在elasticsearch平台中,
     * 可以执行创建索引,获取索引,删除索引,搜索索引等操作
	 * @return TransportClient
	 */
	@SuppressWarnings("unchecked")
	public TransportClient initSettingsClient(String inputPath) {
        try {
            if (settingClient == null) {
            	synchronized (EsClientBuilder.class){
		            if (settingClient == null) {
		            	if (!BaseStringUtil.isEmpty(inputPath)){
				            infoPath=inputPath;
			            }
		            	Map<String,String> value = PropStringUtils.getValues(infoPath);
		            	if (value.isEmpty()){
		            		logger.info("根据配置文件:"+infoPath+"获取的配置信息为空!");
		            		return null;
			            }
			            clusterName = value.get("elasticsearch.cluster.name");
			            serverIPs = value.get("elasticsearch.cluster.ip");
			            String sniff = value.get("elasticsearch.transport.sniff");
			            if (!BaseStringUtil.isEmpty(sniff)){
				            isAppend=Boolean.getBoolean(sniff);
			            }
			            //初始化操作实现
			            settingClient=initClient(clusterName,isAppend,serverIPs);
		            }
	            }
                return settingClient;
            } else {
                return settingClient;
            }
        } catch (Exception e) {
        	logger.error("创建 TransportClient 对象出现异常,异常信息是{}",e.getMessage());
            if (settingClient != null) {
            	settingClient.close();
            }
            return null;
        }
    }
	
	/**
	 * 返回创建的客户端信息
	 * @param clusterName
	 * @param isAppend
	 * @param serverIPs
	 * @return
	 */
	private TransportClient initClient(String clusterName,boolean isAppend,String serverIPs) {
		Settings settings = Settings.builder()
				// 集群名
				.put("cluster.name", clusterName)
				// 自动把集群下的机器添加到列表中:true.是;false.否
				//.put("client.transport.sniff", isAppend)
				// 忽略集群名字验证, 打开后集群名字不对也能连接上
				//.put("client.transport.ignore_cluster_name", true)
				.build();
		TransportClient settingClient = new PreBuiltTransportClient(settings);
		//节点信息
		Map<String, Integer> nodeMap = parseNodeIps(serverIPs);
		for (Map.Entry<String, Integer> entry : nodeMap.entrySet()) {
			try {
				settingClient.addTransportAddress(new TransportAddress(InetAddress.getByName(entry.getKey()), entry.getValue()));
			} catch (UnknownHostException e) {
				logger.error("添加索引IP,Port出现异常,异常信息是{}",e.getMessage());
			}
		}
		return settingClient;
	}
	
	/**
	 * 通过参数获得对象.
     * ES TransPortClient 客户端连接
     * 在elasticsearch平台中,
     * 可以执行创建索引,获取索引,删除索引,搜索索引等操作
	 * @param clusterName:集群的名称
	 * @param serverIPs：集群的ip端口,单个：192.168.1.1:9300;多个 ：192.168.1.1:9300;192.168.1.2:9300
	 * @param isAppend：是否自动加载集群的机器到列表中：true.是;false.否
	 * @return TransportClient
	 */
    @SuppressWarnings("unchecked")
	public TransportClient initParamsClient(String clusterName,String serverIPs,boolean isAppend) {
        try {
            if (paramClient == null) {
	            synchronized (EsClientBuilder.class){
	                if(BaseStringUtil.isEmpty(clusterName) || BaseStringUtil.isEmpty(serverIPs)){
	                    return null;
	                }
		            //初始化操作实现
		            paramClient=initClient(clusterName,isAppend,serverIPs);
                }
                return paramClient;
            } else {
                return paramClient;
            }
        } catch (Exception e) {
        	logger.error("创建 TransportClient 对象出现异常,异常信息是{}",e.getMessage());
            if (paramClient != null) {
            	paramClient.close();
            }
            return null;
        }
    }

	/**
	 * 解析节点IP信息,多个节点用逗号隔开,IP和端口用冒号隔开
	 * @return
	 */
	private Map<String, Integer> parseNodeIps(String serverIPs) {
		String[] nodeIpInfoArr = serverIPs.split(",");
		Map<String, Integer> resultMap = new HashMap<String, Integer>(nodeIpInfoArr.length);
		for (String ipInfo : nodeIpInfoArr) {
			String[] ipInfoArr = ipInfo.split(":");
			resultMap.put(ipInfoArr[0], Integer.parseInt(ipInfoArr[1]));
		}
		return resultMap;
	}

	
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	public String getServerIPs() {
		return serverIPs;
	}
	public void setServerIPs(String serverIPs) {
		this.serverIPs = serverIPs;
	}
	public boolean isAppend() {
		return isAppend;
	}
	public void setAppend(boolean isAppend) {
		this.isAppend = isAppend;
	}
}
