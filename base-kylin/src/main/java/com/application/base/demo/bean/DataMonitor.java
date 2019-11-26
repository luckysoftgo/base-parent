package com.application.base.demo.bean;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据监控对象 data_monitor
 * 
 * @author data
 */
@Data
public class DataMonitor implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 自增长的主键 */
    private Long id;

    /** 删除标志,1删除,0正常使用 */
    private Integer disabled;

    /** 创建者 */
    private String createUser;
	/** 创建时间 */
	private Date createTime;
    /** 更新者 */
    private String updateUser;
	/** 更新时间  */
	private Date updateTime;
    /** 项目名称 */
    private String projectName;

    /** cube名称 */
    private String cubeName;

    /** 表名称 */
    private String tableName;

    /** schem名称 */
    private String schemName;

    /** 时间戳 */
    private String processingDttm;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("disabled", getDisabled())
            .append("createUser", getCreateUser())
            .append("updateUser", getUpdateUser())
            .append("projectName", getProjectName())
            .append("cubeName", getCubeName())
            .append("tableName", getTableName())
            .append("schemName", getSchemName())
            .append("processingDttm", getProcessingDttm())
            .toString();
    }
}
