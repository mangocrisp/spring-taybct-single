package io.github.mangocrisp.spring.taybct.admin.file.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.mangocrisp.spring.taybct.admin.file.domain.SysFile;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ModelConvertible;
import io.github.mangocrisp.spring.taybct.tool.core.bean.UpdateModel;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.support.SqlPageParams;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 * 针对表【sys_file(文件管理)】的数据库操作Service
 * </pre>
 *
 * @author 24154
 * @see SysFile
 * @since 2024-09-01 21:20:40
 */
public interface ISysFileService extends IService<SysFile> {
    /**
     * 上传文件
     *
     * @param pathSet 上传后的文件路径
     * @return 记录保存文件
     */
    boolean upload(Set<String> pathSet);

    /**
     * 清理无效的文件
     *
     * @param params 参数
     * @return 是否成功
     */
    boolean cleanNotLinkedFile(Map<String, Object> params);

    /**
     * 链接
     *
     * @param list 修改内容
     * @return 是否操作成功
     */
    boolean link(List<SysFile> list);

    /**
     * 根据条件更新数据
     *
     * @param model 条件
     * @return 是否更新成功
     */
    <UM extends ModelConvertible<? extends SysFile>, QM extends ModelConvertible<? extends SysFile>> boolean update(UpdateModel<SysFile, UM, QM> model);

    /**
     * 查询分页
     *
     * @param params        查询条件
     * @param sqlPageParams 分页参数
     * @param <E>           结果类型
     * @return 分页结果
     */
    <E extends SysFile> IPage<E> page(JSONObject params, SqlPageParams sqlPageParams);

    /**
     * 查询列表
     *
     * @param params        查询条件
     * @param sqlPageParams 分页参数防止查询全表
     * @param <E>           返回对象类型
     * @return 返回列表数据
     */
    <E extends SysFile> List<E> list(JSONObject params, SqlPageParams sqlPageParams);

    /**
     * 查询详情
     *
     * @param params 查询条件
     * @param <E>    结果类型
     * @return 详情
     */
    <E extends SysFile> E detail(JSONObject params);

}
