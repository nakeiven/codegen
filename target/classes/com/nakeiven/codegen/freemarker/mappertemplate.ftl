<#include "common.ftl">
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${_daofullname}">	
    <sql id="allFields">
    	<#list obj.props as prop> 
		<#if prop.columnName != prop.name>
		${prop.columnName} as ${prop.name}<#if prop_has_next>,</#if>
		<#else>
		${prop.columnName}<#if prop_has_next>,</#if>
		</#if>
		</#list>
    </sql>
    
	<sql id="insertFields">
    	<#list obj.props as prop> 
		${prop.columnName}<#if prop_has_next>,</#if>
		</#list>
    </sql>    		
	
    <sql id="whereClause">
	<#list obj.props as prop> 
		<#if obj.primaryKey != prop.name>
		<if test="${prop.name} != null">
			<#if (prop_index > 1)>and </#if>${prop.columnName} = #${_brack}${prop.name}}
		</if>
		</#if>
	</#list>
	</sql>
	
	<insert id="create" parameterType="${_cfullname}">
		insert into ${obj.tableName} (<include refid="insertFields"/>)
		values (
		<#list obj.props as prop> 
			#${_brack}${prop.name}}<#if prop_has_next>,</#if>
		</#list>
		)
	</insert>
	
	<insert id="createInBatch" parameterType="${_cfullname}">
		insert into ${obj.tableName} (<include refid="insertFields"/>) values 
		<foreach collection="list" item="item" index="index" separator=",">
			(
				<#list obj.props as prop> 
					#${_brack}item.${prop.name}}<#if prop_has_next>,</#if>
				</#list>
			)
		</foreach>
	</insert>
	
	<delete id="delete">
		delete from ${obj.tableName} where ${obj.jdbcPK} = #${_brack}${obj.primaryKey}}
	</delete>
	
	<delete id="multiDelete" parameterType="List">
		delete from ${obj.tableName}
		where ${obj.jdbcPK} in
		<foreach collection="list" item="item" open="(" close=")" separator=",">
			${r"#{item}"}
		</foreach>
	</delete>
	
	<delete id="deleteByWhere" parameterType="Map">
		delete from ${obj.tableName} 
		<where>
		    <include refid="whereClause"/>
		</where>
	</delete>
	
	<update id="update" parameterType="${_cfullname}">
		 update ${obj.tableName} 
		    <set>	
				<#list obj.props as prop> 
				<#if obj.primaryKey != prop.name>
				<if test="${prop.name} != null">
					${prop.columnName} = #${_brack}${prop.name}}<#if prop_has_next>,</#if>
				</if>
				</#if>
				</#list>
			</set>
		  where ${obj.jdbcPK} = #${_brack}${obj.primaryKey}}
	</update>
	
	<update id="updateByMap" parameterType="Map">
		 update ${obj.tableName} 
		    <set>	
				<#list obj.props as prop> 
				<#if obj.primaryKey != prop.name>
				<if test="${prop.name} != null">
					${prop.columnName} = #${_brack}${prop.name}}<#if prop_has_next>,</#if>
				</if>
				</#if>
				</#list>
			</set>
		  where ${obj.jdbcPK} = #${_brack}${obj.primaryKey}}
	</update>
    
	<select id="get" resultType="${_cfullname}" parameterType="${obj.pkJavaType}" >
		select <include refid="allFields"/> 
		  from ${obj.tableName} 
		 where ${obj.jdbcPK} = #${_brack}${obj.primaryKey}} 
	</select>
	
	<select id="getByIds" parameterType="List" resultType="${_cfullname}">
		select <include refid="allFields"/> from ${obj.tableName} 
		where ${obj.jdbcPK} in
		<foreach collection="list" item="item" open="(" close=")" separator=",">
			${r"#{item}"}
		</foreach>
	</select>
		
	<select id="getAll" resultType="${_cfullname}">
		select <include refid="allFields"/> 
		from ${obj.tableName} 
	</select>

	<select id="count" resultType="long">
		select count(*) from ${obj.tableName} 
	</select>
	
	<select id="getCountByWhere" parameterType="Map" resultType="Long">
		select count(*) from ${obj.tableName} 
		<where>
		    <include refid="whereClause"/>
		</where>
	</select>

	<select id="findByWhere" parameterType="Map" resultType="${_cfullname}">
		select <include refid="allFields"/>
		from ${obj.tableName} 
		<where>
		    <include refid="whereClause"/>
		</where>
	</select>
	
	<#if _haveBizkeys = '1'>
	
	<sql id="bizKeysWhereClause">
	<#list obj.bizKeys as biz> 
		<#if obj.primaryKey != biz.name>
			<if test="${biz.name} != null">
				<#if (biz_index >= 1)>and </#if>${biz.columnName} = #${_brack}${biz.name}}
			</if>
		</#if>
	</#list>
	</sql>
	
	<select id="getByBizKeys" resultType="${_cfullname}" parameterType="Map" >
		select <include refid="allFields"/> 
		  from ${obj.tableName} 
		 <where>
		    <include refid="bizKeysWhereClause"/>
		</where>
	</select>  
	
	<update id="updateByBizKeys" parameterType="${_cfullname}">
		 update ${obj.tableName} 
		    <set>	
				<#list obj.props as prop> 
				<#if obj.primaryKey != prop.name>
				<if test="${prop.name} != null">
					${prop.columnName} = #${_brack}${prop.name}}<#if prop_has_next>,</#if>
				</if>
				</#if>
				</#list>
			</set>
		 <where>
		    <include refid="bizKeysWhereClause"/>
		</where>
	</update>
	
	<delete id="deleteByBizKeys" parameterType="Map">
		delete from ${obj.tableName} 
		<where>
		    <include refid="bizKeysWhereClause"/>
		</where>
	</delete>
	</#if>	
</mapper>    
