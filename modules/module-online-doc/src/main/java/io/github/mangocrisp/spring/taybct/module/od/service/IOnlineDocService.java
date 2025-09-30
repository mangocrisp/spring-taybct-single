package io.github.mangocrisp.spring.taybct.module.od.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.mangocrisp.spring.taybct.module.od.domain.OnlineDoc;
import io.github.mangocrisp.spring.taybct.module.od.dto.add.OnlineDocAddDTO;
import io.github.mangocrisp.spring.taybct.module.od.dto.update.OnlineDocUpdateDTO;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ModelConvertible;
import io.github.mangocrisp.spring.taybct.tool.core.bean.UpdateModel;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.support.SqlPageParams;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 针对表【t_online_doc(在线文档)】的数据库操作Service
 * </pre>
 *
 * @author xijieyin
 * @since 2025/9/20 03:28
 */
public interface IOnlineDocService extends IService<OnlineDoc> {

    /**
     * onlyoffice 回调接口
     *
     * @param request  请求
     * @param response 响应
     * @return 结果
     */
    String callback(HttpServletRequest request, HttpServletResponse response);

    /**
     * 保存文档信息
     *
     * @param dto  文档信息
     * @param file 上传的文件
     * @return 结果
     */
    boolean save(OnlineDocAddDTO dto, MultipartFile file);

    /**
     * 修改文档信息
     *
     * @param dto 文档信息
     * @return 结果
     */
    boolean update(OnlineDocUpdateDTO dto);

    /**
     * 删除文档，会同时删除文档相关的 excel 导入数据信息、上传的文件、共享范围等相关信息
     *
     * @param id 文档 id
     * @return 删除结果
     */
    boolean remove(Long id);

    /**
     * 检查操作权限
     *
     * @param id 文档 id
     */
    void checkOperatePermission(Long id);

    /**
     * 查询 Map 列表
     *
     * @param fields        指定需要查询的字段
     * @param params        查询条件
     * @param sqlPageParams 分页参数防止查询全表
     * @return 返回列表数据
     */
    List<Map<String, Object>> listMap(List<String> fields, JSONObject params, SqlPageParams sqlPageParams);

    /**
     * 根据条件更新数据
     *
     * @param model 条件
     * @return 是否更新成功
     */
    <UM extends ModelConvertible<? extends OnlineDoc>, QM extends ModelConvertible<? extends OnlineDoc>> boolean update(UpdateModel<OnlineDoc, UM, QM> model);

    /**
     * 查询总数
     *
     * @param params 查询条件
     * @param <E>    结果类型
     * @return 分页结果
     */
    <E extends OnlineDoc> long total(JSONObject params);

    /**
     * 查询分页
     *
     * @param params        查询条件
     * @param sqlPageParams 分页参数
     * @param <E>           结果类型
     * @return 分页结果
     */
    <E extends OnlineDoc> IPage<E> page(JSONObject params, SqlPageParams sqlPageParams);

    /**
     * 查询列表
     *
     * @param params        查询条件
     * @param sqlPageParams 分页参数防止查询全表
     * @param <E>           返回对象类型
     * @return 返回列表数据
     */
    <E extends OnlineDoc> List<E> list(JSONObject params, SqlPageParams sqlPageParams);

    /**
     * 查询详情
     *
     * @param params 查询条件
     * @param <E>    结果类型
     * @return 详情
     */
    <E extends OnlineDoc> E detail(JSONObject params);
}
