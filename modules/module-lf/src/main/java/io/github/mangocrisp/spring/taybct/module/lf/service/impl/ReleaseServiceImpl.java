package io.github.mangocrisp.spring.taybct.module.lf.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Design;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Release;
import io.github.mangocrisp.spring.taybct.module.lf.dto.ReleasePublishDTO;
import io.github.mangocrisp.spring.taybct.module.lf.dto.ReleaseQueryDTO;
import io.github.mangocrisp.spring.taybct.module.lf.enums.DesignPermissionsType;
import io.github.mangocrisp.spring.taybct.module.lf.mapper.DesignMapper;
import io.github.mangocrisp.spring.taybct.module.lf.mapper.ReleaseMapper;
import io.github.mangocrisp.spring.taybct.module.lf.mapper.ReleasePermissionsMapper;
import io.github.mangocrisp.spring.taybct.module.lf.service.IDesignPermissionsService;
import io.github.mangocrisp.spring.taybct.module.lf.service.IReleaseService;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.BaseServiceImpl;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.request.SqlQueryParams;
import io.github.mangocrisp.spring.taybct.tool.core.util.MyBatisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * @author admin
 * <br>description 针对表【lf_release(流程发布表)】的数据库操作Service实现
 * @since 2023-07-03 11:31:36
 */
public class ReleaseServiceImpl extends BaseServiceImpl<ReleaseMapper, Release>
        implements IReleaseService {

    @Autowired(required = false)
    protected DesignMapper designMapper;

    @Autowired(required = false)
    protected IDesignPermissionsService designPermissionsService;

    @Autowired(required = false)
    protected ReleasePermissionsMapper releasePermissionsMapper;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean publish(ReleasePublishDTO dto) {
        if (designMapper.exists(Wrappers.<Design>lambdaQuery()
                .isNull(Design::getData)
                .eq(Design::getId, dto.getDesignId()))) {
            throw new BaseException("流程设计数据为空！无法发布！");
        }
        if (!designPermissionsService.checkPermission(Collections.singleton(dto.getDesignId()), new DesignPermissionsType[]{
                DesignPermissionsType.PUBLISH
        })) {
            throw new BaseException("没有发布权限！");
        }
        boolean b = getBaseMapper().publish(Collections.singleton(dto)) > 0;
        if (b) {
            // 发布完，要把权限从设计图复制一份到发行
            if (!(releasePermissionsMapper.publishPermission(IdWorker.getId(), dto.getId(), dto.getDesignId()) > 0)) {
                throw new BaseException("权限保存失败！");
            }
        }
        // 更新最后版本号
        designMapper.update(Wrappers.<Design>lambdaUpdate()
                .set(Design::getLastVersion, dto.getVersion())
                .eq(Design::getId, dto.getDesignId()));
        return b;
    }

    @Override
    public IPage<? extends Release> publishList(ReleaseQueryDTO dto, SqlQueryParams sqlQueryParams) {
        return getBaseMapper().page(MyBatisUtil.genPage(sqlQueryParams), dto);
    }

    @Override
    public Release detail(Long id) {
        return getBaseMapper().selectByPrimaryKey(id);
    }

}




