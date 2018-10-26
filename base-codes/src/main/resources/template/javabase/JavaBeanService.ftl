package ${JavaBeanServicePath};

import ${JavaBeanPath}.${poName};
import com.application.base.common.page.Pagination;
import java.util.List;
import java.util.Map;


/**
*@desc ${poName}表相关Service基础接口.
*@author admin
*/
public interface ${poName}Service {

    /**
    * 添加对象。
    * @param ${firstLowerPoName}
    * @return
    */
    int save${poName}(${poName} ${firstLowerPoName});

    /**
    * 修改对象
    * @param ${firstLowerPoName}
    * @return
    */
    int update${poName}(${poName} ${firstLowerPoName});

    /**
    * 删除对象
    * @param ${firstLowerPoName}
    * @return
    */
    int delete${poName}(${poName} ${firstLowerPoName});

    /**
    * 根据唯一条件查询
    * @param param
    * @return
    */
    ${poName} query${poName}(Map<String, Object> param);

    /**
    * 查询满足条件的列表
    * @param param
    * @return
    */
    List<${poName}> query${poName}s(Map<String, Object> param);

    /**
    * 分頁查询满足条件的列表
    * @param param
    * @return
    */
    List<${poName}> queryPage${poName}s(Map<String, Object> param);

    /**
    * 按照条件查询总条数
    * @param param
    * @return
    */
    Integer totalCount(Map<String, Object> param);

    /**
    * 分页查询
    *@param param
    *@param pageNo
    *@param pageSize
    * @return
    */
    Pagination<${poName}> queryPagePagination(Map<String, Object> param, int pageNo, int pageSize);

}