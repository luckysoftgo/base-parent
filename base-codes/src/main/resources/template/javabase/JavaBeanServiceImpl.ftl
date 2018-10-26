package ${JavaBeanServiceImplPath};

import java.util.List;
import java.util.Map;

import com.application.base.common.page.Pagination;
import com.application.base.common.exception.CommonException;
import com.application.base.common.BaseCommonMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${JavaBeanPath}.${poName};
import ${JavaBeanDaoPath}.${poName}Mapper;
import ${JavaBeanServicePath}.${poName}Service;


/**
 * @desc ${poName}Service接口实现
 * @author admin
 */
@Service("${firstLowerPoName}Service")
public class ${poName}ServiceImpl implements ${poName}Service {

	@Autowired
	private ${poName}Mapper ${firstLowerPoName}Mapper;

	/**
	* 添加对象。
	* @param ${firstLowerPoName}
	* @return
	*/
	@Override
	public int save${poName}(${poName} ${firstLowerPoName}){
		try {
			return ${firstLowerPoName}Mapper.save${poName}(${firstLowerPoName});
		}catch (Exception ex){
			throw new CommonException(BaseCommonMsg.ADD_DATA_TO_DB_FAIL_MSG.getCode(),BaseCommonMsg.ADD_DATA_TO_DB_FAIL_MSG.getMsg());
		}
	}

	/**
	* 修改对象
	* @param ${firstLowerPoName}
	* @return
	*/
	@Override
	public int update${poName}(${poName} ${firstLowerPoName}){
		try {
			return ${firstLowerPoName}Mapper.update${poName}(${firstLowerPoName});
		}catch (Exception ex){
			throw new CommonException(BaseCommonMsg.UPDATE_DATA_TO_DB_FAIL_MSG.getCode(),BaseCommonMsg.UPDATE_DATA_TO_DB_FAIL_MSG.getMsg());
		}
	}

	/**
	* 删除对象
	* @param ${firstLowerPoName}
	* @return
	*/
	@Override
	public int delete${poName}(${poName} ${firstLowerPoName}){
		try {
			return ${firstLowerPoName}Mapper.delete${poName}(${firstLowerPoName});
		}catch (Exception ex){
			throw new CommonException(BaseCommonMsg.DELETE_DATA_TO_DB_FAIL_MSG.getCode(),BaseCommonMsg.DELETE_DATA_TO_DB_FAIL_MSG.getMsg());
		}
	}

	/**
	* 根据唯一条件查询
	* @param param
	* @return
	*/
	@Override
	public ${poName} query${poName}(Map<String, Object> param){
		try {
			return ${firstLowerPoName}Mapper.query${poName}(param);
		}catch (Exception ex){
			throw new CommonException(BaseCommonMsg.SELECT_DATA_FROM_DB_FAIL_MSG.getCode(),BaseCommonMsg.SELECT_DATA_FROM_DB_FAIL_MSG.getMsg());
		}
	}

	/**
	* 查询满足条件的列表
	* @param param
	* @return
	*/
	@Override
	public List<${poName}> query${poName}s(Map<String, Object> param){
		try {
			return ${firstLowerPoName}Mapper.query${poName}s(param);
		}catch (Exception ex){
			throw new CommonException(BaseCommonMsg.SELECT_DATA_FROM_DB_FAIL_MSG.getCode(),BaseCommonMsg.SELECT_DATA_FROM_DB_FAIL_MSG.getMsg());
		}
	}

	/**
	* 分頁查询满足条件的列表
	* @param param
	* @return
	*/
    @Override
	public List<${poName}> queryPage${poName}s(Map<String, Object> param){
		try {
			return ${firstLowerPoName}Mapper.queryPage${poName}s(param);
		}catch (Exception ex){
			throw new CommonException(BaseCommonMsg.SELECT_DATA_FROM_DB_FAIL_MSG.getCode(),BaseCommonMsg.SELECT_DATA_FROM_DB_FAIL_MSG.getMsg());
		}
	}

	/**
	* 按照条件查询总条数
	* @param param
	* @return
	*/
	@Override
	public Integer totalCount(Map<String, Object> param){
		try {
			return ${firstLowerPoName}Mapper.totalCount(param);
		}catch (Exception ex){
			throw new CommonException(BaseCommonMsg.QUERY_TOTAL_DATA_FAIL_MSG.getCode(),BaseCommonMsg.QUERY_TOTAL_DATA_FAIL_MSG.getMsg());
		}
	}


	/**
	* 分页查询
	* @param param
	  @param pageNo
      @param pageSize
	* @return
	*/
	@Override
	public Pagination<${poName}> queryPagePagination(Map<String, Object> param, int pageNo, int pageSize){
		int count = 0;
		List<${poName}> list = queryPage${poName}s(param);
		// 如果list为空则没有必须再查询总条数
		if (list != null && list.size() > 0) {
			count = totalCount(param);
		}
		Pagination<${poName}> pageResult = new Pagination<${poName}>(list, pageNo, pageSize);
		// 如果总条数为零则不需要设置初始化数值
		if (count > 0) {
			pageResult.setRowCount(count);
		}
		return pageResult;
	}
}
