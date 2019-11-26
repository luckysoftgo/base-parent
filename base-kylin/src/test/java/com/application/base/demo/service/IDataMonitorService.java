package com.application.base.demo.service;


import com.application.base.demo.bean.DataMonitor;

import java.util.List;

/**
 * 数据监控Service接口
 * @author data
 */
public interface IDataMonitorService 
{
    /**
     * 查询数据监控
     * 
     * @param id 数据监控ID
     * @return 数据监控
     */
    public DataMonitor selectDataMonitorById(Long id);

    /**
     * 查询数据监控列表
     * 
     * @param dataMonitor 数据监控
     * @return 数据监控集合
     */
    public List<DataMonitor> selectDataMonitorList(DataMonitor dataMonitor);

    /**
     * 新增数据监控
     * 
     * @param dataMonitor 数据监控
     * @return 结果
     */
    public int insertDataMonitor(DataMonitor dataMonitor);

	/**
	 * 批量新增数据监控
	 *
	 * @param dataMonitor 数据监控
	 * @return 结果
	 */
	public boolean insertDataMonitors(List<DataMonitor> dataMonitors);

    /**
     * 修改数据监控
     * 
     * @param dataMonitor 数据监控
     * @return 结果
     */
    public int updateDataMonitor(DataMonitor dataMonitor);

    /**
     * 批量删除数据监控
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDataMonitorByIds(String ids);

    /**
     * 删除数据监控信息
     * 
     * @param id 数据监控ID
     * @return 结果
     */
    public int deleteDataMonitorById(Long id);
}
