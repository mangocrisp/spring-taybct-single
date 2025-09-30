<script setup lang="ts">
    import {ref} from "vue";
    import {cloneDeep} from "@pureadmin/utils";
    import type {EditFormDTO} from "./utils/types";

    const props = withDefaults(defineProps < EditFormDTO > (), {
        isAddForm: true,
        formInline: () => ({
            <#list tableClass.baseBlobFields as field>
            /** ${field.remark!} */
                ${field.fieldName}: undefined<#sep>, </#sep>
            </#list>
        })
    });

    const formRef = ref();
    const isAddForm = ref(props.isAddForm);
    const formData = ref(props.formInline);
    /** 重置表单方法 Ref */
    const restFormValue = cloneDeep(props.formInline);

    /**获取表单Ref方法 */
    function getFormRef() {
        return formRef.value;
    }

    /**
     * 重置表单
     * @param formEl 表单引用
     */
    function resetForm() {
        if (!formRef.value) return;
        if (isAddForm.value) {
            formRef.value.resetFields();
        } else {
            formData.value = cloneDeep(restFormValue);
        }
    }

    defineExpose({getFormRef, resetForm});
</script>

<template>
    <div class="w-[100%]">
        <el-form
                ref="formRef"
                :model="formData"
                :rules="formRules"
                label-width="auto"
        >
            <el-row :gutter="20">
                <#list tableClass.baseBlobFields as field>
                    <el-col :span="12" :xs="24" :sm="12">
                        <el-tooltip content="${field.remark!}" :trigger-keys="[]">
                            <el-form-item label="${field.remark!}" prop="${field.fieldName}">
                                <#if field.jdbcType=="VARCHAR">
                                    <el-input
                                            v-model="formData.${field.fieldName}"
                                            clearable
                                            placeholder="请输入${field.remark!}"
                                    />
                                <#elseif field.jdbcType=="DATE">
                                    <el-date-picker
                                            v-model="formData.${field.fieldName}"
                                            clearable
                                            value-format="YYYY-MM-DD"
                                            type="date"
                                            placeholder="请输入${field.remark!}"
                                    />
                                <#elseif field.jdbcType=="TIMESTAMP">
                                    <el-date-picker
                                            v-model="formData.${field.fieldName}"
                                            clearable
                                            value-format="YYYY-MM-DD HH:mm:ss"
                                            type="datetime"
                                            placeholder="请输入${field.remark!}"
                                    />
                                <#elseif field.jdbcType=="BIGINT" || field.jdbcType=="NUMERIC" || field.jdbcType=="INTEGER" || field.jdbcType=="SMALLINT" || field.jdbcType=="TINYINT">
                                    <el-input
                                            v-model="formData.${field.fieldName}"
                                            clearable
                                            type="number"
                                            placeholder="请输入${field.remark!}"
                                    />
                                <#elseif field.jdbcType=="BOOLEAN">
                                    <el-switch
                                            v-model="formData.${field.fieldName}"
                                            inline-prompt
                                            :active-value="true"
                                            :inactive-value="false"
                                            active-text="启用"
                                            inactive-text="停用"
                                    />
                                <#else>
                                    <el-input
                                            v-model="formData.${field.fieldName}"
                                            clearable
                                            placeholder="请输入${field.remark!}"
                                    />
                                </#if>
                            </el-form-item>
                        </el-tooltip>
                    </el-col>
                </#list>
            </el-row>
        </el-form>
    </div>
</template>

<style lang="scss" scoped>
    .ellipsis {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        color: #000;
        display: inline-block;
    }
</style>
