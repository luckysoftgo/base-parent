package com.application.base.generate.mongo;

import com.application.base.generate.mongo.def.FtlDef;
import com.application.base.generate.mongo.factory.MonGoCodeGenerateFactory;

/**
 * @desc 生成信息
 * @author 孤狼
 */
public class MonGoCodeProductorUtil {

	/**
	 * test it .
	 * @param args
	 */
	public static void main(String[] args) {
		String tableName = "mongo_morphia";
		String systemName = "base-system";
	    String codeName = "测试";
	    String codePrefix = "测试";
		// "Y"读写分离,"N"不读写分离
	    String writeOrRead = "y";
		//如果是目录就用"\\",否则就不用.
	    //String entityPackage = "zhongsou\\yjmanager\\";
		//如果是目录就用"\\",否则就不用.
	    String entityPackage = "mongo";
	    MonGoCodeGenerateFactory.codeGenerate(tableName,systemName, codeName, entityPackage, FtlDef.KEY_TYPE_MAN,writeOrRead);
	}
	
	/**
	 * test it .
	 * @param args
	 */
	public static void codeGenerate(String tableName,String systemName,String codeName,String codePrefix,String writeOrRead,String entityPackage) throws Exception {
	    MonGoCodeGenerateFactory.codeGenerate(tableName,systemName, codeName, entityPackage, FtlDef.KEY_TYPE_MAN,writeOrRead);
	}
}
