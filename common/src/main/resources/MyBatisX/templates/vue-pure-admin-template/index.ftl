<script setup lang="ts">
    import {use$

    {
        tableClass.shortClassName
    }
    }
    from
    "./utils/hook";
    import {ref} from "vue";

    defineOptions({
        name: "permission"
    });

    const formRef = ref();
    const tableRef = ref();
    const contentRef = ref();
    const {
        operateName,
        queryForm,
        loading,
        columns,
        rowStyle,
        pageList,
        pagination,
        onSearch,
        resetForm,
        openDialog,
        excelTemplate,
        beforeUpload,
        handleDelete,
        handleDeleteBatch,
        handleSizeChange,
        handleCurrentChange,
        handleSelectionChange,
        SelectionExport,
        SelectionExportRef,
        visibleExportExcelField,
        defaultExportFieldKeys,
        confirmExportExcel,
        showSelectionExport
    } = use${tableClass.shortClassName}();

    /**覆盖默认的上传行为 */
    const requestUpload = () => Promise.resolve();
</script>

<template>
    <div class="main">
        <el-form
                ref="formRef"
                :inline="true"
                :model="queryForm"
                class="search-form bg-bg_color w-full pl-8 pt-[12px] overflow-auto"
        >
            <#list tableClass.pkFields as field>
                <el-form-item label="${field.remark!}" prop="${field.fieldName}">
                    <el-input
                            v-model="queryForm.${field.fieldName}"
                            placeholder="请输入${field.remark!}"
                            clearable
                            class="w-[180px]!"
                    />
                </el-form-item>
            </#list>
            <#list tableClass.baseBlobFields as field>
                <el-form-item label="${field.remark!}" prop="${field.fieldName}">
                    <#if field.jdbcType=="VARCHAR">
                        <el-input
                                v-model="queryForm.${field.fieldName}"
                                placeholder="请输入${field.remark!}"
                                clearable
                                class="w-[180px]!"
                        />
                    <#elseif field.jdbcType=="DATE">
                        <el-date-picker
                                v-model="queryForm.${field.fieldName}"
                                value-format="YYYY-MM-DD"
                                type="daterange"
                                placeholder="请选择${field.remark!}"
                                @change="
                range => (
                  (queryForm.${field.fieldName}_ge = range[0]), (queryForm.${field.fieldName}_le = range[1])
                )
              "
                        />
                    <#elseif field.jdbcType=="TIMESTAMP">
                        <el-date-picker
                                v-model="queryForm.${field.fieldName}"
                                value-format="YYYY-MM-DD"
                                type="daterange"
                                placeholder="请选择${field.remark!}"
                                @change="
                range => (
                  (queryForm.${field.fieldName}_ge = range[0]), (queryForm.${field.fieldName}_le = range[1])
                )
              "
                        />

                    <#elseif field.jdbcType=="BOOLEAN">
                        <el-select v-model="queryForm.${field.fieldName}" clearable class="w-[180px]!"
                                   placeholder="请选择${field.remark!}">
                            <el-option label="是" value="true"/>
                            <el-option label="否" value="false"/>
                        </el-select>
                    <#else>
                        <el-input
                                v-model="queryForm.${field.fieldName}"
                                placeholder="请输入${field.remark!}"
                                clearable
                                class="w-[180px]!"
                        />
                    </#if>
                </el-form-item>
            </#list>
            <el-form-item>
                <el-button
                        type="primary"
                        :icon="useRenderIcon('ri/search-line')"
                        :loading="loading"
                        @click="onSearch"
                >
                    搜索
                </el-button>
                <el-button :icon="useRenderIcon(Refresh)" @click="resetForm(formRef)">
                    重置
                </el-button>
            </el-form-item>
        </el-form>

        <div
                ref="contentRef"
                :class="['flex', deviceDetection() ? 'flex-wrap' : '']"
        >
            <PureTableBar
                    :title="`${r'$'}{operateName}管理`"
                    :columns="columns"
                    @refresh="onSearch"
            >
                <template #buttons>
                    <el-button
                            type="primary"
                            :icon="useRenderIcon(AddFill)"
                            @click="openDialog()"
                    >
                        新增
                    </el-button>

                    <el-dropdown class="mr-2 ml-2">
                        <el-button
                                type="success"
                                :icon="useRenderIcon(FileIconsMicrosoftExcel)"
                        >
                            Excel
                        </el-button>
                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item>
                                    <el-button
                                            link
                                            type="primary"
                                            :icon="useRenderIcon(LucideBookTemplate)"
                                            @click="excelTemplate"
                                    >
                                        模板
                                    </el-button>
                                </el-dropdown-item>
                                <el-dropdown-item>
                                    <el-upload
                                            action="#"
                                            :http-request="requestUpload"
                                            :show-file-list="false"
                                            :on-change="beforeUpload"
                                    >
                                        <el-button
                                                link
                                                type="primary"
                                                :icon="useRenderIcon(EpUploadFilled)"
                                        >
                                            导入
                                        </el-button>
                                    </el-upload>
                                </el-dropdown-item>
                                <el-dropdown-item>
                                    <el-button
                                            link
                                            type="primary"
                                            :icon="useRenderIcon(MaterialSymbolsCloudDownload)"
                                            @click="showSelectionExport"
                                    >
                                        导出
                                    </el-button>
                                </el-dropdown-item>
                            </el-dropdown-menu>
                        </template>
                    </el-dropdown>

                    <el-popconfirm
                            :title="`是否批量删除勾选的${r'$'}{operateName}?`"
                            @confirm="handleDeleteBatch"
                    >
                        <template #reference>
                            <el-button type="danger" :icon="useRenderIcon(Delete)">
                                删除
                            </el-button>
                        </template>
                    </el-popconfirm>
                </template>
                <template v-slot="{ size, dynamicColumns }">
                    <pure-table
                            ref="tableRef"
                            align-whole="center"
                            showOverflowTooltip
                            table-layout="auto"
                            :loading="loading"
                            :size="size"
                            adaptive
                            select-on-indeterminate
                            stripe
                            border
                            :row-style="rowStyle"
                            :adaptiveConfig="{ offsetBottom: 108 }"
                            :data="pageList"
                            :columns="dynamicColumns"
                            :pagination="{ ...pagination, size }"
                            :header-cell-style="{
              background: 'var(--el-fill-color-light)',
              color: 'var(--el-text-color-primary)'
            }"
                            :row-key="row => row.id"
                            @selection-change="handleSelectionChange"
                            @page-size-change="handleSizeChange"
                            @page-current-change="handleCurrentChange"
                    >
                        <template #operation="{ row }">
                            <el-button
                                    class="reset-margin"
                                    link
                                    type="primary"
                                    :size="size"
                                    :icon="useRenderIcon(EditPen)"
                                    @click="openDialog('修改', row)"
                            >
                                修改
                            </el-button>
                            <el-popconfirm
                                    :title="`是否确认删除这条${r'$'}{operateName}数据`"
                                    @confirm="handleDelete(row)"
                            >
                                <template #reference>
                                    <el-button
                                            class="reset-margin"
                                            link
                                            type="danger"
                                            :size="size"
                                            :icon="useRenderIcon(Delete)"
                                    >
                                        删除
                                    </el-button>
                                </template>
                            </el-popconfirm>
                        </template>
                    </pure-table>
                </template>
            </PureTableBar>
        </div>
        <SelectionExport
                ref="SelectionExportRef"
                v-model="visibleExportExcelField"
                :default-checked-keys="defaultExportFieldKeys"
                :default-export-file-name="`${r'$'}{operateName}数据导出表`"
                @confirm="confirmExportExcel"
        />
    </div>
</template>

<style lang="scss" scoped>
    :deep(.el-dropdown-menu__item i) {
        margin: 0;
    }

    .operation_bar_btn_interval {
        margin-left: 12px;
    }

    .search-form {
        :deep(.el-form-item) {
            margin-bottom: 12px;
        }
    }
</style>
