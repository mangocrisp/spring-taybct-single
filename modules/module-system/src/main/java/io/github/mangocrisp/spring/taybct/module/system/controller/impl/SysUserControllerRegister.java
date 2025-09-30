package io.github.mangocrisp.spring.taybct.module.system.controller.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUser;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUserOnline;
import io.github.mangocrisp.spring.taybct.api.system.dto.SysUserSafeIn;
import io.github.mangocrisp.spring.taybct.api.system.vo.UserInfoVO;
import io.github.mangocrisp.spring.taybct.module.system.controller.ISysUserController;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserOnlineService;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiLog;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.SafeConvert;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ISecurityUtil;
import io.github.mangocrisp.spring.taybct.tool.core.constant.OperateType;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 系统用户相关接口
 *
 * @author xijieyin <br> 2022/8/5 21:32
 * @see SysUser
 * @see ISysUserService
 * @since 1.0.0
 */
public class SysUserControllerRegister implements ISysUserController {

    @Autowired(required = false)
    protected ISysUserOnlineService sysUserOnlineService;
    @Autowired(required = false)
    protected ISecurityUtil securityUtil;
    @Autowired(required = false)
    protected ISysUserService sysUserService;

    @Override
    public ISysUserService getBaseService() {
        return this.sysUserService;
    }

    public ISecurityUtil getSecurityUtil() {
        return this.securityUtil;
    }

    public ISysUserOnlineService getSysUserOnlineService() {
        return this.sysUserOnlineService;
    }

    /**
     * 我的信息<br>
     * 从请求头里面获取到用户 token 然后解析成用户，然后去数据查询用户信息返回
     *
     * @return {@code R<UserInfoVO> }
     * @author xijieyin <br> 2022/8/5 21:33
     * @since 1.0.0
     */
    @Override
    public R<UserInfoVO> myInfo() {
        return R.data(getBaseService().getUserInfoByUserId(getSecurityUtil().getLoginUser().getUserId()));
    }

    /**
     * 根据 id 修改个人信息 <br>
     * 这里只允许修改自己的信息，不能前端传入别人的 id 来修改个人信息
     *
     * @param domain 请求实体，{key:value}，实体需要有主键 id
     * @return {@code R<? extends T>}
     * @author xijieyin <br> 2022/8/4 18:39
     * @since 2.1.2
     */
    @WebLog
    @ApiLog(title = "根据 id 修改个人信息", description = "根据 id 修改个人信息", type = OperateType.UPDATE)
    @SafeConvert(key = "domain", safeIn = SysUserSafeIn.class, ignoreIn = {"id", "username", "password", "openid", "phone", "email"})
    @Override
    public R<SysUser> updateMyInfo(@Valid @NotNull @RequestBody SysUser domain) {
        domain.setId(getSecurityUtil().getLoginUser().getUserId());
        return getBaseService().updateMyInfo(domain) ? R.data(domain) : R.fail(String.format("更新%s失败！", getResource()));
    }

    @WebLog
    @ApiLog(title = "变更个人修改密码的时间", description = "变更个人修改密码的时间", type = OperateType.UPDATE)
    @Override
    public R<?> updatePasswdTime() {
        SysUser entity = new SysUser();
        entity.setId(getSecurityUtil().getLoginUser().getUserId());
        entity.setPasswdTime(LocalDateTime.now());
        return getBaseService().customizeUpdateById(entity) ? R.ok() : R.fail("更新失败");
    }

    /**
     * 登录成功记录用户登录
     *
     * @param dto 用户登录成功后的信息 token 等
     */
    @Override
    public R<?> login(@RequestBody JSONObject dto) {
        return getSysUserOnlineService().login(dto, true) ? R.ok("操作成功！") : R.fail("操作失败！");
    }

    /**
     * 登出操作，需要清空登录状态记录
     *
     * @param dto 用户登录成功后的信息 token 等
     */
    @Override
    public R<?> logoff(@RequestBody JSONObject dto) {
        return getSysUserOnlineService().logoff(dto, "账号已经登出！") ? R.ok() : R.fail();
    }

    /**
     * 获取在线列表
     *
     * @return {@code R<List<SysUserOnline>>}
     * @author xijieyin <br> 2022/8/5 21:40
     * @since 1.0.0
     */
    @Override
    public R<List<SysUserOnline>> onlineLinst() {
        return R.data(getSysUserOnlineService().onlineList());
    }

    /**
     * 获取在线分页
     *
     * @param sqlQueryParams {@literal sql 查询参数}
     * @return {@code R<IPage<SysUserOnline>>}
     * @author xijieyin <br> 2022/8/5 21:40
     * @since 1.0.0
     */
    @Override
    public R<IPage<SysUserOnline>> onlinePage(@RequestParam(required = false) Map<String, Object> sqlQueryParams) {
        return R.data(getSysUserOnlineService().onlinePage(sqlQueryParams));
    }

    /**
     * 强制登出
     *
     * @param clientId 客户端 id
     * @param username 用户名
     * @return R
     * @author xijieyin <br> 2022/8/5 21:41
     * @since 1.0.0
     */
    @Override
    public R<?> force(@PathVariable String clientId, @PathVariable String username) {
        return getSysUserOnlineService().force(clientId, username) ? R.ok() : R.fail();
    }

    /**
     * 强制登出（批量）
     *
     * @param jti token jti 数组
     * @return R
     * @author xijieyin <br> 2022/8/5 21:41
     * @since 1.0.0
     */
    @Override
    public R<?> forceAll(@RequestBody String[] jti, @RequestParam(required = false, defaultValue = "被强制登出！") String message) {
        return getSysUserOnlineService().force(jti, message) ? R.ok() : R.fail();
    }


}
