package ${JavaBeanDaoPath};

import ${JavaBeanPath}.${poName};
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
*@desc ${poName}表相关DAO基础接口.
*@author 孤狼.
*/
@Repository
public interface ${poName}Mapper {

    /**
    * 添加对象。
    * @param ${firstLowerPoName}
    * @return
    * @throws Exception
    */
    int save${poName}(${poName} ${firstLowerPoName}) throws Exception;

    /**
    * 批量添加对象。
    * @param ${firstLowerPoName}s
    * @return
    * @throws Exception
    */
    boolean save${poName}s(List<${poName}> ${firstLowerPoName}s) throws Exception;

    /**
    * 修改对象
    * @param ${firstLowerPoName}
    * @return
    * @throws Exception
    */
    int update${poName}(${poName} ${firstLowerPoName}) throws Exception;

    /**
    * 删除对象
    * @param ${firstLowerPoName}
    * @return
    * @throws Exception
    */
    int delete${poName}(${poName} ${firstLowerPoName}) throws Exception;

    /**
    * 根据唯一条件查询
    * @param param
    * @return
    * @throws Exception
    */
    ${poName} query${poName}(Map<String, Object> param) throws Exception;

    /**
    * 查询满足条件的列表
    * @param param
    * @return
    * @throws Exception
    */
    List<${poName}> query${poName}s(Map<String, Object> param) throws Exception;

    /**
    * 按照条件查询总条数
    * @param param
    * @return
    * @throws Exception
    */
    Integer totalCount(Map<String, Object> param) throws Exception;

    /**
    * 分頁查询满足条件的列表
    * @param param
    * @return
    * @throws Exception
    */
    List<${poName}> queryPage${poName}s(Map<String, Object> param) throws Exception;

}