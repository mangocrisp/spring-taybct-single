/** ${tableClass.remark!} 类型定义 */
declare namespace ${tableClass.shortClassName}Type {
/** ${tableClass.remark!} 接口数据结构 */
export interface Domain extends DefaultParams {
<#list tableClass.pkFields as field>
    /** ${field.remark!} */
    ${field.fieldName}: string;
</#list>
<#list tableClass.baseBlobFields as field>
    /** ${field.remark!} */
    ${field.fieldName}?: <#if field.jdbcType=="BOOLEAN">boolean<#else>string</#if>;
</#list>
}
/** ${tableClass.remark!} 新增数据结构 */
export interface AddDTO {
<#list tableClass.baseBlobFields as field>
    /** ${field.remark!} */
    ${field.fieldName}?: <#if field.jdbcType=="BOOLEAN">boolean<#else>string</#if>;
</#list>
}
/** ${tableClass.remark!} 修改数据结构 */
export interface UpdateDTO {
<#list tableClass.pkFields as field>
    /** ${field.remark!} */
    ${field.fieldName}: string;
</#list>
<#list tableClass.baseBlobFields as field>
    /** ${field.remark!} */
    ${field.fieldName}?: <#if field.jdbcType=="BOOLEAN">boolean<#else>string</#if>;
</#list>
}

/** ${tableClass.remark!} 查询条件 */
export interface QueryDTO {
<#list tableClass.pkFields as field>
/** ${field.remark!} */
${field.fieldName}?: string;
/** ${field.remark!} */
${field.fieldName}Selection?: Array
<string>;
    </#list>
    <#list tableClass.baseBlobFields as field>
        <#if field.jdbcType=="VARCHAR">
            /** ${field.remark!} */
            ${field.fieldName}?: string;
        <#elseif field.jdbcType=="TIMESTAMP" || field.jdbcType=="DATE">
            /** ${field.remark!} */
            ${field.fieldName}?: string;
            /** ${field.remark!} */
            ${field.fieldName}_ge?: string;
            /** ${field.remark!} */
            ${field.fieldName}_le?: string;
        <#elseif field.jdbcType=="BOOLEAN">
            /** ${field.remark!} */
            ${field.fieldName}?: boolean;
        <#else>
            /** ${field.remark!} */
            ${field.fieldName}?: string;
        </#if>
    </#list>
    }

    /** ${tableClass.remark!} 查询体 */
    export interface QueryBody {
    ${tableClass.shortClassName?uncap_first}QueryDTO: QueryDTO;
    /** 可以继续添加关联表的查询条件 */
    }
    }
