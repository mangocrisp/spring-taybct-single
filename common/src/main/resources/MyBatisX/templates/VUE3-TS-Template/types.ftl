/** ${tableClass.remark!} 类型定义 */
declare namespace ${tableClass.shortClassName}Type {
/** ${tableClass.remark!} 数据实体类型 */
export interface ${tableClass.shortClassName} extends DefaultParams {

<#list tableClass.pkFields as field>
    /** ${field.remark!} */
    ${field.fieldName}: string;
</#list>
<#list tableClass.baseBlobFields as field>
    /** ${field.remark!} */
    ${field.fieldName}?: string;
</#list>

}

/** ${tableClass.remark!}查询体 */
export interface ${tableClass.shortClassName}QueryBody extends AnyObject {
/** ${tableClass.remark!}查询条件 DTO */
${tableClass.shortClassName?uncap_first}QueryDTO: {

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
            ${field.fieldName}_ge?: string;
            /** ${field.remark!} */
            ${field.fieldName}_le?: string;
        <#else>
            /** ${field.remark!} */
            ${field.fieldName}?: string;
        </#if>
    </#list>

    };
    /** 可以继续添加关联表的查询条件 */
    }

    /** ${tableClass.remark!}新增 DTO */
    export interface ${tableClass.shortClassName}AddDTO extends AnyObject {

    <#list tableClass.baseBlobFields as field>
        /** ${field.remark!} */
        ${field.fieldName}?: string;
    </#list>

    }

    /** ${tableClass.remark!}修改更新 DTO */
    export interface ${tableClass.shortClassName}UpdateDTO extends AnyObject {

    <#list tableClass.pkFields as field>
        /** ${field.remark!} */
        ${field.fieldName}: string;
    </#list>
    <#list tableClass.baseBlobFields as field>
        /** ${field.remark!} */
        ${field.fieldName}?: string;
    </#list>

    }

    /** 总数查询 */
    export interface Total {
    (params: any): HttpReturn
    <Number>
        }

        /** 分页查询 */
        export interface Page {
        (params: any): HttpReturnPage<${tableClass.shortClassName}>
        }

        /** 新增接口 */
        export interface Add {
        (params: ${tableClass.shortClassName}AddDTO): HttpReturn<${tableClass.shortClassName}>
        }

        /** 更新接口 */
        export interface Update {
        (params: ${tableClass.shortClassName}UpdateDTO): HttpReturn<${tableClass.shortClassName}>
        }

        /** 查询单个接口 */
        export interface Get {
        (paramsKey: string): HttpReturn<${tableClass.shortClassName}>
        }

        /** 可选字段导出 Excel 类型 */
        export interface SelectionExportExcelType {

        /** 格式 */
        format?: string;
        /** 字段，如果是MAP导出,这个是map的key */
        key?: string;
        /** 字段描述 */
        name?: string;
        /** 字段默认值 */
        defaultValue?: null | AnyObject;
        /** 是属于哪个表（标题分组） */
        groupName?: string;
        /** 字段排序 */
        orderNum?: string;
        /** 列宽 */
        width?: number;
        /** 是否需要合并 */
        needMerge?: boolean;
        /** 单元格纵向合并 */
        mergeVertical?: boolean;
        /** 替换规则，a_1,b_2 */
        replace?: string[];
        /** 字典名称 */
        dict?: string;

        }

        /** 查询导出可选字段 */
        export interface GetExportSelectedField {
        (paramsKey: any): HttpReturn
        <SelectionExportExcelType
        []>
        }

        }