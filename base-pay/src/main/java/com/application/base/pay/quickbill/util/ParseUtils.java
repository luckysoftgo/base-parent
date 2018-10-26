package com.application.base.pay.quickbill.util;

import com.application.base.pay.quickbill.entity.TransInfo;

import java.util.HashMap;

/**
 * @author 孤狼
 */
public class ParseUtils {

	public static HashMap parseXML(String resXml, TransInfo transInfo) {
		HashMap returnRespXml = null;
		ParseXmlUtils pxu = ParseXmlUtils.initParseXMLUtil();
		if (resXml != null) {
			if (transInfo.isFlag()) {
				returnRespXml = pxu.returnXMLData(pxu.parseXML(resXml), transInfo.getRecordetext1(),
						transInfo.getRecordeText2());
			}
			else {
				returnRespXml = pxu.returnXMLDataList(pxu.parseXML(resXml), transInfo.getRecordetext1(),
						transInfo.getRecordeText2());
			}
		}
		return returnRespXml;

	}

}
