package ${JavaBeanPath};

import com.application.base.utils.common.BaseEntity;

<#list importList as importStr>
	<#if importStr?? >
import ${importStr};
	</#if>
</#list>

/**
 * ${poName}实体
 * @author 孤狼
 */
public class ${poName} extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String TABLE_NAME = "${tableName}";
	
<#list poColumnList as obj>
	/**${obj.remarks}*/
	private ${obj.type} ${obj.name};
	/**${obj.remarks} 对应的静态变量值*/
	public static final String FIELD_${obj.staticFinalName} = "${obj.name}";
</#list>

	public ${poName} () {
		super();
	}
	
	public ${poName} (<#list poColumnList as obj>${obj.type} ${obj.name} <#if obj_has_next>,</#if></#list>) {
		super();
		<#list poColumnList as obj>
		 this.${obj.name} = ${obj.name};
		</#list>
	}

	public ${poName} (InstanceBuilder builder) {
		super(builder);
	<#list poColumnList as obj>
		set${obj.firstUpperName}(builder.${obj.name});
	</#list>
	}

<#list poColumnList as obj>
	public ${obj.type} get${obj.firstUpperName}() {
		return ${obj.name};
	}
	public void set${obj.firstUpperName}(${obj.type} ${obj.name}) {
		this.${obj.name} = ${obj.name};
	}
</#list>

	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer("${poName} info:[");
	<#list poColumnList as obj>
		<#if obj_has_next>
		buffer.append("${obj.name}="+${obj.name}+",");
		</#if>
		<#if !obj_has_next>
		buffer.append("${obj.name}="+${obj.name});
		</#if>
	</#list>
		buffer.append("]");
		return buffer.toString();
	}

	/**
	* 构造者模式
	*/
	public static class InstanceBuilder extends BasicBuilder {

	<#list poColumnList as obj>
		/**${obj.remarks}*/
		private ${obj.type} ${obj.name};
	</#list>

		/**
		* 空构造器
		*/
		public InstanceBuilder() {
		}

	<#list poColumnList as obj>
		public InstanceBuilder ${obj.name}(${obj.type} val) {
			this.${obj.name} = val;
			return this;
		}
	</#list>

		public ${poName} build() {
			return new ${poName}(this);
		}

	}
}
