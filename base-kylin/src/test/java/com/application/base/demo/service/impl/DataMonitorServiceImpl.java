package com.application.demo.service.impl;

import com.application.base.demo.bean.DataMonitor;
import com.application.base.demo.mapper.DataMonitorMapper;
import com.application.demo.service.IDataMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 数据监控Service业务层处理
 * 
 * @author data
 */
@Service
public class DataMonitorServiceImpl implements IDataMonitorService
{
    @Autowired
    private DataMonitorMapper dataMonitorMapper;

    /**
     * 查询数据监控
     * 
     * @param id 数据监控ID
     * @return 数据监控
     */
    @Override
    public DataMonitor selectDataMonitorById(Long id)
    {
        return dataMonitorMapper.selectDataMonitorById(id);
    }

    /**
     * 查询数据监控列表
     * 
     * @param dataMonitor 数据监控
     * @return 数据监控
     */
    @Override
    public List<DataMonitor> selectDataMonitorList(DataMonitor dataMonitor)
    {
        return dataMonitorMapper.selectDataMonitorList(dataMonitor);
    }

    /**
     * 新增数据监控
     * 
     * @param dataMonitor 数据监控
     * @return 结果
     */
    @Override
    public int insertDataMonitor(DataMonitor dataMonitor)
    {
        return dataMonitorMapper.insertDataMonitor(dataMonitor);
    }

	/**
	 * 新增数据监控
	 *
	 * @param dataMonitor 数据监控
	 * @return 结果
	 */
	@Override
	public boolean insertDataMonitors(List<DataMonitor> dataMonitors)
	{
		return dataMonitorMapper.insertDataMonitors(dataMonitors);
	}

    /**
     * 修改数据监控
     * 
     * @param dataMonitor 数据监控
     * @return 结果
     */
    @Override
    public int updateDataMonitor(DataMonitor dataMonitor)
    {
        return dataMonitorMapper.updateDataMonitor(dataMonitor);
    }

    /**
     * 删除数据监控对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDataMonitorByIds(String ids)
    {
        return dataMonitorMapper.deleteDataMonitorByIds(ids.split(","));
    }

    /**
     * 删除数据监控信息
     * 
     * @param id 数据监控ID
     * @return 结果
     */
    public int deleteDataMonitorById(Long id)
    {
        return dataMonitorMapper.deleteDataMonitorById(id);
    }
}
