import useAxios from '@base-lib/hooks/core/useAxios'
import { GetExportSelectedField } from '@/types/global'

/**异步请求 */
const request = useAxios()
/**模块路径*/
const modulePath = '';
/**请求对接的接口 */
const URL = modulePath + '/v1/${tableClass.shortClassName?uncap_first}';

export default class ${tableClass.shortClassName}Api {

/**
* 参数管理列表(分页)
* @param params 查询参数
* @returns 分页
*/
static page: ${tableClass.shortClassName}Type.Page = (params) => {
return request.post({
url: `${r'$'}{URL}/page`,
data: params,
params: {
pageNum: params?.pageNum || 1,
pageSize: params?.pageSize || 100,
pageOrder: 'update_time desc'
}
})
}

/**
* 查询总数
* @param params 查询参数
* @returns 总数
*/
static total: ${tableClass.shortClassName}Type.Total = (params: any) => {
return request.post({
url: `${r'$'}{URL}/total`,
data: params
})
}

/**
* 查询单个详情
* @param id 查询主键
* @returns 数据详情
*/
static detail: ${tableClass.shortClassName}Type.Get = (id: string) => {
return request.get({
url: `${r'$'}{URL}/${r'$'}{id}`
})
}

/**
* 新增
* @param data 新增数据
* @returns 新增后的数据
*/
static add: ${tableClass.shortClassName}Type.Add = (data) => {
return request.post({
url: `${r'$'}{URL}`,
data
})
}

/**
* 更新
* @param data 更新的数据
* @returns 更新后的数据
*/
static update: ${tableClass.shortClassName}Type.Update = (data) => {
return request.put({
url: `${r'$'}{URL}`,
data
})
}

/**
* 删除单个
* @param id 需要删除的数据的主键 id
* @returns 删除的结果
*/
static remove = (id: string) => {
return request.delete({
url: `${r'$'}{URL}/${r'$'}{id}`
})
}

/**
* 批量删除
* @param ids 批量删除的 id 集合
* @returns 批量删除的结果
*/
static batchRemove = (ids: string[]) => {
return request.delete({
url: `${r'$'}{URL}/batch`,
data: ids
})
}

/**
* 导出数据
* @param params 需要导出的数据查询条件
* @returns
*/
static exportExcel = (params) => {
return request.post({
url: `${r'$'}{URL}/exportSelectedField`,
data: params,
responseType: 'blob'
})
}

// 获取 excel 导出数据可选的字段
static getExportSelectedField: GetExportSelectedField = (params) => {
return request.get({
url: `${r'$'}{URL}/exportSelectedField`,
params
})
}

/**
* 获取导入数据的模板
* @returns 模板(blob)
*/
static getImportExcelTemplate = () => {
return request.get({
url: `${r'$'}{URL}/template`,
responseType: 'blob'
})
}

/**
* 导入数据
* @param data 数据
* @returns 是否导入成功
*/
static importExcel = (data: any) => {
return request.post({
url: `${r'$'}{URL}/imp`,
data,
headers: {
'Content-Type': 'multipart/form-data'
}
})
}

}
