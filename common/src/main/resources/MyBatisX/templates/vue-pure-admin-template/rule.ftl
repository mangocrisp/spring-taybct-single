import { reactive } from "vue";
import type { FormRules } from "element-plus";

/** 自定义表单规则校验 */
export const formRules = reactive(
<FormRules>{
    <#list tableClass.allFields as field>
        <#if !field.nullable || field.jdbcType=="VARCHAR">
            <#if !field.nullable>
                ${field.fieldName}: [{ required: true, message: "请输入${field.remark!}", trigger: "blur" }]<#sep>,</#sep>
            </#if>
        </#if>
    </#list>
    });
