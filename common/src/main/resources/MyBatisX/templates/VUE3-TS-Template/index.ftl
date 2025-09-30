<script setup lang="ts">
    import {defineAsyncComponent, ref, watch} from 'vue'
    import usePageTable from '@base-lib/hooks/web/usePageTable'
    import useForm from '@base-lib/hooks/web/useForm'
    import useTableSelection from '@base-lib/hooks/web/useTableSelection'
    import {downloadByData} from '@/utils/download'
    import ${tableClass

    .shortClassName
    }

    Api
    from
    '@/api/'

    defineOptions({
        name: '${tableClass.shortClassName}List'
    })

    /**查询参数条件 */
    const {
        form,
        resetForm
    } = useForm < ${tableClass.shortClassName}Type.${tableClass.shortClassName}QueryBody > (() => ({
        ${domain.fileName?uncap_first}QueryDTO: {
            <#list tableClass.pkFields as field>
            /** ${field.remark!} */
                ${field.fieldName}: undefined,
            /** ${field.remark!} */
                ${field.fieldName}Selection: [],
            </#list>
            <#list tableClass.baseBlobFields as field>
            <#if field.jdbcType=="VARCHAR">
            /** ${field.remark!} */
                ${field.fieldName}: undefined,
            <#elseif field.jdbcType=="TIMESTAMP" || field.jdbcType=="DATE">
            /** ${field.remark!} */
                ${field.fieldName}_ge: undefined,
            /** ${field.remark!} */
                ${field.fieldName}_le: undefined,
            <#else>
            /** ${field.remark!} */
                ${field.fieldName}: undefined,
            </#if>
            </#list>
        }
    }))

    /**监听查询参数有没有变化  */
    watch(
        form,
        (newValue, oldValue) => {
            handleQueryFormChanged();
        },
        {deep: true} // 深度监听
    )

    /**分页组件工具 */
    const {list, pageParam, loading, setLoading, loadList, reloadList, pageChange}
        = usePageTable(form, ${tableClass.shortClassName}Api.page)

    /**选择多行选择器 */
    const {selectionRows, setSelectionRows, clearSelectionRows}
        = useTableSelection < ${tableClass.shortClassName}Type.${tableClass.shortClassName} > ()

    /**条数（异步获取） */
    const totalRef = ref < Number > (10)

    /**查询参数是否有变动，如果没有变动过就不需要去重复的查询面面的总数 */
    const queryFormChangedRef = ref < Boolean > (true)

    /**显示所有的查询条件 */
    const queryFormItemShowAllRef = ref < Boolean > (false)

    /**新增、修改弹窗 */
    const ${tableClass.shortClassName}Edit = defineAsyncComponent(() => import('./edit.vue'))

    /**新增、修改弹窗显隐 */
    const dialogVisible = ref(false)

    /**新增、修改弹窗 Ref */
    const ${tableClass.shortClassName}EditRef = ref < InstanceType < typeof ${tableClass.shortClassName}Edit > | null > ()

    /**查询参数 from Ref */
    const queryFormRef = ref(null)

    /**
     * 列表修改按钮点击事件
     * @param data 被选中的行数据
     */
    const handleUpdate = async (data: ${tableClass.shortClassName}Type.${tableClass.shortClassName}) => {
        dialogVisible.value = true
        const {data: detail} = await ${tableClass.shortClassName}Api.detail(data.id)
        ${tableClass.shortClassName}EditRef.value?.initUpdateForm(detail)
    }

    /**
     * 表单变动事件
     */
    const handleQueryFormChanged = () => {
        queryFormChangedRef.value = true;
    }

    /**
     * 列表删除事件
     * @param data 选中的行数据
     */
    const handleDel = async (data: ${tableClass.shortClassName}Type.${tableClass.shortClassName}) => {
        setLoading(true)
        try {
            const {message} = await ${tableClass.shortClassName}Api.remove(data.id)
            ElMessage.success(message)
            reloadList()
            totalFn()
        } catch (error) {
            console.error('error =>', error)
        } finally {
            setLoading(false)
        }
    }

    /**批量删除事件 */
    const handleBatchDel = async () => {
        if (selectionRows.length === 0) {
            ElMessage.warning('请至少勾选一条数据!')
            return
        }
        setLoading(true)
        try {
            const {message} = await ${tableClass.shortClassName}Api.batchRemove(selectionRows.map(item => item.id))
            ElMessage.success(message)
            reloadList(clearSelectionRows)
            totalFn()
        } catch (error) {
            console.error('error =>', error)
        } finally {
            setLoading(false)
        }
    }

    /**查询总数的方法 */
    const totalFn = async () => {
        if (queryFormChangedRef.value) {
            const {data} = await ${tableClass.shortClassName}Api.total(form);
            totalRef.value = Number(data);
            queryFormChangedRef.value = false;
        }
    }

    /**切换显示所有相询条件的显示状态 */
    const toggleShowAllQueryFormItem = () => {
        const queryForm = document.getElementsByClassName('query-form')
        if (queryForm && queryForm.length > 0) {
            const queryFormItems = queryForm[0].getElementsByClassName('query-form-item')
            if (queryFormItems && queryFormItems.length > 8) {
                for (let i = 7; i < queryFormItems.length; i++) {
                    const item = queryFormItems[i];
                    (<any>item).style['display'] = queryFormItemShowAllRef.value ? null : 'none'
                        }
                        }
                        }
                        }

                        //------------- 可选字段导出 start ---------------

                        /**可选字段导出面板 */
                        const SelectionExport = defineAsyncComponent(() => import('./selectionExport.vue'))

                        /**可选字段导出面板 Ref */
                        const SelectionExportRef = ref<InstanceType
                        <typeof SelectionExport> | null>()

                            /**弹窗显示显示状态 */
                            const [visibleExportExcelField, exportExcelField] = useToggle(false)

                            /**打开导出面板 */
                            const showSelectionExport = async () => {
                                if (SelectionExportRef.value?.getSelectionFields().length === 0) {
                                const {data} = await ${tableClass.shortClassName}Api.getExportSelectedField({})
                                SelectionExportRef.value?.init(data)
                            }
                                exportExcelField(true);
                            }

                            /**默认需要导出的字段 */
                            const defaultExportFieldKeys = []

                            /**
                            * 提交导出
                            * @param exportForm 导出的数据
                            */
                            const confirmExportExcel = (exportForm) => {
                                exportExcel(exportForm)
                                SelectionExportRef.value?.pass()
                            }

                            /**导出查询条件 */
                            const exportExcelQueryParams =
                            ref<${tableClass.shortClassName}Type.${tableClass.shortClassName}QueryBody>({
                                ${tableClass.shortClassName?uncap_first}QueryDTO: {
                                idSelection: []
                            }
                            })

                                /**
                                * 导出 Excel
                                * @param exportForm 需要导出的数据
                                */
                                const exportExcel = (exportForm: AnyObject) => {
                                    console.log(exportForm.exportTemplateField)
                                    if (!exportForm.exportTemplateField || exportForm.exportTemplateField.length === 0) {
                                    ElMessage.warning('请至少勾选一个字段导出!')
                                    return
                                }
                                    const params = {
                                    fileName: exportForm.fileName || '${tableClass.remark!}数据导出表',
                                    mergeSameStartRow: 3,
                                    exportTemplateField: exportForm.exportTemplateField,
                                    params: exportExcelQueryParams.value,
                                    sqlPageParams: {
                                    pageNum: 1,
                                    pageSize: 1000,
                                    pageOrder: 'id asc,updateTime desc'
                                }
                                }
                                    if (Reflect.ownKeys(form).length > 0) {
                                    params.params.${tableClass.shortClassName?uncap_first}QueryDTO = {...form.${tableClass.shortClassName?uncap_first}QueryDTO}
                                }
                                    if (selectionRows.length > 0) {
                                    params.params.${tableClass.shortClassName?uncap_first}QueryDTO = {}
                                    params.params.${tableClass.shortClassName?uncap_first}QueryDTO.idSelection = selectionRows.map(row => row.id)
                                }
                                    ${tableClass.shortClassName}Api.exportExcel(params)
                                    .then((res) => {
                                    const xlsx = 'application/vnd.ms-excel'
                                    const blob = new Blob([res], {type: xlsx})
                                    downloadByData(blob, `${'$'}{exportForm?.fileName || '${tableClass.remark!}数据导出表'}.xlsx`)
                                })
                                }

                                //------------- 可选字段导出 end ---------------


                                //------------- 导入数据 start ---------------

                                /**获取导入数据的模板 */
                                const getImportExcelTemplate = async () => {
                                    ${tableClass.shortClassName}Api.getImportExcelTemplate()
                                        .then((res) => {
                                            const xlsx = 'application/vnd.ms-excel'
                                            const blob = new Blob([res], {type: xlsx})
                                            downloadByData(blob, `${tableClass.remark!}导入模板.xlsx`)
                                        })
                                }

                                /**覆盖默认的上传行为 */
                                const requestUpload = () => Promise.resolve()

                                /**
                                * 导入数据
                                * @param file 文件
                                */
                                const beforeUpload = async (file) => {
                                    if (file.status == 'success') {
                                    if (!file.name.includes('.xls')) {
                                    ElMessage.error('文件格式错误，请上传xlsx类型,如：xlsx，xls后缀的文件。')
                                    return
                                }
                                    const formData = new FormData()
                                    formData.append('file', file.raw)
                                    await ${tableClass.shortClassName}Api.importExcel(formData).then((res) => {
                                    if (res.code == 200) {
                                    ElMessage.success('导入成功')
                                    reloadList()
                                    totalFn()
                                }
                                })
                                }
                                }

                                //------------- 导入数据 end ---------------


                                /**页面加载监听 */
                                onMounted(() => {
                                    loadList()
                                    totalFn()
                                    toggleShowAllQueryFormItem();
                                })
