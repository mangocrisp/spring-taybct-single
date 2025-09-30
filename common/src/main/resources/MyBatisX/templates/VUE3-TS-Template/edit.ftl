<script lang="ts">
</script>

<script setup lang="ts">
    import {computed, reactive, ref} from 'vue'
    import type {FormInstance, FormRules} from 'element-plus'
    import GlobalDialog from '@base-lib/components/GlobalDialog/index.vue'
    import {useToggle} from '@base-lib/hooks/web/useToggle'
    import {Select} from '@base-lib/components/Status'
    import useForm from '@base-lib/hooks/web/useForm'
    import ${tableClass.shortClassName}Api from '@/api/${domain.fileName?uncap_first}/${domain.fileName?uncap_first}'

    defineOptions({
        name: '${tableClass.shortClassName}Edit'
    })

    const props = defineProps({
        modelValue: {
            type: Boolean,
            required: true
        }
    })

    /**
     * 回调钩子用来控制弹窗显隐
     */
    const emit = defineEmits(['update:modelValue', 'resetTable'])

    /**
     * 弹窗显示状态
     */
    const visible = computed({
        get() {
            return props.modelValue
        },
        set(paramsVal: boolean) {
            emit('update:modelValue', paramsVal)
        }
    })

    /**
     * 操作类型：新增/编辑
     */
    const actionType = ref < 'add' | 'update' > ('add')
    /**
     * 表单的 Ref 对象
     */
    const formRef = ref < FormInstance | null > ()
    /**
     * 新增对象 DTO
     */
    const addDTO = {
        <#list tableClass.baseBlobFields as field>
        /** ${field.remark!} */
            ${field.fieldName}: undefined,
        </#list>
    };

    /**表单数据 Ref */
    const form = ref < ${tableClass.shortClassName}Type.${tableClass.shortClassName}AddDTO | ${tableClass.shortClassName}Type.${tableClass.shortClassName}UpdateDTO > (JSON.parse(JSON.stringify(addDTO)));

    /**默认数据 Ref */
    const defaultConfigForm = ref < ${tableClass.shortClassName}Type.${tableClass.shortClassName}AddDTO | ${tableClass.shortClassName}Type.${tableClass.shortClassName}UpdateDTO > ();

    /**重置表单方法 Ref */
    const restFormFnRef = ref < any > (null)

    /**初始化新增表单 */
    const iniAddForm = () => {
        const {
            form: formData,
            resetForm
        } = useForm < ${tableClass.shortClassName}Type.${tableClass.shortClassName}AddDTO > (() => (JSON.parse(JSON.stringify(addDTO))))
        form.value = formData;
        restFormFnRef.value = resetForm;
        defaultConfigForm.value = JSON.parse(JSON.stringify(addDTO));
    }
    /**
     * 初始化编辑修改表单
     * @param data 需要被编辑的数据
     */
    const iniUpdateForm = (data: ${tableClass.shortClassName}Type.${tableClass.shortClassName}UpdateDTO) => {
        // 编辑的是复制的对象
        const {
            form: formData,
            resetForm
        } = useForm < ${tableClass.shortClassName}Type.${tableClass.shortClassName}UpdateDTO > (() => (JSON.parse(JSON.stringify(data))))
        form.value = formData;
        restFormFnRef.value = resetForm;
        defaultConfigForm.value = data;
    }

    const rules = reactive < FormRules > ({
        <#list tableClass.allFields as field>
        <#if !field.nullable || field.jdbcType=="VARCHAR">
        <#if !field.nullable>
        ${field.fieldName}: [{required: true, message: '请输入${field.remark!}', trigger: 'blur'}]<#sep>, </#sep>
        </#if>
        </#if>
        </#list>
    })

    const [loading, setLoading] = useToggle(false)

    const handleSubmit = async () => {
        await formRef.value?.validate(async (valid) => {
            if (valid) {
                setLoading(true)
                try {
                    const {message} = await (actionType.value === 'add' ? ${tableClass.shortClassName}Api.add : ${tableClass.shortClassName}Api.update)(form.value)
                    ElMessage.success(message)
                    visible.value = false
                    emit('resetTable')
                } catch (error) {
                    console.error('handleSubmit => error', error)
                } finally {
                    setLoading(false)
                }
            }
        })
    }

    /**
     * 关闭弹窗
     */
    const close = () => {
        actionType.value = 'add'
        iniAddForm();
    }

    /**
     * 加载更新表单
     * @param data 更新数据
     */
    const initUpdateForm = (data: ${tableClass.shortClassName}Type.${tableClass.shortClassName}) => {
        const {<#list tableClass.allFields as field>${field.fieldName}<#sep>, </#list>} = data
        // 这里如果是 json 对象，需要拿出他里面的 value 属性的值
        iniUpdateForm({<#list tableClass.allFields as field>${field.fieldName}<#sep>, </#list>});
        actionType.value = 'update'
    }

    /**
     * 重置表单
     * @param formEl 表单引用
     */
    const rest = (formEl: FormInstance | undefined | null) => {
        if (!formEl) return
        if (actionType.value === 'add') {
            formEl.resetFields()
        } else {
            form.value = restFormFnRef.value();
        }
    }

    /**
     * 暴露方法
     */
    defineExpose({
        initUpdateForm
    })

    // 进来就初始化新增的 form
    iniAddForm();
</script>

<template>
    <GlobalDialog width="50%" v-model="visible"
                  :title="`${r'$'}{actionType === 'add' ? '新增' : '修改'} ${tableClass.remark!}`" @closed="close">
        <ElForm ref="formRef" v-loading="loading" :model="form" :rules="rules" class="edit-form">
            <ElRow :gutter="20">
                <#list tableClass.pkFields as field>
                    <ElCol v-if="form.${field.fieldName}" :span="12">
                        <ElTooltip content="${field.remark!}" :trigger-keys="[]">
                            <ElFormItem v-if="form.${field.fieldName}" class="edit-form-item" label="${field.remark!}">
                                <ElInput v-model="form.${field.fieldName}" readonly disabled/>
                            </ElFormItem>
                        </ElTooltip>
                    </ElCol>
                </#list>
                <#list tableClass.baseBlobFields as field>
                    <ElCol :span="12">
                        <ElTooltip content="${field.remark!}" :trigger-keys="[]">
                            <ElFormItem class="edit-form-item" label="${field.remark!}" prop="${field.fieldName}">
                                <#if field.jdbcType=="VARCHAR">
                                    <ElInput v-model="form.${field.fieldName}" placeholder="请输入" clearable/>
                                <#elseif field.jdbcType=="DATE">
                                    <el-date-picker v-model="form.${field.fieldName}" value-format="YYYY-MM-DD"
                                                    type="date" placeholder="日期类型"/>
                                <#elseif field.jdbcType=="TIMESTAMP">
                                    <el-date-picker v-model="form.${field.fieldName}" value-format="YYYY-MM-DD HH:mm:ss"
                                                    type="datetime" placeholder="日期时间类型"/>
                                <#else>
                                    <ElInput v-model="form.${field.fieldName}" placeholder="请输入" clearable/>
                                </#if>
                            </ElFormItem>
                        </ElTooltip>
                    </ElCol>
                </#list>
            </ElRow>
        </ElForm>
        <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" :loading="loading" @click="handleSubmit">确定</el-button>
        <el-button @click="rest(formRef)">重置</el-button>
        <el-button :loading="loading" @click="visible = false">取消</el-button>
      </span>
        </template>
    </GlobalDialog>
</template>

<style lang="scss" scoped></style>
<style lang="scss">
    /
    /
    文字显示省略号
    .edit-form-item > label,
    .ellipsis {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        color: #000;
        display: inline-block;
    }

    /
    /
    编辑元素的标题
    .edit-form-item > label {
        width: 120px;
    }
</style>