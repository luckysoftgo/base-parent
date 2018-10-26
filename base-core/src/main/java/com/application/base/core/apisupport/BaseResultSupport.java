package com.application.base.core.apisupport;

import java.util.ArrayList;
import java.util.List;

import com.application.base.core.result.ResultDataVO;
import com.application.base.core.result.ResultInfo;
import com.application.base.utils.common.BaseEntity;
import com.application.base.utils.page.Pagination;
import com.application.base.utils.json.JsonConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.application.base.core.constant.CoreConstants;
import com.application.base.core.exception.BusinessException;
import com.application.base.core.result.MessageContext;

/**
 * @desc 消息信息加载.
 * @author 孤狼
 */
public class BaseResultSupport {

	private final Logger logger = LoggerFactory.getLogger(BaseResultSupport.class.getName());
	
    @Autowired
    protected MessageContext context;

    /**
     * 根据结果消息容器键key，获取结果消息
     *
     * @param key
     * @return ResultInfo
     */
    protected ResultInfo getResultInfo(String key) {
        ResultInfo result = context.getResultInfo(key);
        return result == null ? context.getResultInfo(CoreConstants.CommonMsgResult.SYSTEM_SUCCESS_MSG) : result;
    }

    /**
     * 根据结果消息容器键key，获得结果信息
     *
     * @param key
     * @return Result
     */
    @SuppressWarnings("rawtypes")
	protected ResultDataVO getResult(String key) {
        return new ResultDataVO(getResultInfo(key));
    }

    /**
     * 获得操作成功结果信息
     *
     * @return Result
     */
    @SuppressWarnings("rawtypes")
    protected ResultDataVO getSuccessResult() {
        return new ResultDataVO(getResultInfo(CoreConstants.CommonMsgResult.SYSTEM_SUCCESS_MSG));
    }

    /**
     * 获得操作成功的结果信息
     *
     * @param result
     * @return Result
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected ResultDataVO getSuccessResult(Object result) {
        ResultDataVO res = new ResultDataVO(getResultInfo(CoreConstants.CommonMsgResult.SYSTEM_SUCCESS_MSG));
        res.setData(result);
        return res;
    }

    /**
     * 获得操作成功结果信息的JSON串
     *
     * @return String
     */
    protected String successResultJSON() {
        return JsonConvertUtils.toJson(getSuccessResult());
    }

    /**
     * 获得操作成功结果信息JSON串，带有返回的结果内容
     *
     * @param result
     * @return String
     */
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected String successResultJSON(Object result) {
        ResultDataVO res = getSuccessResult();
        res.setData(result);
        //改输出String字段null值为""
        String resultJson = JsonConvertUtils.toJsonHasNull(res);
        logger.info("调用接口成功，返回JSON数据 resultJson:[{}]", resultJson);
        return resultJson;
    }

    /**
     * @param result
     * @param nullAble 是否序列化为null的字段
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected String successResultJSON(Object result, boolean nullAble) {
        ResultDataVO res = getSuccessResult();
        res.setData(result);
        String resultJson = JsonConvertUtils.toJsonHasNull(res, nullAble);
        logger.debug("调用接口成功，返回JSON数据 resultJson:[{}]", resultJson);
        return resultJson;
    }

    /**
     * 获得结果信息的JSON串
     *
     * @param result
     * @return String
     */
    @SuppressWarnings("rawtypes")
    protected String resultJSON(ResultDataVO result) {
        return JsonConvertUtils.toJsonHasNull(result);
    }

    /**
     * 获得结果信息的JSON串
     *
     * @param resultKey
     * @return String
     */
    protected String resultJSON(String resultKey) {
        return JsonConvertUtils.toJsonHasNull(getResult(resultKey));
    }


    /**
     * 根据异常信息返回错误消息
     *
     * @param ex
     * @return String
     */
    @SuppressWarnings("rawtypes")
    protected String resultJSON(BusinessException ex) {
        ResultDataVO result = null;
        if (!StringUtils.isEmpty(ex.getExceptionCode()+"") && !StringUtils.isEmpty(ex.getExceptionMsg())){
            result = new ResultDataVO(ex.getExceptionCode()+"",ex.getExceptionMsg());
        }else{
            if (!StringUtils.isEmpty(ex.getExceptionKey())) {
                result = getResult(ex.getExceptionKey());
            }else {
                result = getResult(CoreConstants.CommonMsgResult.SYSTEM_ERROR_MSG);
            }
        }
        return JsonConvertUtils.toJsonHasNull(result);
    }

    /**
     * java顶级异常处理
     * @param e
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected String resultJSON(Exception e) {
        BusinessException ex = new BusinessException(e);
        return resultJSON(ex);
    }

    /**
     * 将Pagination对象转换为指定类型的Pagination对象
     *
     * @param pagination
     * @param <T>
     * @return
     */
    protected <T> Pagination<T> getVOPagination(Pagination<? extends BaseEntity> pagination, Votransfer<T> transfer) {
        List<T> data = new ArrayList<T>();
        for (BaseEntity po : pagination.getData()) {
            data.add(transfer.trans(po));
        }
        Pagination<T> result = new Pagination<T>(data, pagination.getPageNo(), pagination.getPageSize());
        result.setPageCount(pagination.getPageCount());
        result.setRowCount(pagination.getRowCount());
        return result;
    }
    
    /**
     * PO 转换成 VO
     * @param <T>
     */
    protected interface Votransfer<T> {
        /**
         * po 转换成 vo
         * @param po
         * @return
         */
        T trans(BaseEntity po);
    }

}
