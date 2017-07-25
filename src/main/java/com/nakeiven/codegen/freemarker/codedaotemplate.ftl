<#include "common.ftl">
/*
 * 文件名称: ${_cname}Dao.java
 * 版权信息: Copyright 2001-${_year} ${_company}. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: ${_uname}
 * 修改日期: ${_time}
 * 修改内容: 
 */
package ${_daopck};
<#if _haveBizkeys = '1'>
import com.manfen.modules.dao.IParentDaoWithBizKeys;
<#else>
import com.manfen.modules.dao.IParentDAO;
</#if>
import com.manfen.modules.dao.MyBatisRepository;
import ${_cfullname};

/**
 * ${_description}Dao接口类
 * 
 * @author ${_uname} created on ${_time}
 * @since 
 */
 @MyBatisRepository
public interface ${_daoname} extends <#if _haveBizkeys = '1'>IParentDaoWithBizKeys<#else>IParentDAO</#if><${_cname}, ${obj.pkJavaType}> {
	
}
