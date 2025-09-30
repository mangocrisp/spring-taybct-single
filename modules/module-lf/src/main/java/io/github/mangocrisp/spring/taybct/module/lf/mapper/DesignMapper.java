package io.github.mangocrisp.spring.taybct.module.lf.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Design;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.DataScope;
import io.github.mangocrisp.spring.taybct.tool.core.enums.DataScopeGetNotDealType;
import io.github.mangocrisp.spring.taybct.tool.core.enums.DataScopeType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author admin
 * <br>description 针对表【lf_design(流程图设计)】的数据库操作Mapper
 * @see Design
 * @since 2023-04-18 17:11:59
 */
public interface DesignMapper extends BaseMapper<Design> {

    @Override
    @DataScope(alias = "lf_design"
            , field = "id"
            , type = DataScopeType.MULTI
            , multiTable = "lf_design_permissions"
            , multiFiled = "design_id"
            , multiDeptId = "dept_id"
            , notExistDealType = DataScopeGetNotDealType.ALLOW
    )
    default <P extends IPage<Design>> P selectPage(P page, @Param(Constants.WRAPPER) Wrapper<Design> queryWrapper) {
        page.setRecords(selectList(page, queryWrapper));
        return page;
    }

    @DataScope(alias = "lf_design"
            , field = "id"
            , type = DataScopeType.MULTI
            , multiTable = "lf_design_permissions"
            , multiFiled = "design_id"
            , multiDeptId = "dept_id"
            , notExistDealType = DataScopeGetNotDealType.ALLOW
    )
    @Override
    List<Design> selectList(@Param(Constants.WRAPPER) Wrapper<Design> queryWrapper);
}
