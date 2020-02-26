package com.application.base.kylin.rest.api;

import com.application.base.kylin.exception.KylinException;

/**
 * @author : 孤狼
 * @NAME: KylinRestSession
 * @DESC: kylin 接口描述.
 *  http://kylin.apache.org/docs15/howto/howto_use_restapi.html#authentication
 *  https://blog.csdn.net/songchunhong/article/details/79144080
 **/
public interface KylinRestSession {

	/**************************************************** Query ************************************************/
	/**
	 * 获得认证信息.
	 * @return
	 * @throws KylinException
	 */
	public boolean authToken() throws KylinException;
	
	/**
	 * 用户认证 POST /kylin/api/user/authentication.
	 * @return
	 * @throws KylinException
	 */
	public String login() throws KylinException;
	
	/**
	 * 查询的sql语句 POST /kylin/api/query
	 * @param sql
	 * @return
	 * @throws KylinException
	 */
	public String query(String sql) throws KylinException;
	
	/**
	 *
	 * 查询 POST /kylin/api/query
	 * @param sql:String类型的参数，需要执行的sql语句,查询的sql (select * from tableName )
	 * @param offset:int类型的参数，开始执行的位置，若在sql中指定了则可以将此参数省略
	 * @param limit:int类型的参数，限制返回的行数，同样如果在sql中指定了则可以省略此参数
	 * @param projectName:string类型的参数，执行query的工程，默认值为DEDFAULT
	 * @return
	 * @throws KylinException
	 */
	public String query(String sql,Integer offset,Integer limit,Boolean acceptPartial,String projectName) throws KylinException;
	
	/**
	 * 列出可供查询的表 GET /kylin/api/tables_and_columns
	 * @param projectName
	 * @return
	 * @throws KylinException
	 */
	public String queryTables(String projectName) throws KylinException;
	
	/**************************************************** Cube ************************************************/
	/**
	 *
	 * 列出可供查询的cube名字 GET /kylin/api/cubes
	 * @param offset 开始
	 * @param limit 每页多少条
	 * @param cubeName cube名称
	 * @param projectName kylin项目名称
	 * @return
	 * @throws KylinException
	 */
	public String listCubes(int offset,int limit,String cubeName,String projectName ) throws KylinException;

	/**
	 * 获取cube GET /kylin/api/cubes/{cubeName}
	 * @param cubeName
	 * @return
	 * @throws KylinException
	 */
	public String getCube(String cubeName) throws KylinException;
	
	/**
	 * 获取cube的描述信息 GET /kylin/api/cube_desc/{cubeName}
	 * @param cubeName
	 * @return
	 * @throws KylinException
	 */
	public String getCubeDesc(String cubeName) throws KylinException;
	
	/**
	 * 	获取数据的模式 GET /kylin/api/model/{modelName}
	 * @param modelName Data model name, by default it should be the same with cube name.
	 * @return
	 * @throws KylinException
	 */
	public String getDataModel(String modelName)throws KylinException;
	
	/**
	 *
	 * 构建cube	 PUT /kylin/api/cubes/{cubeName}/build
	 * @param cubeName
	 * @return
	 * @throws KylinException
	 */
	public String buildCube(String cubeName)throws KylinException;
	
	/**
	 * 开启cube PUT /kylin/api/cubes/{cubeName}/enable
	 * @param cubeName cubeName Cube name.
	 * @return
	 * @throws KylinException
	 */
	public String enableCube(String cubeName)throws KylinException;
	
	/**
	 * 关闭cube PUT /kylin/api/cubes/{cubeName}/disable
	 * @param cubeName cubeName Cube name.
	 * @return
	 * @throws KylinException
	 */
	public String disableCube(String cubeName)throws KylinException;
	
	
	/**
	 * 禁用cube PUT /kylin/api/cubes/{cubeName}/purge
	 * @param cubeName cubeName Cube name.
	 * @return
	 */
	public String purgeCube(String cubeName)throws KylinException;
	
