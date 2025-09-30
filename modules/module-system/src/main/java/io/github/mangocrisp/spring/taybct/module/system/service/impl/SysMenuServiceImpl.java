package io.github.mangocrisp.spring.taybct.module.system.service.impl;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysMenu;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysRoleMenu;
import io.github.mangocrisp.spring.taybct.api.system.dto.SysMenuQueryDTO;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysMenuMapper;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysRoleMenuMapper;
import io.github.mangocrisp.spring.taybct.api.system.vo.RouterPerm;
import io.github.mangocrisp.spring.taybct.api.system.vo.RouterVO;
import io.github.mangocrisp.spring.taybct.api.system.vo.SysMenuVO;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysMenuService;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.BaseServiceImpl;
import io.github.mangocrisp.spring.taybct.tool.core.constant.ISysParamsObtainService;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.result.ResultCode;
import io.github.mangocrisp.spring.taybct.tool.core.util.MyBatisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author xijieyin
 */
@Slf4j
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu>
        implements ISysMenuService {

    @Autowired(required = false)
    protected SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired(required = false)
    protected ISysParamsObtainService sysParamsObtainService;

    @Override
    public List<SysMenuVO> list(Map<String, Object> sqlQueryParams) {
        String pageOrder = MyBatisUtil.getPageOrder(sqlQueryParams);
        Set<String> authorities = checkAuthorities();
        SysMenuQueryDTO dto = JSONObject.parseObject(JSONObject.toJSONString(sqlQueryParams), SysMenuQueryDTO.class);
        // 编辑的时候回显，需要反查这个树回来
        Long permCheckId = Convert.convert(Long.class, dto.getPermCheckId(), -1L);
        SysMenu sysMenu;
        // 所有的父级的 id ，用于做过滤
        Set<Long> parentIds;
        JSONObject params = JSONObject.parseObject(JSONObject.toJSONString(dto));
        params.remove("permCheckId");
        params.remove("expansion");
        if (permCheckId != null && permCheckId > 0 && (sysMenu = getBaseMapper().selectById(permCheckId)) != null) {
            // 根据选中的父级菜单的 id 获取到父级菜单
            parentIds = getParentTree(sysMenu.getParentId(), Long.valueOf(sysParamsObtainService.get(CacheConstants.Params.MENU_LAYOUT)));
            parentIds.add(sysMenu.getParentId());
        } else {
            parentIds = Collections.emptySet();
        }
        return getBaseMapper().listByRoleCode(MyBatisUtil.humpToUnderline(params), pageOrder, authorities, checkRoot())
                .stream()
                // 只有和
                .peek(m -> m.setChecked(parentIds.contains(m.getId())))
                .collect(Collectors.toList());
    }

    /**
     * 获取到所有的父级（树）
     *
     * @param checkParentId 选中的父级的 id
     * @param topParentId   顶级父级 id 默认0
     */
    private Set<Long> getParentTree(Long checkParentId, Long topParentId) {
        Set<Long> all = new LinkedHashSet<>();
        SysMenu sysMenu = getBaseMapper().selectOne(Wrappers.<SysMenu>lambdaQuery()
                .select(SysMenu::getParentId)
                .eq(SysMenu::getId, checkParentId));
        if (sysMenu != null) {
            all.add(sysMenu.getParentId());
        }
        if (!checkParentId.equals(topParentId)) {
            // 如果不是顶级就继续往上找
            all.addAll(getParentTree(sysMenu.getParentId(), topParentId));
        }
        // 如果已经是顶级就直接返回所有
        return all;
    }


    @Override
    public List<RouterVO> loadRouterByRoleCode() {
        Set<String> authorities = checkAuthorities();
        return getBaseMapper().loadRouterByRoleCode(authorities, checkRoot());
    }

    @Override
    public Set<RouterPerm> loadPermByRoleCode() {
        Set<String> authorities = checkAuthorities();
        return getBaseMapper().loadPermByRoleCode(authorities, checkRoot());
    }

    @Override
    public boolean removeById(Serializable id) {
        return removeById(id, serializable -> {
            // 判断是否有子级菜单
            if (getBaseMapper().selectCount(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getParentId, serializable)) > 0) {
                throw new BaseException("该菜单下还有其他菜单，不允许直接删除父级菜单！");
            }
            // 判断是否还有关联角色
            if (sysRoleMenuMapper.selectCount(Wrappers.<SysRoleMenu>lambdaQuery()
                    .eq(SysRoleMenu::getMenuId, serializable)
                    .exists("select id from sys_role where id = sys_role_menu.role_id and is_deleted = 0")) > 0) {
                throw new BaseException("有角色正在使用当前菜单，不允许直接删除！");
            }
            //TODO 判断是否有关联用户，需要清除 token，这个要去到角色管理去做
            //
            return true;
        });
    }

    /**
     * 删除菜单
     *
     * @param id        菜单 id
     * @param predicate 断言判断是否具备删除菜单的条件
     */
    public boolean removeById(Serializable id, Predicate<Serializable> predicate) {
        if (predicate.test(id)) {
            return super.removeById(id);
        }
        throw new BaseException(ResultCode.ERROR, "删除菜单失败！");
    }
}




