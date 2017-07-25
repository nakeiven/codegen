<#include "common.ftl">
/*
 * 文件名称: ${_cname}.java
 * 版权信息: Copyright 2001-${_year} ${_company}. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: ${_uname}
 * 修改日期: ${_time}
 * 修改内容: 
 */
package ${_cpck};

<#if _haveFileField = "1">
import com.mfh.comn.fs.FileAbleField;
import com.mfh.comn.fs.IFileAvailableBean;
import net.sf.persist.annotations.NoColumn;
<#else>
import com.manfen.modules.entity.MfhEntity;
</#if>

/**
 * ${_description}对象类
 * @author ${_uname} created on ${_time}
 * @since
 */ 
@SuppressWarnings("serial")
public class ${_cname} <#if _haveFileField = "1">extends FileAbleField implements IFileAvailableBean<${obj.pkJavaType}> <#else>extends MfhEntity<${obj.pkJavaType}> </#if>  {
	<#list obj.props as prop>
	<#if ((obj.primaryKey != prop.name)
		&& ('createdBy' != prop.name) 
		&& ('createdDate' != prop.name) 
		&& ('updatedBy' != prop.name) 
		&& ('updatedDate' != prop.name))>	
	private ${prop.type} ${prop.name};<#if (prop.comment != '')> //${prop.comment}</#if>
	</#if>	
	</#list>
	
	<#if _haveFileField = "1">
    @NoColumn
    @Override
    public String getFilePath() {
        return ${_attachFileFieldName};
    }

    @NoColumn
    @Override
    public void setFilePath(String path) {
    	this.${_attachFileFieldName} = path;
    }
    
    </#if>
	<#list obj.props as prop>
	<#if ((obj.primaryKey != prop.name) 
		&& ('createdBy' != prop.name) 
		&& ('createdDate' != prop.name )
		&& ('updatedBy' != prop.name )
		&& ('updatedDate' != prop.name))>
	public ${prop.type} get${prop.name?cap_first}() {
		return ${prop.name};
	}

	public void set${prop.name?cap_first}(${prop.type} ${prop.name}) {
		this.${prop.name} = ${prop.name};
	}
	
	</#if>	
	</#list>
}