</script>

<template>
    <div class="main-view">
        <!-- 搜索条件 -->
        <div class="search">
            <ElForm :model="form" @submit.prevent class="query-form" ref="queryFormRef" inline>
                <ElRow :gutter="20">
                    <#list tableClass.pkFields as field>
                        <ElCol :span="6">
                            <ElTooltip content="${field.remark!}" :trigger-keys="[]">
                                <ElFormItem class="query-form-item" label="${field.remark!}">
                                    <ElInput
                                            v-model="form.${tableClass.shortClassName?uncap_first}QueryDTO.${field.fieldName}"
                                            clearable/>
                                </ElFormItem>
                            </ElTooltip>
                        </ElCol>
                    </#list>
                    <#list tableClass.baseBlobFields as field>
                        <#if field.jdbcType=="VARCHAR">
                            <ElCol :span="6">
                                <ElTooltip content="${field.remark!}" :trigger-keys="[]">
                                    <ElFormItem class="query-form-item" label="${field.remark!}">
                                        <ElInput
                                                v-model="form.${tableClass.shortClassName?uncap_first}QueryDTO.${field.fieldName}"
                                                placeholder="请输入" clearable/>
                                    </ElFormItem>
                                </ElTooltip>
                            </ElCol>
                        <#elseif field.jdbcType=="DATE">
                            <ElCol :span="6">
                                <ElTooltip content="${field.remark!}（日期范围开始）：查询比这个日期大的"
                                           :trigger-keys="[]">
                                    <ElFormItem class="query-form-item" label="${field.remark!}（日期范围开始）">
                                        <el-date-picker
                                                v-model="form.${tableClass.shortClassName?uncap_first}QueryDTO.${field.fieldName}_ge"
                                                value-format="YYYY-MM-DD" type="date" placeholder="查询比这个日期大的"/>
                                    </ElFormItem>
                                </ElTooltip>
                            </ElCol>
                            <ElCol :span="6">
                                <ElTooltip content="${field.remark!}（日期范围结束）：查询比这个日期小的"
                                           :trigger-keys="[]">
                                    <ElFormItem class="query-form-item" label="${field.remark!}（日期范围结束）">
                                        <el-date-picker
                                                v-model="form.${tableClass.shortClassName?uncap_first}QueryDTO.${field.fieldName}_le"
                                                value-format="YYYY-MM-DD" type="date" placeholder="查询比这个日期小的"/>
                                    </ElFormItem>
                                </ElTooltip>
                            </ElCol>
                        <#elseif field.jdbcType=="TIMESTAMP">
                            <ElCol :span="6">
                                <ElTooltip content="${field.remark!}（时间范围开始）：查询比这个时间大的"
                                           :trigger-keys="[]">
                                    <ElFormItem class="query-form-item" label="${field.remark!}（时间范围开始）">
                                        <el-date-picker
                                                v-model="form.${tableClass.shortClassName?uncap_first}QueryDTO.${field.fieldName}_ge"
                                                value-format="YYYY-MM-DD HH:mm:ss" type="datetime"
                                                placeholder="查询比这个时间大的"/>
                                    </ElFormItem>
                                </ElTooltip>
                            </ElCol>
                            <ElCol :span="6">
                                <ElTooltip content="${field.remark!}（时间范围结束）：查询比这个时间小的"
                                           :trigger-keys="[]">
                                    <ElFormItem class="query-form-item" label="${field.remark!}（时间范围结束）">
                                        <el-date-picker
                                                v-model="form.${tableClass.shortClassName?uncap_first}QueryDTO.${field.fieldName}_le"
                                                value-format="YYYY-MM-DD HH:mm:ss" type="datetime"
                                                placeholder="查询比这个时间小的"/>
                                    </ElFormItem>
                                </ElTooltip>
                            </ElCol>
                        <#else>
                            <ElCol :span="6">
                                <ElTooltip content="${field.remark!}" :trigger-keys="[]">
                                    <ElFormItem class="query-form-item" label="${field.remark!}">
                                        <ElInput
                                                v-model="form.${tableClass.shortClassName?uncap_first}QueryDTO.${field.fieldName}"
                                                placeholder="请输入" clearable/>
                                    </ElFormItem>
                                </ElTooltip>
                            </ElCol>
                        </#if>
                    </#list>
                </ElRow>
            </ElForm>
        </div>
        <!-- 批量操作按钮 -->
        <div class="main">
            <div class="action-bar">
                <ElButton v-perm="[]" type="primary" @click="dialogVisible = true">
                    <Icon icon="ep:plus" class="mr-5px"/>
                    新增
                </ElButton>
                <ElPopconfirm title="是否批量删除勾选参数?" placement="right" @confirm="handleBatchDel">
                    <template #reference>
                        <ElButton v-perm="[]" type="danger" plain>
                            <Icon icon="ep:delete" class="mr-5px"/>
                            删除
                        </ElButton>
                    </template>
                </ElPopconfirm>
                <ElButton v-perm="[]" class="m-10px" type="info" @click="showSelectionExport">
                    按字段导出
                </ElButton>
                <ElButton v-perm="[]" class="m-10px" type="warning" @click="getImportExcelTemplate">
                    下载导入模板
                </ElButton>
                <ElUpload v-perm="[]" action="#" :http-request="requestUpload" :show-file-list="false"
                          :on-change="beforeUpload"
                          style="display: inline-block;">
                    <ElButton class="m-10px" type="info" color="rgb(0,153,255)">
                        导入数据
                    </ElButton>
                </ElUpload>
                <ElButton type="primary" native-type="submit" @click="reloadList(); totalFn();">
                    查询
                </ElButton>
                <ElButton @click="reloadList([resetForm, clearSelectionRows]); handleQueryFormChanged(); totalFn();">
                    重置
                </ElButton>
                <ElButton type="primary" link
                          @click="queryFormItemShowAllRef = !queryFormItemShowAllRef; toggleShowAllQueryFormItem()">
                    {{ queryFormItemShowAllRef ? '收起' : '展开' }}
                    <Icon v-if="queryFormItemShowAllRef" icon="mingcute:up-fill" width="24" height="24"/>
                    <Icon v-else icon="mingcute:down-fill" width="24" height="24"/>
                </ElButton>
            </div>
            <ElTable v-loading="loading" :data="list" stripe border @selection-change="setSelectionRows"
                     max-height="75vh">
                <!-- 显示列 -->
                <el-table-column type="selection" width="55" align="center" fixed/>
                <el-table-column type="index" label="序号" width="55" align="center" fixed/>
                <#list tableClass.pkFields as field>
                    <el-table-column prop="${field.fieldName}" label="${field.remark!}" width="190" fixed/>
                </#list>
                <#list tableClass.baseBlobFields as field>
                    <#if field.jdbcType=="VARCHAR">
                        <el-table-column prop="${field.fieldName}" label="${field.remark!}" width="150">
                            <template #default="{ row }">
                                <ElTooltip :content="row.${field.fieldName}" :trigger-keys="[]">
                                    <span class="list-table-column-value ellipsis">{{ row.${field.fieldName} }}</span>
                                </ElTooltip>
                            </template>
                        </el-table-column>
                    <#elseif field.jdbcType=="DATE">
                        <el-table-column prop="${field.fieldName}" label="${field.remark!}" width="190">
                            <template #default="{ row }">
                                <ElTooltip :content="row.${field.fieldName}" :trigger-keys="[]">
                                    <span class="list-table-column-value ellipsis">{{ row.${field.fieldName} }}</span>
                                </ElTooltip>
                            </template>
                        </el-table-column>
                    <#elseif field.jdbcType=="TIMESTAMP">
                        <el-table-column prop="${field.fieldName}" label="${field.remark!}" width="190">
                            <template #default="{ row }">
                                <ElTooltip :content="row.${field.fieldName}" :trigger-keys="[]">
                                    <span class="list-table-column-value ellipsis">{{ row.${field.fieldName} }}</span>
                                </ElTooltip>
                            </template>
                        </el-table-column>
                    <#else>
                        <el-table-column prop="${field.fieldName}" label="${field.remark!}" width="150">
                            <template #default="{ row }">
                                <ElTooltip :content="row.${field.fieldName}" :trigger-keys="[]">
                                    <span class="list-table-column-value ellipsis">{{ row.${field.fieldName} }}</span>
                                </ElTooltip>
                            </template>
                        </el-table-column>
                    </#if>
                </#list>
                <!-- 操作按钮 -->
                <el-table-column prop="sort" label="操作" fixed="right" align="center" width="190">
                    <template #default="{ row }">
                        <ElButton v-perm="[]" link type="primary" @click="handleUpdate(row)">
                            修改
                        </ElButton>
                        <ElPopconfirm title="是否删除该参数?" placement="left" @confirm="handleDel(row)">
                            <template #reference>
                                <ElButton v-perm="[]" link type="danger">
                                    删除
                                </ElButton>
                            </template>
                        </ElPopconfirm>
                    </template>
                </el-table-column>
            </ElTable>
            <Pagination v-if="list.length > 0" v-model:page="pageParam.pageNum" v-model:limit="pageParam.pageSize"
                        :total="totalRef" @pagination="pageChange"/>
        </div>
        <${tableClass.shortClassName}Edit ref="${tableClass.shortClassName}EditRef" v-model="dialogVisible"
                                          @reset-table="reloadList(); totalFn();"/>
        <SelectionExport ref="SelectionExportRef" v-model="visibleExportExcelField"
                         :default-checked-keys="defaultExportFieldKeys"
                         default-export-file-name="${tableClass.remark!}数据导出表" @confirm="confirmExportExcel"/>
    </div>
</template>

<style lang="scss" scoped>
    @import '@/styles/base-table';
</style>
<style lang="scss">
    /
    /
    文字显示省略号
    .query-form-item > label,
    .ellipsis {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        color: #000;
        display: inline-block;
    }

    /
    /
    表格的文字详情
    .list-table-column-value {
        width: 100%;
    }

    /
    /
    查询列表的标题文字详情
    .query-form-item > label {
        width: 120px;
    }
</style>