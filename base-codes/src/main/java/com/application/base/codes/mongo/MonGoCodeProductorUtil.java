package com.application.base.codes.mongo;

import com.application.base.codes.mongo.def.FtlDef;
import com.application.base.codes.mongo.factory.MonGoCodeGenerateFactory;

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
	    String writeOrRead = "y"; // "Y"读写分离,"N"不读写分离
	    //String entityPackage = "zhongsou\\yjmanager\\"; //如果是目录就用"\\",否则就不用.
	    String entityPackage = "mongo"; //如果是目录就用"\\",否则就不用.
	    MonGoCodeGenerateFactory.codeGenerate(tableName,systemName, codeName, entityPackage,FtlDef.KEY_TYPE_MAN,writeOrRead);
	}
	
	/**
	 * test it .
	 */
	public static void codeGenerate(String tableName,String systemName,String codeName,String codePrefix,String writeOrRead,String entityPackage) throws Exception {
	    MonGoCodeGenerateFactory.codeGenerate(tableName,systemName, codeName, entityPackage,FtlDef.KEY_TYPE_MAN,writeOrRead);
	}
}
