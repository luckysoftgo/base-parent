<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >

<mapper namespace="${poName}">

	<!-- 输出列 -->
	<sql id="baseColumnList">
		<#list columnList as obj>
		u.${obj.dbName} as ${obj.name} <#if obj_has_next>,</#if>
		</#list>
	</sql>
	
	
	<!-- 查询的列 -->
	<sql id="searchCriteria">
        <include refid="Util.baseSelect" />
		<#list poColumnList as obj>
		<#if obj.type == "Date">
		<if test="${obj.name} != null ">
		and u.${obj.dbName} = #{${obj.name},jdbcType=${obj.sqlMapColumnType}}
		</if>
		<if test="${obj.name}Start != null ">
		and  <![CDATA[  u.${obj.dbName} >= #{${obj.name}Start,jdbcType=${obj.sqlMapColumnType}} ]]> 
		</if>
		<if test="${obj.name}End != null ">
		and  <![CDATA[  u.${obj.dbName} <= #{${obj.name}End,jdbcType=${obj.sqlMapColumnType}} ]]>
		</if>
		</#if>
		<#if obj.type == "Integer">
		<if test="${obj.name} != null and ${obj.name} !=-1  ">
		and u.${obj.dbName} = #{${obj.name},jdbcType=${obj.sqlMapColumnType}}
		</if>
		</#if>
		<#if obj.type == "Double">
		<if test="${obj.name} != null and ${obj.name} !=-1 ">
		and u.${obj.dbName} = #{${obj.name},jdbcType=${obj.sqlMapColumnType}}
		</if>
		</#if>
		<#if obj.type == "BigDecimal">
		<if test="${obj.name} != null and ${obj.name} !=-1 ">
		and u.${obj.dbName} = #{${obj.name},jdbcType=${obj.sqlMapColumnType}}
		</if>
		</#if>
		<#if obj.type == "String">
		<#if obj.name =="${tablePKVal}">
		<if test="${obj.name} != null and ${obj.name} != ''">
		and u.${obj.dbName} = #{${obj.name},jdbcType=${obj.sqlMapColumnType}}
		</if>
		<#elseif obj.name =="uuid" >
		<if test="${obj.name} != null and ${obj.name} != ''">
		and u.${obj.dbName} = #{${obj.name},jdbcType=${obj.sqlMapColumnType}}
		</if>
		<#else>
		<if test="${obj.name} != null and ${obj.name} != ''">
		and u.${obj.dbName} = #{${obj.name},jdbcType=${obj.sqlMapColumnType}}
		</if>
		<if test="${obj.name}Like != null and ${obj.name}Like != ''">
		and u.${obj.dbName} like CONCAT('%',#{${obj.name}Like,jdbcType=${obj.sqlMapColumnType}},'%') 
		</if>
		</#if>
		</#if>
		</#list>
	</sql>


	<!-- 查询列表数据 -->
	<select id="queryListResult" parameterType="java.util.Map" flushCache="false" resultType="${resultType}.${poName}">
		select
			<include refid="baseColumnList" />
		from
			${tableName} u
		where
			<#if existIsDelete == "0">
		    u.disabled = 0
			<#else>
			<#if "${primaryKeyType}" == "NUMERIC"> 
			u.${tablePK} > 0  
			<#else> 
			<![CDATA[ u.${tablePK} <> '' AND u.${tablePK} is not NULL ]]>
			</#if>
			</#if>
			<include refid="searchCriteria" />
			<include refid="Util.orderCriteria" />
			<include refid="Util.mysqlPage" />
	</select>
	
	
	<!-- 查询总数 -->
	<select id="queryListResultCount" parameterType="java.util.Map" flushCache="false" resultType="java.lang.Integer">
		select
			count(u.${tablePK})
		from
			${tableName} u
		where
			<#if existIsDelete == "0">
		    u.disabled = 0
			<#else>
			<#if "${primaryKeyType}" == "NUMERIC"> 
			u.${tablePK} > 0  
			<#else> 
			<![CDATA[ u.${tablePK} <> '' AND u.${tablePK} is not NULL ]]>
			</#if>
			</#if>
			<include refid="searchCriteria" />
	</select>
	
	<!-- 查询所有列表数据 -->
	<select id="queryAllListResult" parameterType="java.util.Map" flushCache="false" resultType="${resultType}.${poName}">
		select
			<include refid="baseColumnList" />
		from
			${tableName} u
		where
			<#if existIsDelete == "0">
		    u.disabled = 0
			<#else>
			<#if "${primaryKeyType}" == "NUMERIC"> 
			u.${tablePK} > 0  
			<#else> 
			<![CDATA[ u.${tablePK} <> '' AND u.${tablePK} is not NULL ]]>
			</#if>
			</#if>
			<include refid="searchCriteria" />
			<include refid="Util.orderCriteria" />
	</select>
	
	
	<!-- 自定义查询列表where条件 -->
	<!-- statementType="STATEMENT":非预编译;PREPARED:预编译;CALLABLE:调用存储过程 -->
	<select id="queryListResultByWhere" parameterType="java.util.Map" statementType="STATEMENT" flushCache="false" resultType="${resultType}.${poName}">
		select
			<include refid="baseColumnList" />
		from
			${tableName} u
		where
			<#if existIsDelete == "0">
		    u.disabled = 0
			<#else>
			<#if "${primaryKeyType}" == "NUMERIC"> 
			u.${tablePK} > 0  
			<#else> 
			<![CDATA[ u.${tablePK} <> '' AND u.${tablePK} is not NULL ]]>
			</#if>
			</#if>
		    <if test="whereSqlStr != null and whereSqlStr != ''">
			and %%{whereSqlStr}
			</if>
	</select>
	
	
	<!-- 自定义查询列表where条件 -->
	<!-- statementType="STATEMENT":非预编译;PREPARED:预编译;CALLABLE:调用存储过程 -->
	<select id="queryListResultCountByWhere" parameterType="java.util.Map" statementType="STATEMENT" flushCache="false" resultType="java.lang.Integer">
		select
			count(u.${tablePK})
		from
			${tableName} u
		where
			<#if existIsDelete == "0">
		    u.disabled = 0
			<#else>
			<#if "${primaryKeyType}" == "NUMERIC"> 
			u.${tablePK} > 0  
			<#else> 
			<![CDATA[ u.${tablePK} <> '' AND u.${tablePK} is not NULL ]]>
			</#if>
			</#if>
			<if test="whereSqlStr != null and whereSqlStr != ''">
			and %%{whereSqlStr}
			</if>
	</select>
	
	
	<!-- 查询一条数据 -->
	<select id="querySingleResultById" <#if "${primaryKeyType}" == "NUMERIC"> parameterType="java.lang.Integer" <#else> parameterType="java.lang.String" </#if> flushCache="false" resultType="${resultType}.${poName}">
		select
			<include refid="baseColumnList" />
		from
			${tableName} u
		where
			<#if existIsDelete == "0">
		    u.disabled = 0
			<#else>
			<#if "${primaryKeyType}" == "NUMERIC"> 
			u.${tablePK} > 0  
			<#else> 
			<![CDATA[ u.${tablePK} <> '' AND u.${tablePK} is not NULL ]]>
			</#if>
			</#if>
			and u.${tablePK} = #{${tablePKVal},jdbcType=${primaryKeyType}}
	</select>
	
	
	<#if existUuid=="0">
	<!-- 通过uuid查询 -->
	<select id="querySingleResultByUUId" parameterType="java.lang.String" flushCache="false" resultType="${resultType}.${poName}">
		select
			<include refid="baseColumnList" />
		from
			${tableName} u
		where
			<#if existIsDelete == "0">
		    u.disabled = 0
			<#else>
			<#if "${primaryKeyType}" == "NUMERIC"> 
			u.${tablePK} > 0  
			<#else> 
			<![CDATA[ u.${tablePK} <> '' AND u.${tablePK} is not NULL ]]>
			</#if>
			</#if>
			and u.uuid = #{uuid,jdbcType=VARCHAR}
	</select>
	</#if>
	
	
	<!-- 通过属性查询 -->
	<select id="querySingleResultByParams" parameterType="java.util.Map" flushCache="false" resultType="${resultType}.${poName}">
		select
			<include refid="baseColumnList" />
		from
			${tableName} u
		where
			<#if existIsDelete == "0">
		    u.disabled = 0
			<#else>
			<#if "${primaryKeyType}" == "NUMERIC"> 
			u.${tablePK} > 0  
			<#else> 
			<![CDATA[ u.${tablePK} <> '' AND u.${tablePK} is not NULL ]]>
			</#if>
			</#if>
			<include refid="searchCriteria" />
	</select>


	<!-- 保存对象,返回主键设置 -->
	<insert id="saveObject" parameterType="${resultType}.${poName}" useGeneratedKeys="true" keyProperty="${tablePKVal}" keyColumn="${tablePK}" flushCache="true" >
		insert into ${tableName} (
	<#list columnList as obj>
		<#if "${primaryKeyStyle}" == "AUTO">
		<#if obj.name != "${tablePKVal}">
			${obj.dbName}<#if obj_has_next>,</#if>
		</#if>
		<#else>
			${obj.dbName}<#if obj_has_next>,</#if>
		</#if>
	</#list>
		) values (
	<#list columnList as obj>
		<#if "${primaryKeyStyle}" == "AUTO">
		<#if obj.name != "${tablePKVal}">
			#{${obj.name},jdbcType=${obj.sqlMapColumnType}}<#if obj_has_next>,</#if>
		</#if>
		<#else>
			#{${obj.name},jdbcType=${obj.sqlMapColumnType}}<#if obj_has_next>,</#if>
		</#if>
	</#list>
		)
	</insert>
	
	
	<insert id="saveBatchObject" parameterType="java.util.ArrayList" flushCache="true" >
		insert into ${tableName} (
	<#list columnList as obj>
		<#if "${primaryKeyStyle}" == "AUTO">
		<#if obj.name != "${tablePKVal}">
			${obj.dbName}<#if obj_has_next>,</#if>
		</#if>
		<#else>
			${obj.dbName}<#if obj_has_next>,</#if>
		</#if>
	</#list>
		) values 
		<foreach collection="list" item="item" index="index" separator=",">
		(
	<#list columnList as obj>
		<#if "${primaryKeyStyle}" == "AUTO">
		<#if obj.name != "${tablePKVal}">
			#{item.${obj.name},jdbcType=${obj.sqlMapColumnType}}<#if obj_has_next>,</#if>
		</#if>
		<#else>
			#{item.${obj.name},jdbcType=${obj.sqlMapColumnType}}<#if obj_has_next>,</#if>
		</#if>
	</#list>
		)
		</foreach>
	</insert>


	<!-- 修改条件 -->
	<sql id="updateColumnWhere">
		<set>
			u.create_time = #{createTime,jdbcType=TIMESTAMP}
		<#list columnList as obj>
				 <#if obj.name != "${tablePKVal}" && obj.name != 'createUser' && obj.name != 'createTime' >
                 	<#if existUuid=='0'>
                 		<#if obj.name != "uuid">
		<#if obj.type == "Date">
		<if test="${obj.name} != null ">
			, u.${obj.dbName} = #{${obj.name},jdbcType=${obj.sqlMapColumnType}}
		</if>
		</#if>
		<#if obj.type == "Integer">
		<if test="${obj.name} != null and ${obj.name} !=-1 ">
			, u.${obj.dbName} = #{${obj.name},jdbcType=${obj.sqlMapColumnType}}
		</if>
		</#if>
		<#if obj.type == "Double">
		<if test="${obj.name} != null and ${obj.name} !=-1 ">
			, u.${obj.dbName} = #{${obj.name},jdbcType=${obj.sqlMapColumnType}}
		</if>
		</#if>
		<#if obj.type == "BigDecimal">
		<if test="${obj.name} != null and ${obj.name} !=-1 ">
			, u.${obj.dbName} = #{${obj.name},jdbcType=${obj.sqlMapColumnType}}
		</if>
		</#if>
		<#if obj.type == "String">
		<if test="${obj.name} != null and ${obj.name} != ''">
			, u.${obj.dbName} = #{${obj.name},jdbcType=${obj.sqlMapColumnType}}
		</if>
		</#if>
            			</#if>
            		<#else>
		<#if obj.type == "Date">
		<if test="${obj.name} != null ">
			, u.${obj.dbName} = #{${obj.name},jdbcType=${obj.sqlMapColumnType}}
		</if>
		</#if>
		<#if obj.type == "Integer">
		<if test="${obj.name} != null and ${obj.name} !=-1 ">
			, u.${obj.dbName} = #{${obj.name},jdbcType=${obj.sqlMapColumnType}}
		</if>
		</#if>
		<#if obj.type == "Double">
		<if test="${obj.name} != null and ${obj.name} !=-1 ">
			, u.${obj.dbName} = #{${obj.name},jdbcType=${obj.sqlMapColumnType}}
		</if>
		</#if>
		<#if obj.type == "BigDecimal">
		<if test="${obj.name} != null and ${obj.name} !=-1 ">
			, u.${obj.dbName} = #{${obj.name},jdbcType=${obj.sqlMapColumnType}}
		</if>
		</#if>
		<#if obj.type == "String">
		<if test="${obj.name} != null and ${obj.name} != ''">
			, u.${obj.dbName} = #{${obj.name},jdbcType=${obj.sqlMapColumnType}}
		</if>
		</#if>
            		</#if>
            	</#if>
			</#list>
		</set>		
	</sql>

	<!-- 修改 -->
	<update id="updateObjectById" flushCache="true" parameterType="${resultType}.${poName}">
		update ${tableName} u
			<include refid="updateColumnWhere" />
		where
            u.${tablePK} = #{${tablePKVal},jdbcType=${primaryKeyType}}
	</update>
	
	
<#if existUuid == "0">
	<!-- 修改 -->
	<update id="updateObjectByUUId" flushCache="true" parameterType="${resultType}.${poName}">
		update ${tableName} u
		<include refid="updateColumnWhere" />
		where
            u.uuid = #{uuid,jdbcType=VARCHAR}
	</update>
</#if>
	
	
	<!-- 自定义修改列以及修改条件 -->
	<!-- statementType="STATEMENT":非预编译;PREPARED:预编译;CALLABLE:调用存储过程 -->
	<update id="updateCustomColumnByWhere" flushCache="true" parameterType="java.util.Map" statementType="STATEMENT">
		update ${tableName} %%{updateSqlStr} where %%{whereSqlStr}
	</update>
	 
	
<#if existIsDelete == "0">
	<!-- 逻辑删除 -->
	<update id="logicDelete" flushCache="true" parameterType="java.util.Map">
		update ${tableName} u set u.disabled = 1
		<if test="updateTime != null ">
		    ,u.update_time = #{updateTime,jdbcType=TIMESTAMP}
		</if>
		<if test="updateUser != null and updateUser != ''">
            ,u.update_user = #{updateUser,jdbcType=NUMERIC}
		</if>
		where
			<if test="${tablePKVal} != null and ${tablePKVal} != '' and ${tablePKVal} != -1 ">
				u.${tablePK} = #{${tablePKVal},jdbcType=${primaryKeyType}}
			</if>
			<#if existUuid=="0">
			<if test="${tablePKVal} == null and uuid != null and uuid != '' ">
				u.uuid = #{uuid,jdbcType=VARCHAR}
			</if>
			<if test="${tablePKVal} == null and uuid == null">
				u.${tablePK} = null
			</if>
			</#if>
	</update>
	
	
	<!-- 自定义逻辑条件及修改条件 -->
	<update id="logicWhereDelete" flushCache="true" parameterType="java.util.Map" statementType="STATEMENT">
		update ${tableName} set disabled = 1 where %%{whereSqlStr}
	</update>
	
</#if>
	
	
	<!-- 物理删除 -->
	<delete id="physicalDelete" flushCache="true" <#if "${primaryKeyType}" == "NUMERIC"> parameterType="java.lang.Integer" <#else> parameterType="java.lang.String" </#if> >
		delete from ${tableName} where ${tablePK} = #{${tablePKVal},jdbcType=${primaryKeyType}}
	</delete>
	
	
	<!-- 自定义物理条件 -->
	<delete id="physicalWhereDelete" flushCache="true" parameterType="java.util.Map" statementType="STATEMENT">
		delete from ${tableName} where %%{whereSqlStr}
	</delete>
	
	
</mapper>