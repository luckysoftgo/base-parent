package com.application.base.test.excel;

import com.application.base.utils.common.BaseEntity;
import com.application.base.utils.execl.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * C1 失信被执行人信息 LESSCREDIT 对象 task_company_lesscredit
 * 
 * @author data-process
 * @date 2019-09-16
 */
public class TaskCompanyLesscredit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增长的主键 */
    private Long id;

    /** 删除标志,1删除,0正常使用 */
    private Integer disabled;

    /** 工业库企业Id */
    private String companyId;

    /** 企业名称 */
    @Excel(name = "企业名称")
    private String companyName;

    /** 统一社会信用代码 */
    @Excel(name = "统一社会信用代码")
    private String socialCreditCode;

    /** 案号 */
    @Excel(name = "案号")
    private String caseCode;

    /** 法院名称 */
    @Excel(name = "法院名称")
    private String court;

    /** 执行依据文号 */
    @Excel(name = "执行依据文号")
    private String zxyj;

    /** 履行情况 */
    @Excel(name = "履行情况")
    private String performance;

    /** 具体情形 */
    @Excel(name = "具体情形")
    private String disrupt;

    /** 发布时间 */
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date pdate;

    /** 立案时间 */
    @Excel(name = "立案时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lasj;

    /** 状态 */
    @Excel(name = "状态")
    private String execStatus;
    
    public void setDisabled(Integer disabled) 
    {
        this.disabled = disabled;
    }

    public Integer getDisabled() 
    {
        return disabled;
    }
    public void setCompanyId(String companyId) 
    {
        this.companyId = companyId;
    }

    public String getCompanyId() 
    {
        return companyId;
    }
    public void setCompanyName(String companyName) 
    {
        this.companyName = companyName;
    }

    public String getCompanyName() 
    {
        return companyName;
    }
    public void setSocialCreditCode(String socialCreditCode) 
    {
        this.socialCreditCode = socialCreditCode;
    }

    public String getSocialCreditCode() 
    {
        return socialCreditCode;
    }
    public void setCaseCode(String caseCode) 
    {
        this.caseCode = caseCode;
    }

    public String getCaseCode() 
    {
        return caseCode;
    }
    public void setCourt(String court) 
    {
        this.court = court;
    }

    public String getCourt() 
    {
        return court;
    }
    public void setZxyj(String zxyj) 
    {
        this.zxyj = zxyj;
    }

    public String getZxyj() 
    {
        return zxyj;
    }
    public void setPerformance(String performance) 
    {
        this.performance = performance;
    }

    public String getPerformance() 
    {
        return performance;
    }
    public void setDisrupt(String disrupt) 
    {
        this.disrupt = disrupt;
    }

    public String getDisrupt() 
    {
        return disrupt;
    }
    public void setPdate(Date pdate) 
    {
        this.pdate = pdate;
    }

    public Date getPdate() 
    {
        return pdate;
    }
    public void setLasj(Date lasj) 
    {
        this.lasj = lasj;
    }

    public Date getLasj() 
    {
        return lasj;
    }
    public void setExecStatus(String execStatus) 
    {
        this.execStatus = execStatus;
    }

    public String getExecStatus() 
    {
        return execStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("disabled", getDisabled())
            .append("companyId", getCompanyId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("companyName", getCompanyName())
            .append("socialCreditCode", getSocialCreditCode())
            .append("caseCode", getCaseCode())
            .append("court", getCourt())
            .append("zxyj", getZxyj())
            .append("performance", getPerformance())
            .append("disrupt", getDisrupt())
            .append("pdate", getPdate())
            .append("lasj", getLasj())
            .append("execStatus", getExecStatus())
            .toString();
    }
}
