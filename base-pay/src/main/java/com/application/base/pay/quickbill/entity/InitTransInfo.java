package com.application.base.pay.quickbill.entity;

/**
 * @desc 初始化交易访问地址
 * @author 孤狼
 */
public class InitTransInfo {

	/**
	 * 设置传输交易类型
	 * @param txnType
	 * @return
	 */
	public static TransInfo setTxnType(String txnType) {
		TransInfo transInfo = new TransInfo();
		transInfo.setTxnType(txnType);
		transInfo.setRecordetext1("TxnMsgContent");
		transInfo.setRecordeText2("ErrorMsgContent");
		transInfo.setFlag(true);
		String pur ="PUR",inp="INP",pre="PRE",cfm="CFM",vtx="VTX",rfd="RFD";
		if (pur.equals(txnType)) {
			transInfo.setPostUrl("/cnp/purchase");
		}
		else if (inp.equals(txnType)) {
			transInfo.setPostUrl("/cnp/installment_purchase");
		}
		else if (pre.equals(txnType)) {
			transInfo.setPostUrl("/cnp/preauth");
		}
		else if (cfm.equals(txnType)) {
			transInfo.setPostUrl("/cnp/confirm");
		}
		else if (vtx.equals(txnType)) {
			transInfo.setPostUrl("/cnp/void");
		}
		else if (rfd.equals(txnType)) {
			transInfo.setPostUrl("/cnp/refund");
		}
		return transInfo;
	}

	
	/**
	 * 设置非正式交易类型
	 * @param otherType
	 * @return
	 */
	public static TransInfo setOtherType(String otherType) {
		TransInfo transInfo = new TransInfo();
		String str1= "batch_refund",str2="query_txn",str3="query_txn_list",str4="query_confirm_txn_list",
				str5="query_settlement_txn_list",str6="query_cardinfo",str7="card_validation",str8="card_phoneno_binding",
				str9="dp_request",str10="get_confirm_txnlist",str11="get_settlement_list";
		if (str1.equals(otherType)) {
			transInfo.setRecordetext1("BatchRefundContent");
			transInfo.setRecordeText2("ErrorMsgContent");
			transInfo.setFlag(true);
			transInfo.setPostUrl("/cnp/batch_refund");
		}
		else if (str2.equals(otherType)) {
			transInfo.setRecordetext1("QryTxnMsgContent");
			transInfo.setRecordeText2("TxnMsgContent");
			transInfo.setFlag(true);
			transInfo.setPostUrl("/cnp/query_txn");
		}
		else if (str3.equals(otherType)) {
			transInfo.setRecordetext1("BatchQryTxnMsgContent");
			transInfo.setRecordeText2("TxnListContent");
			transInfo.setFlag(false);
			transInfo.setPostUrl("/cnp/query_txn_list");
		}
		else if (str4.equals(otherType)) {
			transInfo.setRecordetext1("QryConfirmListContent");
			transInfo.setRecordeText2("TxnListContent");
			transInfo.setFlag(false);
			transInfo.setPostUrl("/cnp/query_confirm_txn_list");
		}
		else if (str5.equals(otherType)) {
			transInfo.setRecordetext1("QrySettlementListContent");
			transInfo.setRecordeText2("SettleListContent");
			transInfo.setFlag(false);
			transInfo.setPostUrl("/cnp/query_settlement_txn_list");
		}
		else if (str6.equals(otherType)) {
			transInfo.setRecordetext1("QryCardContent");
			transInfo.setRecordeText2("CardInfoContent");
			transInfo.setFlag(true);
			transInfo.setPostUrl("/cnp/query_cardinfo");
		}
		else if (str7.equals(otherType)) {
			transInfo.setRecordetext1("SvcCardContent");
			transInfo.setRecordeText2("ErrorMsgContent");
			transInfo.setFlag(true);
			transInfo.setPostUrl("/cnp/card_validation");
		}
		else if (str8.equals(otherType)) {
			transInfo.setRecordetext1("CardPhoneBindContent");
			transInfo.setRecordeText2("ErrorMsgContent");
			transInfo.setFlag(true);
			transInfo.setPostUrl("/cnp/card_phoneno_binding");
		}
		else if (str9.equals(otherType)) {
			transInfo.setRecordetext1("DyPinContent");
			transInfo.setRecordeText2("ErrorMsgContent");
			transInfo.setFlag(true);
			transInfo.setPostUrl("/cnp/dp_request");
		}
		else if (str10.equals(otherType)) {
			transInfo.setFlag(true);
			transInfo.setPostUrl("/mcs/get_confirm_txnlist");
		}
		else if (str11.equals(otherType)) {
			transInfo.setFlag(true);
			transInfo.setPostUrl("/mcs/get_settlement_list");
		}
		return transInfo;
	}

	
	/**
	 * 快捷支付交易设置
	 * @param quickType
	 * @return
	 */
	public static TransInfo setQuicklyPay(String quickType) {
		TransInfo transInfo = new TransInfo();
		String str1= "getDynNum",str2="PUR",str3="pci_store",str4="pci_query",
				str5="pci_del",str6="ind_auth",str7="ind_auth_verify";
		
		transInfo.setTxnType(quickType);
		if (str1.equals(quickType)) {
			transInfo.setRecordetext1("GetDynNumContent");
			transInfo.setRecordeText2("ErrorMsgContent");
			transInfo.setFlag(true);
			transInfo.setPostUrl("/cnp/getDynNum");
		}
		else if (str2.equals(quickType)) {
			transInfo.setRecordetext1("TxnMsgContent");
			transInfo.setRecordeText2("ErrorMsgContent");
			transInfo.setFlag(true);
			transInfo.setPostUrl("/cnp/purchase");
		}
		else if (str3.equals(quickType)) {
			transInfo.setRecordetext1("PciDataContent");
			transInfo.setRecordeText2("ErrorMsgContent");
			transInfo.setFlag(false);
			transInfo.setPostUrl("/cnp/pci_store");
		}
		else if (str4.equals(quickType)) {
			transInfo.setRecordetext1("PciQueryContent");
			transInfo.setRecordeText2("ErrorMsgContent");
			transInfo.setFlag(false);
			transInfo.setPostUrl("/cnp/pci_query");
		}
		else if (str5.equals(quickType)) {
			transInfo.setRecordetext1("PciDeleteContent");
			transInfo.setRecordeText2("ErrorMsgContent");
			transInfo.setFlag(false);
			transInfo.setPostUrl("/cnp/pci_del");
		}
		else if (str6.equals(quickType)) {
			transInfo.setRecordetext1("indAuthContent");
			transInfo.setRecordeText2("ErrorMsgContent");
			transInfo.setFlag(true);
			transInfo.setPostUrl("/cnp/ind_auth");
		}
		else if (str7.equals(quickType)) {
			transInfo.setRecordetext1("indAuthDynVerifyContent");
			transInfo.setRecordeText2("ErrorMsgContent");
			transInfo.setFlag(true);
			transInfo.setPostUrl("/cnp/ind_auth_verify");
		}
		return transInfo;
	}
}
