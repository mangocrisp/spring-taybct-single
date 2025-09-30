package io.github.mangocrisp.spring.taybct.module.lf.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Design;
import io.github.mangocrisp.spring.taybct.module.lf.domain.DesignPermissions;
import io.github.mangocrisp.spring.taybct.module.lf.mapper.DesignMapper;
import io.github.mangocrisp.spring.taybct.module.lf.service.IDesignPermissionsService;
import io.github.mangocrisp.spring.taybct.module.lf.service.IDesignService;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.BaseServiceImpl;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author admin
 * <br>description 针对表【lf_design(流程图设计)】的数据库操作Service实现
 * @since 2023-04-18 17:11:59
 */
public class DesignServiceImpl extends BaseServiceImpl<DesignMapper, Design>
        implements IDesignService {

    @Autowired(required = false)
    protected IDesignPermissionsService designPermissionsService;

    @Setter
    @Getter
    private String field = "id,create_user,create_time,update_user,update_time,is_deleted,name,status,description,type,icon,last_version";

    @Override
    public <E extends IPage<Design>> E page(E page, Wrapper<Design> queryWrapper) {
        ((QueryWrapper<Design>) queryWrapper)
                .select(field.split(","))
                // 这里查询的时候按用户过滤
                .exists(String.format("select 1 from lf_design_permissions ldp where ldp.design_id = lf_design.id and (user_id = '%s' or user_id is null)"
                        , securityUtil.getLoginUser().getUserId()));
        return super.page(page, queryWrapper);
    }

    @Override
    public List<Design> list(Wrapper<Design> queryWrapper) {
        ((QueryWrapper<Design>) queryWrapper)
                .select(field.split(","))
                // 这里查询的时候按用户过滤
                .exists(String.format("select 1 from lf_design_permissions ldp where ldp.design_id = lf_design.id and (user_id = '%s' or user_id is null)"
                        , securityUtil.getLoginUser().getUserId()));
        return super.list(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Design entity) {
        boolean save = super.save(entity);
        if (save) {
            // 保存完默认给用户所有权限
            DesignPermissions permissions = new DesignPermissions();
            permissions.setDesignId(entity.getId());
            permissions.setUserId(securityUtil.getLoginUser().getUserId());
            if (!designPermissionsService.save(permissions)) {
                throw new BaseException("权限保存失败！");
            }
        }
        return save;
    }

}
