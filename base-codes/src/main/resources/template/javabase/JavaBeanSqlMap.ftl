<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >

<mapper namespace="${beanMapperPath}">

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


    <!-- 保存对象 -->
    <insert id="save${poName}" parameterType="${resultType}.${poName}" >
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

    <!-- 批量保存对象 -->
    <insert id="save${poName}s" parameterType="java.util.ArrayList" >
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


    <!-- 修改 -->
    <update id="update${poName}" parameterType="${resultType}.${poName}">
        update ${tableName} u
        	<include refid="updateColumnWhere" />
        where
        	u.${tablePK} = #{${tablePKVal},jdbcType=${primaryKeyType}}
    </update>

    <!-- 逻辑删除 -->
    <update id="delete${poName}" parameterType="${resultType}.${poName}">
        update ${tableName} u set u.disabled = 1
        	<if test="updateTime != null ">
            ,u.update_time = #{updateTime,jdbcType=TIMESTAMP}
        	</if>
        	<if test="updateUser != null and updateUser != ''">
            ,u.update_user = #{updateUser,jdbcType=VARCHAR}
        	</if>
        where
        	<if test="${tablePKVal} != null and ${tablePKVal} != '' ">
            u.${tablePK} = #{${tablePKVal},jdbcType=${primaryKeyType}}
        	</if>
    </update>

    <!-- 查询一条数据 -->
    <select id="query${poName}" parameterType="java.util.Map" resultType="${resultType}.${poName}">
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

    <!-- 查询所有列表数据 -->
    <select id="query${poName}s" parameterType="java.util.Map" resultType="${resultType}.${poName}">
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

    <!-- 查询分页列表数据 -->
    <select id="queryPage${poName}s" parameterType="java.util.Map" resultType="${resultType}.${poName}">
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
    <select id="totalCount" parameterType="java.util.Map" resultType="java.lang.Integer">
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

</mapper>