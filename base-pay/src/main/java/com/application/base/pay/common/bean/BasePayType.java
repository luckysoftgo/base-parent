package com.application.base.pay.common.bean;

/**
 * @desc 基础的支付类型
 * @author 孤狼
 */
public interface BasePayType {

    /**
     * 根据支付类型获取交易类型
     * @param transactionType 类型值
     * @return  交易类型
     */
    TransactionType getTransactionType(String transactionType);

}