	/**************************************************** Job ************************************************/
	
	/**
	 * 恢复任务 PUT /kylin/api/jobs/{jobId}/resume
	 * @param jobId 任务Id
	 * @return
	 */
	public String resumeJob(String jobId)throws KylinException;
	
	/**
	 * Pause job 停用job  PUT /kylin/api/jobs/{jobId}/pause
	 * @param jobId  Job id.
	 * @return
	 */
	public String pauseJob(String jobId) throws KylinException;
	
	/**
	 * 取消job  PUT /kylin/api/jobs/{jobId}/cancel
	 * @param jobId  Job id.
	 * @return
	 */
	public String cancelJob(String jobId) throws KylinException;
	
	/**
	 * 获取job状态 GET /kylin/api/jobs/{jobId}
	 * @param jobId  Job id.
	 * @return
	 * @throws KylinException
	 */
	public String getJobStatus(String jobId)throws KylinException;
	
	/**
	 *
	 *获取job每一步的输出 GET /kylin/api/jobs/{jobId}/steps/{stepId}/output
	 * @param jobId Job id.
	 * @param stepId  Step id; the step id is composed by jobId with step sequence id;
	 * for example, the jobId is “fb479e54-837f-49a2-b457-651fc50be110”, its 3rd step id
	 * is “fb479e54-837f-49a2-b457-651fc50be110-3”,
	 * @return
	 * @throws KylinException
	 */
	public String getJobStepOutput(String jobId,String stepId) throws KylinException;
	
	
	/**************************************************** Metadata ************************************************/
	
	/**
	 * 获取hive的表 GET /kylin/api/tables/{tableName}
	 * @param tableName table name to find.
	 * @return
	 * @throws KylinException
	 */
	public String getHiveTable(String tableName) throws KylinException;
	
	/**
	 * 获取hive表的扩展信息 GET /kylin/api/tables/{tableName}/exd-map
	 * @param tableName  table name to find.
	 * @return
	 * @throws KylinException
	 */
	public String getHiveTableInfo(String tableName) throws KylinException;
	
	/**
	 *  获取hive的所有表 GET /kylin/api/tables
	 * @param project
	 * @param extend
	 * @return
	 * @throws KylinException
	 */
	public String getHiveTables(String project,boolean extend) throws KylinException;
	
	/**
	 * 加载hive表的所有信息 POST /kylin/api/tables/{tables}/{project}
	 * @param tables：表名称,用","隔开
	 * @param projectName 项目的名称.
	 * @return
	 * @throws KylinException
	 */
	public String loadHiveTables(String tables,String projectName) throws KylinException;
	
	/**************************************************** Streaming ************************************************/
	
	/**
	 * 设置cube的启动位置 PUT /kylin/api/cubes/{cubeName}/init_start_offsets
	 * @param cubeName
	 * @return
	 * @throws KylinException
	 */
	public String initCubeStartOffSet(String cubeName) throws KylinException;
	
	/**
	 * 构建流式cube  PUT /kylin/api/cubes/{cubeName}/build2
	 * @param cubeName
	 * @return
	 * @throws KylinException
	 */
	public String buildSteam(String cubeName) throws KylinException;
	
	/**
	 *检查阶段孔 GET /kylin/api/cubes/{cubeName}/holes
	 * @param cubeName
	 * @return
	 * @throws KylinException
	 */
	public String checkCubeHole(String cubeName) throws KylinException;
	
	/**
	 * 填充段孔 PUT /kylin/api/cubes/{cubeName}/holes
	 * @param cubeName
	 * @return
	 * @throws KylinException
	 */
	public String fillCubeHole(String cubeName) throws KylinException;
	
	/**
	 * 获得建模下的sql信息.
	 * @param cubeName
	 * @return
	 */
	public String getCubeSql(String cubeName)  throws KylinException;
}
