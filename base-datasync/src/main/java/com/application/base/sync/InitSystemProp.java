package com.application.base.sync;

import com.application.base.sync.core.SyncContextPrivder;
import com.application.base.sync.conts.DataConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @program: data-process
 * @desc: 初始化系統参数
 * @author: 孤狼
 **/
@Component
@Order(1)
public class InitSystemProp implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
	private SyncContextPrivder syncContextPrivder;
	
	/**
	 * xml 的 path 地址.
	 */

    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        syncContextPrivder.clearContext();
        syncContextPrivder.clearSetting();
	    initXmlInfo();
    }
	
	/**
	 * 读取文件信息.
	 */
	public void initXmlInfo() {
		try {
			String filePath = DataConstant.XML_FILE_PATH;
			File file = new File(filePath);
			if (file.isDirectory()){
				File[] files = file.listFiles();
				if (files!=null && files.length>0){
					for (File instance : files) {
						if (syncContextPrivder.isXml(instance)){
							syncContextPrivder.initUsedInfo(instance.getAbsolutePath(),null);
						}
					}
				}
			}else{
				if (syncContextPrivder.isXml(file)){
					syncContextPrivder.initUsedInfo(file.getAbsolutePath(),null);
				}
			}
		} catch (Exception e) {
			logger.error("读取xml中的内容失败.");
			e.printStackTrace();
		}
	}
}
