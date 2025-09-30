<script lang="tsx" setup>
    import {onMounted, reactive, ref, unref} from 'vue'
    import {ElCheckbox} from 'element-plus'
    import {useState, useToggle} from '@gx-web/tool'

    import {required} from '@base-lib/utils/formRules'
    import {SelectionExportExcelType} from '@/types/global'
    import type {FunctionalComponent} from 'vue'
    import type {CheckboxValueType, Column, FormInstance, FormRules} from 'element-plus'

    defineOptions({
        name: 'SelectionExport'
    })

    type
    SelectionCellProps = {
        key: string
        value: boolean
        intermediate? : boolean
        onChange: (value: CheckboxValueType) => void
    }

    interface
    FormType
    {
        fileName: string
        exportTemplateField: SelectionExportExcelType[]
    }

    const props = withDefaults(defineProps < {
        title? : string
        defaultCheckedKeys? : string[]
        defaultExportFileName? : string
    } > (), {
        title: '导出数据',
        defaultCheckedKeys: () => [],
        defaultExportFileName: ''
    })

    const emit = defineEmits < {
    (e
    :
    'confirm', form
    :
    FormType
    ):
    void
    }>
    ()

    const visible = defineModel({type: Boolean, default: false})

    const selectionFieldsRef = ref < SelectionExportExcelType[] > ([])

    const [loading, toggleLoading] = useToggle()
    const [dataLoading, toggleDataLoading] = useToggle()

    const [list, setList, resetList] = useState < SelectionExportExcelType[] > (() => [])
    const [cacheList, setCacheList] = useState < SelectionExportExcelType[] > (() => [])
    const [selections, setSelections, resetSelections] = useState < SelectionExportExcelType[] > (() => [])

    const [query, , resetQuery] = useState(() => ({name: ''}))

    const [form, setForm] = useState < FormType > (() => ({
        fileName: props.defaultExportFileName,
        exportTemplateField: []
    }))

    const rules = reactive < FormRules > ({
        fileName: [required(), {
            pattern: /^(?!.*[\\/:*?"<>|]).*$/,
            message: '请输入正确的文件名',
            trigger: ['blur', 'change']
        }]
    })

    const formRef = ref < FormInstance | null > ()

    const columns: Column<any>[] = [
        {
            key: 'key',
            dataKey: 'name',
            title: '字段名',
            width: 610
        }
    ]

    // 自定义字段 用于表格选择状态
    const checkedField = 'myCheckedField'
    const SelectionCell: FunctionalComponent<SelectionCellProps> = ({
                                                                        value,
                                                                        intermediate = false,
                                                                        onChange,
                                                                        key
                                                                    }) => {
        return (
            <ElCheckbox
                key={key}
                onChange={onChange}
                modelValue={value}
                indeterminate={intermediate}
            />
        )
    }

    const cloneSelectionRow = (row) => {
        row[checkedField] = false
        selections.splice(selections.indexOf(row), 1)
    }

    const setDefaultSelections = (list) => {
        if (!list.length)
            return
        const defaultSelections = cacheList.filter(row => list.includes(row.key))
        defaultSelections.map(item => item[checkedField] = true)
        setSelections(defaultSelections)
    }

    columns.unshift({
        key: 'selection',
        width: 50,
        cellRenderer: ({rowData}) => {
            const onChange = (value: CheckboxValueType) => {
                if (value) {
                    selections.push(rowData)
                } else {
                    cloneSelectionRow(rowData)
                }
                rowData[checkedField] = value
            }

            return <SelectionCell key={rowData.key} value={rowData[checkedField]} onChange={onChange}/>
        },

        headerCellRenderer: () => {
            const _list = unref(cacheList)
            const _selections = unref(selections)

            const onChange = (value: CheckboxValueType) => {
                list.map(item => item[checkedField] = value)
                if (value) {
                    setSelections(list)
                } else {
                    resetSelections()
                }
            }

            // 使用 Set 去重并比较长度
            const uniqueOriginal = new Set(_list)
            const uniqueSelected = new Set(_selections)

            const allSelected = uniqueOriginal.size > 0 && uniqueOriginal.size === uniqueSelected.size
            const containsChecked = uniqueSelected.size > 0

            return (
                <SelectionCell
                    key="header-selectioin"
                    value={allSelected}
                    intermediate={containsChecked && !allSelected}
                    onChange={onChange}
                />
            )
        }
    })

    const init = (data: SelectionExportExcelType[]) => {
        if (selectionFieldsRef.value.length == 0) {
            selectionFieldsRef.value = data;
            load();
        }
    }

    const getSelectionFields = () => {
        return selectionFieldsRef.value;
    }

    const load = () => {
        toggleDataLoading(true)
        try {
            setList(selectionFieldsRef.value)
            setCacheList(selectionFieldsRef.value)
            setDefaultSelections(props.defaultCheckedKeys)
        } finally {
            toggleDataLoading(false)
        }
    }

    const filter = () => {
        resetList()
        if (!query.name) {
            setList(cacheList)
            return
        }
        setList(cacheList.filter(item => item.name?.includes(query.name)))
    }

    const resetFilter = () => {
        resetQuery()
        filter()
    }

    const handleSubmit = async () => {
        await formRef.value?.validate()

        // 把序号放数组第一位
        const isIndex = selections.findIndex(obj => obj.format === 'isAddIndex')
        if (isIndex !== -1) {
            const [index] = selections.splice(isIndex, 1)
            selections.unshift(index)
        }

        setForm({
            exportTemplateField: selections.map((obj) => {
                return Object.keys(obj)
                    .filter(key => key !== checkedField) // 过滤掉要移除的字段
                    .reduce((newObj, key) => {
                        newObj[key] = obj[key]
                        return newObj
                    }, {})
            }) as SelectionExportExcelType[]
        })

        emit('confirm', form)
    }

    const pass = () => {
        toggleLoading(false)
        visible.value = false
    }

    onMounted(load)

    defineExpose({
        getSelectionFields,
        init,
        pass
    })
</script>

<template>
    <GlobalDialog v-model="visible" :title="title" width="700px">
        <ElForm inline :model="query" @submit.prevent>
            <ElFormItem label="字段名">
                <ElInput v-model="query.name" placeholder="请输入" clearable/>
            </ElFormItem>
            <ElFormItem>
                <ElButton type="primary" native-type="submit" @click="filter">
                    查询
                </ElButton>
                <ElButton @click="resetFilter">
                    重置
                </ElButton>
                <el-popover placement="bottom" :width="1000" trigger="click">
                    <template #reference>
                        <el-button>
                            查看已选择
                        </el-button>
                    </template>
                    <div v-if="selections.length === 0" class="text-center">
                        数据为空
                    </div>
                    <div class="h-400px overflow-auto flex flex-wrap gap-2">
                        <el-tag v-for="tag in selections" :key="tag.key" closable @close="cloneSelectionRow(tag)">
                            {{ tag.name }}
                        </el-tag>
                    </div>
                </el-popover>
            </ElFormItem>
        </ElForm>

        <div v-loading="dataLoading" style="height: 400px">
            <el-auto-resizer>
                <template #default="{ height, width }">
                    <el-table-v2 :columns="columns" :data="list" :width="width" :height="height" fixed/>
                </template>
            </el-auto-resizer>
        </div>

        <ElForm ref="formRef" class="mt-20px" inline :rules="rules" :model="form" @submit.prevent>
            <ElFormItem prop="fileName" label="导出文件名">
                <ElInput v-model="form.fileName" class="!w-350px" placeholder="请输入" clearable/>
            </ElFormItem>
        </ElForm>
        <template #footer>
      <span class="dialog-footer">
        <el-button :loading="loading" @click="visible = false">取消</el-button>
        <el-button type="primary" :loading="loading" @click="handleSubmit">确定</el-button>
      </span>
        </template>
    </GlobalDialog>
</template>
