package com.application.base.web;

import com.application.base.conts.DataConstant;
import com.application.base.core.DataParser;
import com.application.base.core.SyncContextPrivder;
import com.application.base.util.sql.PkProvider;
import com.application.base.util.xml.TableInfo;
import com.application.base.util.xml.XmlDataInfo;
import com.application.base.web.test.GetDataUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : 孤狼
 * @NAME: DataSyncController
 * @DESC: 测试数据入库操作.
 **/
@RestController
@RequestMapping("/dataSync")
public class DataSyncController {
	
	@Autowired
	private DataParser dataParser;
	
	@Autowired
	private SyncContextPrivder syncContextPrivder;
	
	
	@RequestMapping("/test")
	public String testApi() {
		String settingId = "develop";
		XmlDataInfo dataInfo =syncContextPrivder.getSetting(settingId);
		if (dataInfo==null){
			return "null";
		}
		List<TableInfo> tableInfos = dataInfo.getTableInfos();
		for (TableInfo info : tableInfos ) {
			if (info!=null){
				String resultJson = GetDataUtil.getDataInfo(dataInfo.getOrginInfo().getUrl(),info.getApiKey(),"不要乱科技","2311497658");
				if (StringUtils.isBlank(resultJson)){
					return "";
				}
				try {
					if (info.getAutoPk().equalsIgnoreCase(DataConstant.FLAG_VALUE)){
						dataParser.getInsertSql(settingId, resultJson, info.getUniqueKey(), "2311497658");
					}else{
						dataParser.getInsertSql(settingId, resultJson, info.getUniqueKey(), "2311497658", new PkProvider() {
							@Override
							public String getStrPrimKey() {
								return null;
							}
							@Override
							public Integer getIntPrimKey() {
								return null;
							}
						});
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		return "success";
	}
	
}
