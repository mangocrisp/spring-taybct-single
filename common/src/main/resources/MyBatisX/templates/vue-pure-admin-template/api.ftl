import useAxios from "@/hooks/core/useAxios";

/**异步请求 */
const request = useAxios();

/**模块路径*/
const modulePath = "/";

/**请求对接的接口 */
const URL = `${r'$'}{modulePath}/v1/${tableClass.shortClassName?uncap_first}`;

export default class ${tableClass.shortClassName}Api {
/**
* 参数管理列表(分页)
* @param params 查询参数
* @returns 分页
*/
static page = (
queryBody: ${tableClass.shortClassName}Type.QueryBody,
pageParams: BaseApi.SqlPageParams
): HttpReturnPage
<${tableClass.shortClassName}Type.Domain> => {
    return request.post({
    url: `${r'$'}{URL}/page`,
    data: queryBody,
    params: {
    ...pageParams,
    pageOrder: "update_time desc"
    }
    });
    };

    /**
    * 查询总数
    * @param params 查询参数
    * @returns 总数
    */
    static total = (queryBody: ${tableClass.shortClassName}Type.QueryBody): HttpReturn
    <number> => {
        return request.post({
        url: `${r'$'}{URL}/total`,
        data: queryBody
        });
        };

        /**
        * 查询单个详情
        * @param id 查询主键
        * @returns 数据详情
        */
        static detail = (id: string): HttpReturn
        <${tableClass.shortClassName}Type.Domain> => {
            return request.get({
            url: `${r'$'}{URL}/${r'$'}{id}`
            });
            };

            /**
            * 新增
            * @param data 新增数据
            * @returns 新增后的数据
            */
            static add = (
            data: ${tableClass.shortClassName}Type.AddDTO
            ): HttpReturn
            <${tableClass.shortClassName}Type.Domain> => {
                return request.post({
                url: `${r'$'}{URL}`,
                data
                });
                };

                /**
                * 更新
                * @param data 更新的数据
                * @returns 更新后的数据
                */
                static update = (
                data: ${tableClass.shortClassName}Type.UpdateDTO
                ): HttpReturn
                <${tableClass.shortClassName}Type.Domain> => {
                    return request.put({
                    url: `${r'$'}{URL}`,
                    data
                    });
                    };

                    /**
                    * 删除单个
                    * @param id 需要删除的数据的主键 id
                    * @returns 删除的结果
                    */
                    static remove = (id: string): HttpReturn
                    <any> => {
                        return request.delete({
                        url: `${r'$'}{URL}/${r'$'}{id}`
                        });
                        };

                        /**
                        * 批量删除
                        * @param ids 批量删除的 id 集合
                        * @returns 批量删除的结果
                        */
                        static batchRemove = (ids: string[]): HttpReturn
                        <any> => {
                            return request.delete({
                            url: `${r'$'}{URL}/batch`,
                            data: ids
                            });
                            };

                            /**
                            * 导出数据（字段可选）
                            * @param params 需要导出的数据查询条件
                            * @returns
                            */
                            static exportExcelSelectedField = (params: any): any => {
                            return request.post({
                            url: `${r'$'}{URL}/exportSelectedField`,
                            data: params,
                            responseType: "blob"
                            });
                            };

                            // 获取 excel 导出数据可选的字段
                            static getExportSelectedField = (
                            params: any
                            ): HttpReturn
                            <SelectionExportExcelType
                            []> => {
                            return request.get({
                            url: `${r'$'}{URL}/exportSelectedField`,
                            params
                            });
                            };

                            /**
                            * 导出数据（全部字段）
                            * @param params 需要导出的数据查询条件
                            * @returns
                            */
                            static exportExcel = (
                            queryBody: ${tableClass.shortClassName}Type.QueryBody,
                            pageParams: BaseApi.SqlPageParams
                            ): any => {
                            return request.post({
                            url: `${r'$'}{URL}/exp`,
                            data: queryBody,
                            params: {
                            ...pageParams,
                            pageOrder: "update_time desc"
                            },
                            responseType: "blob"
                            });
                            };

                            /**
                            * 获取导入数据的模板
                            * @returns 模板(blob)
                            */
                            static getImportExcelTemplate = (): any => {
                            return request.get({
                            url: `${r'$'}{URL}/template`,
                            responseType: "blob"
                            });
                            };

                            /**
                            * 导入数据
                            * @param data 数据
                            * @returns 是否导入成功
                            */
                            static importExcel = (data: any): HttpReturn
                            <${tableClass.shortClassName}Type.Domain
                            []> => {
                            return request.post({
                            url: `${r'$'}{URL}/imp`,
                            data,
                            headers: {
                            "Content-Type": "multipart/form-data"
                            }
                            });
                            };
                            }
